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
import main.java.view.ModelListener;

public class Canvas extends JPanel implements MouseInputListener, ModelListener {
	ArrayList<DShape> shapes;
	DShape selectedShape = null;
	double width;
	double height;
	int selectedKnob = -1;
	boolean change = true;
	int anchor;
	Controls controls;
	

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
				super.repaint();
		}
		super.repaint();
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
			selectedKnob = selectedShape.isKnob(e.getPoint());
			if (selectedKnob != -1) {
				if(selectedShape instanceof DLine)
				{
					anchor = (selectedKnob == 1)?0:1;
				}else
				{
					anchor = getAnchor(selectedKnob);
				}

			} else {
				selectedShape.setKnobVisibility(false);
				selectedShape.setIsChanged(true);
				selectedShape = null;
			}
		}

		for (DShape d : shapes) {
			if (d.isSelected(p)) {
				selectedShape = d;
			}

		}

		if (selectedShape != null) {
			selectedShape.setKnobVisibility(true);
			paintComponent(getGraphics());
			width = e.getX() - selectedShape.getModel().getX();
			height = e.getY() - selectedShape.getModel().getY();

		}
		
		controls.reDraw();
		repaint();
	}

	public int getAnchor(int knob) {
		anchor = -1;
		
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
			model.addListener(this);
			model.addListener(rect);

		} else if (model instanceof DOvalModel) {
			DOval ov = new DOval(model);
			shapes.add(ov);
			model.addListener(this);
			model.addListener(ov);
			;

		} else if (model instanceof DLineModel) {
			DLine ln = new DLine(model);
			shapes.add(ln);
			model.addListener(this);
			model.addListener(ln);
			;

		} else if (model instanceof DTextModel) {
			DText tx = new DText(model);
			shapes.add(tx);
			model.addListener(this);
			model.addListener(tx);

		} else {
			System.out.println("none of the above");
		}
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		if (selectedShape != null) {
//			selectedKnob = selectedShape.isKnob(e.getPoint());
//
//		}
		//repaint();
		
		Point p = e.getPoint();

		if (selectedShape != null) {
			selectedKnob = selectedShape.isKnob(e.getPoint());
			if (selectedKnob != -1) {
				anchor = getAnchor(selectedKnob);

			} else {
				selectedShape.setKnobVisibility(false);
				selectedShape.setIsChanged(true);
				selectedShape = null;
			}
		}

		for (DShape d : shapes) {
			if (d.isSelected(p)) {
				selectedShape = d;
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
//				selectedKnob = selectedShape.isKnob(e.getPoint());
				if(selectedShape instanceof DLine)
				{
					anchor = (selectedKnob == 1)?0:1;

					if(anchor == 0)
					{
						((DLineModel)selectedShape.getModel()).setX2((int) (e.getX()));
						((DLineModel)selectedShape.getModel()).setY2((int) (e.getY()));
					}else
					{
						((DLineModel)selectedShape.getModel()).setX((int) (e.getX()));
						((DLineModel)selectedShape.getModel()).setY((int) (e.getY()));
					}
				}else
				{
					anchor = getAnchor(selectedKnob);
				
				Rectangle r = selectedShape.getKnobs()[anchor];
				int x = (int) r.getCenterX();
				int y = (int) r.getCenterY();
				selectedShape.resize(e.getX(), e.getY(), x, y);
				 
				selectedShape.generateKnobs((int) selectedShape.getModel().getX(),
						(int) selectedShape.getModel().getY(), (int) selectedShape.getModel().getWidth(),
						(int) selectedShape.getModel().getHeight(), anchor);
				}
				
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
		
		if(selectedShape instanceof DLine)
		{
			int differenceBetX = (int) (((DLineModel)selectedShape.getModel()).getX2()-((DLineModel)selectedShape.getModel()).getX1());
			int differenceBetY = (int) (((DLineModel)selectedShape.getModel()).getY2()-((DLineModel)selectedShape.getModel()).getY1());
			selectedShape.getModel().setX((int) (pm.getX() - width));
			selectedShape.getModel().setY((int) (pm.getY() - height));
			((DLineModel)selectedShape.getModel()).setX2((int) (((DLineModel)selectedShape.getModel()).getX1()+differenceBetX));
			((DLineModel)selectedShape.getModel()).setY2((int) (((DLineModel)selectedShape.getModel()).getY1()+differenceBetY));
			

		}
		selectedShape.getModel().setX((int) (pm.getX() - width));
		selectedShape.getModel().setY((int) (pm.getY() - height));

//		selectedShape.generateKnobs((int) selectedShape.getModel().getX(), (int) selectedShape.getModel().getY(),
//				(int) selectedShape.getModel().getWidth(), (int) selectedShape.getModel().getHeight(),0);
//		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modelChanged(DShapeModel model) {
		repaint();

	}
	
	
	
	public void changeFont(String nType)
	{
		if(selectedShape != null && (selectedShape instanceof DText))
		{
			DTextModel text = (DTextModel)selectedShape.getModel();
			text.setType(nType);
		}
	}
	public void changeContent(String nText) 
	{
		if(selectedShape != null && (selectedShape instanceof DText))
		{
			((DTextModel)selectedShape.getModel()).setStr(nText);
//			DTextModel text = (DTextModel)selectedShape.getModel();
//			text.setStr(nText);
		}
	}
	
	public void setControls(Controls c)
	{
		this.controls = c;
	}
	
	
}
