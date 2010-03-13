package org.bechclipse.review;


import org.bechclipse.review.annotation.EditorTracker;
import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class GuiActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.bechclipse.review.gui";

	// The shared instance
	private static GuiActivator plugin;

	private EditorTracker editorTracker;
	
	private ProjectResourceListener prl = new ProjectResourceListener();

	/**
	 * The constructor
	 */
	public GuiActivator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		editorTracker = new EditorTracker(getWorkbench());
		
		ResourcesPlugin.getWorkspace().addResourceChangeListener(prl);
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		for (IProject project : root.getProjects()) {
			ReviewFacadeFactory.getFacade().reload(project);
		}

	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		
		ResourcesPlugin.getWorkspace().addResourceChangeListener(prl);
		editorTracker.dispose();
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static GuiActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
