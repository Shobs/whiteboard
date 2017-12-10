package main.java.model;

public class DTextModel extends DShapeModel{
	
	private String str ;
	private String type;
	public 	int size;
	
	
	public DTextModel(int i, int j, int k, int l, String st,String type) {
		super(i,j,k,l);
		str = st;
		this.type = type ;
		
	}
	
	public DTextModel()
	
	{
		super();
		str = "Hello";
		type = "Dialog";
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