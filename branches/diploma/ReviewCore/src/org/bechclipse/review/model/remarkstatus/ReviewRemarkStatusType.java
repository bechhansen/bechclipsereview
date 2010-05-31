package org.bechclipse.review.model.remarkstatus;

import java.util.List;

import org.bechclipse.review.model.ReviewRemarkStatusEnum;

public interface ReviewRemarkStatusType {

	public void inspection(ReviewRemarkStatusContext context);

	public void ignore(ReviewRemarkStatusContext context);

	public void rejected(ReviewRemarkStatusContext context);

	public void redundant(ReviewRemarkStatusContext context);

	public void rework(ReviewRemarkStatusContext context);

	public void followUp(ReviewRemarkStatusContext context);

	public void reworkAccpted(ReviewRemarkStatusContext context);
	
	public List<ReviewRemarkStatusType> getLegalTransitions();
	
	public void changeStatus(ReviewRemarkStatusContext context, ReviewRemarkStatusType newStatus);

	public String getName();

	public ReviewRemarkStatusEnum getReviewRemarkEnum();
}
