package org.bechclipse.review.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IActionFilter;

@XmlRootElement(name = "Review", namespace = "http://core.review.bechclipse.org")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "review")
public class Review implements IReview, IActionFilter {

	private Long id;
	private IProject project;
	private String name;
	private List<String> reviewers;
	private String description;
	private ReviewState reviewState;
	private Set<String> files;
	private ReviewChecklist checklist;
	private ReviewProgress progress;

	public Review() {
	}

	public Review(String name, String description) {
		this(name, description, ReviewState.CREATED);
	}

	public Review(String name, String description, ReviewState state) {
		this.name = name;
		this.description = description;
		reviewState = state;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setReviewers(List<String> reviewers) {
		this.reviewers = reviewers;
	}

	@XmlElementWrapper(name = "Reviewers")
	public List<String> getReviewers() {
		return reviewers;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public String toString() {
		return getName() + " (" + getReviewState() + ")";
	}

	public void setReviewState(ReviewState reviewState) {
		this.reviewState = reviewState;
	}

	@XmlElement
	public ReviewState getReviewState() {
		return reviewState;
	}

	public boolean testAttribute(Object target, String name, String value) {

		if (name.equalsIgnoreCase("startable")) {

			boolean expected = Boolean.valueOf(value).booleanValue();

			return expected != getReviewState().equals(ReviewState.STARTED);

		}

		return false;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	@XmlTransient
	public IProject getProject() {
		return project;
	}

	@XmlTransient
	public Set<IFile> getFiles() {
		Set<IFile> iFiles = new HashSet<IFile>();

		if (files != null) {
			for (String string : files) {
				IPath path = Path.fromPortableString(string);

				IFile file = getProject().getFile(path);
				iFiles.add(file);
			}
		}
		return iFiles;
	}

	public void setFiles(Set<IFile> selectedFiles) {
		files = new HashSet<String>();
		if (selectedFiles != null) {
			for (IFile iFile : selectedFiles) {
				files.add(iFile.getProjectRelativePath().toPortableString());
			}
		}
	}

	@XmlElementWrapper(name = "Files")
	@XmlElement(name = "File")
	public String[] getFileNames() {
		return files.toArray(new String[0]);
	}

	public void setFileNames(String[] filenames) {
		files = new HashSet<String>();

		for (String string : filenames) {
			files.add(string);
		}
	}

	public void setChecklist(ReviewChecklist checklist) {
		this.checklist = checklist;
	}

	@XmlTransient
	public ReviewChecklist getChecklist() {
		return checklist;
	}

	public void setProgress(ReviewProgress progress) {
		this.progress = progress;
	}

	@XmlTransient
	public ReviewProgress getProgress() {
		return progress;
	}
}
