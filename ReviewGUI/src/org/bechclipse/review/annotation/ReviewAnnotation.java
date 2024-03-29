package org.bechclipse.review.annotation;
/*******************************************************************************
 * Copyright (c) 2006 Mountainminds GmbH & Co. KG
 * This software is provided under the terms of the Eclipse Public License v1.0
 * See http://www.eclipse.org/legal/epl-v10.html.
 *
 * $Id: CoverageAnnotation.java 12 2006-08-28 20:07:13Z mho $
 ******************************************************************************/


import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;

public class ReviewAnnotation extends Annotation {
  
  private static final String REVIEW_ANNOTATION = "org.bechclipse.review.gui.ReviewAnnotation";
  
  private final Position position;
  
  public ReviewAnnotation(int offset, int length) {
    super(REVIEW_ANNOTATION, false, null);
    position = new Position(offset, length);
  }
  
  public Position getPosition() {
    return position;
  }
  
  

}
