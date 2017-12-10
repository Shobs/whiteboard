package main.java.view;
import main.java.model.*;
import java.awt.*;

public class DLine extends DShape{
	private Graphics g;
	private DLineModel lineModel = null;
	public DLine(DShapeModel model){
		super(model);
		lineModel = (DLineModel)model;
		this.knobs = new Rectangle[2];
	}

	@Override
	public void draw(Graphics g){
		this.g = g;
		g.setColor(lineModel.getColor());
		g.drawLine((int)lineModel.getX1(), (int)lineModel.getY1(), (int)lineModel.getX2(), (int)lineModel.getY2());
		generateKnobs((int)model.getX(),(int) model.getY(), (int)model.getWidth(), (int) model.getHeight(),0);
		super.draw(g);
	}

	/**
	 * @Override
	 */
	public void generateKnobs(int x, int y, int width, int height, int anchor){
		knobs[0] = new Rectangle((int)lineModel.getX1(), (int)lineModel.getY1(), 8, 8);
		knobs[1] = new Rectangle((int)lineModel.getX2(), (int)lineModel.getY2(), 8, 8);
	}

	/**
	 * @Override
	 */
	public void modelChanged(DShapeModel model){
		isChanged = true;
	}
}