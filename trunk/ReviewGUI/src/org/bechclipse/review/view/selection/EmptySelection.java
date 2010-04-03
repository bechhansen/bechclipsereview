package org.bechclipse.review.view.selection;

import org.eclipse.jface.viewers.ISelection;

public class EmptySelection implements ISelection {

	@Override
	public boolean isEmpty() {
		return true;
	}

}
