package org.bechclipse.review.model;

public enum ReviewRemarkType {

	LOGIC, TESTABILITY, MAINTAINABLILITY, USEABILITY, CODE_COMMENT, PERFORMANCE, DESIGN_ERROR, OTHER;

	public String toString() {

		switch (this.ordinal()) {
		case 0:
			return "Logic";
		case 1:
			return "Testability";
		case 2:
			return "Maintainability";
		case 3:
			return "Useability";
		case 4:
			return "Code comments";
		case 5:
			return "Performance";
		case 6:
			return "Design error";
		case 7:
			return "Other";
		default:
			throw new UnsupportedOperationException();	
		}		
	}
}
