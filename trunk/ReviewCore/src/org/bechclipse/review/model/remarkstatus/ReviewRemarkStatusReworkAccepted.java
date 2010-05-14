package org.bechclipse.review.model.remarkstatus;

import java.util.Collections;
import java.util.List;

import org.bechclipse.review.model.ReviewRemarkStatusEnum;

public class ReviewRemarkStatusReworkAccepted extends AbstractReviewRemarkStatus {

	private static ReviewRemarkStatusReworkAccepted instance;

	private ReviewRemarkStatusReworkAccepted() {
	}

	public static ReviewRemarkStatusReworkAccepted getInstance() {
		if (instance == null) {
			instance = new ReviewRemarkStatusReworkAccepted();
		}
		return instance;
	}

	public List<ReviewRemarkStatusType> getLegalTransitions() {
		return Collections.emptyList();
	}

	@Override
	public String getName() {
		return "Rework accepted";
	}

	@Override
	public ReviewRemarkStatusEnum getReviewRemarkEnum() {
		return ReviewRemarkStatusEnum.REVIEWACCEPTED;
	}
}
