package developer;


import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;

/**
 * 	위젯 자동 평가중 실패하는 경우 실패사유를 enumEvalFailCase enumeration을 매개변수로  예외처리를 한다.
 *     이 예외를 통해 평가실패를 DB에 기록한다. 
 * 
 * @author cmk
 *
 */
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
