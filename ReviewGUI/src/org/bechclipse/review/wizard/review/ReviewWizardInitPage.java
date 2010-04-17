package org.bechclipse.review.wizard.review;

import org.bechclipse.review.model.Review;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ReviewWizardInitPage extends WizardPage {

	private Text nameText;
	private Text descriptionText;
	private Review review;

	public ReviewWizardInitPage() {
		super("CodeReviewInformation");
		setTitle("Code Review information");
		setDescription("Set name and description for the review");
	}

	public ReviewWizardInitPage(Review review) {
		this();
		this.review = review;
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
		label.setText("&Name:");

		nameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		nameText.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Description:");

		descriptionText = new Text(container, SWT.BORDER | SWT.MULTI);
		gd = new GridData(GridData.FILL_BOTH);
		descriptionText.setLayoutData(gd);

		if (review != null) {
			descriptionText.setText(review.getDescription());
			nameText.setText(review.getName());
		}

		setControl(container);
	}

	public String getName() {
		return nameText.getText();
	}

	public String getDescriptionText() {
		return descriptionText.getText();
	}
}
