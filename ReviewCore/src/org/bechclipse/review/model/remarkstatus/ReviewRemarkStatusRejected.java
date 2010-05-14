package org.bechclipse.review.model.remarkstatus;

import java.util.Collections;
import java.util.List;

import org.bechclipse.review.model.ReviewRemarkStatusEnum;

public class ReviewRemarkStatusRejected extends AbstractReviewRemarkStatus {

	private static ReviewRemarkStatusRejected instance;	
	
	private ReviewRemarkStatusRejected() {				
	}
	
	public static ReviewRemarkStatusRejected getInstance() {
		if (instance == null) {
			instance = new ReviewRemarkStatusRejected();
		}
		return instance;
	} 

	public List<ReviewRemarkStatusType> getLegalTransitions() {
		return Collections.emptyList();
	}

	@Override
	public String getName() {
		return "Rejected";
	}

	@Override
	public ReviewRemarkStatusEnum getReviewRemarkEnum() {
		return ReviewRemarkStatusEnum.REJECTED;
	}
}
