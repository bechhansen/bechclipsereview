package org.bechclipse.review.model.remarkstatus;

public class ReviewRemarkStatusContext {

	private ReviewRemarkStatusType status;

	public ReviewRemarkStatusContext(ReviewRemarkStatusType instance) {
		changeState(instance);
	}

	void changeState(ReviewRemarkStatusType status) {
		this.status = status;
	}

	public ReviewRemarkStatusType getStatus() {
		return status;
	}
}
