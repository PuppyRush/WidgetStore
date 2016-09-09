<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<%@ page import ="java.io.IOException" %>
<%@ page import="com.oreilly.servlet.MultipartRequest,
                   com.oreilly.servlet.multipart.DefaultFileRenamePolicy,
                   java.util.*" 
%>
<%

	try{
		
		
		
		int sizeLimit = 1024*1024*15;
		
		MultipartRequest multi = new MultipartRequest(request,"/home/cmk/workspace/WidgetStore/WebContent/upload/TemporaryUploadWidget/", sizeLimit, "utf-8", new DefaultFileRenamePolicy());
	
		if(multi.getParameter("contents")==null || multi.getParameter("git-url")==null || 
				multi.getParameter("git-id")==null || multi.getParameter("repository-name")==null)
			throw new NullPointerException("widget-upload 폼으로부터 값이 모두 전송되지 않았습니다.");

		String _contents = (String)multi.getParameter("contents");
		String _gitUrl= (String)multi.getParameter("git-url");
		String _gitId= (String)multi.getParameter("git-id");
		String _gitRepo = (String)multi.getParameter("repository-name");
		String sessionId = (String)multi.getParameter("sessionId");
		
	}
	catch(IOException e){
		/* returns.put("view", "StoreEditor/EditorPageGit");
		returns.put("message", "업로드파일 저장중 문제가 발생하였습니다.");
		returns.put("isSuccessUpload", "false");
		System.out.println(e.getMessage());
		e.printStackTrace();
		return returns; */
	}
%>

</body>
</html>