package org.bechclipse.review.wizard.review;

import java.util.Set;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.Review;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class ReviewWizard extends Wizard implements INewWizard {

	private ReviewWizardInitPage initPage;
	private ReviewWizardTeamPage reviewsPage;
	private ReviewWizardFilesPage filesPage;
	private IProject project = null;
	private Review review;

	public ReviewWizard(IProject project) {
		super();
		this.project = project;
		setNeedsProgressMonitor(true);
		setWindowTitle("Create new code review");
	}

	public ReviewWizard(Review review) {
		super();
		this.review = review;
		this.project = review.getProject();
		setNeedsProgressMonitor(true);
		setWindowTitle("Edit existing code review");
	}

	public void addPages() {

		if (review != null) {
			initPage = new ReviewWizardInitPage(review);
			reviewsPage = new ReviewWizardTeamPage(review);
			filesPage = new ReviewWizardFilesPage(review);
		} else {
			initPage = new ReviewWizardInitPage();
			reviewsPage = new ReviewWizardTeamPage();
			filesPage = new ReviewWizardFilesPage();
		}

		addPage(initPage);
		addPage(reviewsPage);
		addPage(filesPage);
	}

	@Override
	public boolean performFinish() {
		final String name = initPage.getName();
		final String description = initPage.getDescriptionText();
		final Set<IFile> selectedFiles = filesPage.getSelectedFiles();

		try {
			doFinish(name, description, selectedFiles);
		} catch (CoreException e) {

			MessageDialog.openError(getShell(), "Error", e.getMessage());
			return false;
		}

		return true;

	}

	private void doFinish(/* IProgressMonitor monitor, */String name, String description, Set<IFile> selectedFiles) throws CoreException {

		if (this.review == null) {

			Review review = new Review(name, description);
			review.setProject(project);
			populateReview(review, selectedFiles);
			ReviewFacadeFactory.getFacade().addReview(review);
		} else {
			populateReview(this.review, selectedFiles);
			ReviewFacadeFactory.getFacade().updateReview(this.review);
		}
	}

	private void populateReview(Review review, Set<IFile> selectedFiles) {
		review.setFiles(selectedFiles);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}

	/*
	 * IRunnableWithProgress op = new IRunnableWithProgress() { public void
	 * run(IProgressMonitor monitor) throws InvocationTargetException { try {
	 * doFinish(monitor, name, description); } catch (CoreException e) { throw
	 * new InvocationTargetException(e); } finally { monitor.done(); } }
	 * 
	 * }; try { getContainer().run(true, false, op); } catch
	 * (InterruptedException e) { return false; } catch
	 * (InvocationTargetException e) { Throwable realException =
	 * e.getTargetException(); MessageDialog.openError(getShell(), "Error",
	 * realException .getMessage()); return false; } return true;
	 */

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
	 * source = new StringBufferInputStream("Blah"); file.create(source, true,
	 * monitor);
	 */

	/*
	 * IContainer container = (IContainer) resource; final IFile file =
	 * container.getFile(new Path(fileName)); try { InputStream stream =
	 * openContentStream(); if (file.exists()) { file.setContents(stream, true,
	 * true, monitor); } else { file.create(stream, true, monitor); }
	 * stream.close(); } catch (IOException e) { }
	 */
	// monitor.worked(1);
	// monitor.setTaskName("Opening file for editing...");
	/*
	 * getShell().getDisplay().asyncExec(new Runnable() { public void run() {
	 * IWorkbenchPage page =
	 * PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(); try
	 * { IDE.openEditor(page, file, true); } catch (PartInitException e) { } }
	 * });
	 */
	// monitor.worked(1);
}
