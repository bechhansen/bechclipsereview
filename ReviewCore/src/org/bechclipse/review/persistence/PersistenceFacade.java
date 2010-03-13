package org.bechclipse.review.persistence;

import java.util.Collection;

import org.bechclipse.review.model.IReview;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewRemark;
import org.eclipse.core.resources.IProject;

public interface PersistenceFacade {

	public boolean persistReview(Review review) throws Exception;

	public boolean persistReviewRemark(ReviewRemark remark) throws Exception;

	public Collection<IReview> loadReviews(IProject project) throws Exception;

	public void deleteReview(Review review) throws Exception;

}
