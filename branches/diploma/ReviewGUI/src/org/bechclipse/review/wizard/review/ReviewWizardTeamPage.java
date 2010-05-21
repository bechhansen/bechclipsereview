package org.bechclipse.review.wizard.review;

import java.util.Arrays;

import org.bechclipse.review.model.Review;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ReviewWizardTeamPage extends WizardPage {

	private Text leaderText;
	private Text recorderText;
	private Text readerText;
	private Text reviewerText;
	private List reviewers;
	private List readers;
	private Review review;

	public ReviewWizardTeamPage() {
		super("Reviewers");
		setTitle("Code Review team");
		setDescription("Specify the review team members");
	}

	public ReviewWizardTeamPage(Review review) {
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
		layout.verticalSpacing = 3;
		Label label = new Label(container, SWT.NULL);
		label.setText("&Leader:");

		leaderText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		leaderText.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Recorder:");

		recorderText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		recorderText.setLayoutData(gd);

		addSouthPanel(container);

		if (review != null) {
			java.util.List<String> reviewersList = review.getReviewers();
			if (reviewersList != null) {
				for (String string : reviewersList) {
					reviewers.add(string);
				}
			}

			java.util.List<String> readersList = review.getReaders();
			if (readersList != null) {
				for (String string : readersList) {
					readers.add(string);
				}
			}

			String leader = review.getLeader();
			if (leader != null) {
				leaderText.setText(leader);
			}

			String recorder = review.getRecorder();
			if (leader != null) {
				recorderText.setText(recorder);
			}
		}

		setControl(container);
	}

	private void addSouthPanel(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		container.setLayoutData(gd);

		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.verticalSpacing = 3;
		layout.marginWidth = 0;
		container.setLayout(layout);

		Label label = new Label(container, SWT.NULL);
		label.setText("R&eaders:");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("Re&viewers:");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		readerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		readerText.setLayoutData(gd);

		ToolBar toolBar = new ToolBar(container, SWT.NONE);

		ToolItem addReader = new ToolItem(toolBar, SWT.PUSH);
		addReader.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		addReader.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				String str = readerText.getText().trim();
				readerText.setText("");

				java.util.List<String> items = Arrays.asList(readers.getItems());
				if (!str.isEmpty() && !items.contains(str)) {
					readers.add(str);
				}
			}
		});

		ToolItem removeReader = new ToolItem(toolBar, SWT.PUSH);
		removeReader.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				int[] selectionIndices = readers.getSelectionIndices();
				readers.remove(selectionIndices);
			}
		});
		removeReader.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_DELETE));

		reviewerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		reviewerText.setLayoutData(gd);

		toolBar = new ToolBar(container, SWT.NONE);

		ToolItem addReviewer = new ToolItem(toolBar, SWT.PUSH);
		addReviewer.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));

		addReviewer.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				String str = reviewerText.getText().trim();
				reviewerText.setText("");

				java.util.List<String> items = Arrays.asList(reviewers.getItems());
				if (!str.isEmpty() && !items.contains(str)) {
					reviewers.add(str);
				}
			}
		});

		ToolItem removeReviewer = new ToolItem(toolBar, SWT.PUSH);
		removeReviewer.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				int[] selectionIndices = reviewers.getSelectionIndices();
				reviewers.remove(selectionIndices);
			}
		});
		removeReviewer.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_DELETE));

		readers = new List(container, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		readers.setLayoutData(gd);

		reviewers = new List(container, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		reviewers.setLayoutData(gd);
	}

	public String getLeader() {
		return leaderText.getText();
	}

	public String getRecorder() {
		return recorderText.getText();
	}

	public java.util.List<String> getReviewers() {
		return Arrays.asList(reviewers.getItems());
	}

	public java.util.List<String> getReaders() {
		return Arrays.asList(readers.getItems());
	}

}
