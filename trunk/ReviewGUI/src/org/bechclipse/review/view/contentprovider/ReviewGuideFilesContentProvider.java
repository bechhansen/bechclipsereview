package org.bechclipse.review.view.contentprovider;

import org.bechclipse.review.facade.ReviewDataListener;
import org.bechclipse.review.facade.ReviewFacade;
import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.Review;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ReviewGuideFilesContentProvider implements ITreeContentProvider, ReviewDataListener {

	private Viewer viewer;
	
	private ReviewFacade facade = ReviewFacadeFactory.getFacade();

	public ReviewGuideFilesContentProvider() {
		facade.addDataListener(Review.class, this);
	}	
	

	@Override
	public void dispose() {
		facade.removeDataListener(Review.class, this);
	}
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
	}

	public Object[] getElements(Object parent) {
		Review selectedReview = facade.getSelectedReview();
		if(selectedReview != null && selectedReview.getFiles() != null) {
			return selectedReview.getFiles().toArray();	
		}
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
