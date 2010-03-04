package org.bech.review.model;

public enum ReviewRemarkScope {
	CLASS, METHOD, SELECTION;

	public String toString() {

		if (this.equals(CLASS)) {
			return "Class";
		} else if (this.equals(METHOD)) {
			return "Method";
		} else if (this.equals(SELECTION)) {
			return "Selection";
		}
		throw new UnsupportedOperationException();
	}

}
