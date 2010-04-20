package org.bechclipse.review.model;

import javax.xml.bind.annotation.XmlTransient;

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
	private Review parent;
	private Long id;
	
	public ReviewRemark() {
		
	}

	public ReviewRemark(Review parent, ReviewRemarkType type, ReviewRemarkSeverityType severity, String description, String solution, ReviewRemarkScope scope, String user, String file, int offset, int length) {		
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
}