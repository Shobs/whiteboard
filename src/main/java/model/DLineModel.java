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

	public void setX1(int x)
	{
		p1.setLocation(x, p1.getY());
		updateSize();
	}

	public void setY1(int y)
	{
		p1.setLocation(p1.getX(), y);
		updateSize();
	}

	public void setX2(int x)
	{
		p2.setLocation(x, p2.getY());
		updateSize();
	}

	public void setY2(int y)
	{
		p2.setLocation(p2.getX(), y);
		updateSize();
	}

	public void updateSize(){
		super.setX(Math.min((int)p1.getX(), (int)p2.getX()));
		super.setY(Math.min((int)p1.getY(), (int)p2.getY()));
		super.setWidth(Math.abs((int)p1.getX() - (int)p2.getX()));
		super.setHeight(Math.abs((int)p1.getY() - (int)p2.getY()));
	}

	public double getX1(){ return p1.getX(); }

	public double getX2(){ return p2.getX(); }

	public double getY1(){ return p1.getY(); }

	public double getY2(){ return p2.getY(); }
}