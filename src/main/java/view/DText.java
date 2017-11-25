package main.java.view;
import main.java.model.*;
import java.awt.*;

import com.sun.prism.Graphics;

import main.java.model.DShapeModel;

public class DText extends DShape{
	public DText(DShapeModel model){
		super.model = model;
	}

	@Override
	public void draw(Graphics g){

	}

	/**
	 * @Override
	 */
	public void modelChanged(DshapeModel model){
		
	} 
}