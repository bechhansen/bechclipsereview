package org.bechclipse.review.wizard.reviewremarkstatus;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.ReviewRemark;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class ReviewRemarkStatusWizard extends Wizard implements INewWizard {

	private ReviewRemarkStatusInitPage initPage;
	private final ReviewRemark remark;

	public ReviewRemarkStatusWizard(ReviewRemark remark) {
		super();
		this.remark = remark;
		setNeedsProgressMonitor(true);
		setWindowTitle("Change review comment status");		
	}

	public void addPages() {
		initPage = new ReviewRemarkStatusInitPage(remark);
		addPage(initPage);
	}

	@Override
	public boolean performFinish() {
		try {
			ReviewFacadeFactory.getFacade().changeReviewRemarkStatus(remark, initPage.getStatus(), initPage.getComment());
			return true;
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
			return false;
		}
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// this.selection = selection;
	}

}
