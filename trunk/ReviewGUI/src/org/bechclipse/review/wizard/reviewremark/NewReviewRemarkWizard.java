package org.bechclipse.review.wizard.reviewremark;

import org.bechclipse.review.facade.ReviewFacadeFactory;
import org.bechclipse.review.model.ReviewProgress;
import org.bechclipse.review.model.ReviewRemarkScope;
import org.bechclipse.review.model.ReviewRemarkSeverityType;
import org.bechclipse.review.model.ReviewRemarkType;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewReviewRemarkWizard extends Wizard implements INewWizard {

	private NewReviewRemarkInitPage initPage;
	private NewReviewRemarkCommentPage commentPage;

	private final IFile file;
	private final ITextSelection textSelection;
	private ReviewProgress progress;

	public NewReviewRemarkWizard(IFile file, ITextSelection textSelection) {
		super();
		this.file = file;
		this.textSelection = textSelection;
		setNeedsProgressMonitor(true);
		setWindowTitle("Create review comment");
	}

	public NewReviewRemarkWizard(IFile file, ITextSelection textSelection, ReviewProgress progress) {
		this(file, textSelection);
		this.progress = progress;
	}

	public void addPages() {
		initPage = new NewReviewRemarkInitPage(file, progress);
		addPage(initPage);

		commentPage = new NewReviewRemarkCommentPage(progress);
		addPage(commentPage);
	}

	@Override
	public boolean performFinish() {
		final ReviewRemarkType type = initPage.getType();
		final ReviewRemarkSeverityType severity = initPage.getSeverity();
		final ReviewRemarkScope scope = initPage.getScope();
		final String reviewDescription = commentPage.getReviewDescription();
		final String reviewSolution = commentPage.getReviewSolution();
				
		try {
			doFinish(ReviewRemarkScope.GENEREL.equals(scope) ? null : file, textSelection, type, severity, reviewDescription, reviewSolution, scope);
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

	private void doFinish(IFile file, ITextSelection textSelection, ReviewRemarkType type, ReviewRemarkSeverityType severity, String reviewDescription, String reviewSolution, ReviewRemarkScope scope) throws CoreException {

		// monitor.beginTask("Creating review", 2);

		ReviewFacadeFactory.getFacade().addReviewRemark(file, textSelection, type, severity, reviewDescription, reviewSolution, scope);

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
		// this.selection = selection;
	}

}
