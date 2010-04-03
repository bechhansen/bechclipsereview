package org.bechclipse.review.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.bechclipse.review.model.Constants;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewChecklist;
import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.model.checklist.Checklist;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

public class PersistenceFacadeImpl implements PersistenceFacade {

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Review> loadReviews(IProject project) throws Exception {

		ArrayList<Review> result = new ArrayList<Review>();

		try {
			JAXBContext jc = JAXBContext.newInstance(Review.class);
			Unmarshaller um = jc.createUnmarshaller();
			
			JAXBContext jcChecklist = JAXBContext.newInstance("org.bechclipse.review.model.checklist");
			Unmarshaller umChecklist = jcChecklist.createUnmarshaller();

			IFolder folder = project.getFolder(Constants.rootFolderName);
			if (folder.exists()) {

				File f = new File(folder.getLocationURI());

				String[] files = f.list();
				for (String fileName : files) {
					File file = new File(f.getAbsolutePath() + "\\" + fileName + "\\" + Constants.reviewFileName);
					if(file.exists()) {
						Review review = (Review) um.unmarshal(file);
						review.setProject(project);
						result.add(review);
						
						File checklistFile = new File(f.getAbsolutePath() + "\\" + fileName + "\\" + Constants.checklistFileName);
						if(checklistFile.exists()) {
							
							try {							
								JAXBElement<Checklist> element = (JAXBElement<Checklist>) umChecklist.unmarshal(checklistFile);
								Checklist value = element.getValue();
								review.setChecklist(new ReviewChecklist(value));
							} catch (JAXBException e) {
								
							}
						}
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to read review XML");
		}

		return result;
	}

	@Override
	public boolean persistReview(Review review) throws Exception {

		boolean isNew = false;
		if (review.getId() == null) {
			review.setId(System.currentTimeMillis());
			isNew = true;
		}

		try {

			IFolder folder = review.getProject().getFolder(Constants.rootFolderName);
			if (!folder.exists()) {
				folder.create(true, true, null);
			}

			IFolder reviewFoler = folder.getFolder(String.valueOf(review.getId()));
			if (!reviewFoler.exists()) {
				reviewFoler.create(true, true, null);
			}

			JAXBContext jc = JAXBContext.newInstance(Review.class);
			Marshaller m = jc.createMarshaller();

			File xmlFile = new File(reviewFoler.getLocationURI());
			m.marshal(review, new File(xmlFile.getAbsolutePath() + "\\" + Constants.reviewFileName));

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to save review XML");
		}

		return isNew;
	}

	@Override
	public boolean persistReviewRemark(ReviewRemark remark) {
		return false;
	}

	@Override
	public void deleteReview(Review review) throws Exception {
		IFolder folder = review.getProject().getFolder(Constants.rootFolderName);

		IFolder reviewFolder = folder.getFolder(String.valueOf(review.getId()));
		reviewFolder.delete(true, null);

	}
}
