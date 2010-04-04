package org.bechclipse.review.action;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.wizard.reviewremark.NewReviewRemarkWizard;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class AddReviewRemarkFromGuide implements IObjectActionDelegate {

	private ITextSelection textSelection;
	private IStructuredSelection structuredSelection;
	private Shell shell;

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		ISelection selection = targetPart.getSite().getWorkbenchWindow().getSelectionService().getSelection();
		if (selection instanceof ITextSelection) {
			textSelection = (TextSelection) selection;
		}

		shell = targetPart.getSite().getShell();
	}

	@Override
	public void run(IAction action) {
		if (structuredSelection == null || textSelection == null) {
			return;
		}

		Object firstElement = structuredSelection.getFirstElement();

		if (firstElement != null && firstElement instanceof IFile) {
			IFile file = (IFile) firstElement;

			
			Review selectedReview = ReviewFacadeFactory.getFacade().getSelectedReview();
			if(selectedReview != null) {
			
				NewReviewRemarkWizard wizard = new NewReviewRemarkWizard(file, textSelection, selectedReview.getProgress());
				WizardDialog dialog = new WizardDialog(shell, wizard);
				dialog.open();
		}

		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		structuredSelection = (IStructuredSelection) selection;
	}
}
