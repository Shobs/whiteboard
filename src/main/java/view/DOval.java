package main.java.view;

public class DOval extends DShape{
	public DOval(DShapeModel model){
		super.model = model;
	}

	@Override
	public void draw(Graphics g){
		model.setColor(model.getColor());
		model.fillOval(model.getX(), model.getY(), model.getWidth(), model.getHeight());
		model.draw(model);
	}
}