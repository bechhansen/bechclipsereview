package org.bechclipse.review.view.contentprovider;

import org.bechclipse.review.facade.ReviewDataListener;
import org.bechclipse.review.facade.ReviewFacade;
import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.Review;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ReviewContentProvider implements IStructuredContentProvider, ReviewDataListener {
	
	private Viewer v;
	
	private ReviewFacade facade = ReviewFacadeFactory.getFacade();

	public ReviewContentProvider() {		
		facade.addDataListener(Review.class, this);		
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		this.v = v;
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {		
		return facade.getReviews().toArray();
	}	
	
	public void update() {
		if (v != null) {
			v.refresh();
		}
	}
}