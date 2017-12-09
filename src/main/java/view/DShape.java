package main.java.view;

import main.java.model.*;
import java.awt.*;

import com.sun.javafx.geom.Point2D;

public class DShape implements ModelListener {
	protected boolean isChanged = false;
	protected DShapeModel model; // Data that represents the view
	protected Rectangle[] knobs = new Rectangle[4]; // Rectangles that indicate
													// the corners of the shape
	protected boolean knobVisibility; // Determines the visbility of the knob to
										// the user

	/**
	 * Method that draws object on canvas
	 */
	public void draw(Graphics g) {
		System.out.println("In DShape draw");
		if (knobVisibility) {
			if(model instanceof DLineModel)
			{
				for(int i = 0;  i < 2; i++)
				{
					g.setColor(Color.blue);
					 g.fillRect((int) knobs[i].getX(), (int) knobs[i].getY(), (int) knobs[i].getWidth(), (int) knobs[i].getHeight());
				}
			}else
			{
			for (Rectangle r : knobs) {
				
				g.setColor(Color.BLUE);
			 g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
			}
			}
		}

	}

	public boolean getIsChanged() {
		return isChanged;
	}

	public void setIsChanged(boolean c) {
		isChanged = c;
	}

	public boolean contains(Point p) {
		boolean r = getBounds().contains(p);

		if (r == false) {
			for (Rectangle rec : knobs) {
				r = rec.getBounds().contains(p);
			}
		}
		return r;
	}

	/**
	 * @return a Rectangle object representation of the shape
	 */
	public Rectangle getBounds() {
		return model.getBounds();
	}

	public void delete(){
		model.deleteM();
		model = null;
	}

	/**
	 * @Override
	 */
	public void modelChanged(DShapeModel model) {

	}

	// Setters and Getters
	public DShapeModel getModel() {
		return model;
	}

	/**
	 * Checks if point on canvas is on a knob
	 * @param p is the point to be checked
	 * @return the index of the knob which contains the point
	 */
	public int isKnob(Point p) {
		
		for (int i = 0; i < knobs.length; i++)
			if (knobs[i].contains(p))
				return i;
		return -1;
	}

	public boolean isSelected(Point p) {
		return model.contains(p);
	}

	public void generateKnobs(int x, int y, int width, int height, int anchor) {
		int w = 8, h = 8;
		double topLeftX;
		double topLeftY;
		double topRightX;
		double topRightY;
		double bottomLeftX;
		double bottomLeftY;
		double bottomRightX;
		double bottomRightY;
		
		
		 topLeftX = x;
		 topLeftY = y;
		 topRightX = x + width;
		 topRightY = topLeftY;
		 bottomLeftX = topLeftX;
		 bottomLeftY = topLeftY + height;
		 bottomRightX = topRightX;
		 bottomRightY = topRightY + height;
		
		// topLeft
		knobs[0] = new Rectangle((int) topLeftX - 4, (int) topLeftY - 4, w, h);
		// topRight
		knobs[1] = new Rectangle((int) topRightX - 4, (int) topRightY - 4, w, h);
		// bottomLeft
		knobs[2] = new Rectangle((int) bottomLeftX - 4, (int) bottomLeftY - 4, w, h);
		// bottomRight
		knobs[3] = new Rectangle((int) bottomRightX - 4, (int) bottomLeftY - 4, w, h);
	}

	public void resize(int X, int Y, int Xa, int Ya) {
		model.setX(Math.min(X, Xa));
		model.setY(Math.min(Y, Ya));
		model.setWidth((Math.abs(X - Xa)));
		model.setHeight(Math.abs(Y - Ya));
		
		model.notifyListeners();
		
	}

	public void setModel(DShapeModel model) {
		this.model = model;
	}

	public Rectangle[] getKnobs() {
		return knobs;
	}

	public void setKnobs(Rectangle[] knobs) {
		this.knobs = knobs;
	}

	public void setKnobVisibility(boolean visibility) {
		knobVisibility = visibility;
		this.isChanged = true;
	}

	public boolean getKnobVisibility() {
		return knobVisibility;
	}
}