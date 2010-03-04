package org.bech.review.facade;

import java.util.Collection;

import org.bech.review.model.ReviewRemark;
import org.bech.review.model.ReviewRemarkScope;
import org.bech.review.model.ReviewRemarkSeverityType;
import org.bech.review.model.ReviewRemarkType;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextSelection;

public interface ReviewFacade {

	public void addReviewRemark(IFile file, ITextSelection textSelection, ReviewRemarkType type, ReviewRemarkSeverityType severity, String reviewDescription, String reviewSolution, ReviewRemarkScope scope);

	public Collection<ReviewRemark> getReviewRemarks();

	public void addDataListener(Class<?> clazz, ReviewDataListener dataListener);

	public void removeDataListener(Class<?> clazz, ReviewDataListener dataListener);

}
