package main.java.view;
import main.java.model.*;
import java.awt.*;

public class DRect extends DShape{
	public DRect(DShapeModel model){
		super.model = model;
	}

	@Override
	public void draw(Graphics g){
		g.setColor(model.getColor());
		g.fillRect((int)model.getX(), (int)model.getY(), (int)model.getWidth(), (int)model.getHeight());
	}

	/**
	 * @Override
	 */
	public void modelChanged(DShapeModel model){
		
	} 
}