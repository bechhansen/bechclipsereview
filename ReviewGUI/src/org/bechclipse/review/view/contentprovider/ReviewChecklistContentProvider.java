package org.bechclipse.review.view.contentprovider;

import java.util.List;

import org.bechclipse.review.facade.ReviewDataListener;
import org.bechclipse.review.facade.ReviewFacade;
import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewChecklist;
import org.bechclipse.review.model.ReviewProgress;
import org.bechclipse.review.model.checklist.Checkpoint;
import org.bechclipse.review.model.checklist.Feature;
import org.bechclipse.review.model.checklist.Scope;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

public class ReviewChecklistContentProvider implements ITreeContentProvider, ReviewDataListener {

	private ReviewFacade facade = ReviewFacadeFactory.getFacade();	

	private Viewer viewer;

	public ReviewChecklistContentProvider() {
		facade.addDataListener(Review.class, this);		
		
		facade.addDataListener(ReviewProgress.class, new ReviewDataListener() {

			@Override
			public void update(Object object) {				
				viewer.refresh();							
			}			
		});
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
	}

	public Object[] getElements(Object parent) {
		Review selectedReview = facade.getSelectedReview();
		if (selectedReview == null) {
			return new Object[0];
		}

		ReviewChecklist checklist = selectedReview.getChecklist();
		if (checklist == null) {
			return new Object[0];
		}

		List<Scope> scope = checklist.getScope();
		if (scope == null) {
			return new Object[0];
		}

		return scope.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {

		if (parentElement instanceof Scope) {
			Scope scope = (Scope) parentElement;
			List<Feature> featureList = scope.getFeature();			
			return featureList.toArray();

		} else if (parentElement instanceof Feature) {
			Feature feature = (Feature) parentElement;
			List<Checkpoint> checkpointList = feature.getCheckpoint();			
			return checkpointList.toArray();
		} 

		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		
		Review selectedReview = facade.getSelectedReview();
		if (selectedReview == null) {
			return null;
		}

		ReviewChecklist checklist = selectedReview.getChecklist();
		if (checklist == null) {
			return null;
		}
				
		return checklist.getParent(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Scope) {
			Scope scope = (Scope) element;
			return !scope.getFeature().isEmpty();

		} else if (element instanceof Feature) {
			Feature feature = (Feature) element;
			return !feature.getCheckpoint().isEmpty();

		} else if (element instanceof Checkpoint) {
			return false;
		}
		
		return false;
	}

	public void update(Object object) {
		if (viewer != null) {			
			viewer.refresh();	
			
			if(viewer instanceof TreeViewer) {
				((AbstractTreeViewer) viewer).expandAll();
			}
		}
	}

}
