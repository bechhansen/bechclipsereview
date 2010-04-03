package org.bechclipse.review.model;

import java.util.ArrayList;
import java.util.List;

import org.bechclipse.review.model.checklist.Checkpoint;
import org.eclipse.core.resources.IFile;

public class ReviewProgress {
	
	private List<IFile> files = new ArrayList<IFile>();
	private int currentCheckpointIndex = -1;
	private IFile currentFile = null;
	private List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();

	public ReviewProgress(Review review) {		
		if (review.getChecklist() != null) {
			checkpoints.addAll(review.getChecklist().getCheckpoint());
		}
		
		
		files.addAll(review.getFiles());
	}

	public Checkpoint getCurrentCheckpoint() {
		if (currentCheckpointIndex == -1) {
			return null;
		}
		return checkpoints.get(currentCheckpointIndex);
	}

	public IFile getCurrentFile() {
		return currentFile;
	}

	public int getCurrentCheckpointIndex() {
		return currentCheckpointIndex;
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
		if (currentCheckpointIndex > -1 && currentCheckpointIndex < checkpoints.size() - 1) {
			currentCheckpointIndex++;
		}		
	}
	
	public void stepBackwards() {
		if (currentCheckpointIndex > 0) {
			currentCheckpointIndex--;
		}		
	}
	
	public void stepForwardFile() {		
		if(currentFile != null) {			
			if (currentFile.equals(files.get(files.size() - 1))) {				
				if(currentCheckpointIndex < checkpoints.size() - 1) {
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
		if(currentFile != null) {			
			if (currentFile.equals(files.get(0))) {				
				if(currentCheckpointIndex > 0) {
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
			currentCheckpointIndex = 0;
		}
	}

	public int getStep() {
		return (currentCheckpointIndex * files.size()) + (files.indexOf(currentFile));				 
	}
	
	public boolean isFirst() {
		return currentCheckpointIndex == 0;
	}
	
	public boolean isFirstFile() {
		return files.indexOf(currentFile) == 0;
	}
	
	public boolean isLast() {
		return currentCheckpointIndex == checkpoints.size() - 1;
	}
	
	public boolean isLastFile() {
		return currentFile != null && currentFile.equals(files.get(files.size() - 1));
	}
}
