package org.bechclipse.review.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bechclipse.review.model.IReview;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewMemoryModel;
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

	private void fireUpdate(ReviewRemark remark) {
		Collection<ReviewDataListener> collection = listeners.get(remark.getClass());
		if (collection != null) {
			for (ReviewDataListener reviewDataListener : collection) {
				reviewDataListener.update();
			}
		}
	}

	private void fireUpdate() {
		Collection<Collection<ReviewDataListener>> values = listeners.values();
		for (Collection<ReviewDataListener> collection : values) {
			for (ReviewDataListener reviewDataListener : collection) {
				reviewDataListener.update();
			}
		}

	}

	private void fireUpdate(Review review) {
		Collection<ReviewDataListener> collection = listeners.get(review.getClass());
		if (collection != null) {
			for (ReviewDataListener reviewDataListener : collection) {
				reviewDataListener.update();
			}
		}
	}

	@Override
	public void addReview(Review review) {
		try {
			pFacade.persistReview(review);
			// reviewmodel.addReview(review);
			fireUpdate(review);

		} catch (Exception e) {
			MessageDialog.openError(null, "Error adding review", e.getMessage());
		}
	}

	@Override
	public void addReviewRemark(IFile file, ITextSelection textSelection, ReviewRemarkType type, ReviewRemarkSeverityType severity, String description, String solution, ReviewRemarkScope scope) {

		try {
			String username = System.getenv("USERNAME");

			ReviewRemark remark = new ReviewRemark(type, severity, description, solution, scope, username, file.getProjectRelativePath().toString(), textSelection.getOffset(), textSelection.getLength());
			getReviewModel().getReviewRemarks().add(remark);
			pFacade.persistReviewRemark(remark);
			fireUpdate(remark);
		} catch (Exception e) {
			MessageDialog.openError(null, "Error adding review remark", e.getMessage());
		}

	}

	@Override
	public void deleteReview(Review review) {
		try {
			pFacade.deleteReview(review);
			// reviewmodel.removeReview(review);
			fireUpdate(review);

		} catch (Exception e) {
			MessageDialog.openError(null, "Error deleting", e.getMessage());
		}
	}

	@Override
	public void reload(IProject project) {
		try {
			getReviewModel().setReviewsForProjectproject(project, pFacade.loadReviews(project));
			fireUpdate();
		} catch (Exception e) {
			MessageDialog.openError(null, "Error loding reviews", e.getMessage());
		}

	}

	@Override
	public void selectReview(Review review) {
		getReviewModel().selectReview(review);
		fireUpdate(review);
	}
	
	public Review getSelectedReview() {
		return getReviewModel().getSelectedReview();
	}
}
