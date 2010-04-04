package org.bechclipse.review.view.labelprovider;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewProgress;
import org.bechclipse.review.model.checklist.Checkpoint;
import org.bechclipse.review.model.checklist.Feature;
import org.bechclipse.review.model.checklist.Scope;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public class ReviewChecklistLabelProvider implements ILabelProvider, IFontProvider {

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

	@Override
	public Font getFont(Object element) {
		
		if (element instanceof Checkpoint) {
			Review selectedReview = ReviewFacadeFactory.getFacade().getSelectedReview();

			if (selectedReview != null) {
				ReviewProgress progress = selectedReview.getProgress();
				if (progress != null && element.equals(progress.getCurrentCheckpoint())) {
					return JFaceResources.getFontRegistry().getBold("CHECKLIST");
				}
			}
		}

		return JFaceResources.getFontRegistry().get("CHECKLIST");
	}
}
