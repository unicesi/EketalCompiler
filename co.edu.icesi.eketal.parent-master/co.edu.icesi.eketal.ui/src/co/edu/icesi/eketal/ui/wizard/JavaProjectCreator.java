package co.edu.icesi.eketal.ui.wizard;

import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.google.inject.Inject;

public class JavaProjectCreator extends EketalProjectCreator {
	
	@Inject
	private IOutputConfigurationProvider outputConfigurationProvider;
	
	@Override
	protected String getModelFolderName() {
		return "src/core";
	}
	
	@Override
	protected String[] getProjectNatures() {
		return new String[] {
			XtextProjectHelper.NATURE_ID, "org.eclipse.ajdt.ui.ajnature"
		};
	}
	
}
