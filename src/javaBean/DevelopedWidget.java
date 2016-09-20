package javaBean;

import java.sql.Timestamp;

import property.Widget;

public class DevelopedWidget extends Widget{
		
	private final String contents;
	private final Timestamp updatedDate;
	private final String repImagePath;
			
	public DevelopedWidget(int id, String name,String kind, String contents, String imgPath, Timestamp updatedDate) throws Exception{
		super(id,name,kind);
		
		this.contents = contents;
		this.updatedDate = updatedDate;
		repImagePath = imgPath;
		
	}

	public String getContents() {
		return contents;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public String getRepImagePath() {
		return repImagePath;
	}
	
}
