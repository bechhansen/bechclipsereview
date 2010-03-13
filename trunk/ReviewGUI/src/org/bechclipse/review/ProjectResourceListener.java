package org.bechclipse.review;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.Constants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

public class ProjectResourceListener implements IResourceChangeListener {

	private IProject project;
	
	@Override
	public void resourceChanged(IResourceChangeEvent event) {

		try {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				event.getDelta().accept(new IResourceDeltaVisitor() {


					public boolean visit(IResourceDelta delta) throws CoreException {
						
						
						final IResource resource = delta.getResource();
						
						if (resource instanceof IProject) {
							project = (IProject)resource;
						
						} else if (resource.getName().equals(Constants.rootFolderName)) {
							ReviewFacadeFactory.getFacade().reload(project);
							return false;							
						}
						return true;
					}
				});
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
