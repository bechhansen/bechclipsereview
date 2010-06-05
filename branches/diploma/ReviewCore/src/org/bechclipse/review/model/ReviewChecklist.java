package org.bechclipse.review.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bechclipse.review.model.checklist.Checklist;
import org.bechclipse.review.model.checklist.Checkpoint;
import org.bechclipse.review.model.checklist.Feature;
import org.bechclipse.review.model.checklist.Scope;


public class ReviewChecklist {
	
	private Map<Object, Object> parentMap = new HashMap<Object, Object>();
	private List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
	private final Checklist checklist;

	public ReviewChecklist(Checklist checklist) {
		
		this.checklist = checklist;
		List<Scope> scopeList = checklist.getScope();
		for (Scope scope : scopeList) {
			
			List<Feature> featureList = scope.getFeature();
			for (Feature feature : featureList) {
				parentMap.put(feature, scope);
				
				
				List<Checkpoint> checkpointList = feature.getCheckpoint();
				for (Checkpoint checkpoint : checkpointList) {
					parentMap.put(checkpoint, feature);
					checkpoints.add(checkpoint);

				}				
			}
		}
	}

	public List<Scope> getScope() {
		return checklist.getScope();
	}

	public Object getParent(Object element) {
		return parentMap.get(element);
	}

	public Collection<? extends Checkpoint> getCheckpoint() {
		return checkpoints;
	}
}
