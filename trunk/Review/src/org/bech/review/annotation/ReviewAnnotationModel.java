package org.bech.review.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.bech.review.facade.ReviewDataListener;
import org.bech.review.facade.ReviewFacadeFactory;
import org.bech.review.model.ReviewRemark;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModelEvent;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.text.source.IAnnotationModelListener;
import org.eclipse.jface.text.source.IAnnotationModelListenerExtension;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * IAnnotationModel implementation for efficient coverage highlighting.
 * 
 * @author Marc R. Hoffmann
 * @version $Revision: 330 $
 */
public class ReviewAnnotationModel implements IAnnotationModel, ReviewDataListener {

	/** Key used to piggyback our model to the editor's model. */
	private static final String KEY = "REVIEW_ANNOTATION";

	// private final ITextEditor editor;
	private final IDocument document;

	private ITextEditor editor;

	private Collection<IAnnotationModelListener> annotationModelListeners = new ArrayList<IAnnotationModelListener>();

	private int openConnections;

	private ReviewAnnotationModel(ITextEditor editor, IDocument document) {
		this.editor = editor;
		this.document = document;
		
		ReviewFacadeFactory.getFacade().addDataListener(ReviewRemark.class, this);
	}

	/**
	 * Attaches a coverage annotation model for the given editor if the editor
	 * can be annotated. Does nothing if the model is already attached.
	 * 
	 * @param editor
	 *            Editor to attach a annotation model to
	 */
	public static void attach(ITextEditor editor) {
		IDocumentProvider provider = editor.getDocumentProvider();
		// there may be text editors without document providers (SF #1725100)
		if (provider == null)
			return;
		IAnnotationModel model = provider.getAnnotationModel(editor.getEditorInput());
		if (!(model instanceof IAnnotationModelExtension))
			return;
		IAnnotationModelExtension modelex = (IAnnotationModelExtension) model;

		IDocument document = provider.getDocument(editor.getEditorInput());

		ReviewAnnotationModel coveragemodel = (ReviewAnnotationModel) modelex.getAnnotationModel(KEY);
		if (coveragemodel == null) {
			coveragemodel = new ReviewAnnotationModel(editor, document);
			modelex.addAnnotationModel(KEY, coveragemodel);
		}
	}

	public void addAnnotationModelListener(IAnnotationModelListener listener) {
		annotationModelListeners.add(listener);
	}

	public void removeAnnotationModelListener(IAnnotationModelListener listener) {
		annotationModelListeners.remove(listener);
	}

	protected void fireModelChanged(AnnotationModelEvent event) {

		event.markSealed();
		if (!event.isEmpty()) {
			for (Iterator i = annotationModelListeners.iterator(); i.hasNext();) {
				IAnnotationModelListener l = (IAnnotationModelListener) i.next();
				if (l instanceof IAnnotationModelListenerExtension) {
					((IAnnotationModelListenerExtension) l).modelChanged(event);
				} else {
					l.modelChanged(this);
				}
			}
		}
	}

	public void connect(IDocument document) {
		if (this.document != document)
			throw new RuntimeException("Can't connect to different document."); //$NON-NLS-1$

		Iterator<ReviewAnnotation> annotations = getAnnotationIterator();
		
		for (Iterator i = annotations; i.hasNext();) {
			ReviewAnnotation ca = (ReviewAnnotation) i.next();
			try {
				document.addPosition(ca.getPosition());
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

	}

	public void disconnect(IDocument document) {
		if (this.document != document)
			throw new RuntimeException("Can't disconnect from different document."); //$NON-NLS-1$

		Iterator<ReviewAnnotation> annotations = getAnnotationIterator();
		
		for (Iterator i = annotations; i.hasNext();) {
			ReviewAnnotation ca = (ReviewAnnotation) i.next();
			document.removePosition(ca.getPosition());
		}
		/*if (--openConnections == 0) { // CoverageTools.removeJavaCoverageListener(coverageListener);
			document.removeDocumentListener(documentListener);
		}*/

	}

	/**
	 * External modification is not supported.
	 */
	public void addAnnotation(Annotation annotation, Position position) {
		throw new UnsupportedOperationException();
	}

	/**
	 * External modification is not supported.
	 */
	public void removeAnnotation(Annotation annotation) {
		throw new UnsupportedOperationException();
	}

	public Position getPosition(Annotation annotation) {
		if (annotation instanceof ReviewAnnotation) {
			return ((ReviewAnnotation) annotation).getPosition();
		} else {
			return null;
		}
	}

	@Override
	public Iterator<ReviewAnnotation> getAnnotationIterator() {

		Collection<ReviewAnnotation> list = new ArrayList<ReviewAnnotation>();

		IEditorInput editorInput = editor.getEditorInput();

		if (editorInput instanceof FileEditorInput) {

			IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
			String fileName = fileEditorInput.getFile().getProjectRelativePath().toString();

			Collection<ReviewRemark> remarks = ReviewFacadeFactory.getFacade().getReviewRemarks();
			for (ReviewRemark reviewRemark : remarks) {

				if (reviewRemark.getFile().equalsIgnoreCase(fileName)) {

					list.add(new ReviewAnnotation(reviewRemark.getOffset(), reviewRemark.getLenght()));
				}
			}
		}

		/*
		 * ReviewAnnotation reviewAnnotation = new ReviewAnnotation(12, 10);
		 * list.add(reviewAnnotation); reviewAnnotation = new
		 * ReviewAnnotation(120, 120); list.add(reviewAnnotation);
		 */

		return list.iterator();

	}

	@Override
	public void update() {
		fireModelChanged(new AnnotationModelEvent(this));
		
	}

}
