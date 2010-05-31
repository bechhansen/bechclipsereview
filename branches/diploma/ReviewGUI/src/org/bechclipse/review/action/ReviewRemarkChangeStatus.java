package org.bechclipse.review.action;

import java.util.Iterator;

import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.wizard.reviewremarkstatus.ReviewRemarkStatusWizard;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ReviewRemarkChangeStatus implements IObjectActionDelegate {

	private ISelection selection;
	private Shell shell;

	public ReviewRemarkChangeStatus() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	@SuppressWarnings("unchecked")
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			for (Iterator iterator = sSelection.iterator(); iterator.hasNext();) {
				ReviewRemark remark = (ReviewRemark) iterator.next();
				ReviewRemarkStatusWizard wizard = new ReviewRemarkStatusWizard(remark);

				// Create the wizard dialog
				WizardDialog dialog = new WizardDialog(shell, wizard);
				// Open the wizard dialog
				dialog.open();

				break;
			}
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
