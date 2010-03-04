package org.bech.review.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bech.review.model.ReviewModel;
import org.bech.review.model.ReviewRemark;
import org.bech.review.model.ReviewRemarkScope;
import org.bech.review.model.ReviewRemarkSeverityType;
import org.bech.review.model.ReviewRemarkType;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextSelection;

public class ReviewFacadeImpl implements ReviewFacade {

	private ReviewModel reviewmodel;

	private Map<Class<?>, Collection<ReviewDataListener>> listeners = new HashMap<Class<?>, Collection<ReviewDataListener>>();

	@Override
	public void addReviewRemark(IFile file, ITextSelection textSelection, ReviewRemarkType type, ReviewRemarkSeverityType severity, String description, String solution, ReviewRemarkScope scope) {

		String username = System.getenv("USERNAME");

		ReviewRemark remark = new ReviewRemark(type, severity, description, solution, scope, username, file.getProjectRelativePath().toString(), textSelection.getOffset(), textSelection.getLength());
		getReviewModel().getReviewRemarks().add(remark);
		fireUpdate(remark);

	}

	@Override
	public Collection<ReviewRemark> getReviewRemarks() {
		return getReviewModel().getReviewRemarks();
	}

	private ReviewModel getReviewModel() {

		if (reviewmodel == null) {
			reviewmodel = new ReviewModel();
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

}
