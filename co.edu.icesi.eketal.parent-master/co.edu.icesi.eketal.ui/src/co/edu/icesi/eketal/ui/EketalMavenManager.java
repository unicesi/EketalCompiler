package co.edu.icesi.eketal.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import co.edu.icesi.eketal.ui.apis.artifacts.Metadata;

public class EketalMavenManager {

	public static void addEketalMavenRepositoryConfiguration(
			Document pomDocument, Node projectNode) {
		// Adding Aspose Cloud Maven Repository configuration
		Element repositories = pomDocument.createElement("repositories");
		projectNode.appendChild(repositories);
		Element repository = pomDocument.createElement("repository");
		repositories.appendChild(repository);
		Element id = pomDocument.createElement("id");
		id.appendChild(pomDocument.createTextNode("AsposeJavaAPI"));
		Element name = pomDocument.createElement("name");
		name.appendChild(pomDocument.createTextNode("Aspose Java API"));
		Element url = pomDocument.createElement("url");
		url.appendChild(pomDocument
				.createTextNode("http://maven.aspose.com/artifactory/simple/ext-release-local/"));
		repository.appendChild(id);
		repository.appendChild(name);
		repository.appendChild(url);
	}
	
	public static Document getXmlDocument(String mavenPomXmlfile)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document pomDocument = docBuilder.parse(mavenPomXmlfile);

		return pomDocument;
	}
	
	public static void addEketalMavenDependency(Document doc,
			Element dependenciesTag, Metadata dependency) {
		Element dependencyTag = doc.createElement("dependency");
		dependenciesTag.appendChild(dependencyTag);

		Element groupIdTag = doc.createElement("groupId");
		groupIdTag.appendChild(doc.createTextNode(dependency.getGroupId()));
		dependencyTag.appendChild(groupIdTag);

		Element artifactId = doc.createElement("artifactId");
		artifactId.appendChild(doc.createTextNode(dependency.getArtifactId()));
		dependencyTag.appendChild(artifactId);
		Element version = doc.createElement("version");
		version.appendChild(doc.createTextNode(dependency.getVersioning()
				.getLatest()));
		dependencyTag.appendChild(version);
		if (dependency.getClassifier() != null) {
			Element classifer = doc.createElement("classifier");
			classifer
					.appendChild(doc.createTextNode(dependency.getClassifier()));
			dependencyTag.appendChild(classifer);
		}
	}
	
	public static List<Metadata> getEketalProjectMavenDependencies() {
		return eketalProjectMavenDependencies;
	}

	public static void clearEketalProjectMavenDependencies() {
		eketalProjectMavenDependencies.clear();
	}

	private static List<Metadata> eketalProjectMavenDependencies = new ArrayList<Metadata>();

	public static void updatePOM(Document pomDocument,
			IProgressMonitor monitor, IProject project) throws CoreException,
			TransformerException {
		IFile pom = project.getFile(IMavenConstants.POM_FILE_NAME);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(pomDocument);

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();

		StreamResult result = new StreamResult(bytes);
		transformer.transform(source, result);

		pom.setContents(new ByteArrayInputStream(bytes.toByteArray()),
				IFile.FORCE, monitor);
	}
    public static boolean isInternetConnected() {
        try {
            InetAddress address = InetAddress.getByName(EketalConstants.INTERNTE_CONNNECTIVITY_PING_URL);
            if (address == null) {
                return false;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public static boolean retrieveAsposeMavenDependencies(IProgressMonitor monitor) {
        try {
            getEketalProjectMavenDependencies().clear();
            monitor.subTask("Retrieving Aspose Maven Dependencies latest artifacts...");

            for (EketalJavaAPI component : EketalMavenProject.getApiList().values()) {

                if (component.is_selected()) {
                	   monitor.subTask("Retrieving "+component.get_name()+" Maven artifact info...");

                    Metadata productMavenDependency = getProductMavenDependency(component.get_mavenRepositoryURL());
                    if (productMavenDependency != null) {
                        getEketalProjectMavenDependencies().add(productMavenDependency);
                    }
                }
                monitor.worked(1);
            }
        } catch (Exception rex) {
            return false;
        }
        if (!getEketalProjectMavenDependencies().isEmpty()) {

            return true;
        } else {
            return false;
        }
    }
    public static Metadata getProductMavenDependency(String productMavenRepositoryUrl) {
        final String mavenMetaDataFileName = "maven-metadata.xml";
        Metadata data = null;

        try {
            String productMavenInfo;
            productMavenInfo = readURLContents(productMavenRepositoryUrl + mavenMetaDataFileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(co.edu.icesi.eketal.ui.apis.artifacts.ObjectFactory.class);
            Unmarshaller unmarshaller;
            unmarshaller = jaxbContext.createUnmarshaller();
            EketalConstants.println(productMavenInfo);
            data = (Metadata) unmarshaller.unmarshal(new StreamSource(new StringReader(productMavenInfo)));

            String remoteArtifactFile = productMavenRepositoryUrl + data.getVersioning().getLatest() + "/" + data.getArtifactId() + "-" + data.getVersioning().getLatest();

            EketalConstants.println(remoteArtifactFile);
            if (!remoteFileExists(remoteArtifactFile + ".jar")) {
            	EketalConstants.println("Not Exists");
                data.setClassifier(getResolveSupportedJDK(remoteArtifactFile));
            } else {
            	EketalConstants.println("Exists");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            data = null;
        }
        return data;
    }
    public static String readURLContents(String Url) throws MalformedURLException, IOException {
        URL url = new URL(Url);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        String body = new String(baos.toByteArray(), encoding);
        return body;
    }
    public static boolean remoteFileExists(String URLName) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String getResolveSupportedJDK(String ProductURL) {
        String supportedJDKs[] = {"jdk17", "jdk16", "jdk15", "jdk14", "jdk18"};
        String classifier = null;
        for (String jdkCheck : supportedJDKs) {
        	EketalConstants.println(ProductURL + "-" + jdkCheck + ".jar");
            if (remoteFileExists(ProductURL + "-" + jdkCheck + ".jar")) {
            	EketalConstants.println("Exists");
                classifier = jdkCheck;
                break;
            } else {
            	EketalConstants.println("Not Exists");
            }
        }
        return classifier;
    }
	
	
}
