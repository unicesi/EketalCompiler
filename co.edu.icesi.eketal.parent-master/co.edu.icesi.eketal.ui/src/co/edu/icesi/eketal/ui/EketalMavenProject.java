package co.edu.icesi.eketal.ui;

import java.util.HashMap;
import java.util.Map;

public class EketalMavenProject {
	private static Map<String, EketalJavaAPI> apiList = new HashMap<String, EketalJavaAPI>();

	static {
		// ASPOSE_CELLS
		EketalJavaAPI asposeCells = new EketalJavaAPI();
		asposeCells.set_name(EketalConstants.ASPOSE_CELLS);
		asposeCells
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-cells/");
		getApiList().put(EketalConstants.ASPOSE_CELLS, asposeCells);

		// ASPOSE_WORDS
		EketalJavaAPI asposeWords = new EketalJavaAPI();
		asposeWords.set_name(EketalConstants.ASPOSE_WORDS);
		asposeWords
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-words/");
		getApiList().put(EketalConstants.ASPOSE_WORDS, asposeWords);

		// ASPOSE_PDF
		EketalJavaAPI asposePDF = new EketalJavaAPI();
		asposePDF.set_name(EketalConstants.ASPOSE_PDF);
		asposePDF
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-pdf/");
		getApiList().put(EketalConstants.ASPOSE_PDF, asposePDF);
		// ASPOSE_Slides
		EketalJavaAPI asposeSlides = new EketalJavaAPI();
		asposeSlides.set_name(EketalConstants.ASPOSE_SLIDES);
		asposeSlides
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-slides/");
		getApiList().put(EketalConstants.ASPOSE_SLIDES, asposeSlides);

		// ASPOSE_BarCode
		EketalJavaAPI asposeBarcode = new EketalJavaAPI();
		asposeBarcode.set_name(EketalConstants.ASPOSE_BARCODE);
		asposeBarcode
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-barcode/");
		getApiList().put(EketalConstants.ASPOSE_BARCODE, asposeBarcode);

		// ASPOSE_Tasks
		EketalJavaAPI asposeTasks = new EketalJavaAPI();
		asposeTasks.set_name(EketalConstants.ASPOSE_TASKS);
		asposeTasks
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-tasks/");
		getApiList().put(EketalConstants.ASPOSE_TASKS, asposeTasks);

		// ASPOSE_Email
		EketalJavaAPI asposeEmail = new EketalJavaAPI();
		asposeEmail.set_name(EketalConstants.ASPOSE_EMAIL);
		asposeEmail
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-email/");
		getApiList().put(EketalConstants.ASPOSE_EMAIL, asposeEmail);

		// ASPOSE_OCR
		EketalJavaAPI asposeOCR = new EketalJavaAPI();
		asposeOCR.set_name(EketalConstants.ASPOSE_OCR);
		asposeOCR
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-ocr/");
		getApiList().put(EketalConstants.ASPOSE_OCR, asposeOCR);

		// ASPOSE_Imaging
		EketalJavaAPI asposeImaging = new EketalJavaAPI();
		asposeImaging.set_name(EketalConstants.ASPOSE_IMAGING);
		asposeImaging
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-imaging/");
		getApiList().put(EketalConstants.ASPOSE_IMAGING, asposeImaging);

		// ASPOSE_Diagram
		EketalJavaAPI asposeDiagram = new EketalJavaAPI();
		asposeDiagram.set_name(EketalConstants.ASPOSE_DIAGRAM);
		asposeDiagram
				.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-diagram/");
		getApiList().put(EketalConstants.ASPOSE_DIAGRAM, asposeDiagram);
	}

	public static Map<String, EketalJavaAPI> getApiList() {
		return apiList;
	}

	public static void clearSelection() {
		for (EketalJavaAPI component : getApiList().values()) {
			component.set_selected(false);
		}

	}
}
