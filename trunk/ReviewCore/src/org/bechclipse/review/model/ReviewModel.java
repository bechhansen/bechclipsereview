package org.bechclipse.review.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

public class ReviewModel {

	private Map<IProject, Collection<IReview>> reviews = new HashMap<IProject, Collection<IReview>>();
	
	private Collection<ReviewRemark> reviewRemark = new ArrayList<ReviewRemark>();

	public Collection<ReviewRemark> getReviewRemarks() {
		return reviewRemark;
	}

	public void setReviewRemarks(Collection<ReviewRemark> reviewRemark) {
		this.reviewRemark = reviewRemark;
	}

	public void addReview(Review review) {
		Collection<IReview> col = reviews.get(review.getProject());
		
		if (col == null) {
			col = new ArrayList<IReview>();			
		}
		col.add(review);
		reviews.put(review.getProject(), col);
	}

	public Collection<IReview> getReviews() {
		Collection<IReview> result = new ArrayList<IReview>();
		
		Collection<Collection<IReview>> values = reviews.values();
		for (Collection<IReview> reviewCollection : values) {
			result.addAll(reviewCollection);
		}
		return result;
	}

	public void removeReview(IReview review) {
		Collection<IReview> col = reviews.get(review.getProject());
		
		if (col != null) {
			col.remove(review);
		}
		
	}
}
