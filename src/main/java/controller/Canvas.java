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
		if (selectedShape != null) {
			if (selectedShape.getIsChanged()) {
				super.repaint();
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
		}
		for (DShape d : shapes) {
			if (d.isSelected(p)) {
				selectedShape = d;
				selectedShape.setKnobVisibility(true);
				paintComponent(getGraphics());
				width = e.getX() - selectedShape.getModel().getX();
				height = e.getY() - selectedShape.getModel().getY();
				selectedKnob = selectedShape.isKnob(e.getPoint());
			}
		}
		
		if(selectedKnob == -1)
		{
		selectedShape.setKnobVisibility(false);
		selectedKnob = -1;
		selectedShape = null;
		}
		repaint();

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
		if(selectedShape != null)
		{
		selectedKnob = selectedShape.isKnob(e.getPoint());
		}
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
	public void mouseDragged(MouseEvent e) {
		if (selectedShape != null) {

		
			boolean changed = false;
			if (selectedKnob != -1) {
				changed = true;
				selectedShape.resize(selectedKnob, e.getPoint());
			}  
			else {
				
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

		selectedShape.generateKnobs();
		
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
