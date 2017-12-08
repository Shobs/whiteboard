package main.java.view;
import main.java.model.*;
import java.awt.*;


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
		
		Shape clip = g.getClip();
	//computeFont(g, (DTextModel)model)
		Font font = new Font(((DTextModel)model).getType(), Font.PLAIN, computeFont(g, (DTextModel)model));
		g.setFont(font);
		System.out.println("1working");
		
		
		//g.setClip(clip.getBounds().createIntersection(getBounds()));
		g.drawString(((DTextModel)model).getStr(), model.x, model.y+(int)(model.height*.75));
		// make sure to figure out what to do with where to place the text
		g.setClip(clip);
		System.out.println("Draw ");
		super.draw(g);
	}
	private int computeFont(Graphics g, DTextModel textMod)
	{
		System.out.println("compute");
		double size = 1.0;
		
		Font font = new Font(textMod.getType(), Font.PLAIN, (int)size);
		FontMetrics fMetric = g.getFontMetrics(font);
		System.out.println("compute");
		while(fMetric.getHeight() <  model.height)
		{
			size = (size * 1.10) + 1;
			System.out.println("compute");
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
		
		System.out.println("compute");
		
		return (int)size;
	}
	public void modelChanged(DShapeModel model){
		
	}

	
}