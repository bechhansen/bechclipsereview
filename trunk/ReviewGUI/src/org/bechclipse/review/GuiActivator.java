package org.bechclipse.review;


import org.bechclipse.review.annotation.EditorTracker;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class GuiActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "EclipseCodeReview";

	// The shared instance
	private static GuiActivator plugin;

	private EditorTracker editorTracker;

	/**
	 * The constructor
	 */
	public GuiActivator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		editorTracker = new EditorTracker(getWorkbench());

	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
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
