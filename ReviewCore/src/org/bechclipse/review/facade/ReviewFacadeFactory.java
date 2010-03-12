package org.bechclipse.review.facade;

public class ReviewFacadeFactory {
	
	

	private static ReviewFacade facade;

	public static ReviewFacade getFacade() {

		if(facade == null) {
			facade = new ReviewFacadeImpl();
		}
		
		return facade;
	}

}
