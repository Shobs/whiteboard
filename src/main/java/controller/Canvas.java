package main.java.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.model.DLineModel;
import main.java.model.DOvalModel;
import main.java.model.DRectModel;
import main.java.model.DShapeModel;
import main.java.model.DTextModel;
import main.java.view.DLine;
import main.java.view.DOval;
import main.java.view.DRect;
import main.java.view.DShape;
import main.java.view.DText;

public class Canvas extends JPanel {
	ArrayList<DShape> shapes;
	DShape selectedShape;

	public Canvas() {
		super();
		shapes = new ArrayList<DShape>();
		selectedShape = shapes.get(shapes.size() - 1);
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.WHITE);
	}

	public void paintComponents()
	{
		for(DShape shape : shapes)
		{
			shape.draw();
		}
	}
	public void setSelectedShape(DShape shape) {
		selectedShape = shape;
	}

	public DShape getSelectedShape() {
		return selectedShape;
	}

	
	public void addShape(DShapeModel model) {
		if (model instanceof DRectModel) {
			shapes.add(new DRect(model));
			
		} else if (model instanceof DOvalModel) {
			shapes.add(new DOval(model));
			
		} else if (model instanceof DLineModel) {
			shapes.add(new DLine(model));
			
		} else if (model instanceof DTextModel) {
			shapes.add(new DText(model));
			
		} else {
			System.out.println("none of the above");
		}
		this.repaint();
	}
}
