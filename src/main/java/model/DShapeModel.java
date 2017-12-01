package main.java.model;

import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;

import main.java.view.DShape;
import main.java.view.ModelListener;

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

    public DShapeModel(int h, int w, int x, int y){
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
    
    public void setX(int x)
    {
    	this.x = x;
    	notifyListeners();
    }
    public void setY(int y)
    {
    	this.y = y;
    	notifyListeners();
    }

    public void setColor(Color c){
        this.c = c;
        notifyListeners();
    }

    public void notifyListeners(){
    	for(ModelListener d : mls )
    	{
    		d.modelChanged(this);
    	}
    }








}