package org.bechclipse.review.view;

import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.view.contentprovider.ReviewRemarkContentProvider;
import org.bechclipse.review.view.labelprovider.ReviewRemarkLabelProvider;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class ReviewRemarks extends ViewPart {

	private TableViewer viewer;
	private Text problemText;
	private Text solutionText;

	public ReviewRemarks() {

	}

	@Override
	public void createPartControl(Composite parent) {

		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;

		parent.setLayout(gridLayout1);

		Table table = createTable(parent);
		viewer = new TableViewer(table);
		viewer.getTable().setHeaderVisible(true);
		

		ReviewRemarksTableSorter sorter = new ReviewRemarksTableSorter();
		
		viewer.setContentProvider(new ReviewRemarkContentProvider());
		viewer.setLabelProvider(new ReviewRemarkLabelProvider());
		viewer.setComparator(sorter);

		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.grabExcessVerticalSpace = true;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		table.setLayoutData(gd);

		Label problemLabel = new Label(parent, SWT.NONE);
		problemLabel.setText("Problem:");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		problemLabel.setLayoutData(gd);

		Label solutionLabel = new Label(parent, SWT.NONE);
		solutionLabel.setText("Solution:");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		solutionLabel.setLayoutData(gd);

		problemText = new Text(parent, SWT.WRAP | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 60;
		problemText.setLayoutData(gd);

		solutionText = new Text(parent, SWT.WRAP | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 60;
		solutionText.setLayoutData(gd);

		hookContextMenu();

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection) event.getSelection();
				if (selection.size() != 1) {
					solutionText.setText("");
					problemText.setText("");
				} else {
					ReviewRemark remark = (ReviewRemark) selection.getFirstElement();
					solutionText.setText(remark.getSolution());
					problemText.setText(remark.getDescription());
				}
			}

		});

		viewer.addOpenListener(new IOpenListener() {

			@Override
			public void open(OpenEvent event) {

				ISelection selection = event.getSelection();

				if (selection instanceof StructuredSelection) {
					StructuredSelection sSelection = (StructuredSelection) selection;

					ReviewRemark rRemark = (ReviewRemark) sSelection.getFirstElement();

					if (rRemark.getFile() == null) {
						return;
					}

					IWorkbenchPage page = getSite().getPage();
					// IWorkspace root = ResourcesPlugin.getWorkspace();
					// IProject[] projects = root.getRoot().getProjects();

					IFile file = rRemark.getParent().getProject().getFile(rRemark.getFile());
					if (file != null) {
						try {
							IEditorPart editor = IDE.openEditor(page, file, true);

							if (editor instanceof AbstractTextEditor) {
								AbstractTextEditor ate = (AbstractTextEditor) editor;
								ate.selectAndReveal(rRemark.getOffset(), rRemark.getLength());
							}

						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}

				}
			}
		});

		TableColumn tc1 = new TableColumn(viewer.getTable(), SWT.LEFT, 0);
		tc1.setText("Type");
		tc1.setWidth(80);
		tc1.addSelectionListener(new ColumnSelectionListener(tc1, sorter, 0 ));

		

		TableColumn tc2 = new TableColumn(viewer.getTable(), SWT.LEFT, 1);
		tc2.setText("Scope");
		tc2.setWidth(70);
		tc2.addSelectionListener(new ColumnSelectionListener(tc2, sorter, 1));

		TableColumn tc3 = new TableColumn(viewer.getTable(), SWT.LEFT, 2);
		tc3.setText("File");
		tc3.setWidth(300);
		tc3.addSelectionListener(new ColumnSelectionListener(tc3, sorter, 2));
		
		TableColumn tc4 = new TableColumn(viewer.getTable(), SWT.LEFT, 3);
		tc4.setText("User");
		tc4.setWidth(80);
		tc4.addSelectionListener(new ColumnSelectionListener(tc4, sorter, 3));

		TableColumn tc5 = new TableColumn(viewer.getTable(), SWT.LEFT, 4);
		tc5.setText("Category");
		tc5.setWidth(60);
		tc5.addSelectionListener(new ColumnSelectionListener(tc5, sorter, 4));

		TableColumn tc6 = new TableColumn(viewer.getTable(), SWT.LEFT, 5);
		tc6.setText("Severity");
		tc6.setWidth(70);
		tc6.addSelectionListener(new ColumnSelectionListener(tc6, sorter, 5));

		TableColumn tc7 = new TableColumn(viewer.getTable(), SWT.LEFT, 6);
		tc7.setText("Status");
		tc7.setWidth(110);
		tc7.addSelectionListener(new ColumnSelectionListener(tc7, sorter, 6));

		viewer.setInput(getViewSite());

	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		IWorkbenchPartSite site = getSite();
		if (site != null) {
			site.registerContextMenu(menuMgr, viewer);
		}
	}

	/**
	 * Create the main tree control
	 * 
	 * @param parent
	 * @return Tree
	 */
	protected Table createTable(Composite parent) {
		Table table = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		return table;
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	class ColumnSelectionListener extends SelectionAdapter {
		
		private final TableColumn column;
		private final ReviewRemarksTableSorter sorter;
		private final int i;

		public ColumnSelectionListener(TableColumn column, ReviewRemarksTableSorter sorter, int i) {
			this.column = column;
			this.sorter = sorter;
			this.i = i;			
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			sorter.setColumn(i);	
			int dir = viewer.getTable().getSortDirection();
			if (viewer.getTable().getSortColumn() == column) {
				dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
			} else {

				dir = SWT.DOWN;
			}
			viewer.getTable().setSortDirection(dir);
			viewer.getTable().setSortColumn(column);
			viewer.refresh();
		}
	}
}
