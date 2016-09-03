package store.develop;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.helper.StringUtil;

import javax.servlet.annotation.WebServlet;

import property.commandAction;



public class DeleteTempImg implements commandAction {
	
	
	
	@Override
	public HashMap<String, String> requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
			
		HashMap<String, String> returns = new HashMap<String, String>();

		  try
		  {
		    Properties prop = new Properties();
		    
		    String separator = prop.getProp("folder_separator");      
		    String workspace_path = prop.getProp("folder_workspace");   
		    
		    String[] array_upload_path = StringUtil.procNull(upload_path, "").split(",");       
		    String[] array_save_file_name = StringUtility.procNull(save_file_name, "").split(",");
		    
		    if(array_upload_path.length > 0)
		    {
		      File file = null;
		      File dir  = null;
		      
		      for(int i=0; i<array_upload_path.length; i++)
		      {
		        // 파일삭제
		        file = new File(workspace_path+separator+array_upload_path[i]+separator+array_save_file_name[i]);
		        file.delete();
		        
		        // 폴더에 아무 데이터가 없을 시 폴더 삭제
		        dir = new File(workspace_path+separator+array_upload_path[i]);
		        if(dir.isDirectory())         // 디렉토리 인지 확인
		        { 
		          if(dir.list().length == 0)  // 파일이 없으면
		          {
		            dir.delete();
		          }
		        }
		      } // for i
		    }
		    
		  } // try
		  catch(Exception e)
		  {
		    // 에러 처리 하세요~
		  }
	
		
		
		return returns;
	}

	private static class FileProperty{
		
		private String separator;       											 // 경로 구분자
		private String full_url;        											 // 전체 주소
		private String workspace_path; 											 // 워크스페이스 경로
		private String upload_path;    											 // 파일 업로드 폴더 경로
		private String time;                                            // 현재 시간
		private String filename;                                        // 실제 파일명
		private String saveFilename;                                    // 저장 파일명
		private String file_type;                                       // 파일 타입
		private String file_size;                                       // 파일 사이즈
		public String getSeparator() {
			return separator;
		}
		public void setSeparator(String separator) {
			this.separator = separator;
		}
		public String getFull_url() {
			return full_url;
		}
		public void setFull_url(String full_url) {
			this.full_url = full_url;
		}
		public String getWorkspace_path() {
			return workspace_path;
		}
		public void setWorkspace_path(String workspace_path) {
			this.workspace_path = workspace_path;
		}
		public String getUpload_path() {
			return upload_path;
		}
		public void setUpload_path(String upload_path) {
			this.upload_path = upload_path;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public String getSaveFilename() {
			return saveFilename;
		}
		public void setSaveFilename(String saveFilename) {
			this.saveFilename = saveFilename;
		}
		public String getFile_type() {
			return file_type;
		}
		public void setFile_type(String file_type) {
			this.file_type = file_type;
		}
		public String getFile_size() {
			return file_size;
		}
		public void setFile_size(String file_size) {
			this.file_size = file_size;
		}
				
		
	}

}
