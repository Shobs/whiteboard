package main.java.model;

public class DTextModel extends DShapeModel{
	
	private String str ;
	private String type;
	public 	int size;
	
	public DTextModel(int x, int y, int width, int height, String st,String type) {
		super(x, y, width, height);
		str = st;
		this.type = type ;
		
	}
	
	public DTextModel(){
		super();
		str = "Hello";
		type = "Papyrus";
	}
	
	public void setStr(String st)
	{
		str = st;
		notifyListeners();
	}
	
	public void setType(String st)
	{
		type = st;
		notifyListeners();
	}
	
	
	public String getType()
	{
		return type;
	}
	public String getStr()
	{
		return str;
	}
	
	
}