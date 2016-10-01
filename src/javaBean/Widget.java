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
	
	private static ArrayList<Widget> widgets;
	private enumWidgetKind kind;
	private int id;
	private String name;
	
	protected Widget(int id, String name,String kind) throws Exception{
		this.id = id;
		this.name = name;
		
		boolean isEmpty=true;
		for(enumWidgetKind k : enumWidgetKind.values()){
			if(k.getString().equalsIgnoreCase(kind)){
				this.kind = k;
				isEmpty = false;
			}
		}
		if(isEmpty){
			throw new Exception("생성자의 kind변수가 enumWidgetKind에 해당 하는것이 없습니다.");
		}
		
	}
	
	public static ArrayList<Widget> getDevelopedWidgets() {
		return widgets;
	}

	public static void setDevelopedWidgets(ArrayList<Widget> widgets) {
		Widget.widgets = widgets;
	}

	public static boolean isContainsWidget(Widget w){
		return widgets.contains(w);
	}

	public static boolean isContainsWidget(int wId){
		for(Widget w : widgets){
			if(w.getWidgetId()==wId)
				return true;
		}
		return false;
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
	
	public void removeWidget(int wId) throws Exception{
	
		/*
		 * DB와의 갱신필요 
		 */
		boolean isEmpty = true;
		for(Widget w : widgets){
			if(w.getWidgetId()==wId){
				widgets.remove(w);
				isEmpty = false;
				break;
			}
		}
		if(isEmpty)
			throw new Exception("해당 파라메터의 WidgetId가 객체가 갖고 있는 위젯 배열에 없습니다.");
		
	}

	/**
	 * 	객체가 가지고 있는 개발위젯들을 삭제한다. 이때 DB정보와의 일치를 위해 DB접근을 하게된다. 
	 * @param widgetIds	
	 * @throws Exception
	 */
	public void removeWidget(int [] widgetIds) throws Exception{
		
		/*
		 * DB와의 갱신필요 
		 */
		boolean isEmpty = true;
		for(int i : widgetIds)
			for(Widget w : widgets){
				if(w.getWidgetId()==i){
					widgets.remove(w);
					isEmpty = false;
					break;
				}
			}
		if(isEmpty)
			throw new Exception("해당 파라메터의 WidgetId가 객체가 갖고 있는 위젯 배열에 없습니다.");
		
	}
	
	public void removeDevelopedWidget(Widget widget) throws Exception{
		
		/*
		 * DB와의 갱신필요 
		 */
		if(widgets.contains(widget)){
			widgets.remove(widget);
			
		}else
			throw new Exception("해당 파라메터의 WidgetId가 객체가 갖고 있는 위젯 배열에 없습니다.");
		
	}
	
	public void removeWidget(Widget[] widget) throws Exception{
		
		/*
		 * DB와의 갱신필요 
		 */
		boolean isEmpty = true;
		for(Widget w : widget)
			if(widgets.contains(w)){
				widgets.remove(w);
				isEmpty = false;
				break;
			}
		if(isEmpty)
			throw new Exception("해당 파라메터의 WidgetId가 객체가 갖고 있는 위젯 배열에 없습니다.");
		
	}
		
	/**
	 *	유저가 새로 위젯을 추가하면 배열에 추가하고 DB 정보를 갱신한다. 
	 * @param widgetIds	추가할 위젯 id
	 * @throws Exception 이미 배열에 위젯이 있다면 예외처리한다.
	 */
	public void addWidget(Widget [] widgetIds) throws Exception{
		
		/*
		 * DB로 접근이 필요하다.
		 */
		
		
		for(Widget w : widgetIds){
			if(Widget.isContainsWidget(w))
				throw new Exception("해당 파라메터의 WidgetId가 객체가 갖고 있는 위젯 배열에 이미 존재합니다.");
			else{
				widgets.add(w);
			}
		}
		
			
		
	}

	public enumWidgetKind getKind() {
		return kind;
	}

	public void setKind(enumWidgetKind kind) {
		this.kind = kind;
	}

		
}
