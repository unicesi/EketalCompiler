package co.edu.icesi.eketal.ui.wizard;

import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.ui.util.ProjectFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MavenProjectCreator extends EketalProjectCreator {
	
	@Override
	protected String getModelFolderName() {
		return "src/main/java";
	}
	
}
