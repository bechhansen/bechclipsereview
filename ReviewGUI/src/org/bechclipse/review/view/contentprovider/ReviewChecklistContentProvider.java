package org.bechclipse.review.view.contentprovider;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ReviewChecklistContentProvider implements ITreeContentProvider {

	

	private Viewer viewer;

	public ReviewChecklistContentProvider() {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
	}

	public Object[] getElements(Object parent) {
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	public void update() {
		if (viewer != null) {
			viewer.refresh();
		}
	}

}
