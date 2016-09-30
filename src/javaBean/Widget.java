package javaBean;

import java.util.ArrayList;

import property.enums.widget.enumWidgetKind;


/**
 *     유저가 다운로드한 위젯과 개발한 위젯으 super class.
 * 	개발자의 개발 위젯정보들이 요구될 때 최초에 DB에 접근하여 배열로 갖고 있다가 추후에 다시 돌려줘야 할 때 DB를 거치지 않고 값을 돌려준다. 
 * 	중요한 점은 새로 개발한 위젯이 업데이트 된 경우 Member필드의 이 객체 정보들을 갱신해야 한다.
 * @author cmk
 *
 */

public class Widget {
	
	
	private enumWidgetKind kind;
	private int id;
	private String name;
	
	protected Widget(int id, String name,String kind) throws Exception{
		this.id = id;
		this.name = name;
		
		boolean isEmpty=true;
		for(enumWidgetKind k : enumWidgetKind.values()){
			if(k.getString().equals(kind)){
				this.kind = k;
				isEmpty = false;
			}
		}
		if(isEmpty){
			throw new Exception("생성자의 kind변수가 enumWidgetKind에 해당 하는것이 없습니다.");
		}
		
	}

	
	public int getWidgetId() {
		return id;
	}

	public void setWidgetId(int widgetId) {
		this.id = widgetId;
	}

	public String getWidgetName() {
		return name;
	}

	public void setWidgetName(String widgetName) {
		this.name = widgetName;
	}
	


	public enumWidgetKind getKind() {
		return kind;
	}

	public void setKind(enumWidgetKind kind) {
		this.kind = kind;
	}

		
}
