package org.bechclipse.review.model.remarkstatus;

import java.util.ArrayList;
import java.util.List;

import org.bechclipse.review.model.ReviewRemarkStatusEnum;

public class ReviewRemarkStatusFollowUp extends AbstractReviewRemarkStatus {

	private static ReviewRemarkStatusFollowUp instance;
	private List<ReviewRemarkStatusType> transitions;

	private ReviewRemarkStatusFollowUp() {
	}

	public static ReviewRemarkStatusFollowUp getInstance() {
		if (instance == null) {
			instance = new ReviewRemarkStatusFollowUp();
		}
		return instance;
	}

	@Override
	public void rework(ReviewRemarkStatusContext context) {
		context.changeState(ReviewRemarkStatusRework.getInstance());
	}

	@Override
	public void reworkAccpted(ReviewRemarkStatusContext context) {
		context.changeState(ReviewRemarkStatusReworkAccepted.getInstance());
	}

	@Override
	public List<ReviewRemarkStatusType> getLegalTransitions() {
		if (transitions == null) {
			transitions = new ArrayList<ReviewRemarkStatusType>();
			transitions.add(ReviewRemarkStatusRework.getInstance());
			transitions.add(ReviewRemarkStatusReworkAccepted.getInstance());
		}
		return transitions;
	}

	@Override
	public String getName() {
		return "Follow-up";
	}

	@Override
	public ReviewRemarkStatusEnum getReviewRemarkEnum() {
		return ReviewRemarkStatusEnum.FOLLOWUP;
	}
}
