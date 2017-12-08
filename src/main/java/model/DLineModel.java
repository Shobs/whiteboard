package main.java.model;
import java.awt.*;

public class DLineModel extends DShapeModel{
	private Point p1;
	private Point p2;

	public DLineModel(int x1, int y1, int x2, int y2){
		super(x1, y1, x2, y2);
		p1 = new Point((int)this.getX(), (int)this.getY());
		p2 = new Point((int)(this.getX() + this.getWidth()), (int)(this.getY() + this.getHeight()));
	}

	public DLineModel()
	{
		super();
		p1 = new Point((int)this.getX(), (int)this.getY());
		p2 = new Point((int)(this.getX() + this.getWidth()),(int)(this.getY() + this.getHeight()));
	}

	public double getX1(){ return p1.getX(); }

	public double getX2(){ return p2.getX(); }

	public double getY1(){ return p1.getY(); }

	public double getY2(){ return p2.getY(); }
}