package org.bechclipse.review.action;

import org.bechclipse.review.wizard.review.NewReviewWizard;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;

public class ReviewAddAction implements IViewActionDelegate {

	private Shell shell;

	// private IWorkbenchPart targetPart;
	// private ISelection selection;

	public ReviewAddAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// this.targetPart = targetPart;
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		// if (selection instanceof IStructuredSelection) {
		// IStructuredSelection sSelection = (IStructuredSelection) selection;
		NewReviewWizard wizard = new NewReviewWizard();

		// Create the wizard dialog
		WizardDialog dialog = new WizardDialog(shell, wizard);
		// Open the wizard dialog
		dialog.open();
		// }
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		//this.selection = selection;
		//action.setEnabled(true);
	}

	@Override
	public void init(IViewPart view) {

	}
}
