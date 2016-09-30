package javaBean;

import java.sql.Timestamp;

import property.enums.widget.enumWidgetPosition;

public class EvaluatingWidget extends Widget{
	
	private final boolean isUpdate;
	private final int evalId;
	private final String widgetRoot;
	private final Timestamp uploadedDate;
	private final String position;
	private final int devId;
	private final String nickname;
	private  Timestamp storedDate;
			
	public EvaluatingWidget(String name, String kind, Timestamp uploadDate ,String widgetRoot, String pos, int evalNum, boolean isUpdate,int devId ,String nick) throws Exception{
		super(-1,name,kind);
		this.devId = devId;
		nickname = nick;
		this.isUpdate = isUpdate;
		this.evalId = evalNum;
		this.widgetRoot = widgetRoot;
		this.uploadedDate = uploadDate;
		position = pos;
		
	}


	public String getWidgetRoot() {
		return widgetRoot;
	}

	public Timestamp getUploadedDate() {
		return uploadedDate;
	}

	public String getPosition() {
		return position;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public int getEvalId() {
		return evalId;
	}

	public String getNickname() {
		return nickname;
	}

	public Timestamp getStoredDate() {
		return storedDate;
	}


	public int getDevId() {
		return devId;
	}
	


}
