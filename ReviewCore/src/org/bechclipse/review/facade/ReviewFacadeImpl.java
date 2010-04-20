package org.bechclipse.review.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bechclipse.review.model.IReview;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewMemoryModel;
import org.bechclipse.review.model.ReviewProgress;
import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.model.ReviewRemarkScope;
import org.bechclipse.review.model.ReviewRemarkSeverityType;
import org.bechclipse.review.model.ReviewRemarkType;
import org.bechclipse.review.persistence.PersistenceFacade;
import org.bechclipse.review.persistence.PersistenceFacadeImpl;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;

public class ReviewFacadeImpl implements ReviewFacade {	

	private PersistenceFacade pFacade = new PersistenceFacadeImpl();
	private ReviewMemoryModel reviewmodel;

	private Map<Class<?>, Collection<ReviewDataListener>> listeners = new HashMap<Class<?>, Collection<ReviewDataListener>>();

	@Override
	public Collection<ReviewRemark> getReviewRemarks() {
		return getReviewModel().getReviewRemarks();
	}

	@Override
	public Collection<IReview> getReviews() {
		return getReviewModel().getReviews();
	}

	private ReviewMemoryModel getReviewModel() {
		if (reviewmodel == null) {			
			reviewmodel = new ReviewMemoryModel();
		}
		return reviewmodel;
	}

	@Override
	public void addDataListener(Class<?> clazz, ReviewDataListener dataListener) {
		Collection<ReviewDataListener> collection = listeners.get(clazz);
		if (collection == null) {
			collection = new ArrayList<ReviewDataListener>();
			listeners.put(clazz, collection);
		}
		collection.add(dataListener);
	}

	@Override
	public void removeDataListener(Class<?> clazz, ReviewDataListener dataListener) {
		Collection<ReviewDataListener> collection = listeners.get(clazz);
		if (collection != null) {
			collection.remove(dataListener);
		}
	}	

	private void fireUpdate() {
		Collection<Collection<ReviewDataListener>> values = listeners.values();
		for (Collection<ReviewDataListener> collection : values) {
			for (ReviewDataListener reviewDataListener : collection) {
				reviewDataListener.update(null);
			}
		}
	}	

	private void fireUpdate(Object object) {
		Collection<ReviewDataListener> collection = listeners.get(object.getClass());
		if (collection != null) {
			for (ReviewDataListener reviewDataListener : collection) {
				reviewDataListener.update(object);
			}
		}
	}	

	@Override
	public void addReview(Review review) {
		try {
			pFacade.persistReview(review);
			reviewmodel.addReview(review);
			fireUpdate(review);

		} catch (Exception e) {
			MessageDialog.openError(null, "Error adding review", e.getMessage());
		}
	}
	
	@Override
	public void updateReview(Review review) {
		try {
			
			reviewmodel.updateReview(review);
			fireUpdate(review);
			pFacade.persistReview(review);

		} catch (Exception e) {
			MessageDialog.openError(null, "Error updating", e.getMessage());
		}		
	}

	@Override
	public void deleteReview(Review review) {
		try {
			pFacade.deleteReview(review);
			reviewmodel.removeReview(review);
			fireUpdate(review);

		} catch (Exception e) {
			MessageDialog.openError(null, "Error deleting", e.getMessage());
		}
	}

	@Override
	public void addReviewRemark(IFile file, ITextSelection textSelection, ReviewRemarkType type, ReviewRemarkSeverityType severity, String description, String solution, ReviewRemarkScope scope) {
		
		if(getSelectedReview() == null) {
			return;
		}
		try {
			String username = System.getenv("USERNAME");

			ReviewRemark remark = new ReviewRemark(getSelectedReview(), type, severity, description, solution, scope, username, file != null ? file.getProjectRelativePath().toString() : null, textSelection.getOffset(), textSelection.getLength());
			getReviewModel().getReviewRemarks().add(remark);
			pFacade.persistReviewRemark(remark);
			fireUpdate(remark);
		} catch (Exception e) {
			MessageDialog.openError(null, "Error adding review remark", e.getMessage());
		}
	}
	
	@Override
	public void updateReviewRemark(ReviewRemark remark) {
		try {			
			fireUpdate(remark);
			pFacade.persistReviewRemark(remark);

		} catch (Exception e) {
			MessageDialog.openError(null, "Error updating", e.getMessage());
		}		
	}

	@Override
	public void deleteReviewRemark(ReviewRemark remark) {
		try {			
			pFacade.deleteReviewRemark(remark);
			reviewmodel.removeReviewRemark(remark);			
			fireUpdate(remark);

		} catch (Exception e) {
			MessageDialog.openError(null, "Error deleting", e.getMessage());
		}	
	}
	

	@Override
	public void reload(IProject project) {
		try {
			getReviewModel().setReviewsForProject(project, pFacade.loadReviews(project));
			fireUpdate();
		} catch (Exception e) {
			MessageDialog.openError(null, "Error loading reviews", e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void selectReview(Review review) {
		if (review == null) {
			return;
		}
		getReviewModel().selectReview(review);
		fireUpdate(review);
	}
	
	public Review getSelectedReview() {
		return getReviewModel().getSelectedReview();
	}
	
	@Override
	public void startGuide() {
		ReviewProgress progress = ReviewFacadeFactory.getFacade().getSelectedReview().getProgress();
		progress.start();
		fireUpdate(progress);
		
	}

	@Override
	public void stepGuideForwardFile() {
		ReviewProgress progress = ReviewFacadeFactory.getFacade().getSelectedReview().getProgress();
		progress.stepForwardFile();
		fireUpdate(progress);
	}

	@Override
	public void stepGuideBackwards() {
		ReviewProgress progress = ReviewFacadeFactory.getFacade().getSelectedReview().getProgress();
		progress.stepBackwards();
		fireUpdate(progress);
		
	}

	@Override
	public void stepGuideBackwardsFile() {
		ReviewProgress progress = ReviewFacadeFactory.getFacade().getSelectedReview().getProgress();
		progress.stepBackwardsFile();
		fireUpdate(progress);		
	}

	@Override
	public void stepGuideForward() {
		ReviewProgress progress = ReviewFacadeFactory.getFacade().getSelectedReview().getProgress();
		progress.stepForward();
		fireUpdate(progress);		
	}	
}
