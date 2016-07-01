package co.edu.icesi.eketal.ui.wizard;

import java.util.List;
import java.util.Set;

import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

public class MavenProjectCreator extends EketalProjectCreator {
	
	@Inject
	private IOutputConfigurationProvider outputConfigurationProvider;
	
	@Override
	protected String getModelFolderName() {
		System.out.println("funciona");
		return "src/main/java";
	}
	
	@Override
	protected String[] getProjectNatures() {
		return new String[] {
			XtextProjectHelper.NATURE_ID, "org.eclipse.m2e.core.maven2Nature"
		};
	}
	
	@Override
	protected List<String> getAllFolders() {
		Set<OutputConfiguration> outputConfigurations = outputConfigurationProvider.getOutputConfigurations();
		String outputFolder = "src-gen";
		String testJava = "src/test/java";
		String resourceJava = "src/main/resources";
		String resourceTest = "src/test/resources";
		
		for (OutputConfiguration outputConfiguration : outputConfigurations) {
			if (IFileSystemAccess.DEFAULT_OUTPUT.equals(outputConfiguration.getName())) {
				outputFolder = outputConfiguration.getOutputDirectory();
				break;
			}
		}
		return ImmutableList.of(getModelFolderName(), outputFolder, testJava, resourceJava, resourceTest);
	}
	
}
