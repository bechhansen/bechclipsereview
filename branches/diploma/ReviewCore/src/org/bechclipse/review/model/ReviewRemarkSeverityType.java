package org.bechclipse.review.model;

public enum ReviewRemarkSeverityType {

	HIGH, MEDIUM, LOW;

	public String toString() {

		if (this.equals(HIGH)) {
			return "High";
		} else if (this.equals(MEDIUM)) {
			return "Medium";
		} else if (this.equals(LOW)) {
			return "Low";
		}
		throw new UnsupportedOperationException();
	}

}
