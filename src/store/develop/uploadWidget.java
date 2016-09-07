package store.develop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javaBean.Member;
import property.commandAction;

public class uploadWidget implements commandAction {

	@Override
	public HashMap<String, Object> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		Member mdb = new Member();
		HashMap<String , Object> returns = new HashMap<String , Object>();
		
		String _contents = (String)request.getParameter("content");
		String _gitUrl= (String)request.getParameter("git_url");
		String _gitId= (String)request.getParameter("git_id");
		String _gitRepo = (String)request.getParameter("git_repo");

		int sizeLimit = 1024*1024*15;
		try{
		
			MultipartRequest multi = new MultipartRequest(request, "/upload/TemporaryUploadWidget", sizeLimit, "utf-8", new DefaultFileRenamePolicy());		
		}
		catch(IOException e){
			returns.put("view", "StoreEditor/EditorPageGit");
			returns.put("message", "업로드파일 저장중 문제가 발생하였습니다.");
			returns.put("isSuccessUpload", "false");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return returns;
		}
		
		returns.put("view", "StoreEditor/EditorPageGit");
		returns.put("isSuccessUpload", "true");
		
		return returns;
	}
	

	public static void wholeHtmlPaser(String url){
			
		Document doc = null;
			
		try {
			doc = Jsoup.connect("https://raw.githubusercontent.com/PuppyRush/WidgetStore/master/WebContent/Item.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	
}
