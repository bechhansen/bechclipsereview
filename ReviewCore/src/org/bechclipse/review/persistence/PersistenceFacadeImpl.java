package org.bechclipse.review.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.bechclipse.review.model.Constants;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewChecklist;
import org.bechclipse.review.model.ReviewProgress;
import org.bechclipse.review.model.ReviewRemark;
import org.bechclipse.review.model.ReviewRemarkList;
import org.bechclipse.review.model.checklist.Checklist;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

public class PersistenceFacadeImpl implements PersistenceFacade {

	private Map<String, ReviewRemarkList> remarkCache = new HashMap<String, ReviewRemarkList>();

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Review> loadReviews(IProject project) throws Exception {

		ArrayList<Review> result = new ArrayList<Review>();

		try {
			JAXBContext reviewJc = JAXBContext.newInstance(Review.class);
			Unmarshaller reviewUm = reviewJc.createUnmarshaller();

			IFolder folder = project.getFolder(Constants.REVIEW_ROOTFOLDERNAME);
			if (folder.exists()) {

				File f = new File(folder.getLocationURI());

				String[] files = f.list();
				for (String fileName : files) {
					File file = new File(f.getAbsolutePath() + "\\" + fileName + "\\" + Constants.REVIEWFILENAME);
					if (file.exists()) {
						
						//Read review
						Review review = (Review) reviewUm.unmarshal(file);
						review.setProject(project);
						result.add(review);

						//Read checkliste
						File checklistFile = new File(f.getAbsolutePath() + "\\" + fileName + "\\" + Constants.CHECKLISTFILENAME);
						if (checklistFile.exists()) {

							try {
								JAXBContext jcChecklist = JAXBContext.newInstance("org.bechclipse.review.model.checklist");
								Unmarshaller umChecklist = jcChecklist.createUnmarshaller();
								JAXBElement<Checklist> element = (JAXBElement<Checklist>) umChecklist.unmarshal(checklistFile);
								Checklist value = element.getValue();
								review.setChecklist(new ReviewChecklist(value));
							} catch (JAXBException e) {

							}
						}

						//Read progress
						String username = System.getenv("USERNAME").toLowerCase();
						File progressFile = new File(f.getAbsolutePath() + "\\" + fileName + "\\" + Constants.PROGRESSFILE_PREFIX + username + ".xml");
						if (progressFile.exists()) {

							JAXBContext progressJc = JAXBContext.newInstance(ReviewProgress.class);
							Unmarshaller progressUm = progressJc.createUnmarshaller();

							ReviewProgress progress = (ReviewProgress) progressUm.unmarshal(progressFile);
							review.setProgress(progress);
						}

						//Read remarks
						JAXBContext remarkJc = JAXBContext.newInstance(ReviewRemarkList.class);
						Unmarshaller remarkUm = remarkJc.createUnmarshaller();

						File remarkFiles = new File(f.getAbsolutePath() + "\\" + fileName);
						String[] array = remarkFiles.list();
						Collection<ReviewRemark> remarkCollection = new ArrayList<ReviewRemark>();
						for (String string : array) {

							if (string.startsWith(Constants.REVIEWFILE_PREFIX)) {
								File rmarkFile = new File(f.getAbsolutePath() + "\\" + fileName + "\\" + string);

								String user = string.replace(Constants.REVIEWFILE_PREFIX, "").replace(".xml", "");
								ReviewRemarkList remarks = (ReviewRemarkList) remarkUm.unmarshal(rmarkFile);

								remarkCache.put(user.toLowerCase(), remarks);
								remarkCollection.addAll(remarks.getRemarks());
							}
						}

						// Set parent for each remark
						for (ReviewRemark reviewRemark : remarkCollection) {
							reviewRemark.setParent(review);
						}
						review.setReviewRemarks(remarkCollection);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to read review XML");
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean persistReview(Review review) throws Exception {

		boolean isNew = false;
		if (review.getId() == null) {
			review.setId(System.currentTimeMillis());
			isNew = true;
		}

		try {
			IFolder folder = review.getProject().getFolder(Constants.REVIEW_ROOTFOLDERNAME);
			if (!folder.exists()) {
				folder.create(true, true, null);
			}

			IFolder reviewFolder = folder.getFolder(String.valueOf(review.getId()));
			if (!reviewFolder.exists()) {
				reviewFolder.create(true, true, null);
			}

			File reviewDir = new File(reviewFolder.getLocationURI());

			if (isNew) {
				//String username = System.getenv("USERNAME");
				InputStream in = getClass().getResourceAsStream("/xml/" + Constants.CHECKLISTFILENAME);
				File checklisteFile = new File(reviewDir.getAbsolutePath() + "\\" + Constants.CHECKLISTFILENAME);
				FileOutputStream fos = new FileOutputStream(checklisteFile);
				int val;
				while ((val = in.read()) != -1) {
					fos.write(val);
				}
				fos.close();
				in.close();

				
				if (checklisteFile.exists()) {
										
					JAXBContext jcChecklist = JAXBContext.newInstance("org.bechclipse.review.model.checklist");
					Unmarshaller umChecklist = jcChecklist.createUnmarshaller();
					JAXBElement<Checklist> element = (JAXBElement<Checklist>) umChecklist.unmarshal(checklisteFile);
					Checklist value = element.getValue();
					review.setChecklist(new ReviewChecklist(value));					
				}
			}

			JAXBContext jc = JAXBContext.newInstance(Review.class);
			Marshaller m = jc.createMarshaller();
			m.marshal(review, new File(reviewDir.getAbsolutePath() + "\\" + Constants.REVIEWFILENAME));

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to save review XML");
		}

		return isNew;
	}

	@Override
	public boolean persistReviewRemark(ReviewRemark remark) throws Exception {

		boolean isNew = false;
		if (remark.getId() == null) {
			remark.setId(System.currentTimeMillis());
			isNew = true;
		}

		try {
			String user = remark.getUser().toLowerCase();

			ReviewRemarkList reviewRemarkList = remarkCache.get(user);
			if (reviewRemarkList == null) {
				reviewRemarkList = new ReviewRemarkList();
				remarkCache.put(user, reviewRemarkList);
			}

			if (isNew) {
				reviewRemarkList.getRemarks().add(remark);
			}

			JAXBContext jc = JAXBContext.newInstance(ReviewRemarkList.class);
			Marshaller m = jc.createMarshaller();

			Review review = remark.getParent();

			IFolder folder = review.getProject().getFolder(Constants.REVIEW_ROOTFOLDERNAME);
			IFolder reviewFoler = folder.getFolder(String.valueOf(remark.getParent().getId()));
			if (!reviewFoler.exists()) {
				reviewFoler.create(true, true, null);
			}

			File xmlFile = new File(reviewFoler.getLocationURI());
			m.marshal(reviewRemarkList, new File(xmlFile.getAbsolutePath() + "\\" + Constants.REVIEWFILE_PREFIX + user.toLowerCase() + ".xml"));

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to save remark XML");
		}

		return isNew;
	}

	@Override
	public void deleteReview(Review review) throws Exception {
		IFolder folder = review.getProject().getFolder(Constants.REVIEW_ROOTFOLDERNAME);

		IFolder reviewFolder = folder.getFolder(String.valueOf(review.getId()));
		reviewFolder.delete(true, null);

	}

	@Override
	public void deleteReviewRemark(ReviewRemark remark) throws Exception {
		try {
			String user = remark.getUser().toLowerCase();

			ReviewRemarkList reviewRemarkList = remarkCache.get(user);
			if (reviewRemarkList != null) {

				reviewRemarkList.getRemarks().remove(remark);

				Review review = remark.getParent();

				IFolder folder = review.getProject().getFolder(Constants.REVIEW_ROOTFOLDERNAME);
				IFolder reviewFoler = folder.getFolder(String.valueOf(remark.getParent().getId()));

				File xmlFile = new File(reviewFoler.getLocationURI());
				File file = new File(xmlFile.getAbsolutePath() + "\\" + Constants.REVIEWFILE_PREFIX + user.toLowerCase() + ".xml");

				if (reviewRemarkList.getRemarks().isEmpty()) {
					file.delete();
				} else {
					JAXBContext jc = JAXBContext.newInstance(ReviewRemarkList.class);
					Marshaller m = jc.createMarshaller();
					m.marshal(reviewRemarkList, file);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to delete remark XML");
		}
	}

	@Override
	public void persistProgress(ReviewProgress progress) throws Exception {
		try {
			JAXBContext jc = JAXBContext.newInstance(ReviewProgress.class);
			Marshaller m = jc.createMarshaller();

			Review review = progress.getParent();

			IFolder folder = review.getProject().getFolder(Constants.REVIEW_ROOTFOLDERNAME);
			IFolder reviewFoler = folder.getFolder(String.valueOf(review.getId()));
			if (!reviewFoler.exists()) {
				reviewFoler.create(true, true, null);
			}

			File xmlFile = new File(reviewFoler.getLocationURI());
			String username = System.getenv("USERNAME").toLowerCase();
			m.marshal(progress, new File(xmlFile.getAbsolutePath() + "\\" + Constants.PROGRESSFILE_PREFIX + username + ".xml"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to save review XML");
		}

	}

}
