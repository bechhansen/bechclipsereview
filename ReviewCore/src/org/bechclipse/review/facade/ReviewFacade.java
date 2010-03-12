package org.bechclipse.review.facade;

import java.util.Collection;

import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.model.ReviewRemarkScope;
import org.bechclipse.review.model.ReviewRemarkSeverityType;
import org.bechclipse.review.model.ReviewRemarkType;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextSelection;

public interface ReviewFacade {

	public void addReviewRemark(IFile file, ITextSelection textSelection, ReviewRemarkType type, ReviewRemarkSeverityType severity, String reviewDescription, String reviewSolution, ReviewRemarkScope scope);

	public Collection<ReviewRemark> getReviewRemarks();

	public void addDataListener(Class<?> clazz, ReviewDataListener dataListener);

	public void removeDataListener(Class<?> clazz, ReviewDataListener dataListener);

}
