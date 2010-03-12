package org.bechclipse.review.wizard.review;

import org.bechclipse.review.model.Review;
import org.bechclipse.review.view.contentprovider.ContentproviderFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewReviewWizard extends Wizard implements INewWizard {

	private IStructuredSelection selection;
	private NewReviewWizardInitPage initPage;
	private NewReviewWizardReviewersPage reviewsPage;

	public NewReviewWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("Opret nyt Code Review");
	}

	public void addPages() {
		initPage = new NewReviewWizardInitPage(selection);
		addPage(initPage);

		reviewsPage = new NewReviewWizardReviewersPage(selection);
		addPage(reviewsPage);
	}

	@Override
	public boolean performFinish() {
		final String name = initPage.getName();
		final String description = initPage.getDescription();

		try {
			doFinish(name, description);
		} catch (CoreException e) {

			MessageDialog.openError(getShell(), "Error", e.getMessage());
			return false;
		}

		return true;

		/*
		 * IRunnableWithProgress op = new IRunnableWithProgress() { public void
		 * run(IProgressMonitor monitor) throws InvocationTargetException { try
		 * { doFinish(monitor, name, description); } catch (CoreException e) {
		 * throw new InvocationTargetException(e); } finally { monitor.done(); }
		 * }
		 * 
		 * }; try { getContainer().run(true, false, op); } catch
		 * (InterruptedException e) { return false; } catch
		 * (InvocationTargetException e) { Throwable realException =
		 * e.getTargetException(); MessageDialog.openError(getShell(), "Error",
		 * realException .getMessage()); return false; } return true;
		 */
	}

	private void doFinish(/* IProgressMonitor monitor, */String name,
			String description) throws CoreException {

		// monitor.beginTask("Creating review", 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		ContentproviderFactory.getReviewContentProvider().addReview(
				new Review(name, description));

		IProject[] projects = root.getProjects();

		/*
		 * String folderName = ".review"; IFolder folder =
		 * projects[0].getFolder(folderName); if (!folder.exists()) {
		 * folder.create(true, true, monitor); }
		 * 
		 * IFile file = folder.getFile("info.xml");
		 * 
		 * InputStream source = new StringBufferInputStream("Blah");
		 * file.create(source, true, monitor);
		 * 
		 * String user = System.getenv("USERNAME");
		 * 
		 * file = folder.getFile(user + ".xml");
		 * 
		 * source = new StringBufferInputStream("Blah"); file.create(source,
		 * true, monitor);
		 */

		/*
		 * IContainer container = (IContainer) resource; final IFile file =
		 * container.getFile(new Path(fileName)); try { InputStream stream =
		 * openContentStream(); if (file.exists()) { file.setContents(stream,
		 * true, true, monitor); } else { file.create(stream, true, monitor); }
		 * stream.close(); } catch (IOException e) { }
		 */
		// monitor.worked(1);
		// monitor.setTaskName("Opening file for editing...");
		/*
		 * getShell().getDisplay().asyncExec(new Runnable() { public void run()
		 * { IWorkbenchPage page =
		 * PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		 * try { IDE.openEditor(page, file, true); } catch (PartInitException e)
		 * { } } });
		 */
		// monitor.worked(1);

	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
