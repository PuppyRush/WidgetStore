package javaBean;

import java.sql.Timestamp;

import property.Widget;
import property.enums.widget.enumWidgetPosition;

public class EvaluatingWidget extends Widget{
	
	private final boolean isUpdate;
	private final int evaluationNumber;
	private final String widgetRoot;
	private final Timestamp uploadedDate;
	private final String position;
	private final int devId;
	private final String nickname;
	private  Timestamp storedDate;
			
	public EvaluatingWidget(int id, String name, String kind, Timestamp uploadDate ,String widgetRoot, String pos, int evalNum, boolean isUpdate,int devId ,String nick) throws Exception{
		super(id,name,kind);
		this.devId = devId;
		nickname = nick;
		this.isUpdate = isUpdate;
		this.evaluationNumber = evalNum;
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

	public int getEvaluationNumber() {
		return evaluationNumber;
	}

	public String getNickname() {
		return nickname;
	}

	public Timestamp getStoredDate() {
		return storedDate;
	}
	


}
