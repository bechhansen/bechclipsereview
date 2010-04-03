package org.bechclipse.review.facade;

import java.util.Collection;

import org.bechclipse.review.model.IReview;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.model.ReviewRemarkScope;
import org.bechclipse.review.model.ReviewRemarkSeverityType;
import org.bechclipse.review.model.ReviewRemarkType;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.ITextSelection;

public interface ReviewFacade {

	public void addReviewRemark(IFile file, ITextSelection textSelection, ReviewRemarkType type, ReviewRemarkSeverityType severity, String reviewDescription, String reviewSolution, ReviewRemarkScope scope);	
	
	public Collection<ReviewRemark> getReviewRemarks();

	public void addDataListener(Class<?> clazz, ReviewDataListener dataListener);

	public void removeDataListener(Class<?> clazz, ReviewDataListener dataListener);

	public void addReview(Review review);

	public Collection<IReview> getReviews();

	public void deleteReview(Review review);

	public void reload(IProject project);
	
	public void selectReview(Review review);

	public Review getSelectedReview();

	public void stepGuideForwardFile();

	public void startGuide();

	public void stepGuideBackwards();

	public void stepGuideForward();

	public void stepGuideBackwardsFile();

	public void syncGuide();
	
	

}
