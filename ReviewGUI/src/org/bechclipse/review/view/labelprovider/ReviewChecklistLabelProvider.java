package org.bechclipse.review.view.labelprovider;

import org.bechclipse.review.model.checklist.Checkpoint;
import org.bechclipse.review.model.checklist.Feature;
import org.bechclipse.review.model.checklist.Scope;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class ReviewChecklistLabelProvider implements ILabelProvider {

		
	
	public void addListener(ILabelProviderListener listener) {
	}

	
	public void dispose() {		
	}

	
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	
	public void removeListener(ILabelProviderListener listener) {

	}


	
	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getText(Object element) {
		if (element instanceof Scope) {
			Scope scope = (Scope) element;
			return scope.getName();
		} else if (element instanceof Feature) {
			Feature feature = (Feature) element;
			return feature.getName();
		} else if (element instanceof Checkpoint) {
			Checkpoint checkpoint = (Checkpoint) element;
			return checkpoint.getQuestion();
		}
		return "";
	}

}
