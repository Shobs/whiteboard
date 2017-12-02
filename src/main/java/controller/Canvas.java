package main.java.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

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

public class Canvas extends JPanel implements MouseInputListener {
	ArrayList<DShape> shapes;
	DShape selectedShape = null;
	double width;
	double height;
	int selectedKnob = -1;
	boolean change = true;
	int anchor ;

	public Canvas() {
		super();
		shapes = new ArrayList<DShape>();
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.WHITE);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	@Override
	public void repaint() {
		if (shapes != null) {
			for (DShape ds : shapes) {
				if (ds.getIsChanged()) {
					super.repaint();
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (DShape shape : shapes) {
			shape.draw(g);
		}
	}

	public void setSelectedShape(DShape shape) {
		selectedShape = shape;
	}

	public DShape getSelectedShape() {
		return selectedShape;
	}

	@Override
	public void mousePressed(MouseEvent e) {

		Point p = e.getPoint();

		if (selectedShape != null) {
			selectedShape.setKnobVisibility(false);
			selectedShape.setIsChanged(true);
			selectedShape = null;
		}

		for (DShape d : shapes) {
			if (d.isSelected(p)) {
				selectedShape = d;

				selectedKnob = selectedShape.isKnob(e.getPoint());
				if(selectedKnob != -1)
				{
					anchor = getAnchor(selectedKnob);
				}
			}
		}

		if (selectedShape != null) {
			selectedShape.setKnobVisibility(true);
			paintComponent(getGraphics());
			width = e.getX() - selectedShape.getModel().getX();
			height = e.getY() - selectedShape.getModel().getY();
		}
		
		repaint();

	}
	
	public int getAnchor(int knob)
	{
	
		switch (knob) {
		case 0:
			anchor = 3;
			break;
		case 1:
			anchor = 2;
			break;
		case 2:
			anchor = 1;
			break;
		case 3:
			anchor = 0;
			break;

		}
	
	
	
	return anchor;
	}

	public void addShape(DShapeModel model) {
		if (model instanceof DRectModel) {
			DRect rect = new DRect(model);
			shapes.add(rect);
			model.addListener(rect);

		} else if (model instanceof DOvalModel) {
			DOval ov = new DOval(model);
			shapes.add(ov);
			model.addListener(ov);
			;

		} else if (model instanceof DLineModel) {
			DLine ln = new DLine(model);
			shapes.add(ln);
			model.addListener(ln);
			;

		} else if (model instanceof DTextModel) {
			DText tx = new DText(model);
			shapes.add(tx);
			model.addListener(tx);

		} else {
			System.out.println("none of the above");
		}
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (selectedShape != null) {
			selectedKnob = selectedShape.isKnob(e.getPoint());

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		change = true;
		
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		if (selectedShape != null) {
			
			if (selectedKnob != -1) {

				Rectangle r = selectedShape.getKnobs()[anchor];
				int x = (int) r.getCenterX();
				int y = (int) r.getCenterY();
				
				selectedShape.resize(e.getX(), e.getY(), x, y);
				 
			} else {

				moveSelectedShape(e);

			}
			repaint();
		}

	}

	public void moveSelectedShape(MouseEvent e) {
		Point pm = e.getPoint();
		Point ps = new Point();
		ps.setLocation(selectedShape.getBounds().getX(), selectedShape.getBounds().getY());

		selectedShape.getModel().setX((int) (pm.getX() - width));
		selectedShape.getModel().setY((int) (pm.getY() - height));

		selectedShape.generateKnobs((int)selectedShape.getModel().getX(), (int)selectedShape.getModel().getY(), (int)selectedShape.getModel().getWidth(),(int)selectedShape.getModel().getHeight());

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
