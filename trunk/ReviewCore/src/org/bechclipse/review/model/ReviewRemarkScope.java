package org.bechclipse.review.model;

public enum ReviewRemarkScope {
	GENEREL, CLASS, METHOD, SELECTION;

	public String toString() {

		if (this.equals(CLASS)) {
			return "Class";
		} else if (this.equals(METHOD)) {
			return "Method";
		} else if (this.equals(SELECTION)) {
			return "Selection";
		} else if (this.equals(GENEREL)) {
			return "Generel";
		}
		throw new UnsupportedOperationException();
	}

}
