package main.java.view;
import main.java.model.*;
import java.awt.*;

public class DLine extends DShape{
	private Graphics g;
	public DLine(DShapeModel model){
		super.model = model;
	}

	@Override
	public void draw(Graphics g){
		this.g = g;
		g.setColor(model.getColor());
		g.drawLine((int)model.getX1(), (int)model.getY1(), (int)model.getX2(), (int)model.getY2());
	}



	/**
	 * @Override
	 */
	public void generateKnobs(){
		this.knobs = new Rectangle[2];
		knobs[0] = new Rectangle(model.getX1(), model.getY1(), DShape.KNOB_SIZE, DShape.KNOB_SIZE);
		knobs[1] = new Rectangle(model.getX2(), model.getY2(), DShape.KNOB_SIZE, DShape.KNOB_SIZE);
	}

	/**
	 * @Override
	 */
	public void modelChanged(DShapeModel model){
		
	} 
}