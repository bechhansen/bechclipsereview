package org.bechclipse.review.wizard.reviewremark;

import org.bechclipse.review.model.ReviewChecklist;
import org.bechclipse.review.model.ReviewProgress;
import org.bechclipse.review.model.ReviewRemarkCategory;
import org.bechclipse.review.model.ReviewRemarkScope;
import org.bechclipse.review.model.ReviewRemarkSeverityType;
import org.bechclipse.review.model.ReviewRemarkType;
import org.bechclipse.review.model.checklist.Category;
import org.bechclipse.review.model.checklist.Scope;
import org.bechclipse.review.model.checklist.Severity;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ReviewRemarkInitPage extends WizardPage {

	private Text fileName;
	private Combo type;
	private Combo category;
	private final IFile file;
	private Combo severity;
	private Button classButton;
	private Button methodButton;
	private Button selectionButton;
	private Button generelButton;
	private final ReviewProgress progress;

	public ReviewRemarkInitPage(IFile file, ReviewProgress progress) {
		super("New Review remark");
		this.file = file;
		this.progress = progress;
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
		label.setText("&File:");

		fileName = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileName.setEditable(false);

		fileName.setText(file.getProjectRelativePath().toString());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		fileName.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Type:");

		type = new Combo(container, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY);
		
		type.setFocus();

		gd = new GridData(GridData.FILL_HORIZONTAL);
		type.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Category:");

		category = new Combo(container, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY);		

		gd = new GridData(GridData.FILL_HORIZONTAL);
		category.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Severity:");

		severity = new Combo(container, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY);	

		gd = new GridData(GridData.FILL_HORIZONTAL);
		severity.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Scope:");

		Composite buttonContainer = new Composite(container, SWT.EMBEDDED);
		GridLayout buttonLayout = new GridLayout();
		buttonContainer.setLayout(buttonLayout);
		buttonLayout.numColumns = 1;
		buttonLayout.verticalSpacing = 9;

		SelectionListener buttonSelectionListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Object source = e.getSource();
				if (source == generelButton) {
					fileName.setText("");
				} else {
					fileName.setText(file.getProjectRelativePath().toString());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};

		classButton = new Button(buttonContainer, SWT.RADIO);
		classButton.setText(ReviewRemarkScope.CLASS.toString());		
		classButton.addSelectionListener(buttonSelectionListener);

		methodButton = new Button(buttonContainer, SWT.RADIO);
		methodButton.setText(ReviewRemarkScope.METHOD.toString());
		methodButton.addSelectionListener(buttonSelectionListener);

		selectionButton = new Button(buttonContainer, SWT.RADIO);
		selectionButton.setText(ReviewRemarkScope.SELECTION.toString());
		selectionButton.addSelectionListener(buttonSelectionListener);

		generelButton = new Button(buttonContainer, SWT.RADIO);
		generelButton.setText(ReviewRemarkScope.GENEREL.toString());
		generelButton.addSelectionListener(buttonSelectionListener);
		
		initialize();		
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
				
		type.add(ReviewRemarkType.LOGIC.toString());
		type.add(ReviewRemarkType.TESTABILITY.toString());
		type.add(ReviewRemarkType.MAINTAINABLILITY.toString());
		type.add(ReviewRemarkType.USEABILITY.toString());
		type.add(ReviewRemarkType.CODE_COMMENT.toString());
		type.add(ReviewRemarkType.PERFORMANCE.toString());
		type.add(ReviewRemarkType.DESIGN_ERROR.toString());
		type.add(ReviewRemarkType.OTHER.toString());		
		type.select(0);
		
		category.add(ReviewRemarkCategory.WRONG.toString());
		category.add(ReviewRemarkCategory.MISSING.toString());
		category.add(ReviewRemarkCategory.EXTRA.toString());		
		
		severity.add(ReviewRemarkSeverityType.HIGH.toString());
		severity.add(ReviewRemarkSeverityType.MEDIUM.toString());
		severity.add(ReviewRemarkSeverityType.LOW.toString());		
		
		if (progress != null && progress.getCurrentCheckpoint() != null) {
			Category checkListCategory = progress.getCurrentCheckpoint().getCategory();
			Severity checkListSeverity = progress.getCurrentCheckpoint().getSeverity();
			
			if(Category.WRONG.equals(checkListCategory)) {
				category.select(0);
			} else if(Category.MISSING.equals(checkListCategory)) {
				category.select(1);
			} else if(Category.EXTRA.equals(checkListCategory)) {
				category.select(2);
			} else {
				category.select(0);
			}
			
			if(Severity.HIGH.equals(checkListSeverity)) {
				severity.select(0);				
			} else if(Severity.MEDIUM.equals(checkListSeverity)) {
				severity.select(1);
			} else if(Severity.LOW.equals(checkListSeverity)) {
				severity.select(2);
			} else {
				severity.select(0);
			}
			
			ReviewChecklist checklist = progress.getParent().getChecklist();
			Object feature = checklist.getParent(progress.getCurrentCheckpoint());
			Scope parent = (Scope) checklist.getParent(feature);
			String scopeName = parent.getName();
			
			if("Class".equalsIgnoreCase(scopeName)) {
				classButton.setSelection(true);
			} else if("Method".equalsIgnoreCase(scopeName)) {
				methodButton.setSelection(true);
			} else if("Generel".equalsIgnoreCase(scopeName)) {
				generelButton.setSelection(true);
			} else {
				classButton.setSelection(true);
			}			
		} else {
			severity.select(0);
			category.select(0);
			classButton.setSelection(true);
		}
	}
	

	public ReviewRemarkType getType() {
		int selectionIndex = type.getSelectionIndex();
		switch (selectionIndex) {
		case 0:
			return ReviewRemarkType.LOGIC;

		case 1:
			return ReviewRemarkType.TESTABILITY;

		case 2:
			return ReviewRemarkType.MAINTAINABLILITY;

		case 3:
			return ReviewRemarkType.USEABILITY;
			
		case 4:
			return ReviewRemarkType.CODE_COMMENT;
			
		case 5:
			return ReviewRemarkType.PERFORMANCE;
			
		case 6:
			return ReviewRemarkType.DESIGN_ERROR;
			
		case 7:
			return ReviewRemarkType.OTHER;

		default:
			return null;
		}
	}

	public ReviewRemarkCategory getCategory() {
		int selectionIndex = category.getSelectionIndex();
		switch (selectionIndex) {
		case 0:
			return ReviewRemarkCategory.WRONG;

		case 1:
			return ReviewRemarkCategory.MISSING;

		case 2:
			return ReviewRemarkCategory.EXTRA;

		case 3:

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
		} else if (generelButton.getSelection()) {
			return ReviewRemarkScope.GENEREL;
		}

		throw new UnsupportedOperationException();
	}
}
