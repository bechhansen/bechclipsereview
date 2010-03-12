package org.bechclipse.review.persistence;

import java.io.File;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.bechclipse.review.model.IReview;
import org.bechclipse.review.model.Review;
import org.bechclipse.review.model.ReviewRemark;
import org.eclipse.core.resources.IFolder;

public class PersistenceFacadeImpl implements PersistenceFacade {

	public final String rootFolderName = ".review";
	public final String reviewFileName = "ReviewInfo.xml";
	
	@Override
	public Collection<IReview> loadReviews() {
		return null;
	}

	@Override
	public boolean persistReview(Review review) throws Exception {

		boolean isNew = false;
		if (review.getId() == null) {
			review.setId(System.currentTimeMillis());
			isNew = true;
		}

		try {
		
			IFolder folder = review.getProject().getFolder(rootFolderName);
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
			m.marshal(review, new File(xmlFile.getAbsolutePath() + "\\" + reviewFileName));

		} catch (Exception e) {
			throw new Exception("Unable to save review");
		}

		return isNew;
	}

	@Override
	public boolean persistReviewRemark(ReviewRemark remark) {
		return false;
	}

	@Override
	public void deleteReview(Review review) throws Exception {
		IFolder folder = review.getProject().getFolder(rootFolderName);
		
		IFolder reviewFolder = folder.getFolder(String.valueOf(review.getId()));
		reviewFolder.delete(true, null);
		
	}
}

/*
 * 
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
