package org.bechclipse.review.wizard.reviewremark;

import org.bechclipse.review.model.ReviewRemarkScope;
import org.bechclipse.review.model.ReviewRemarkSeverityType;
import org.bechclipse.review.model.ReviewRemarkType;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewReviewRemarkInitPage extends WizardPage {

	private Text fileName;
	private Combo type;
	private final IFile file;
	private Combo severity;
	private Button classButton;
	private Button methodButton;
	private Button selectionButton;

	public NewReviewRemarkInitPage(IFile file) {
		super("New Review remark");
		this.file = file;
		setTitle("Code Review remark");
		setDescription("This wizard creates a new reviewremark for the active review");
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

		fileName = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileName.setEditable(false);

		fileName.setText(file.getProjectRelativePath().toString());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		fileName.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Type:");

		type = new Combo(container, SWT.BORDER | SWT.MULTI);

		type.add(ReviewRemarkType.CODESTYLE.toString());
		type.add(ReviewRemarkType.NULLPOINTER.toString());
		type.add(ReviewRemarkType.SECURITY.toString());
		type.add(ReviewRemarkType.STRUCTURE.toString());
		type.select(0);
		type.setFocus();

		gd = new GridData(GridData.FILL_HORIZONTAL);
		type.setLayoutData(gd);
		type.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Severity:");

		severity = new Combo(container, SWT.BORDER | SWT.MULTI);

		severity.add(ReviewRemarkSeverityType.HIGH.toString());
		severity.add(ReviewRemarkSeverityType.MEDIUM.toString());
		severity.add(ReviewRemarkSeverityType.LOW.toString());
		severity.select(0);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		severity.setLayoutData(gd);
		severity.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Scope:");

		Composite buttonContainer = new Composite(container, SWT.EMBEDDED);
		GridLayout buttonLayout = new GridLayout();
		buttonContainer.setLayout(buttonLayout);
		buttonLayout.numColumns = 1;
		buttonLayout.verticalSpacing = 9;

		classButton = new Button(buttonContainer, SWT.RADIO);
		classButton.setText(ReviewRemarkScope.CLASS.toString());
		classButton.setSelection(true);

		methodButton = new Button(buttonContainer, SWT.RADIO);
		methodButton.setText(ReviewRemarkScope.METHOD.toString());

		selectionButton = new Button(buttonContainer, SWT.RADIO);
		selectionButton.setText(ReviewRemarkScope.SELECTION.toString());

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

	public ReviewRemarkType getType() {
		int selectionIndex = type.getSelectionIndex();
		switch (selectionIndex) {
		case 0:
			return ReviewRemarkType.CODESTYLE;

		case 1:
			return ReviewRemarkType.NULLPOINTER;

		case 2:
			return ReviewRemarkType.SECURITY;

		case 3:
			return ReviewRemarkType.STRUCTURE;

		default:
			return null;
		}

	}

	public ReviewRemarkSeverityType getSeverity() {
		int selectionIndex = severity.getSelectionIndex();
		switch (selectionIndex) {
		case 0:
			return ReviewRemarkSeverityType.HIGH;

		case 1:
			return ReviewRemarkSeverityType.MEDIUM;

		case 2:
			return ReviewRemarkSeverityType.LOW;

		default:
			return null;
		}
	}

	public ReviewRemarkScope getScope() {

		if (classButton.getSelection()) {
			return ReviewRemarkScope.CLASS;
		} else if (methodButton.getSelection()) {
			return ReviewRemarkScope.METHOD;
		} else if (selectionButton.getSelection()) {
			return ReviewRemarkScope.SELECTION;
		}

		throw new UnsupportedOperationException();
	}
}
