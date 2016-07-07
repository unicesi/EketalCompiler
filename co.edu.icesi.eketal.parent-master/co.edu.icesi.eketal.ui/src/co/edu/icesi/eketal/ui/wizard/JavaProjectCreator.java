package co.edu.icesi.eketal.ui.wizard;

import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.aspectj.ajdt.core.AspectJCore;
import org.eclipse.jdt.core.JavaCore;

import com.google.inject.Inject;

public class JavaProjectCreator extends EketalProjectCreator {
	
	@Inject
	private IOutputConfigurationProvider outputConfigurationProvider;
	
	@Override
	protected String getModelFolderName() {
		return "src/core";
	}
	
	@Override
	protected String[] getBuilders() {
		return new String[]{
			JavaCore.BUILDER_ID, XtextProjectHelper.BUILDER_ID, AspectJCore.BUILDER_ID
		};
	};
	
	@Override
	protected String[] getProjectNatures() {
		return new String[] {
			JavaCore.NATURE_ID, XtextProjectHelper.NATURE_ID, AspectJCore.NATURE_ID
		};
	}
	
}
