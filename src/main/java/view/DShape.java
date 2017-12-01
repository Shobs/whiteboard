package main.java.view;
import main.java.model.*;
import java.awt.*;


public class DShape implements ModelListener{
	protected boolean isChanged = false;
	protected DShapeModel model; //Data that represents the view
	protected Rectangle[] knobs = new Rectangle[4]; //Rectangles that indicate the corners of the shape
	protected boolean knobVisibility; //Determines the visbility of the knob to the user
	

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
	
	/*
	public boolean contains(Point p)
	{
		boolean r = getBounds().contains(p);
		
		if(r == false)
		{
		for(Rectangle rec : knobs)
		{
			 r = rec.getBounds().contains(p);
		}
		}
		return r;
	}
	
	*/
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
		
		int width = 8, height = 8;
		double topLeftX = model.getX();
		double topLeftY = model.getY();
		double topRightX = model.getX() + model.getWidth();
		double topRightY = topLeftY;
		double bottomLeftX = topLeftX;
		double bottomLeftY = topLeftY + model.getHeight();
		double bottomRightX = topRightX;
		double bottomRightY = topRightY+model.getHeight();
		//topLeft
		knobs[0] = new Rectangle((int)topLeftX - 4,(int)topLeftY - 4, width, height);
		//topRight
		knobs[1] = new Rectangle((int)topRightX - 4, (int)topRightY - 4, width, height);
		//bottomLeft
		knobs[2] = new Rectangle((int)bottomLeftX - 4, (int)bottomLeftY - 4, width, height);
		//bottomRight
		knobs[3] = new Rectangle((int)bottomRightX - 4, (int)bottomLeftY - 4, width, height);
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
				isChanged = true;
				if(newPoint.getX()-model.getX() < 0)
				{
					model.setX((int) newPoint.getX());
					model.setSize((int)(model.getX()-newPoint.getX()), (int)(newPoint.getY() - model.getY()));
				}else
				{
					model.setX((int) model.getMinX());
					model.setSize((int)(newPoint.getX()-model.getX()), (int)(newPoint.getY() - model.getY()));
				}
				if(newPoint.getY() - model.getY() < 0)
				{
					model.setX((int) newPoint.getY());
					model.setSize((int)(model.getX()-newPoint.getX()), (int)( model.getY()- newPoint.getY() ));
				}else
				{
					model.setY((int) model.getMinY());
					model.setSize((int)(newPoint.getX()-model.getX()), (int)(newPoint.getY() - model.getY()));
				}
				
				
				//model.setSize((int)(newPoint.getX()-model.getX()), (int)(newPoint.getY() - model.getY()));
				System.out.println(model.getX() + "  "+ model.getY());
				
				//model.setBounds((int)model.getBounds().getMinX(),(int) (model.getBounds().getMinY()), (int)(newPoint.getX()-model.getX()), (int)(newPoint.getY() - model.getY()));
				generateKnobs();
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