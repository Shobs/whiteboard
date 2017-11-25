package main.java.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class Canvas extends JPanel implements MouseListener {
	ArrayList<DShape> shapes;
	DShape selectedShape=null;

	public Canvas() {
		super();
		shapes = new ArrayList<DShape>();
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.WHITE);
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(DShape shape : shapes)
		{
			shape.draw(g);
			
		}
		
	}
	public void setSelectedShape(DShape shape) {
		selectedShape = shape;
	}

	public DShape getSelectedShape() {
		return selectedShape;
	}
	
	public void mousePressed(MouseEvent e) {
		
		Point p = e.getPoint();
		for(DShape d: shapes)
		{
			if(d.isSelected(p))
			{
				if(selectedShape != null){
					selectedShape.setKnobVisibility(false);
					selectedShape.getModel().setColor(Color.GRAY);
				}
				selectedShape = d;
				selectedShape.setKnobVisibility(true);
//				selectedShape = d;
				d.getModel().setColor(Color.red);
				paintComponent(getGraphics());
			}
		}
		
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

	@Override
	public void mouseClicked(MouseEvent e) {
		
		Point p = e.getPoint();
		for(DShape d: shapes)
		{
			if(d.isSelected(p))
			{
				
				selectedShape = d;
				selectedShape.getModel().setColor(Color.red);
				paintComponent(getGraphics());
			}
		}
		System.out.println("it is working");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
