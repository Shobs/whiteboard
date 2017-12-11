package main.java.model;

import java.awt.*;
import java.util.*;
import main.java.view.*;

public class DShapeModel extends Rectangle {
	private Color c;
	private ArrayList<ModelListener> mls;

	public DShapeModel(){
		height = 100;
		width = 100;
		x = 0;
		y = 0;
		c = new Color(128, 128, 128);
		mls = new ArrayList();
	}

	public DShapeModel(int x, int y, int w, int h){
		height = h;
		width = w;
		this.x = x;
		this.y = y;
		c = new Color(128, 128, 128);
		mls = new ArrayList();
	}

	public DShapeModel(int h, int w, int x, int y, Color c){
		height = h;
		width = w;
		this.x = x;
		this.y = y;
		this.c = c;
		mls = new ArrayList();
	}

	public void addListener(ModelListener ml){
		mls.add(ml);
	}


	public void removeListener(int i){
		mls.remove(i);
	}

	public void removeListener(ModelListener ml){
		mls.remove(mls.indexOf(ml));
	}

	public Color getColor(){
		return c;
	}

	public void setX(int x){
		this.x = x;
		notifyListeners();
	}
	
	public void setY(int y){
		this.y = y;
		notifyListeners();
	}

	public void setWidth(int w){
		width = w;
		notifyListeners();
	}

	public void setHeight(int h){
		height = h;
		notifyListeners();
	}


	public void setColor(Color c){
		this.c = c;
		notifyListeners();
	}

	public void resize(int Xm, int Ym, int Xa, int Ya) {
		int x = Math.min(Xm, Xa);
		int y = Math.min(Ym, Ya);
		int width = (Math.abs(Xm - Xa));
		int height = Math.abs(Ym - Ya);

		setLocation(x, y);
		setSize(width, height);
		notifyListeners();
	}

	public void deleteM() {
		for (ModelListener l : mls) {
			removeListener(l);
		}
		notifyListeners();

	}

	public void notifyListeners(){
		for(ModelListener d : mls )
		{
			d.modelChanged(this);
		}
	}


	public DShape createShape(){
		if (this instanceof DRectModel) {
			DRect rect = new DRect(this);
			return rect;
		} else if (this instanceof DOvalModel) {
			DOval ov = new DOval(this);
			return ov;
		} else if (this instanceof DLineModel) {
			DLine ln = new DLine(this);
			return ln;
		} else  {
			DText tx = new DText(this);
			return tx;
		}
	}

	@Override
	public int hashCode(){
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(height);
		list.add(width);
		list.add(x);
		list.add(y);
		list.add(c);
		return list.hashCode();
	}

	@Override
	public boolean equals(Object o){
		if(this.getClass().equals(o.getClass())){
			DShapeModel m = (DShapeModel)o;
			return this.hashCode() == m.hashCode();
		}
		return false;
	}
}