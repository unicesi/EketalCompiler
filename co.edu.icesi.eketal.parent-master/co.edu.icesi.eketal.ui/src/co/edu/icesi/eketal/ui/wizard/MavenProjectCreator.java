package co.edu.icesi.eketal.ui.wizard;

public class MavenProjectCreator extends EketalProjectCreator {
	
	@Override
	protected String getModelFolderName() {
		System.out.println("funciona");
		return "src/main/java";
	}
	
}
