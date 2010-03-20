package org.bechclipse.review.view;

import org.bechclipse.review.view.contentprovider.ReviewChecklistContentProvider;
import org.bechclipse.review.view.labelprovider.ReviewChecklistLabelProvider;
import org.eclipse.jface.viewers.TreePathViewerSorter;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

public class ReviewChecklist extends ViewPart {

	private TreeViewer viewer;

	public ReviewChecklist() {

	}

	@Override
	public void createPartControl(Composite parent) {

		viewer = new TreeViewer(createTree(parent));
		viewer.getTree().setHeaderVisible(false);
		
		viewer.setContentProvider(new ReviewChecklistContentProvider());
		viewer.setLabelProvider(new ReviewChecklistLabelProvider());
		viewer.setSorter(new TreePathViewerSorter());		
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
