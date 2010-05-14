package org.bechclipse.review.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusContext;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusInspection;
import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class ReviewRemark {

	private ReviewRemarkType type;
	private ReviewRemarkSeverityType severity;
	private String description;
	private String file;
	private int length;
	private int offset;
	private String solution;
	private String user;
	private ReviewRemarkScope scope;
	private ReviewRemarkCategory category;
	private Review parent;
	private Long id;
	private ReviewRemarkStatusContext statusContext;
	private List<ReviewRemarkStatus> remarkStatus = new ArrayList<ReviewRemarkStatus>();

	public ReviewRemark() {
	}

	public ReviewRemark(Review parent, ReviewRemarkType type, ReviewRemarkSeverityType severity, String description, String solution, ReviewRemarkScope scope, ReviewRemarkCategory category, String user, String file, int offset, int length) {

		statusContext = new ReviewRemarkStatusContext(ReviewRemarkStatusInspection.getInstance());
		ReviewRemarkStatus rrs = new ReviewRemarkStatus(statusContext.getStatus(), "Initial", user);
		remarkStatus.add(rrs);

		this.setParent(parent);
		this.setType(type);
		this.setSeverity(severity);
		this.setDescription(description);
		this.setSolution(solution);
		this.setUser(user);
		this.setFile(file);
		this.setOffset(offset);
		this.setLength(length);
		this.setScope(scope);
		this.setCategory(category);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public ReviewRemarkType getType() {
		return type;
	}

	public ReviewRemarkSeverityType getSeverity() {
		return severity;
	}

	public String getDescription() {
		return description;
	}

	public String getSolution() {
		return solution;
	}

	public String getUser() {
		return user;
	}

	public int getOffset() {
		return offset;
	}

	public String getFile() {
		return file;
	}

	public ReviewRemarkScope getScope() {
		return scope;
	}

	@XmlTransient
	public Review getParent() {
		return parent;
	}

	public void setType(ReviewRemarkType type) {
		this.type = type;
	}

	public void setSeverity(ReviewRemarkSeverityType severity) {
		this.severity = severity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setScope(ReviewRemarkScope scope) {
		this.scope = scope;
	}

	public void setParent(Review parent) {
		this.parent = parent;
	}

	@XmlTransient
	public ReviewRemarkStatusContext getStatusContext() {
		if (statusContext == null) {
			ReviewRemarkStatus status = remarkStatus.get(remarkStatus.size() - 1);
			if (status != null) {
				statusContext = new ReviewRemarkStatusContext(status.getStatus());
			}
		}

		return statusContext;
	}

	public void changeStatus(ReviewRemarkStatusType status, String comment, String user) {
		ReviewRemarkStatus rrs = new ReviewRemarkStatus(status, comment, user);
		remarkStatus.add(rrs);
		statusContext.getStatus().changeStatus(statusContext, status);
	}

	public void setRemarkStatus(List<ReviewRemarkStatus> col) {
		this.remarkStatus = col;
	}

	@XmlElement(name = "RemarkStatus")
	@XmlElementWrapper(name = "RemarkStatusList")
	public List<ReviewRemarkStatus> getRemarkStatus() {
		return remarkStatus;
	}

	public void setCategory(ReviewRemarkCategory category) {
		this.category = category;
	}

	public ReviewRemarkCategory getCategory() {
		return category;
	}	
}