package org.bechclipse.review.view.contentprovider;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SelectedFilesContentProvider implements ITreeContentProvider {
	
	private final Set<IFile> selectedFiles;

	public SelectedFilesContentProvider(Set<IFile> selectedFiles) {
		this.selectedFiles = selectedFiles;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	public Object[] getElements(Object parent) {
		return selectedFiles.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}
}
