package main.java.view;
import main.java.model.*;
import java.awt.*;


public abstract class DShape implements ModelListener{
	protected DShapeModel model; //Data that represents the view
	protected Rectangle[] knobs = new Rectangle[4]; //Rectangles that indicate the corners of the shape
	protected boolean knobVisibility; //Determines the visbility of the knob to the user

	/**
	 * Method that draws object on canvas
	 */
	public abstract void draw(Graphics g);
	
	/**
	 * @return a Rectangle object representation of the shape
	 */
	public Rectangle getBounds(){
		return model.getBounds();
	}

	/**
	 * @Override
	 */
	public void modelChanged(DShapeModel model){
		
	} 


	//Setters and Getters
	public DShapeModel getModel(){
		return model;
	}

	/**
	 * Checks if point on canvas is on a knob
	 * @param  p is the point to be checked
	 * @return the index of the knob which contains the point
	 */
	public int isKnob(Point p){
		for(int i = 0; i < knobs.length; i++)
			if(knobs[i].contains(p))
				return i;
		return -1; 
	}	


	public void generateKnobs(){
		int width = 8, height = 8;
		double topLeftX = model.getX();
		double topLeftY = model.getY();
		double topRightX = model.getX() + model.getWidth();
		double topRightY = topLeftY;
		double bottomLeftX = topLeftX;
		double bottomLeftY = model.getY() - model.getHeight();
		double bottomRightX = topRightX;
		double bottomRightY = bottomLeftY;
		//topLeft
		knobs[0] = new Rectangle((int)topLeftX - 4,(int)topLeftY + 4, width, height);
		//topRight
		knobs[1] = new Rectangle((int)topRightX - 4, (int)topRightY + 4, width, height);
		//bottomLeft
		knobs[2] = new Rectangle((int)bottomLeftX - 4, (int)bottomLeftY + 4, width, height);
		//bottomRight
		knobs[3] = new Rectangle((int)bottomRightX - 4, (int)bottomLeftY + 4, width, height);

		for(int i = 0; i < knobs.length; i++)
		{
//			knobs[i].fill(Color.BLUE);
		}
	}

	public void resize(int knob, Point newPoint){
		switch(knob){
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:

		}
	}


	public void setModel(DShapeModel model){
		this.model = model;
	}

	public Rectangle[] getKnobs(){
		return knobs;
	}

	public void setKnobs(Rectangle[] knobs){
		this.knobs = knobs;
	}

	public void setKnobVisibility(boolean visibility){
		knobVisibility = visibility;
	}

	public boolean getKnobVisibility(){
		return knobVisibility;
	}
}