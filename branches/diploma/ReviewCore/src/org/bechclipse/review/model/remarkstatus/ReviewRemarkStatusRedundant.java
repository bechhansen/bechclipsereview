package org.bechclipse.review.model.remarkstatus;

import java.util.Collections;
import java.util.List;

import org.bechclipse.review.model.ReviewRemarkStatusEnum;

public class ReviewRemarkStatusRedundant extends AbstractReviewRemarkStatus {

	private static ReviewRemarkStatusRedundant instance;

	private ReviewRemarkStatusRedundant() {
	}

	public static ReviewRemarkStatusRedundant getInstance() {
		if (instance == null) {
			instance = new ReviewRemarkStatusRedundant();
		}
		return instance;
	}

	public List<ReviewRemarkStatusType> getLegalTransitions() {
		return Collections.emptyList();
	}

	@Override
	public String getName() {
		return "Redundant";
	}

	@Override
	public ReviewRemarkStatusEnum getReviewRemarkEnum() {
		return ReviewRemarkStatusEnum.REDUNDANT;
	}
}
