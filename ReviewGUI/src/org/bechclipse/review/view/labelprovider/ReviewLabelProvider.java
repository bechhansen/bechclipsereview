package org.bechclipse.review.view.labelprovider;

import java.io.InputStream;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.IReview;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewState;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class ReviewLabelProvider extends LabelProvider implements ITableLabelProvider, ITableFontProvider {

	public String getColumnText(Object obj, int index) {
		
		IReview review = (IReview) obj;
		
		//return review.getName() + " (" + review.getProject().getName() + ")";
		return review.getProject().getName() + "/" + review.getName();
	}

	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}

	public Image getImage(Object obj) {
		IReview review = (IReview) obj;

		if (ReviewState.CLOSED.equals(review.getReviewState())) {
			return getImage("/icons/stop.gif");
		} else if (ReviewState.CREATED.equals(review.getReviewState())) {
			return getImage("/icons/sample2.gif");
		} else if (ReviewState.EXECUTED.equals(review.getReviewState())) {
			return getImage("/icons/taskmrk_tsk.gif");
		} else if (ReviewState.STARTED.equals(review.getReviewState())) {
			return getImage("/icons/nav_go.gif");
		}

		return null;
		// return new Image(PlatformUI.getWorkbench().getDisplay(),
		// "composite_change.gif");

	}

	private Image getImage(String image) {
		InputStream stream = getClass().getResourceAsStream(image);
		return new Image(PlatformUI.getWorkbench().getDisplay(), stream);
	}

	@Override
	public Font getFont(Object element, int columnIndex) {
		Review selectedReview = ReviewFacadeFactory.getFacade().getSelectedReview();
		
		if(selectedReview != null && selectedReview.equals(element)) {
			return JFaceResources.getFontRegistry().getBold("REVIEWFONT");
		}
		return JFaceResources.getFontRegistry().get("REVIEWFONT");		
		
	}

}