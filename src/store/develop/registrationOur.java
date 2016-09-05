package store.develop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javaBean.MemberProcess;
import javaBean.Member;
import property.commandAction;

/**
 *  JSP페이지에서 폼을 통하여 값을 전달받아 회원가입을 처리받는다.
 *  	  외부로그인 경우(내부로그인이면 가입한 경우) 이전에 로그인한 적이 있다면 가입절차를 밟지 않는다.
*/
public class registrationOur implements commandAction {

	@Override
	public HashMap<String, String> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		Member mdb = new Member();
		HashMap<String , String> returns = new HashMap<String , String>();
		
		String contents = (String)request.getParameter("content");
		System.out.println(contents);
		
		
		
		if (ServletFileUpload.isMultipartContent(request)){
			    ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
			    //UTF-8 인코딩 설정
			    uploadHandler.setHeaderEncoding("UTF-8");
			    List<FileItem> items = uploadHandler.parseRequest(request);
			    String realname = "";
			    Long size = 0L;
			    //각 필드태그들을 FOR문을 이용하여 비교를 합니다.
			    for (FileItem item : items) {
			        //image.html 에서 file 태그의 name 명을 "image_file"로 지정해 주었으므로 
			        if(item.getFieldName().equals("image_file")) {
			            if(item.getSize() > 0) {
			                String ext = item.getName().substring(item.getName().lastIndexOf(".")+1);
			                //파일 기본경로
			                String defaultPath = request.getServletContext().getRealPath("/");
			                //파일 기본경로 _ 상세경로
			                String path = defaultPath + "upload" + File.separator;
			                 
			                File file = new File(path);
			                 
			                //디렉토리 존재하지 않을경우 디렉토리 생성
			                if(!file.exists()) {
			                    file.mkdirs();
			                }
			                //서버에 업로드 할 파일명(한글문제로 인해 원본파일은 올리지 않는것이 좋음)
			                realname = item.getName();//UUID.randomUUID().toString() + "." + ext;
			                size = item.getSize();
			                ///////////////// 서버에 파일쓰기 ///////////////// 
			                InputStream is = item.getInputStream();
			                OutputStream os=new FileOutputStream(path + realname);
			                int numRead;
			                byte b[] = new byte[(int)item.getSize()];
			                while((numRead = is.read(b,0,b.length)) != -1){
			                    os.write(b,0,numRead);
			                			   }
			                if(is != null)  is.close();
			                os.flush();
			                os.close();
			                ///////////////// 서버에 파일쓰기 /////////////////
			            }
			        }
			    }
			    response.setContentType("text/plain; charset=UTF-8");
			    PrintWriter pw = response.getWriter();
			    //json string 값으로 callback
			    //json 값으로 넘기는 필요 값
			    //imageurl, filename,filesize,imagealign
			    pw.print("{\"imageurl\" : \"/upload/"+realname+"\",\"filename\":\""+realname+"\",\"filesize\": 600,\"imagealign\":\"C\"}");
			    pw.flush();
			    pw.close();
			}
		
		
		return returns;
		
	}
	
}