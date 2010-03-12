package org.bechclipse.review.view;

import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.view.contentprovider.ReviewRemarkContentProvider;
import org.bechclipse.review.view.labelprovider.ReviewRemarkLabelProvider;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreePathViewerSorter;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class ReviewRemarks extends ViewPart {

	private TreeViewer viewer;

	public ReviewRemarks() {

	}

	@Override
	public void createPartControl(Composite parent) {

		viewer = new TreeViewer(createTree(parent));
		viewer.getTree().setHeaderVisible(true);
		
		viewer.setContentProvider(new ReviewRemarkContentProvider());
		viewer.setLabelProvider(new ReviewRemarkLabelProvider());
		viewer.setSorter(new TreePathViewerSorter());
		
		viewer.addOpenListener(new IOpenListener() {
		

			@Override
			public void open(OpenEvent event) {

				ISelection selection = event.getSelection();

				if (selection instanceof ITreeSelection) {
					ITreeSelection treeSelection = (ITreeSelection) selection;

					ReviewRemark rRemark = (ReviewRemark) treeSelection.getFirstElement();

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

					// IEditorPart editor = IDE.openEditor(page, , true);

					// System.out.println("Open");

				}

			}

		});

		TreeColumn tc1 = new TreeColumn(viewer.getTree(), SWT.LEFT, 0);
		tc1.setText("Type");
		tc1.setWidth(100);
		
		viewer.getTree().setSortColumn(tc1);

		TreeColumn tc2 = new TreeColumn(viewer.getTree(), SWT.LEFT, 1);
		tc2.setText("Scope");
		tc2.setWidth(100);

		TreeColumn tc3 = new TreeColumn(viewer.getTree(), SWT.LEFT, 2);
		tc3.setText("File");
		tc3.setWidth(100);

		TreeColumn tc4 = new TreeColumn(viewer.getTree(), SWT.LEFT, 3);
		tc4.setText("Description");
		tc4.setWidth(150);

		TreeColumn tc5 = new TreeColumn(viewer.getTree(), SWT.LEFT, 4);
		tc5.setText("Solution");
		tc5.setWidth(165);

		TreeColumn tc6 = new TreeColumn(viewer.getTree(), SWT.LEFT, 5);
		tc6.setText("User");
		tc6.setWidth(100);

		TreeColumn tc7 = new TreeColumn(viewer.getTree(), SWT.LEFT, 6);
		tc7.setText("Severity");
		tc7.setWidth(100);

		CellEditor cellEditors[] = new CellEditor[viewer.getTree().getColumnCount()];
		CellEditor descriptionCellEditor = new TextCellEditor(viewer.getTree());
		cellEditors[0] = descriptionCellEditor;
		// viewer.setCellEditors(cellEditors);
		// viewer.setCellModifier(cellModifier);
		viewer.setInput(getViewSite());

	}

	/**
	 * Create the main tree control
	 * 
	 * @param parent
	 * @return Tree
	 */
	protected Tree createTree(Composite parent) {
		Tree tree = new Tree(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		tree.setLinesVisible(true);
		return tree;
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();

	}

}
