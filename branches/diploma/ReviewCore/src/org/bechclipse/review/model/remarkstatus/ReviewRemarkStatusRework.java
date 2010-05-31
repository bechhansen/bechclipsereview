package org.bechclipse.review.model.remarkstatus;

import java.util.ArrayList;
import java.util.List;

import org.bechclipse.review.model.ReviewRemarkStatusEnum;

public class ReviewRemarkStatusRework extends AbstractReviewRemarkStatus {

	private static ReviewRemarkStatusRework instance;
	private List<ReviewRemarkStatusType> transitions;

	private ReviewRemarkStatusRework() {		
	}

	public static ReviewRemarkStatusRework getInstance() {
		if (instance == null) {
			instance = new ReviewRemarkStatusRework();
		}
		return instance;
	}

	@Override
	public void followUp(ReviewRemarkStatusContext context) {
		context.changeState(ReviewRemarkStatusFollowUp.getInstance());
	}

	@Override
	public List<ReviewRemarkStatusType> getLegalTransitions() {
		if (transitions == null) {
			transitions = new ArrayList<ReviewRemarkStatusType>();
			transitions.add(ReviewRemarkStatusFollowUp.getInstance());
		}
		return transitions;
	}

	@Override
	public String getName() {
		return "Rework";
	}

	@Override
	public ReviewRemarkStatusEnum getReviewRemarkEnum() {
		return ReviewRemarkStatusEnum.REWORK;
	}
}
