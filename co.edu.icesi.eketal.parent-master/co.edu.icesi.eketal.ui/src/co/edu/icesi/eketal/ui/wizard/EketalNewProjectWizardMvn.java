package co.edu.icesi.eketal.ui.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.ui.util.ProjectFactory;
import org.eclipse.xtext.ui.wizard.IProjectCreator;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class EketalNewProjectWizardMvn extends EketalNewProjectWizard{

	@Inject
	public EketalNewProjectWizardMvn(IProjectCreator projectCreator) {
		super(projectCreator);
		ISelectionService selectionService = super.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		   if (selection instanceof IStructuredSelection) {
		      Object element = ((IStructuredSelection) selection).getFirstElement();
		      if (element instanceof IResource) {
			 IProject project = ((IResource) element).getProject();
			 try {
			   if (!project.hasNature("org.eclipse.m2e.core.maven2Nature")) {
			      IProjectDescription description = project.getDescription();
			      description.setNatureIds(new String[] { "org.eclipse.m2e.core.maven2Nature" });
			      project.setDescription(description, null);
			   }
			 } catch (CoreException e) {
			   e.printStackTrace();
		         }
		      }
		   }
	}

}
