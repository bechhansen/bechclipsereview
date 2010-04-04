package org.bechclipse.review.view.contentprovider;

import org.bechclipse.review.facade.ReviewDataListener;
import org.bechclipse.review.facade.ReviewFacade;
import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.ReviewRemark;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ReviewRemarkContentProvider implements ITreeContentProvider, ReviewDataListener {

	private ReviewFacade facade = ReviewFacadeFactory.getFacade();

	private Viewer viewer;

	public ReviewRemarkContentProvider() {
		facade.addDataListener(ReviewRemark.class, this);
	}

	@Override
	public void dispose() {
		facade.removeDataListener(ReviewRemark.class, this);
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
	}

	public Object[] getElements(Object parent) {
		return facade.getReviewRemarks().toArray();
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

	public void update(Object object) {
		if (viewer != null) {
			viewer.refresh();
		}
	}
}
