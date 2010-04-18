package org.bechclipse.review;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.swt.widgets.Display;

public class ProjectResourceListener implements IResourceChangeListener {

	private final Display display;

	public ProjectResourceListener(Display display) {
		this.display = display;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {

		try {

			IResourceDelta delta = event.getDelta();

			if (delta != null && delta.getResource() != null && delta.getResource() instanceof IWorkspaceRoot) {

				if (event.getType() == IResourceChangeEvent.POST_CHANGE && delta.getKind() == IResourceDelta.CHANGED) {

					IResourceDelta[] affectedChildren = delta.getAffectedChildren();
					for (IResourceDelta iResourceDelta : affectedChildren) {
						
						if (iResourceDelta != null && iResourceDelta.getResource() instanceof IProject && (iResourceDelta.getKind() == IResourceDelta.CHANGED || iResourceDelta.getKind() == IResourceDelta.ADDED) && (iResourceDelta.getFlags() == IResourceDelta.OPEN || iResourceDelta.getFlags() == IResourceDelta.ADDED)) {
							
							IProject project = (IProject) iResourceDelta.getResource();  
							if(project.isOpen()) {
								final IProject p = project;

								display.syncExec(new Runnable() {
									public void run() {
										ReviewFacadeFactory.getFacade().reload(p);
									}
								}); 
							}							
						}
					}

					/*
					 * delta.accept(new IResourceDeltaVisitor() {
					 * 
					 * public boolean visit(IResourceDelta delta) throws
					 * CoreException {
					 * 
					 * final IResource resource = delta.getResource(); //
					 * System.out.println(resource.getFullPath());
					 * 
					 * if (resource instanceof IProject) { IProject project =
					 * (IProject) resource;
					 * ReviewFacadeFactory.getFacade().reload(project); return
					 * false; } return true;
					 * 
					 * } });
					 */
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
