package org.bechclipse.review.model.remarkstatus;

import java.util.ArrayList;
import java.util.List;

import org.bechclipse.review.model.ReviewRemarkStatusEnum;

public class ReviewRemarkStatusInspection extends AbstractReviewRemarkStatus {

	private static ReviewRemarkStatusInspection instance;
	private List<ReviewRemarkStatusType> transitions;

	private ReviewRemarkStatusInspection() {
	}

	public static ReviewRemarkStatusInspection getInstance() {
		if (instance == null) {
			instance = new ReviewRemarkStatusInspection();
		}
		return instance;
	}

	@Override
	public void ignore(ReviewRemarkStatusContext context) {
		context.changeState(ReviewRemarkStatusIgnore.getInstance());
	}

	@Override
	public void redundant(ReviewRemarkStatusContext context) {
		context.changeState(ReviewRemarkStatusRedundant.getInstance());

	}

	@Override
	public void rework(ReviewRemarkStatusContext context) {
		context.changeState(ReviewRemarkStatusRework.getInstance());
	}

	@Override
	public void rejected(ReviewRemarkStatusContext context) {
		context.changeState(ReviewRemarkStatusRejected.getInstance());
	}

	@Override
	public List<ReviewRemarkStatusType> getLegalTransitions() {
		if (transitions == null) {
			transitions = new ArrayList<ReviewRemarkStatusType>();
			transitions.add(ReviewRemarkStatusRework.getInstance());
			transitions.add(ReviewRemarkStatusIgnore.getInstance());
			transitions.add(ReviewRemarkStatusRejected.getInstance());
			transitions.add(ReviewRemarkStatusRedundant.getInstance());
		}
		return transitions;
	}

	@Override
	public String getName() {
		return "Inspection";
	}

	@Override
	public ReviewRemarkStatusEnum getReviewRemarkEnum() {
		return ReviewRemarkStatusEnum.INSPECTION;
	}
}
