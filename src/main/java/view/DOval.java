package main.java.view;
import main.java.model.*;
import java.awt.*;

public class DOval extends DShape{
	private Graphics g ;
	public DOval(DShapeModel model){
		super(model);
		generateKnobs((int)model.getX(),(int)model.getY(),(int)model.getWidth(),(int)model.getHeight(),0);
	}

	@Override
	public void draw(Graphics g){
		this.g = g;
		g.setColor(model.getColor());
		g.fillOval((int)model.getX(), (int)model.getY(), (int)model.getWidth(), (int)model.getHeight());
		generateKnobs((int)model.getX(),(int) model.getY(), (int)model.getWidth(), (int) model.getHeight(),0);
		super.draw(g);
	}

		
	/**
	 * @Override
	 */
	public void modelChanged(DShapeModel model){
		isChanged = true;
	} 
}