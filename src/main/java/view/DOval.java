package main.java.view;
import main.java.model.*;
import java.awt.*;

public class DOval extends DShape{
	public DOval(DShapeModel model){
		super.model = model;
	}

	@Override
	public void draw(Graphics g){
		g.setColor(model.getColor());
		g.fillOval((int)model.getX(), (int)model.getY(), (int)model.getWdith(), (int)model.getHeight());
	}

	/**
	 * @Override
	 */
	public void modelChanged(DshapeModel model){
		
	} 
}