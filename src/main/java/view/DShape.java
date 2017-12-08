package main.java.view;
import main.java.model.*;
import java.awt.*;


public class DShape implements ModelListener{
	protected boolean isChanged = false;
	protected DShapeModel model; //Data that represents the view
	protected Rectangle[] knobs; //Rectangles that indicate the corners of the shape
	protected boolean knobVisibility; //Determines the visbility of the knob to the user
	protected static final int KNOB_SIZE = 8;

	/**
	 * Method that draws object on canvas
	 */
	public void draw(Graphics g){
		if(knobVisibility){

			for(Rectangle r : knobs){
				g.setColor(Color.BLUE);
				g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
			}
		}

	}

	public boolean getIsChanged()
	{
		return isChanged;
	}

	public void setIsChanged(boolean c)
	{
		isChanged = c;
	}

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

	public boolean isSelected(Point p)
	{
		return model.contains(p);
	}



	public void generateKnobs(){
		knobs = new Rectangle[4];
		double topLeftX = model.getX();
		double topLeftY = model.getY();
		double topRightX = model.getX() + model.getWidth();
		double topRightY = topLeftY;
		double bottomLeftX = topLeftX;
		double bottomLeftY = topLeftY + model.getHeight();
		double bottomRightX = topRightX;
		double bottomRightY = topRightY+model.getHeight();
		//topLeft
		knobs[0] = new Rectangle((int)topLeftX - KNOB_SIZE/2, (int)topLeftY - KNOB_SIZE/2, KNOB_SIZE, KNOB_SIZE);
		//topRight
		knobs[1] = new Rectangle((int)topRightX - KNOB_SIZE/2, (int)topRightY - KNOB_SIZE/2, KNOB_SIZE, KNOB_SIZE);
		//bottomLeft
		knobs[2] = new Rectangle((int)bottomLeftX - KNOB_SIZE/2, (int)bottomLeftY - KNOB_SIZE/2, KNOB_SIZE, KNOB_SIZE);
		//bottomRight
		knobs[3] = new Rectangle((int)bottomRightX - KNOB_SIZE/2, (int)bottomLeftY - KNOB_SIZE/2, KNOB_SIZE, KNOB_SIZE);
	}

	public void resize(int knob, Point newPoint){
		switch(knob){
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;

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
		this.isChanged = true;
	}

	public boolean getKnobVisibility(){
		return knobVisibility;
	}
}