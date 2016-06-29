package co.edu.icesi.eketal.ui.wizard;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.xtext.builder.nature.Messages;
import org.eclipse.xtext.builder.nature.NatureAddingEditorCallback;
import org.eclipse.xtext.builder.nature.ToggleXtextNatureAction;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.util.DontAskAgainDialogs;

import com.google.inject.Inject;

public class MiNaturaleza extends NatureAddingEditorCallback {
	
	@Inject
	private ToggleXtextNatureAction toggleNature;

	private @Inject DontAskAgainDialogs dialogs;
	
	@Override
	public void afterCreatePartControl(XtextEditor editor) {
		IResource resource = editor.getResource();		
		if (resource != null && !toggleNature.hasNature(resource.getProject()) && resource.getProject().isAccessible()
				&& !resource.getProject().isHidden()) {
			toggleNature.toggleNature(resource.getProject());
		}else if (resource != null && !toggleNature.hasNature(resource.getProject()) && resource.getProject().isAccessible()
				&& !resource.getProject().isHidden())
		
	}
	
}
