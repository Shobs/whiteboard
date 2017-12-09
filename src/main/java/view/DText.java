package main.java.view;
import main.java.model.*;
import java.awt.*;

import javafx.geometry.Rectangle2D;


public class DText extends DShape{
	Graphics g;
	public DText(DShapeModel model){
		super.model = model;
		System.out.println("Constructor ");
	}


	@Override
	public void draw(Graphics g){
		
		this.g = g;
		g.setColor(model.getColor());
		
//		Shape clip = g.getClip();
	//computeFont(g, (DTextModel)model)
	
		Shape clip = g.getClip();
		
		Font font = new Font(((DTextModel)model).getStr(), Font.PLAIN, computeFont(g, (DTextModel)model));
		
		g.setFont(font);
		g.drawString(((DTextModel)model).getStr(), model.x  , model.y+(int)(model.height*.75));
		
		//g.setClip(clip.getBounds().intersection(getBounds()));
	
		// make sure to figure out what to do with where to place the text
		generateKnobs((int)model.getX(),(int) model.getY(), (int)model.getWidth(), (int) model.getHeight(),0);
		g.setClip(clip);
		
		super.draw(g);
	}
	private int computeFont(Graphics g, DTextModel textMod)
	{
		
		double size = 1.0;
		
		Font font = new Font(textMod.getType(), Font.PLAIN, (int)size);
		FontMetrics fMetric = g.getFontMetrics(font);
		
		while(fMetric.getHeight() <  model.height)
		{
			size = (size * 1.10) + 1;
			
			if(fMetric.getHeight() > model.height)
			{
				return (int)((size - 1) / 1.10);
			}
			else if(fMetric.getHeight() == model.height)
			{
				return (int)size;
			}
			else
			{
				
				fMetric = g.getFontMetrics(new Font(textMod.getType(), Font.PLAIN, (int) size));
			}
		}
	
		return (int)size;
	}
	
//	public Font scaleFont(Graphics g, DTextModel model) {
//		
//		
//	    float fontSize=72.0f;
//	    Font font= new Font(model.getType(), 0, (int)fontSize);
//	    boolean isBigger = true;
//	    
//	    while(isBigger) {                             
//	            font=g.getFont().deriveFont(fontSize);
//	            FontMetrics fm=g.getFontMetrics(font);
//	            int width=fm.stringWidth(text);
//	            nextTry*=.9;
//	            if(width <= rect.width)
//	                isBigger = false;
//	                        
//	    }
//	    int result = (int)Math.min(computeFont(g, textMod), nextTry);
//	    return result;
//	}
	public void modelChanged(DShapeModel model){
		
	}

	
}