package org.bechclipse.review.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IActionFilter;

@XmlRootElement(name = "Review", namespace = "http://core.review.bechclipse.org")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "review")
public class Review implements IReview, IActionFilter {

	private Long id;
	private IProject project;
	private String name;
	private List<String> reviewers;
	private String description;
	private ReviewState reviewState;

	public Review() {
	}

	public Review(String name, String description) {
		this(name, description, ReviewState.CREATED);
	}

	public Review(String name, String description, ReviewState state) {
		this.name = name;
		this.description = description;
		reviewState = state;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setReviewers(List<String> reviewers) {
		this.reviewers = reviewers;
	}

	@XmlElementWrapper(name = "Reviewers")
	public List<String> getReviewers() {
		return reviewers;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public String toString() {
		return getName() + " (" + getReviewState() + ")";
	}	

	public void setReviewState(ReviewState reviewState) {
		this.reviewState = reviewState;
	}
	
	@XmlElement
	public ReviewState getReviewState() {
		return reviewState;
	}

	public boolean testAttribute(Object target, String name, String value) {

		if (name.equalsIgnoreCase("startable")) {

			boolean expected = Boolean.valueOf(value).booleanValue();

			return expected != getReviewState().equals(ReviewState.STARTED);

		}

		return false;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	@XmlTransient
	public IProject getProject() {
		return project;
	}
}
