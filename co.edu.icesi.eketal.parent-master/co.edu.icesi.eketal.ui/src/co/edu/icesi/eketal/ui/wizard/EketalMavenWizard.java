package co.edu.icesi.eketal.ui.wizard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.ui.util.FileOpener;
import org.eclipse.xtext.ui.wizard.IProjectCreator;
import org.eclipse.xtext.ui.wizard.IProjectInfo;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

import com.google.inject.Inject;

public class EketalMavenWizard extends EketalNewProjectWizard{

	private static final Logger logger = Logger.getLogger(EketalNewProjectWizard.class);
	
	@Inject
	private FileOpener fileOpener;
	
	@Inject
	public EketalMavenWizard(IProjectCreator projectCreator) {
		super(projectCreator);
	}
	
	@Override
	protected void doFinish(final IProjectInfo projectInfo, final IProgressMonitor monitor) {
		try {
			
			getCreator().setProjectInfo(projectInfo);
			getCreator().run(monitor);
			fileOpener.selectAndReveal(getCreator().getResult());
			fileOpener.openFileToEdit(getShell(), getCreator().getResult());
			
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		    IResource resource = root.findMember(new Path(projectInfo.getProjectName()));
		    if ((!resource.exists()) || ((resource.getType() & 0x2 | 0x4) == 0))
		    {
		    	logger.error("No existe el pom jaja", new Exception(){

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
		    		@Override
		    		public String getMessage() {
		    			return "No existe el pom jaja";
		    		}
		    	});
		    }
		    
		    IContainer container = (IContainer)resource;
		    final IFile file = container.getFile(new Path("pom.xml"));
		    if (file.exists())
		    {
		    	logger.error("No existe el pom jaja", new Exception(){

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
		    		@Override
		    		public String getMessage() {
		    			return "No existe el pom jaja";
		    		}
		    	});
		    }

		    File pom = file.getLocation().toFile();
		    System.out.println(pom);
//		      MavenPlugin.getProjectConversionManager().convert(resource.getProject(), model, monitor);
//
//		      MavenModelManager modelManager = MavenPlugin.getMavenModelManager();
//		      modelManager.createMavenModel(file, model);
		    
		    ByteArrayOutputStream buf = new ByteArrayOutputStream();
		    
		    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		      documentBuilderFactory.setNamespaceAware(false);
		      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

//		      public void writeModel(Model model, OutputStream out) throws CoreException {
//		    	    try {
//		    	      ((ModelWriter)lookup(ModelWriter.class)).write(out, null, model);
//		    	    } catch (IOException ex) {
//		    	      throw new CoreException(new Status(4, "org.eclipse.m2e.core", -1, 
//		    	        Messages.MavenImpl_error_write_pom, ex));
//		    	    }
//		    	  }
		      
		      
		      Document document = documentBuilder.parse(new ByteArrayInputStream(buf.toByteArray()));
		      Element documentElement = document.getDocumentElement();

		      NamedNodeMap attributes = documentElement.getAttributes();

		      if ((attributes == null) || (attributes.getNamedItem("xmlns") == null)) {
		        Attr attr = document.createAttribute("xmlns");
		        attr.setTextContent("http://maven.apache.org/POM/4.0.0");
		        documentElement.setAttributeNode(attr);
		      }

		      if ((attributes == null) || (attributes.getNamedItem("xmlns:xsi") == null)) {
		        Attr attr = document.createAttribute("xmlns:xsi");
		        attr.setTextContent("http://www.w3.org/2001/XMLSchema-instance");
		        documentElement.setAttributeNode(attr);
		      }

		      if ((attributes == null) || (attributes.getNamedItem("xsi:schemaLocation") == null)) {
		        Attr attr = document.createAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation");
		        attr.setTextContent("http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");
		        documentElement.setAttributeNode(attr);
		      }

		      TransformerFactory transfac = TransformerFactory.newInstance();
		      Transformer trans = transfac.newTransformer();
		      trans.setOutputProperty("omit-xml-declaration", "yes");

		      buf.reset();
		      trans.transform(new DOMSource(document), new StreamResult(buf));
		      
		    file.create(new ByteArrayInputStream(buf.toByteArray()), true, new NullProgressMonitor());

		      getShell().getDisplay().asyncExec(new Runnable() {
		        public void run() {
		          IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		          try {
		            IDE.openEditor(page, file, true);
		          } catch (PartInitException localPartInitException) {
		          }
		        }
		      });
			
			
		}
		catch (final InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}
		catch (final InterruptedException e) {
			// cancelled by user, ok
			return;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
