package org.bechclipse.review.model;

public enum ReviewRemarkCategory {

	MISSING, WRONG, EXTRA;
	
	
	public String toString() {

		if (this.equals(MISSING)) {
			return "Missing";
		} else if (this.equals(WRONG)) {
			return "Wrong";
		} else if (this.equals(EXTRA)) {
			return "Extra";
		} 
		throw new UnsupportedOperationException();
	}
}
