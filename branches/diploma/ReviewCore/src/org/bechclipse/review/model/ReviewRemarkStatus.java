package org.bechclipse.review.model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.bechclipse.review.model.remarkstatus.ReviewRemarkStatusType;

public class ReviewRemarkStatus {

	private ReviewRemarkStatusType status;
	private String comment;
	private String user;
	private Calendar time;

	public ReviewRemarkStatus() {		
	}
	
	public ReviewRemarkStatus(ReviewRemarkStatusType status, String comment, String user) {
		this.setStatus(status);
		this.setComment(comment);
		this.setUser(user);
		time = Calendar.getInstance();
	}

	public void setStatus(ReviewRemarkStatusType status) {
		this.status = status;
	}

	@XmlTransient
	public ReviewRemarkStatusType getStatus() {
		return status;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@XmlElement(name="Comment")
	public String getComment() {
		return comment;
	}
	
	@XmlElement(name="Status")
	public ReviewRemarkStatusEnum getReviewRemarkStatus() {
		return status.getReviewRemarkEnum();
	}
	
	public void setReviewRemarkStatus(ReviewRemarkStatusEnum e) {
		status = e.toRemarkStatusType();		
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	@XmlElement(name="Time")
	public Calendar getTime() {
		return time;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@XmlElement(name="User")
	public String getUser() {
		return user;
	}
}
