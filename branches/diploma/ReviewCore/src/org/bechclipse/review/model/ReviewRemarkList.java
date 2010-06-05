package org.bechclipse.review.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ReviewRemarks", namespace = "http://core.review.bechclipse.org")
@XmlAccessorType(XmlAccessType.NONE)
public class ReviewRemarkList {
	
	public ReviewRemarkList() {
		
	}

	private Collection<ReviewRemark> remarks = new ArrayList<ReviewRemark>();

	public void setRemarks(Collection<ReviewRemark> remarks) {
		this.remarks = remarks;
	}	
	
	@XmlElement(name = "ReviewRemark")
	public Collection<ReviewRemark> getRemarks() {
		return remarks;
	}
}
