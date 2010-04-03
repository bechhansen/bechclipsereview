package org.bechclipse.review.view.selection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;

public class ObjectSelection implements IStructuredSelection {

	List<Object> selection = new ArrayList<Object>();

	public ObjectSelection(Object selection) {
		this.selection.add(selection);
	}

	@Override
	public Object getFirstElement() {
		return selection.get(0);
	}

	@Override
	public Iterator<Object> iterator() {
		return selection.iterator();
	}

	@Override
	public int size() {
		return selection.size();
	}

	@Override
	public Object[] toArray() {
		return selection.toArray();
	}

	@Override
	public List<Object> toList() {
		return selection;
	}

	@Override
	public boolean isEmpty() {
		return selection.isEmpty();
	}
}
