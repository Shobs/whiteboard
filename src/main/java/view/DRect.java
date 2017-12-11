package main.java.view;
import main.java.model.*;
import java.awt.*;

public class DRect extends DShape{
	private Graphics g;

	public DRect(DShapeModel model){
		super(model);
		generateKnobs((int)model.getX(),(int)model.getY(),(int)model.getWidth(),(int)model.getHeight(),0);
	}

	@Override
	public void draw(Graphics g){
		this.g = g;
		g.setColor(model.getColor());
		g.fillRect((int)model.getX(), (int)model.getY(), (int)model.getWidth(), (int)model.getHeight());
		generateKnobs((int)model.getX(),(int) model.getY(), (int)model.getWidth(), (int) model.getHeight(),0);
		super.draw(g);
	}

	public void modelChanged(DShapeModel mod){
		isChanged = true;
	}
}