package main.java.view;
import main.java.model.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DText extends DShape{
	Graphics g;
	public DText(DShapeModel model){
		super(model);
	}

	@Override
	public void draw(Graphics g){
		this.g = g;
		
		g.setColor(model.getColor());
		Rectangle clip = new Rectangle((int)model.getX() - 4, (int)model.getY() - 4, (int)model.getWidth() + 8, (int)(model.getHeight() * 1.75));
		g.setClip(clip);
		
		Font font = new Font(((DTextModel)model).getType(), Font.PLAIN, computeFont(g, (DTextModel)model));
		g.setFont(font);
		
		g.drawString(((DTextModel)model).getStr(), model.x  , model.y+(int)(model.height*.75));
		
		generateKnobs((int)model.getX(),(int) model.getY(), (int)model.getWidth(), (int) model.getHeight(),0);
		g.setClip(null);
		super.draw(g);
	}
	
	private int computeFont(Graphics g, DTextModel textMod){
		double size = 1.0;
	
		Font font = new Font(textMod.getType(), Font.PLAIN, (int)size);
		FontMetrics fMetric = g.getFontMetrics(font);
		
		while(fMetric.getHeight() <  model.height){
			size = (size * 1.10) + 1;
			if(fMetric.getHeight() > model.height)
				return (int)((size - 1) / 1.10);	
			else if(fMetric.getHeight() == model.height)
				return (int)size;	
			else
				fMetric = g.getFontMetrics(new Font(textMod.getType(), Font.PLAIN, (int) size));	
		}
		return (int)size;
	}
	
	public void modelChanged(DShapeModel model){
		
	}
}