package org.bechclipse.review.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

public class ReviewMemoryModel {

	private Map<IProject, Collection<IReview>> reviewsMap = new HashMap<IProject, Collection<IReview>>();
	
	private Collection<ReviewRemark> reviewRemark = new ArrayList<ReviewRemark>();

	private Review selectedReview;

	public ReviewMemoryModel() {
		
	}

	public Collection<ReviewRemark> getReviewRemarks() {
		return reviewRemark;
	}

	public void setReviewRemarks(Collection<ReviewRemark> reviewRemark) {
		this.reviewRemark = reviewRemark;
	}

	public void addReview(IReview review) {
		Collection<IReview> col = reviewsMap.get(review.getProject());
		
		if (col == null) {
			col = new ArrayList<IReview>();			
		}
		col.add(review);
		reviewsMap.put(review.getProject(), col);
	}

	public Collection<IReview> getReviews() {
		Collection<IReview> result = new ArrayList<IReview>();
		
		Collection<Collection<IReview>> values = reviewsMap.values();
		for (Collection<IReview> reviewCollection : values) {
			result.addAll(reviewCollection);
		}
		return result;
	}

	public void removeReview(IReview review) {
		Collection<IReview> col = reviewsMap.get(review.getProject());
		
		if (col != null) {
			col.remove(review);
		}
		
	}

	public void setReviewsForProjectproject(IProject project, Collection<IReview> reviews) {
		reviewsMap.put(project, reviews);		
	}

	public void selectReview(Review review) {
		this.selectedReview = review;		
	}	

	public Review getSelectedReview() {
		return selectedReview;
	}
}
