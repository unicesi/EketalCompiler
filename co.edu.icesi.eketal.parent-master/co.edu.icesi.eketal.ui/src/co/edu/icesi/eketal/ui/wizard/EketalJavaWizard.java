package co.edu.icesi.eketal.ui.wizard;

import org.apache.log4j.Logger;
import org.eclipse.xtext.ui.wizard.IProjectCreator;

import com.google.inject.Inject;

public class EketalJavaWizard extends EketalNewProjectWizard{

	private static final Logger logger = Logger.getLogger(EketalNewProjectWizard.class);
	
	@Inject
	public EketalJavaWizard(IProjectCreator projectCreator) {
		super(projectCreator);
	}

}
