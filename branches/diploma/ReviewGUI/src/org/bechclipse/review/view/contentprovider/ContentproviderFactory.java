package org.bechclipse.review.view.contentprovider;

public class ContentproviderFactory {

	private static ReviewContentProvider reviewContentProvider;

	public static ReviewContentProvider getReviewContentProvider() {
		if (reviewContentProvider == null) {
			reviewContentProvider = new ReviewContentProvider();
		}
		return reviewContentProvider;
	}

}
