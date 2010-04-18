package org.bechclipse.review;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.swt.widgets.Display;

public class ProjectResourceUpdateListener implements IResourceChangeListener {

	private final Display display;

	public ProjectResourceUpdateListener(Display display) {
		this.display = display;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {

		try {

			IResource resource = event.getResource();
			System.out.println("Update caled " + resource);

			if (resource instanceof IProject) {
				final IProject project = (IProject) resource;

				display.syncExec(new Runnable() {
					public void run() {
						ReviewFacadeFactory.getFacade().reload(project);
					}
				});

			}

			/*
			 * delta.accept(new IResourceDeltaVisitor() {
			 * 
			 * public boolean visit(IResourceDelta delta) throws CoreException {
			 * 
			 * final IResource resource = delta.getResource();
			 * 
			 * if(resource instanceof IFile) { IFile file = (IFile) resource;
			 * System.out.println(file.getName()); }
			 * 
			 * if (resource instanceof IProject) { IProject project = (IProject)
			 * resource; ReviewFacadeFactory.getFacade().reload(project); return
			 * false; } return true;
			 * 
			 * } });
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
