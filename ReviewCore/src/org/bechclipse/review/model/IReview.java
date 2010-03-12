package org.bechclipse.review.model;

import java.util.List;

import org.eclipse.core.resources.IProject;

public interface IReview {

	public void setName(String name);

	public String getName();

	public void setReviewers(List<String> reviewers);

	public List<String> getReviewers();

	public void setDescription(String description);

	public String getDescription();
	
	public ReviewState getReviewState();
	
	public IProject getProject();
	

}
