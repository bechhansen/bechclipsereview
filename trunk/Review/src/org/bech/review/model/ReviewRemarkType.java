package org.bech.review.model;

public enum ReviewRemarkType {

	SECURITY, NULLPOINTER, CODESTYLE, STRUCTURE;
	
	
	public String toString() {

		if (this.equals(SECURITY)) {
			return "SSecurity";
		} else if (this.equals(NULLPOINTER)) {
			return "NullPointer";
		} else if (this.equals(CODESTYLE)) {
			return "Code style";
		} else if (this.equals(STRUCTURE)) {
			return "Structual";
		}
		throw new UnsupportedOperationException();
	}
}
