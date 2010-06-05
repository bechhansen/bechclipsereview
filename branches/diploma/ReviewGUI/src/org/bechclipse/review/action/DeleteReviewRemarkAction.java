package org.bechclipse.review.action;

import java.util.Iterator;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.ReviewRemark;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class DeleteReviewRemarkAction implements IObjectActionDelegate {

	private ISelection selection;

	public DeleteReviewRemarkAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
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
				ReviewFacadeFactory.getFacade().deleteReviewRemark(remark);				
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
