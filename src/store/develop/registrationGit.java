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

import javaBean.Member;
import property.commandAction;

public class registrationGit implements commandAction {

	@Override
	public HashMap<String, String> requestPro(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		
		Member mdb = new Member();
		HashMap<String , String> returns = new HashMap<String , String>();
		
		String _contents = (String)request.getParameter("content");
		String _gitUrl= (String)request.getParameter("git_url");
		String _gitId= (String)request.getParameter("git_id");
		String _gitRepo = (String)request.getParameter("git_repo");
		System.out.println(_contents);
		System.out.println(_gitUrl);
		System.out.println(_gitId);
		System.out.println(_gitRepo);
		
		
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
