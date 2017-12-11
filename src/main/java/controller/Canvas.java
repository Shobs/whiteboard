package main.java.controller;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import main.java.model.*;
import main.java.view.*;

public class Canvas extends JPanel implements MouseInputListener, ModelListener {
	protected ArrayList<DShape> shapes;
	protected DShape selectedShape = null;
	protected double width;
	protected double height;
	protected int selectedKnob = -1;
	protected boolean change = true;
	protected int anchor;
	protected int[] diffs;
	protected boolean mouseEvents = true;
	protected Controls controls;

	public Canvas() {
		super();
		shapes = new ArrayList<DShape>();
		diffs = new int[4];
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.WHITE);
		addMouseMotionListener(this);
		addMouseListener(this);
	}


	public DShapeModel[] getModels(){
		DShapeModel[] models = new DShapeModel[shapes.size()];
		for(int i = 0; i < shapes.size(); i++)
			models[i] = shapes.get(i).getModel();
		return  models;
	}
	
	@Override
	public void repaint() {
		if (shapes != null)
			super.repaint();
		super.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (DShape shape : shapes) 
			shape.draw(g);
		controls.sendRemote();
	}

	public void setSelectedShape(DShape shape) {
		selectedShape = shape;
	}

	public DShape getSelectedShape() {
		return selectedShape;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!mouseEvents) return;
		Point p = e.getPoint();

		if (selectedShape != null) {
			selectedKnob = selectedShape.isKnob(e.getPoint());
			if (selectedKnob != -1) {
				if(selectedShape instanceof DLine)
					anchor = (selectedKnob == 1) ? 0 : 1;
				else
					anchor = getAnchor(selectedKnob);
			} else {
				selectedShape.setKnobVisibility(false);
				selectedShape.setIsChanged(true);
				selectedShape = null;
			}
		}

		for (DShape d : shapes) {
			if (d.isSelected(p)) 
				selectedShape = d;
		}

		if (selectedShape != null){
			if(selectedShape instanceof DLine){
				int x1 = (int)((DLineModel)selectedShape.getModel()).getX1();
				int x2 = (int)((DLineModel)selectedShape.getModel()).getX2();
				int y1 = (int)((DLineModel)selectedShape.getModel()).getY1();
				int y2 = (int)((DLineModel)selectedShape.getModel()).getY2();

				diffs[0] = x1 - e.getX();
				diffs[1] = y1 - e.getY();
				diffs[2] = x2 - e.getX();
				diffs[3] = y2 - e.getY();
			}
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

		} else if (model instanceof DLineModel) {
			DLine ln = new DLine(model);
			shapes.add(ln);
			model.addListener(this);
			model.addListener(ln);
			
		} else if (model instanceof DTextModel) {
			DText tx = new DText(model);
			shapes.add(tx);
			model.addListener(this);
			model.addListener(tx);
		} else {
			System.out.println("none of the above");
		}
		this.repaint();
		controls.reDraw();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!mouseEvents) return;
		Point p = e.getPoint();

		if (selectedShape != null) {
			selectedKnob = selectedShape.isKnob(e.getPoint());
			if (selectedKnob != -1) 
				anchor = getAnchor(selectedKnob);
			else {
				selectedShape.setKnobVisibility(false);
				selectedShape.setIsChanged(true);
				selectedShape = null;
			}
		}

		for (DShape d : shapes) 
			if (d.isSelected(p)) 
				selectedShape = d;

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
		if(!mouseEvents) return;
		change = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) { if(!mouseEvents) return;}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(!mouseEvents) return;
		if (selectedShape != null) {
			if (selectedKnob != -1) {
				if(selectedShape instanceof DLine)
				{
					anchor = (selectedKnob == 1)?0:1;
					if(anchor == 0)
					{
						((DLineModel)selectedShape.getModel()).setX2((int) (e.getX()));
						((DLineModel)selectedShape.getModel()).setY2((int) (e.getY()));
					}else
					{
						((DLineModel)selectedShape.getModel()).setX1((int) (e.getX()));
						((DLineModel)selectedShape.getModel()).setY1((int) (e.getY()));
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

					if (e.getY() < y) {
						selectedKnob = ((e.getX() < x)?0:1);
					} else{
						selectedKnob = ((e.getX() < x)?2:3);
					}
				}


			} else moveSelectedShape(e);
			repaint();
			controls.reDraw();
		}
	}


	public void moveSelectedShape(MouseEvent e) {
		Point pm = e.getPoint();
		Point ps = new Point();
		ps.setLocation(selectedShape.getBounds().getX(), selectedShape.getBounds().getY());

		if(selectedShape instanceof DLine){
			((DLineModel)selectedShape.getModel()).setX1(e.getX() + diffs[0]);
			((DLineModel)selectedShape.getModel()).setY1(e.getY() + diffs[1]);
			((DLineModel)selectedShape.getModel()).setX2(e.getX() + diffs[2]);
			((DLineModel)selectedShape.getModel()).setY2(e.getY() + diffs[3]);
		}
		selectedShape.getModel().setX((int) (pm.getX() - width));
		selectedShape.getModel().setY((int) (pm.getY() - height));
	}

	@Override
	public void mouseExited(MouseEvent e) { if(!mouseEvents) return;}

	@Override
	public void mouseMoved(MouseEvent e) { if(!mouseEvents) return;}

	@Override
	public void modelChanged(DShapeModel model) {
		repaint();
	}



	public void changeFont(String nType){
		if(selectedShape != null && (selectedShape.getModel() instanceof DTextModel)){
			DTextModel text = (DTextModel)selectedShape.getModel();
			text.setType(nType);
		}
	}
	
	public void changeContent(String nText){
		if(selectedShape != null && (selectedShape instanceof DText))
			((DTextModel)selectedShape.getModel()).setStr(nText);
	}

	public void setControls(Controls c){
		this.controls = c;
	}

	public void loadModels(DShapeModel[] models){
		for(int i = 0; i < shapes.size(); i++ )
			shapes.get(i).delete();
		
		selectedShape = null;
		shapes.clear();
		for(int i = 0; i < models.length; i++ )
			this.addShape(models[i]);
	}


	public RenderedImage BufferedImage() {
		BufferedImage render = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics graph = render.getGraphics();
		DShape currentShape = selectedShape;
		selectedShape = null;
		paint(graph);
		selectedShape = currentShape;
		return render;
	}

	public ArrayList<DShape> getShapes(){ return shapes; }
	
	public void setShapes(ArrayList<DShape> shapes){ this.shapes = shapes;}
	
	public void setMouseEvents(boolean b){ mouseEvents = b; }
}
