package org.bechclipse.review.view;

import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.view.contentprovider.ReviewRemarkContentProvider;
import org.bechclipse.review.view.labelprovider.ReviewRemarkLabelProvider;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreePathViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class ReviewRemarks extends ViewPart {

	private TableViewer viewer;

	public ReviewRemarks() {

	}

	@Override
	public void createPartControl(Composite parent) {

		viewer = new TableViewer(createTable(parent));
		viewer.getTable().setHeaderVisible(true);
		
		viewer.setContentProvider(new ReviewRemarkContentProvider());
		viewer.setLabelProvider(new ReviewRemarkLabelProvider());
		viewer.setSorter(new TreePathViewerSorter());
		
		hookContextMenu();
		
		viewer.addOpenListener(new IOpenListener() {		

			@Override
			public void open(OpenEvent event) {

				ISelection selection = event.getSelection();

				if (selection instanceof StructuredSelection) {
					StructuredSelection sSelection = (StructuredSelection) selection;

					ReviewRemark rRemark = (ReviewRemark) sSelection.getFirstElement();
					
					if(rRemark.getFile() == null) {
						return;
					}

					IWorkbenchPage page = getSite().getPage();

					IWorkspace root = ResourcesPlugin.getWorkspace();
					IProject[] projects = root.getRoot().getProjects();

					for (int i = 0; i < projects.length; i++) {

						IFile file = projects[i].getFile(rRemark.getFile());
						if (file != null) {
							try {
								IEditorPart editor = IDE.openEditor(page, file, true);

								if (editor instanceof AbstractTextEditor) {
									AbstractTextEditor ate = (AbstractTextEditor) editor;
									ate.selectAndReveal(rRemark.getOffset(), rRemark.getLenght());
								}
								//

								break;
							} catch (PartInitException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});

		TableColumn tc1 = new TableColumn(viewer.getTable(), SWT.LEFT, 0);
		tc1.setText("Type");
		tc1.setWidth(100);
		
		viewer.getTable().setSortColumn(tc1);

		TableColumn tc2 = new TableColumn(viewer.getTable(), SWT.LEFT, 1);
		tc2.setText("Scope");
		tc2.setWidth(100);

		TableColumn tc3 = new TableColumn(viewer.getTable(), SWT.LEFT, 2);
		tc3.setText("File");
		tc3.setWidth(100);

		TableColumn tc4 = new TableColumn(viewer.getTable(), SWT.LEFT, 3);
		tc4.setText("Description");
		tc4.setWidth(150);

		TableColumn tc5 = new TableColumn(viewer.getTable(), SWT.LEFT, 4);
		tc5.setText("Solution");
		tc5.setWidth(165);

		TableColumn tc6 = new TableColumn(viewer.getTable(), SWT.LEFT, 5);
		tc6.setText("User");
		tc6.setWidth(100);

		TableColumn tc7 = new TableColumn(viewer.getTable(), SWT.LEFT, 6);
		tc7.setText("Severity");
		tc7.setWidth(100);

		CellEditor cellEditors[] = new CellEditor[viewer.getTable().getColumnCount()];
		CellEditor descriptionCellEditor = new TextCellEditor(viewer.getTable());
		cellEditors[0] = descriptionCellEditor;
		// viewer.setCellEditors(cellEditors);
		// viewer.setCellModifier(cellModifier);
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
}
