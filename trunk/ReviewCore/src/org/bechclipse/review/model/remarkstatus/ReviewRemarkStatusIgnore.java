package org.bechclipse.review.model.remarkstatus;

import java.util.Collections;
import java.util.List;

import org.bechclipse.review.model.ReviewRemarkStatusEnum;

public class ReviewRemarkStatusIgnore extends AbstractReviewRemarkStatus {

	private static ReviewRemarkStatusIgnore instance;	
	
	private ReviewRemarkStatusIgnore() {				
	}
	
	public static ReviewRemarkStatusIgnore getInstance() {
		if (instance == null) {
			instance = new ReviewRemarkStatusIgnore();
		}
		return instance;
	} 

	public List<ReviewRemarkStatusType> getLegalTransitions() {
		return Collections.emptyList();
	}

	@Override
	public String getName() {
		return "Ignored";
	}

	@Override
	public ReviewRemarkStatusEnum getReviewRemarkEnum() {
		return ReviewRemarkStatusEnum.IGNORE;
	}
}
