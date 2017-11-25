package main.java.view;

public class DRect extends DShape{
	public DRect(DShapeModel model){
		super.model = model;
	}

	@Override
	public void draw(Graphics g){
		model.setColor(model.getColor());
		model.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
		model.draw(model);
	}
}