package org.bechclipse.review.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

public class ReviewMemoryModel {

	private Map<IProject, Collection<Review>> reviewsMap = new HashMap<IProject, Collection<Review>>();
	
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

	public void addReview(Review review) {
		Collection<Review> col = reviewsMap.get(review.getProject());
		
		if (col == null) {
			col = new ArrayList<Review>();			
		}
		col.add(review);
		reviewsMap.put(review.getProject(), col);
	}

	public Collection<IReview> getReviews() {
		Collection<IReview> result = new ArrayList<IReview>();
		
		Collection<Collection<Review>> values = reviewsMap.values();
		for (Collection<Review> reviewCollection : values) {
			result.addAll(reviewCollection);
		}
		return result;
	}

	public void removeReview(IReview review) {
		Collection<Review> col = reviewsMap.get(review.getProject());
		
		if (col != null) {
			col.remove(review);
		}		
	}

	public void setReviewsForProject(IProject project, Collection<Review> reviews) {
		reviewsMap.put(project, reviews);
		
		for (Review review : reviews) {
			ReviewProgress rp = new ReviewProgress(review);
			review.setProgress(rp);		
		}
		
		
	}

	public void selectReview(Review review) {
		this.selectedReview = review;		
	}	

	public Review getSelectedReview() {
		return selectedReview;
	}
}
