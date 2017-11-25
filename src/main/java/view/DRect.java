package main.java.view;

public class DRect extends DShape{
	public DRect(DShapeModel model){
		super.model = model;
	}

	@Override
	public void draw(Graphics g){
		g.setColor(model.getColor());
	/**
	 * g.setColor(getColor())
	 * Rectangle rect = super.getModel().getRectangle();
	 * g.fillRect(rect.x,rect.y,rect.width,rect.height);
	 * super.draw(g);
	 */
	}
}