package org.bechclipse.review.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sletmig {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pattern p = Pattern.compile("http://* ");
        
		String text = "Et eller andet http://www.google.com eller noget andet. http://test.dk";
		Matcher m = p.matcher(text);
        
        boolean result = m.find();
        while(result) {
            m.group();
            result = m.find();
        }

	}

}
