package manager;

import page.PageException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import developer.EvaluationException;
import developer.ManageManifest;

import java.sql.Timestamp;
import java.util.HashMap;
import javaBean.ManageMember;
import javaBean.ManageWidget;
import javaBean.Member;
import netscape.javascript.JSObject;
import property.commandAction;
import property.enums.enumCautionKind;
import property.enums.enumPage;
import property.enums.enumPageError;
import property.enums.member.enumMemberType;
import property.enums.widget.enumWidgetEvaluation;
import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;

/**
 * JSP페이지에서 폼을 통하여 값을 전달받아 회원가입을 처리받는다. 외부로그인 경우(내부로그인이면 가입한 경우) 이전에 로그인한 적이 있다면
 * 가입절차를 밟지 않는다.
 */
public class AcceptWidget implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		Member member = new Member();
		HashMap<String , Object> returns = new HashMap<String , Object>();
		try{
			if(request.getParameter("evalId")==null || request.getParameter("widgetRoot")==null )
				throw new PageException(enumPageError.NO_PARAMATER);
			
			String widgetRoot = request.getParameter("widgetRoot");
			int evalId = Integer.valueOf(request.getParameter("evalId").trim());
			
			ManageManifest manifest = new ManageManifest(widgetRoot);
			manifest.doCollectAll();
			
				
			
			if(ManageWidget.isUpdatingWidget(evalId))
				ManageWidget.addEvaludatedUpdatedWidget(manifest, request.getRequestedSessionId(), evalId );
			else
				ManageWidget.addEvaluatedWidget(manifest, request.getRequestedSessionId(), evalId);
			
			
			
		}catch(PageException e){
			e.printStackTrace();
			
		}
	return returns;
    }
}
