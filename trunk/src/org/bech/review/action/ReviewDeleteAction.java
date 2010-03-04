package org.bech.review.action;

import org.bech.review.model.IReview;
import org.bech.review.view.contentprovider.ContentproviderFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ReviewDeleteAction implements IObjectActionDelegate {

	private Shell shell;
	private ISelection selection;

	public ReviewDeleteAction() {
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
	public void run(IAction action) {		
		if(selection instanceof IStructuredSelection) {			
			IStructuredSelection sSelection = (IStructuredSelection)selection;			
			ContentproviderFactory.getReviewContentProvider().deleteReview((IReview) sSelection.getFirstElement());
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;		
	}

}
