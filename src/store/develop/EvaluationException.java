package store.develop;

import property.enums.enumMemberState;
import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;

public class EvaluationException extends Exception {

	private final enumEvalFailCase failcase;
		
	public EvaluationException(String msg, enumEvalFailCase errcode){ //생성자
		super(msg);
		failcase=errcode;
	}
	
	public EvaluationException(enumEvalFailCase errcode){// 생성자
		this(errcode.getString(), errcode);
	}
	
	public enumEvalFailCase getFailCsae(){
		return failcase;
	}
	
	public String getErrCode(){// 에러 코드를 얻을 수 있는 메서드도 추가한다.
		return failcase.getString();// 이 메서드는 주로 getMessage()와 함께 사용될 것이다.
	}
}
