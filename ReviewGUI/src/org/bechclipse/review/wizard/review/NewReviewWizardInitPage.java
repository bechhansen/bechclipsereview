package org.bechclipse.review.wizard.review;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewReviewWizardInitPage extends WizardPage {

	Text nameText;
	Text descriptionText;
	private IStructuredSelection selection;
	
	public NewReviewWizardInitPage(IStructuredSelection selection) {
		super("NewReview");
		setTitle("Code Review");
		setDescription("This wizard creates a new Coderevew for the project");
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
		Label label = new Label(container, SWT.NULL);
		label.setText("&Name:");

		nameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		nameText.setLayoutData(gd);
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		
		label = new Label(container, SWT.NULL);
		label.setText("&Description:");

		descriptionText = new Text(container, SWT.BORDER | SWT.MULTI);
		gd = new GridData(GridData.FILL_BOTH);
		descriptionText.setLayoutData(gd);
		descriptionText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {		
		
	}

	private void dialogChanged() {
	}
	
	public String getName() {
		return nameText.getText();
	}
	
	public String getDescription() {
		return descriptionText.getText();
	}

}
