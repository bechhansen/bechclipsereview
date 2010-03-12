package org.bechclipse.review.wizard.review;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public class NewReviewWizardReviewersPage extends WizardPage {

	private IStructuredSelection selection;

	public NewReviewWizardReviewersPage(IStructuredSelection selection) {
		super("Reviewers");
		setTitle("Code Reviewers");
		setDescription("Who is beeing revied and who will perform the review");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;		

		setControl(container);
	}

}
