package javaBean;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageWidget {


	private static Connection conn = ConnectMysql.getConnector();


	
	public static void AddEvaluatingWidget(int uId, int wId){
		
	}
	
	public static void uploadToStore(int uId, int wId){
		
	}
	
	public static void removeFaildEvaluationWidget(){
		
	}
	
	/**
	 * 메인에서 개발자 페이지에 접근할 때 불러올 개발자의 위젯 정보들을 반환한다.
	 * @param uId 개발자의 uId
	 * @return (위젯 기본키, 이름, 업데이트 날짜, 위젯 설명 정보, 대표사진 경로)를 반환한다.
	 *		 이때 순서대로 배열 각 요소에 Hashmap의 키 값은(wId, wName, updatedDate, wContent, repImagePath) 		
	 */
	public static ArrayList<HashMap<String,String>> getUserWidgets(String uId){
	
		ArrayList<HashMap<String, String>> widgets = new ArrayList<>();
		
		
		
		return widgets;
		
	}
}
