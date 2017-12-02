package main.java.view;

import main.java.model.*;
import java.awt.*;

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
		if (knobVisibility) {

			for (Rectangle r : knobs) {
				g.setColor(Color.BLUE);
				g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
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
	 * 
	 * @param p
	 *            is the point to be checked
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

	public void generateKnobs(int x, int y, int width, int height) {

		int w = 8, h = 8;
		double topLeftX = x;
		double topLeftY = y;
		double topRightX = x + width;
		double topRightY = topLeftY;
		double bottomLeftX = topLeftX;
		double bottomLeftY = topLeftY + height;
		double bottomRightX = topRightX;
		double bottomRightY = topRightY + height;
		// topLeft
		knobs[0] = new Rectangle((int) topLeftX - 4, (int) topLeftY - 4, w, h);
		// topRight
		knobs[1] = new Rectangle((int) topRightX - 4, (int) topRightY - 4, w, h);
		// bottomLeft
		knobs[2] = new Rectangle((int) bottomLeftX - 4, (int) bottomLeftY - 4, w, h);
		// bottomRight
		knobs[3] = new Rectangle((int) bottomRightX - 4, (int) bottomLeftY - 4, w, h);
	}

	public void resize(int Xm, int Ym, int Xa, int Ya) {

		model.setX(Math.min(Xm, Xa));
		model.setY(Math.min(Ym, Ya));
		model.setWidth((Math.abs(Xm - Xa)));
		model.setHeight(Math.abs(Ym - Ya));

		if(Xm > Xa && Ym > Ya)
		{
			generateKnobs((int) (model.getX()), (int) model.getY(), (int) model.getWidth(), (int) model.getHeight());
		}else
		{
	
		if (Xm < Xa) {
			if (Ym > Ya) {
				generateKnobs((int) ((Xm + model.getWidth())), Ya, -(int) model.getWidth(), (int) model.getHeight());
			} else {
				generateKnobs((int) (Xm + model.getWidth()), (int) (Ym + model.getHeight()), -(int) model.getWidth(),
						-(int) model.getHeight());
			}
		}else 
		{
			if (Xm > Xa) {
				generateKnobs((int) Xa, (int) (Ym + model.getHeight()), (int) model.getWidth(), -(int) model.getHeight());
			} else {
				generateKnobs((int) (Xm + model.getWidth()), (int) (Ym + model.getHeight()), -(int) model.getWidth(),
						-(int) model.getHeight());
			}
		}
		}
		
		// System.out.println("X "+model.getX());
		// System.out.println("Y "+model.getY());
		// System.out.println("W "+model.getWidth());
		// System.out.println("H "+model.getHeight());

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