package org.bechclipse.review.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

public class ReviewMemoryModel {

	private Map<IProject, Collection<Review>> reviewsMap = new HashMap<IProject, Collection<Review>>();
	
	private Review selectedReview;

	public ReviewMemoryModel() {
		
	}

	@SuppressWarnings("unchecked")
	public Collection<ReviewRemark> getReviewRemarks() {
		
		if(selectedReview == null) {
			return Collections.EMPTY_LIST;
		}
		return selectedReview.getReviewRemarks();
	}
	
	public void addReview(Review review) {
		Collection<Review> col = reviewsMap.get(review.getProject());
		
		if (col == null) {
			col = new ArrayList<Review>();			
		}
		col.add(review);
		reviewsMap.put(review.getProject(), col);
	}
	
	public void updateReview(Review review) {
		// TODO Auto-generated method stub
	}
	
	public void removeReview(IReview review) {
		Collection<Review> col = reviewsMap.get(review.getProject());
		
		if (col != null) {
			col.remove(review);
		}		
	}

	public Collection<IReview> getReviews() {
		Collection<IReview> result = new ArrayList<IReview>();
		
		Collection<Collection<Review>> values = reviewsMap.values();
		for (Collection<Review> reviewCollection : values) {
			result.addAll(reviewCollection);
		}
		return result;
	}	

	public void setReviewsForProject(IProject project, Collection<Review> reviews) {
		reviewsMap.put(project, reviews);	
		
		for (Review review : reviews) {
			ReviewProgress rp = new ReviewProgress(review);
			review.setProgress(rp);	
			
			if (selectedReview != null && selectedReview.getId().equals(review.getId())) {
				selectedReview = review;
			}
		}	
	}

	public void selectReview(Review review) {
		this.selectedReview = review;		
	}	

	public Review getSelectedReview() {
		return selectedReview;
	}

	public void removeReviewRemark(ReviewRemark remark) {
		 Review parent = remark.getParent();		 
		 Collection<ReviewRemark> reviewRemarks = parent.getReviewRemarks();
		 reviewRemarks.remove(remark);
		
	}	
}
