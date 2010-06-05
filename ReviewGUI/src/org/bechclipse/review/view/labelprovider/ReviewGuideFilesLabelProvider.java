package org.bechclipse.review.view.labelprovider;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewProgress;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class ReviewGuideFilesLabelProvider extends DecoratingLabelProvider implements IFontProvider {

	public ReviewGuideFilesLabelProvider() {
		super(new WorkbenchLabelProvider(), PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator());
	}

	@Override
	public Font getFont(Object element) {
		if (element instanceof IFile) {
			Review selectedReview = ReviewFacadeFactory.getFacade().getSelectedReview();

			if (selectedReview != null) {
				ReviewProgress progress = selectedReview.getProgress();
				if (progress != null && element.equals(progress.getCurrentFile())) {
					return JFaceResources.getFontRegistry().getBold("GUIDEFILES");
				}
			}
		}
		return JFaceResources.getFontRegistry().get("GUIDEFILES");		
	}

}
