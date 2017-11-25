package main.java.view;

import java.awt.Graphics;

import main.java.model.DShapeModel;

public class DRect extends DShape{
	public DRect(DShapeModel model){
		super.model = model;
	}

	
	public void draw(Graphics g){
		g.setColor(model.getColor());
		g.fillRect((int)model.getX(),(int) model.getY(),(int) model.getWidth(), (int)model.getHeight());
		
	}

	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}