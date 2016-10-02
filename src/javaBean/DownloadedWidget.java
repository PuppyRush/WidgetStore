package javaBean;

import java.sql.Timestamp;

import javaBean.DevelopedWidget.Builder;
import property.enums.widget.enumWidgetPosition;

public class DownloadedWidget extends Widget{
	
	private final int width;
	private final int height;
	private final int x;
	private final int y;
	

	private final String htmlRoot;
	private final int developerId;
	private float version;
	
	public static class Builder{
		
		private int width;
		private int height;
		private int x;
		private int y;
		
	
		private String html_root;
		private float version;
		private int developerId;

		//neccessary field
		private final String wName;
		private final int wId;
		private final String kind;
		
		public Builder(int wId, String wName, String kind){
			this.wName = wName;
			this.wId = wId;
			this.kind = kind;
		}
		
		public Builder setSize(int x, int y, int w, int h){
			this.x = x; this.y = y; this.width =w; this.height = h;		
			return this;
		}
		
		public Builder developerId(int devId){
			developerId = devId; return this;
		}


		public Builder htmlRoot(String root){
			html_root = root; return this;
		}	
		

		public DownloadedWidget build() throws Exception{
			return new DownloadedWidget(this);
		}
	
	
	}
	


	private DownloadedWidget(Builder b) throws Exception{
		super(b.wId, b.wName, b.kind);
		this.htmlRoot = b.html_root;
		
		
		this.developerId = b.developerId;

		
		this.version = b.version;
		
		this.x = b.x;
		this.y = b.y;
		this.width = b.width;
		this.height = b.height;
		
	}



	public int getWidth() {
		return width;
	}



	public int getHeight() {
		return height;
	}



	public int getX() {
		return x;
	}



	public int getY() {
		return y;
	}



	public float getVersion() {
		return version;
	}



	public void setVersion(float version) {
		this.version = version;
	}



	public String getHtmlRoot() {
		return htmlRoot;
	}



	public int getDeveloperId() {
		return developerId;
	}


}
