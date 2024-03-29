package org.bechclipse.review.wizard.review;

import java.util.HashSet;
import java.util.Set;

import org.bechclipse.review.model.Review;
import org.bechclipse.review.view.contentprovider.SelectedFilesContentProvider;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ResourceWorkingSetFilter;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class ReviewWizardFilesPage extends WizardPage {

	private TreeViewer resourceViewer;
	private TreeViewer selectedFilesViewer;

	private Set<IFile> selectedFiles = new HashSet<IFile>();

	private ResourceWorkingSetFilter workingSetFilter = new ResourceWorkingSetFilter();

	public ReviewWizardFilesPage() {
		super("Files");
		setTitle("Files for review");
		setDescription("Select the files to review");
	}

	public ReviewWizardFilesPage(Review review) {
		this();

		Set<IFile> files = review.getFiles();
		if (files != null) {
			selectedFiles.addAll(files);			
		}
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		Composite top = new Composite(parent, SWT.NULL);
		top.setLayout(new FillLayout());

		Composite container = new Composite(top, SWT.NULL);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;

		resourceViewer = createResourceViewer(container);
		GridData gridData = new GridData();
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		resourceViewer.getControl().setLayoutData(gridData);

		ToolBar toolBar = new ToolBar(container, SWT.VERTICAL);

		ToolItem addReader = new ToolItem(toolBar, SWT.PUSH);
		addReader.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		addReader.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = (IStructuredSelection) resourceViewer.getSelection();
				Object obj = selection.getFirstElement();

				if (obj instanceof IFile) {
					addFile((IFile) obj);
				}
			}
		});

		ToolItem removeReader = new ToolItem(toolBar, SWT.PUSH);
		removeReader.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = (IStructuredSelection) selectedFilesViewer.getSelection();
				Object obj = selection.getFirstElement();

				if (obj instanceof IFile) {
					removeFile((IFile) obj);
				}
			}
		});
		removeReader.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_DELETE));

		selectedFilesViewer = createSelectedViewer(container);
		selectedFilesViewer.getControl().setLayoutData(gridData);

		setControl(container);
	}

	/**
	 * Creates the viewer.
	 * 
	 * @param parent
	 *            the parent composite
	 * @since 2.0
	 */
	protected TreeViewer createResourceViewer(Composite parent) {
		TreeViewer viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setUseHashlookup(true);
		initContentProvider(viewer);
		initLabelProvider(viewer);
		initFilters(viewer);

		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				handleResourceDoubleClick(event);
			}
		});

		viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());

		return viewer;
	}

	protected TreeViewer createSelectedViewer(Composite parent) {
		TreeViewer viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setUseHashlookup(false);
		viewer.setContentProvider(new SelectedFilesContentProvider(getSelectedFiles()));
		initLabelProvider(viewer);

		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				handleSelectedDoubleClick(event);
			}
		});

		viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());

		return viewer;
	}

	protected void handleResourceDoubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Object obj = selection.getFirstElement();

		if (obj instanceof IFile) {
			addFile((IFile) obj);
		}
	}

	protected void handleSelectedDoubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Object obj = selection.getFirstElement();

		if (obj instanceof IFile) {
			removeFile((IFile) obj);
		}
	}

	private void removeFile(IFile obj) {
		selectedFiles.remove(obj);
		selectedFilesViewer.refresh();
	}

	private void addFile(IFile obj) {
		selectedFiles.add(obj);
		selectedFilesViewer.refresh();
	}

	protected void initFilters(TreeViewer viewer) {
		// viewer.addFilter(patternFilter);
		viewer.addFilter(workingSetFilter);
	}

	protected void initContentProvider(TreeViewer viewer) {
		viewer.setContentProvider(new WorkbenchContentProvider());
	}

	protected void initLabelProvider(TreeViewer viewer) {
		viewer.setLabelProvider(new DecoratingLabelProvider(new WorkbenchLabelProvider(), PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator()));
	}

	public Set<IFile> getSelectedFiles() {
		return selectedFiles;
	}
}
