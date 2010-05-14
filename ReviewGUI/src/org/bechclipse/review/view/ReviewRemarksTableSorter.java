package org.bechclipse.review.view;

import org.bechclipse.review.model.ReviewRemark;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class ReviewRemarksTableSorter extends ViewerSorter {
	private int propertyIndex;
	private static final int DESCENDING = 1;

	private int direction = DESCENDING;

	public ReviewRemarksTableSorter() {
		this.propertyIndex = 0;
		direction = DESCENDING;
	}

	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		ReviewRemark p1 = (ReviewRemark) e1;
		ReviewRemark p2 = (ReviewRemark) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = p1.getType().compareTo(p2.getType());
			break;
		case 1:
			rc = p1.getScope().compareTo(p2.getScope());
			break;
		case 2:
			rc = p1.getFile().compareTo(p2.getFile());
			break;
		case 3:
			rc = p1.getUser().compareTo(p2.getUser());
			break;
		case 4:
			rc = p1.getCategory().compareTo(p2.getCategory());
			break;
		case 5:
			rc = p1.getSeverity().compareTo(p2.getSeverity());
			break;
		case 6:
			rc = p1.getStatusContext().getStatus().getName().compareTo(p2.getStatusContext().getStatus().getName());
			break;
		default:
			rc = 0;
		}
		// If descending order, flip the direction
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;		
	}
}
