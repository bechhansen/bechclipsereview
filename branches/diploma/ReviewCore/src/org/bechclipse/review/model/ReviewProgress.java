package org.bechclipse.review.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.bechclipse.review.model.checklist.Checkpoint;
import org.eclipse.core.resources.IFile;

@XmlRootElement(name = "ReviewProgress", namespace = "http://core.review.bechclipse.org")
@XmlAccessorType(XmlAccessType.NONE)
public class ReviewProgress {

	private List<IFile> files = new ArrayList<IFile>();
	private int currentCheckpointIndex = -1;
	private IFile currentFile = null;
	private List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
	private String currentFileName;
	private Review review;

	public ReviewProgress() {		
	}
	
	public void setReview(Review review) {
		this.review = review;
		if (review.getChecklist() != null) {
			checkpoints.addAll(review.getChecklist().getCheckpoint());
		}

		files.addAll(review.getFiles());
		for (IFile  iFile : review.getFiles()) {
			if(iFile.getProjectRelativePath().toPortableString().equals(currentFileName)) {
				currentFile = iFile;
				break;
			}
			
		}
	}

	public Checkpoint getCurrentCheckpoint() {
		if (!isStarted()) {
			return null;
		}
		return checkpoints.get(getCurrentCheckpointIndex());
	}

	public IFile getCurrentFile() {
		return currentFile;
	}

	
	
	public boolean isStarted() {
		return getCurrentCheckpointIndex() != -1;
	}

	public int getMaxFile() {
		return files.size();
	}

	public int getMaxCheckpoint() {
		return checkpoints.size();
	}

	public int getMaxSteps() {
		return (getMaxFile() * getMaxCheckpoint());
	}

	public void stepForward() {
		if (getCurrentCheckpointIndex() > -1 && getCurrentCheckpointIndex() < checkpoints.size() - 1) {
			setCurrentCheckpointIndex(getCurrentCheckpointIndex() + 1);
		}
	}

	public void stepBackwards() {
		if (getCurrentCheckpointIndex() > 0) {
			setCurrentCheckpointIndex(getCurrentCheckpointIndex() - 1);
		}
	}

	public void stepForwardFile() {
		if (currentFile != null) {
			if (currentFile.equals(files.get(files.size() - 1))) {
				if (getCurrentCheckpointIndex() < checkpoints.size() - 1) {
					currentFile = files.get(0);
					stepForward();
				}
			} else {
				int indexOf = files.indexOf(currentFile);
				currentFile = files.get(indexOf + 1);
			}
		}
	}

	public void stepBackwardsFile() {
		if (currentFile != null) {
			if (currentFile.equals(files.get(0))) {
				if (getCurrentCheckpointIndex() > 0) {
					currentFile = files.get(files.size() - 1);
					stepBackwards();
				}
			} else {
				int indexOf = files.indexOf(currentFile);
				currentFile = files.get(indexOf - 1);
			}
		}

	}

	public void start() {
		if (files.size() != 0) {
			currentFile = files.get(0);
		}

		if (checkpoints.size() != 0) {
			setCurrentCheckpointIndex(0);
		}
	}

	public int getStep() {
		return (getCurrentCheckpointIndex() * files.size()) + (files.indexOf(currentFile));
	}

	public boolean isFirst() {
		return currentFile == null || getCurrentCheckpointIndex() == 0;
	}

	public boolean isFirstFile() {
		return currentFile == null || files.indexOf(currentFile) == 0;
	}

	public boolean isLast() {
		return currentFile == null || getCurrentCheckpointIndex() == checkpoints.size() - 1;
	}

	public boolean isLastFile() {
		return currentFile == null || currentFile.equals(files.get(files.size() - 1));
	}

	public void setCurrentCheckpointIndex(int currentCheckpointIndex) {
		this.currentCheckpointIndex = currentCheckpointIndex;
	}
	
	@XmlElement(name="CurrentCheckpoint")
	public int getCurrentCheckpointIndex() {
		return currentCheckpointIndex;
	}

	public void setCurrentFileName(String currentFileName) {
		this.currentFileName = currentFileName;
	}

	@XmlElement(name="CurrentFile")
	public String getCurrentFileName() {
		if(currentFile != null) {		
			return currentFile.getProjectRelativePath().toPortableString();
		}
		return null;
	}
	
	public Review getParent() {
		return review;
	}
}
