package org.bechclipse.review.view;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.view.contentprovider.ContentproviderFactory;
import org.bechclipse.review.view.labelprovider.ReviewLabelProvider;
import org.bechclipse.review.wizard.review.ReviewWizard;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.part.ViewPart;

public class ReviewList extends ViewPart implements ISelectionChangedListener, IOpenListener {

	private TableViewer viewer;

	public ReviewList() {

	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(ContentproviderFactory.getReviewContentProvider());
		viewer.setLabelProvider(new ReviewLabelProvider());
		viewer.setInput(getViewSite());

		JFaceResources.getFontRegistry().put("REVIEWFONT", viewer.getControl().getFont().getFontData());

		hookContextMenu();

		viewer.addSelectionChangedListener(this);

		viewer.addOpenListener(this);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		IWorkbenchPartSite site = getSite();
		if (site != null) {
			site.registerContextMenu(menuMgr, viewer);
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		StructuredSelection selection = (StructuredSelection) event.getSelection();
		Review review = (Review) selection.getFirstElement();
		ReviewFacadeFactory.getFacade().selectReview(review);
	}

	@Override
	public void open(OpenEvent event) {

		StructuredSelection selection = (StructuredSelection) event.getSelection();
		Review review = (Review) selection.getFirstElement();

		ReviewWizard wizard = new ReviewWizard(review);
		WizardDialog dialog = new WizardDialog(event.getViewer().getControl().getShell(), wizard);
		dialog.open();
	}
}
