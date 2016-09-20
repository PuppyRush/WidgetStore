package javaBean;

import java.sql.Timestamp;

import property.Widget;

public class DownloadedWidget extends Widget{
		
	private final String contents;
	private final String rootFileName;
	private final Timestamp updatedDate;
	private final String repImagePath;
			
	public DownloadedWidget(int id, String name, String kind, String contents, String imgPath, Timestamp updatedDate, String rootName) throws Exception{
		super(id,name,kind);
		this.rootFileName = rootName;
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

	public String getRootFileName() {
		return rootFileName;
	}
	
}
