package org.bechclipse.review.view.labelprovider;

import org.bechclipse.review.model.ReviewRemark;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ReviewRemarkLabelProvider implements ITableLabelProvider {	

	@Override
	public String getColumnText(Object element, int columnIndex) {
		
		ReviewRemark remark = (ReviewRemark) element;
		
		switch (columnIndex) {
		case 0:
			
			if(remark.getType() == null) {
				return "-";
			}
			
			return remark.getType().toString();
			
		case 1:
			
			if(remark.getScope() == null) {
				return "-";
			}
			
			return remark.getScope().toString();
			
		case 2:	
			
			
			return remark.getFile();			
			
		case 3:
			return remark.getDescription();
			
			
		case 4:
			return remark.getSolution();
			
		case 5:
			return remark.getUser();
			
			
		case 6:
			
			if(remark.getSeverity() == null) {
				return "-";
			}
			
			return remark.getSeverity().toString();
			
			
			

		default:
			return "";
		}
	}

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {
	
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}


	@Override
	public Image getColumnImage(Object element, int columnIndex) {		
		return null;
	}
}
