package org.bech.review.model;

import java.util.ArrayList;
import java.util.Collection;

public class ReviewModel {

	private Collection<ReviewRemark> reviewRemark = new ArrayList<ReviewRemark>();

	public Collection<ReviewRemark> getReviewRemarks() {
		return reviewRemark;
	}

	public void setReviewRemarks(Collection<ReviewRemark> reviewRemark) {
		this.reviewRemark = reviewRemark;
	}
}
