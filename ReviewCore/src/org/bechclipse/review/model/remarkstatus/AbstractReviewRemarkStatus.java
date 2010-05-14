package org.bechclipse.review.model.remarkstatus;

public abstract class AbstractReviewRemarkStatus implements ReviewRemarkStatusType {

	@Override
	public void followUp(ReviewRemarkStatusContext context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void ignore(ReviewRemarkStatusContext context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void inspection(ReviewRemarkStatusContext context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void redundant(ReviewRemarkStatusContext context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rejected(ReviewRemarkStatusContext context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rework(ReviewRemarkStatusContext context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reworkAccpted(ReviewRemarkStatusContext context) {
		throw new UnsupportedOperationException();
	}

	public void changeStatus(ReviewRemarkStatusContext context, ReviewRemarkStatusType newStatus) {
		if (ReviewRemarkStatusFollowUp.getInstance().equals(newStatus)) {
			followUp(context);
		} else if (ReviewRemarkStatusIgnore.getInstance().equals(newStatus)) {
			ignore(context);
		} else if (ReviewRemarkStatusInspection.getInstance().equals(newStatus)) {
			inspection(context);
		} else if (ReviewRemarkStatusRedundant.getInstance().equals(newStatus)) {
			redundant(context);
		} else if (ReviewRemarkStatusRejected.getInstance().equals(newStatus)) {
			rejected(context);
		} else if (ReviewRemarkStatusRework.getInstance().equals(newStatus)) {
			rework(context);
		} else if (ReviewRemarkStatusReworkAccepted.getInstance().equals(newStatus)) {
			reworkAccpted(context);
		} else {
			throw new UnsupportedOperationException();
		}
	}
}
