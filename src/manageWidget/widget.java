package manageWidget;

public class widget {
	private int num = 0;
	private String name = null;
	private String tag = null;
	private int pX = 0;
	private int pY = 0;
	private int width = 0;
	private int height = 0;
	private boolean remote = false;
	public widget(int num, String name, String tag, int pX, int pY, int width, int height, boolean remote){
		this.num = num;
		this.name = name;
		this.tag = tag;
		this.pX = pX;
		this.pY = pY;
		this.width = width;
		this.height = height;
		this.remote = remote;
	}

	public int getNum(){return num;}
	public String getName(){return name;}
	public String getTag(){return tag;}
	public int getPointX(){return pX;}
	public int getPointY(){return pY;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public boolean getRemote(){return remote;}
}
