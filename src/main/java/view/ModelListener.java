package main.java.view;

import main.java.model.DShapeModel;

public interface ModelListener{
	/** 
	 * Method is invoked when there is a change in a shape
	 * @param model is the shape that is changed 
	 */
	public void modelChanged(DShapeModel model);
}