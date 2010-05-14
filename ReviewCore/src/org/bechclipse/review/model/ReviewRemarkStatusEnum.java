package org.bechclipse.review.model;

import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusFollowUp;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusIgnore;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusInspection;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusRedundant;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusRejected;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusRework;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusReworkAccepted;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusType;

public enum ReviewRemarkStatusEnum {
	FOLLOWUP, IGNORE, INSPECTION, REJECTED, REDUNDANT, REWORK, REVIEWACCEPTED;

	public ReviewRemarkStatusType toRemarkStatusType() {
		if (ReviewRemarkStatusFollowUp.getInstance().getReviewRemarkEnum().equals(this)) {
			return ReviewRemarkStatusFollowUp.getInstance();
		} else if (ReviewRemarkStatusIgnore.getInstance().getReviewRemarkEnum().equals(this)) {
			return ReviewRemarkStatusIgnore.getInstance();
		} else if (ReviewRemarkStatusInspection.getInstance().getReviewRemarkEnum().equals(this)) {
			return ReviewRemarkStatusInspection.getInstance();
		} else if (ReviewRemarkStatusRedundant.getInstance().getReviewRemarkEnum().equals(this)) {
			return ReviewRemarkStatusRedundant.getInstance();
		} else if (ReviewRemarkStatusRejected.getInstance().getReviewRemarkEnum().equals(this)) {
			return ReviewRemarkStatusRejected.getInstance();
		} else if (ReviewRemarkStatusRework.getInstance().getReviewRemarkEnum().equals(this)) {
			return ReviewRemarkStatusRework.getInstance();
		} else if (ReviewRemarkStatusReworkAccepted.getInstance().getReviewRemarkEnum().equals(this)) {
			return ReviewRemarkStatusReworkAccepted.getInstance();
		}

		throw new UnsupportedOperationException();
	}
}
