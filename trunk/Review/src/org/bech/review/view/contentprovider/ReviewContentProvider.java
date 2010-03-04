package org.bech.review.view.contentprovider;

import java.util.ArrayList;
import java.util.List;

import org.bech.review.model.IReview;
import org.bech.review.model.Review;
import org.bech.review.model.ReviewState;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ReviewContentProvider implements IStructuredContentProvider {

	private List<IReview> reviews = new ArrayList<IReview>();
	private Viewer v;

	public ReviewContentProvider() {

		reviews.add(new Review("Review1", "Desc", ReviewState.CLOSED));
		reviews.add(new Review("Review2", "Desc", ReviewState.STARTED));
		reviews.add(new Review("Review3", "Desc", ReviewState.EXECUTED));
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		this.v = v;
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {		
		return reviews.toArray();
	}

	public void addReview(IReview review) {
		reviews.add(review);
		v.refresh();
	}

	public void deleteReview(IReview review) {
		reviews.remove(review);
		v.refresh();
	}
}