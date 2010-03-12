package org.bechclipse.review.wizard.export;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

public class ExportReviewWordWizard extends Wizard implements IExportWizard {

	public ExportReviewWordWizard() {
		super();
		setWindowTitle("Test");
		setNeedsProgressMonitor(true);
	}

	public void addPages() {
		ExportReviewPage export = new ExportReviewPage();
		addPage(export);		
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

}
