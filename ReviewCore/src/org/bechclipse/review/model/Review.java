package org.bechclipse.review.model;

import java.util.List;

import org.eclipse.ui.IActionFilter;

public class Review implements IReview, IActionFilter {

	private String name;
	private List<String> reviewers;
	private String description;
	private ReviewState reviewState;

	public Review(String name, String description) {
		this(name, description, ReviewState.CREATED);
	}
	
	public Review(String name, String description, ReviewState state) {		
		this.name = name;
		reviewState = state;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setReviewers(List<String> reviewers) {
		this.reviewers = reviewers;
	}

	public List<String> getReviewers() {
		return reviewers;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return getName() + " (" + getReviewState() + ")";
	}

	private String getStatus() {
		return "Åben";
	}

	public void setReviewState(ReviewState reviewState) {
		this.reviewState = reviewState;
	}

	public ReviewState getReviewState() {
		return reviewState;
	}

	@Override
	public boolean testAttribute(Object target, String name, String value) {

		if (name.equalsIgnoreCase("startable")) {
			
			boolean expected = Boolean.valueOf(value).booleanValue();
			
			return expected != getReviewState().equals(ReviewState.STARTED);

		}

		return false;
	}

}
