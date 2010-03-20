package org.bechclipse.review.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class ReviewGuide extends ViewPart {

	public static final String ID = "TestGui"; // TODO Needs to be whatever is
	// mentioned in plugin.xml
	private Composite top = null;
	private SashForm sashForm = null;
	private Table table = null;
	private Composite composite = null;

	private Action startAction;
	
	private Action stepBackFile;
	private Action stepBack;

	private Action stepForwardFile;
	private Action stepForward;
	
	private Label scopeLabel = null;
	private Text scopeText = null;
	private Label featureLabel = null;
	private Text featureText = null;
	private Label progressLabel = null;
	private Text progressText = null;
	
	private Text questionText = null;

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		top.setLayout(new FillLayout());
		createSashForm();

		makeActions();
		contributeToActionBars();
	}

	@Override
	public void setFocus() {		

	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		
		manager.add(startAction);
		manager.add(new Separator());		
		manager.add(stepBack);
		manager.add(stepBackFile);
		manager.add(stepForwardFile);
		manager.add(stepForward);
		
	}

	private void makeActions() {
		
		startAction = new Action() {
			public void run() {

			}
		};
		startAction.setText("Start");
		startAction.setToolTipText("Start");
		startAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJS_TASK_TSK));
		
		stepBack = new Action() {
			public void run() {

			}
		};
		stepBack.setText("Step back to previous check");
		stepBack.setToolTipText("Step back to previous check");
		stepBack.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_BACK));

		stepForward = new Action() {
			public void run() {

			}
		};
		stepForward.setText("Step forwared to next check");
		stepForward.setToolTipText("Step forwared to next check");
		stepForward.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_FORWARD));

		stepBackFile = new Action() {
			public void run() {

			}
		};
		stepBackFile.setText("Step back to previous file");
		stepBackFile.setToolTipText("Step back to previous file");
		stepBackFile.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_BACK));

		stepForwardFile = new Action() {
			public void run() {

			}
		};
		stepForwardFile.setText("Step forwared to next file");
		stepForwardFile.setToolTipText("Step forwared to next file");
		stepForwardFile.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_FORWARD));

	}

	/**
	 * This method initializes sashForm
	 * 
	 */
	private void createSashForm() {
		sashForm = new SashForm(top, SWT.NONE);

		createComposite();
		table = new Table(sashForm, SWT.NONE);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tc1 = new TableColumn(table, SWT.LEFT, 0);
		tc1.setWidth(200);
		tc1.setText("Filename");

		sashForm.setWeights(new int[] { 75, 25 });
	}

	/**
	 * This method initializes composite
	 * 
	 */
	private void createComposite() {
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 6;
		composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(gridLayout1);
		scopeLabel = new Label(composite, SWT.NONE);
		scopeLabel.setText("Scope:");
		scopeText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		featureLabel = new Label(composite, SWT.NONE);
		featureLabel.setText("  Feature:");
		featureText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		progressLabel = new Label(composite, SWT.NONE);
		progressLabel.setText("  Progress:");
		progressText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		
		questionText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);

		scopeText.setText("Class");
		featureText.setText("Inheritance");
		progressText.setText("7/23");
		
		questionText.setText("Are all parameters used withing a method?");

		StyledText styledText = new StyledText(composite, SWT.BORDER
				| SWT.READ_ONLY);

		GridData gridData = new GridData();
		gridData.horizontalSpan = 6;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		questionText.setLayoutData(gridData);
		
		
		
		gridData = new GridData();
		gridData.horizontalSpan = 6;
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		styledText.setLayoutData(gridData);
		
		styledText.setText("Her skal st� en vejledning af hvad der er galt");
		

	}

} // @jve:decl-index=0:visual-constraint="112,48"