package org.bechclipse.review.wizard.reviewremarkstatus;

import java.util.List;

import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusType;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ReviewRemarkStatusInitPage extends WizardPage {

	private Text comment;
	private Combo status;
	
	private final ReviewRemarkStatusType currentStatus; 

	public ReviewRemarkStatusInitPage(ReviewRemark remark) {
		super("Review remark status ");
		setTitle("Code Review remark status change");
		setDescription("Change the status for the reviewremark");
		
		currentStatus = remark.getStatusContext().getStatus();
		setPageComplete(currentStatus != null && !currentStatus.getLegalTransitions().isEmpty());		
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
		label.setText("&Current status:");
		
		Text currentStatusText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		currentStatusText.setLayoutData(gd);
		currentStatusText.setEditable(false);		
		currentStatusText.setText(currentStatus.getName());
		
		label = new Label(container, SWT.NULL);
		label.setText("&New status:");

		status = new Combo(container, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		status.setLayoutData(gd);
		
		List<ReviewRemarkStatusType> transitions = currentStatus.getLegalTransitions();
		for (ReviewRemarkStatusType remarkStatus : transitions) {
			status.add(remarkStatus.getName());
		}

		label = new Label(container, SWT.NULL);
		label.setText("&Comment:");

		comment = new Text(container, SWT.BORDER | SWT.MULTI);

		if (!transitions.isEmpty()) {
			status.select(0);
			comment.setEditable(true);
		} else {
			comment.setEditable(false);
		}

		gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		comment.setLayoutData(gd);

		setControl(container);
	}
	
	public ReviewRemarkStatusType getStatus() {		
		List<ReviewRemarkStatusType> transitions = currentStatus.getLegalTransitions();
		
		int selectionIndex = status.getSelectionIndex();
		if (selectionIndex == -1) {
			return null;
		}
		return transitions.get(selectionIndex);
	}

	public String getComment() {
		return comment.getText();
	}
}
