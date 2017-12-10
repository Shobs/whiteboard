package main.java.view;

import main.java.model.*;
import java.awt.*;

import com.sun.javafx.geom.Point2D;

public class DShape implements ModelListener {
	protected boolean isChanged = false;
	protected DShapeModel model; 
	protected Rectangle[] knobs = new Rectangle[4]; 
	protected boolean knobVisibility; 

	public DShape(DShapeModel d){
		model = d;
	}
	
	public void draw(Graphics g) {
		if (knobVisibility) {
			if(model instanceof DLineModel){
				for(int i = 0;  i < 2; i++){
					g.setColor(Color.blue);
					g.fillRect((int) knobs[i].getX(), (int) knobs[i].getY(), (int) knobs[i].getWidth(), (int) knobs[i].getHeight());
				}
			}else{
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
		if (r == false) 
			for (Rectangle rec : knobs) 
				r = rec.getBounds().contains(p);
		return r;
	}

	public Rectangle getBounds() {
		return model.getBounds();
	}
	
	public void delete(){
		model.deleteM();
		model = null;
	}

	public void modelChanged(DShapeModel model) {

	}

	public DShapeModel getModel() {
		return model;
	}

	public int isKnob(Point p) {
		for (int i = 0; i < knobs.length; i++)
			if (knobs[i].contains(p))
				return i;
		return -1;
	}

	public boolean isSelected(Point p) {
		return model.contains(p);
	}

	public void generateKnobs(int x, int y, int width, int height,int anchor) {
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