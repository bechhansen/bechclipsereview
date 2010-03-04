package org.bech.review.model;

public class ReviewRemark {

	private final ReviewRemarkType type;
	private final ReviewRemarkSeverityType severity;
	private final String description;
	private final String file;
	private final int length;
	private final int offset;
	private final String solution;
	private final String user;
	private final ReviewRemarkScope scope;

	public ReviewRemark(ReviewRemarkType type, ReviewRemarkSeverityType severity, String description, String solution, ReviewRemarkScope scope, String user, String file, int offset, int length) {
		this.type = type;
		this.severity = severity;
		this.description = description;
		this.solution = solution;
		this.user = user;
		this.file = file;
		this.offset = offset;
		this.length = length;
		this.scope = scope;
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

	public int getLenght() {
		return length;
	}

	public String getFile() {
		return file;
	}

	public ReviewRemarkScope getScope() {
		return scope;
	}
}
