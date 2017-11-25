package main.java.view;

public class DRect extends DShape{
	public DRect(DShapeModel model){
		super.model = model;
	}

	@Override
	public void draw(){
		super.model.fill(super.model.getColor());
		super.model.draw(super.model);
	}
}