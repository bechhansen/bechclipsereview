package org.bechclipse.review.wizard.reviewremark;

import org.bechclipse.review.model.ReviewProgress;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ReviewRemarkCommentPage extends WizardPage {

	private Text description;
	private Text solution;
	private final ReviewProgress progress;

	public ReviewRemarkCommentPage(ReviewProgress progress) {
		super("Comment");
		this.progress = progress;
		setTitle("Review comment");
		setDescription("Comment on the code");
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
		Label label = new Label(container, SWT.NULL);
		label.setText("&Description:");

		description = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		description.setLayoutData(gd);
		description.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				// dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Solution:");

		solution = new Text(container, SWT.BORDER | SWT.MULTI);
		gd = new GridData(GridData.FILL_BOTH);
		solution.setLayoutData(gd);
		/*
		 * solution.addModifyListener(new ModifyListener() { public void
		 * modifyText(ModifyEvent e) { // dialogChanged(); } });
		 */
		// initialize();
		// dialogChanged();

		if (progress != null && progress.getCurrentCheckpoint() != null) {
			description.setText(progress.getCurrentCheckpoint().getProblem());
			solution.setText(progress.getCurrentCheckpoint().getSolution());
		}

		setControl(container);
	}

	public String getReviewDescription() {
		return description.getText();
	}

	public String getReviewSolution() {
		return solution.getText();
	}
}
