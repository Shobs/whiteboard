package main.java.view;

import com.sun.prism.Graphics;
import java.awt.*;
import main.java.model.DShapeModel;

public class DOval extends DShape{
	public DOval(DShapeModel model){
		super.model = model;
	}
	
	
	public void draw(Graphics g){
		((DShapeModel) g).setColor(model.getColor());
		g.fillOval((int)model.getX(),(int) model.getY(),(int) model.getWidth(), (int)model.getHeight());
		
	}
	
	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	}
}