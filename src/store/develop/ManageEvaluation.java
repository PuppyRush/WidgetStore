package store.develop;

import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;

import javaBean.Member;
import property.enums.widget.enumWidgetEvaluation;
import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;
import property.enums.widget.enumWidgetKind;
import property.enums.widget.enumWidgetPosition;


import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.*;
/**
 * 
 * @author PuppyRush / gooddaumi@naver.com<br>
 *
 * 업로드된 위젯의 등록 및 평가를 위해 다음과 같은 순서로 진행된다.<br>
 * 1. 사용자명과 위젯명을 토대로 기본 폴더 생성 	( /upload/widget/<i><b>username</b></i>/<b><i>widgetname</i></b>)<br>
 * 2. 제출된 대표사진을 representiveImages 폴더를 생성하여 저장한다.<br> 
 * <b>1,2는 UploadWidget에서 수행한다.</b>
 * 3.  제출된 압축파일을 widget폴더를 생성하여 해제한다.<br>
 * 경로 :  upload/widget/<i><b>username</b></i>/<b><i>widgetname</i></b>/source/)<br>
 * 4. 기본적인 파일이 있는지 조사한다. (매니페스트, 루트파일)<br>
 * 5. 참조하는 js에 문제가 없는지 조사한다. (실행이 잘 될 요건의 최소사항.)<br> 
 * 6. 매니페스트에 근거하여 위젯정보를 DB에 저장한다.<br>
 * **각 순서에서의 설명은 메서드를 참고.
 */
public class ManageEvaluation implements Runnable{
	
	private class RecommandInfo{
		
		private class Ratio{
			public int ratioNum;
			public int heightRatio;
			public int widthRatio;
			public int heightSize;
			public int widthSize;
		}
		
		public boolean isExistRecommand;
		public boolean _isSupportResolution;
		public String _resolutionMethod;
		public HashMap<String, Ratio> _ratioMap;
		public int _minWidthSize;
		public int _maxWidthSize;
		public int _minHeightSize;
		public int _mixHeightSize;
		public String _iconPath;
		
		public RecommandInfo(){
			_ratioMap = new HashMap<String, Ratio>();
		}
	}
	
	private class GitInfo{
		
		public String _repositoryName;
		public String _gitId;
		public String _tag;
		public String _branch;
		
	}
	
	private RecommandInfo _recommandInfo = null;
	private GitInfo _git = null;
	private int _manifestVersion;
	private float _version;
	private final String _defaultPath;
	private final String _zipFileName;
	private final String _widgetRoot;
	private enumWidgetKind _kind;
	private enumWidgetPosition _position;
	private final Member _member;
	private String _widgetName;

	private String _rootUrl;
	private final HashMap<String, String> _imagesNames;
	private enumWidgetEvaluation _evaluationResult;

	
	public ManageEvaluation(Member member, String widgetName, String zipFileName, HashMap<String, String> images, String kind) throws Exception{
	
		if(member==null || widgetName == null || zipFileName == null || images ==null || kind==null)
			throw new NullPointerException("ManageEvaluation 생성자의 파라메타에 null 존재합니다.");
		
		this._kind = null;
		
		for(enumWidgetKind k : enumWidgetKind.values()){
			if(k.getString().equals(kind)){
				this._kind = k;
				break;
			}
		}
		if(this._kind == null){
			throw new Exception("파라미터로 넘어온 position 값이 시스템에 존재하지 않습니다. ");
		}


		this._imagesNames = images;
		this._zipFileName = zipFileName;	
		this._defaultPath = "/home/cmk/workspace/WidgetStore/WebContent/upload/"; 
		this._widgetName = widgetName;
		this._member = member;
		this._widgetRoot = (new StringBuilder(_defaultPath).append(member.getId()).append("/").append(widgetName).append("/")).toString();
		this._git = new GitInfo();
		
	}

		

	public void run(){
		
		try {
			
			zipDecompress(_zipFileName);
			checkManifest_Required();
			checkJavaScript();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


   private void zipDecompress(String zipFileName) throws Throwable {
	   
	   String directory = _widgetRoot +"source/";
	   	   
	   File zipFile = new File(directory+zipFileName);
	   FileInputStream fis = null;
	   ZipInputStream zis = null;
	   ZipEntry zipentry = null;
	   try {
		//파일 스트림
		   fis = new FileInputStream(zipFile);
		//Zip 파일 스트림
		   zis = new ZipInputStream(fis);
		//entry가 없을때까지 뽑기
		   while ((zipentry = zis.getNextEntry()) != null) {
			   String filename = zipentry.getName();
			   File file = new File(directory, filename);
        //entiry가 폴더면 폴더 생성
			   if (zipentry.isDirectory()) {
				   file.mkdirs();
			   }else{
				   //파일이면 파일 만들기
				   createFile(file, zis);
			   	}
		   	}
		   fileDelete(directory+zipFileName);
		   
		} catch (Throwable e) {
		    throw e;
		} finally {
	    if (zis != null)
	        zis.close();
	    if (fis != null)
	        fis.close();
		}
   	  }
   

   private void createFile(File file, ZipInputStream zis) throws Throwable {
        //디렉토리 확인
        File parentDir = new File(file.getParent());
        //디렉토리가 없으면 생성하자
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        //파일 스트림 선언
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[256];
            int size = 0;
            //Zip스트림으로부터 byte뽑아내기
            while ((size = zis.read(buffer)) > 0) {
                //byte로 파일 만들기
                fos.write(buffer, 0, size);
            }
        } catch (Throwable e) {
            throw e;
        }
   }
	      
   private enumWidgetEvaluation checkManifest_Required(){
	   
	   enumWidgetEvaluation eval = enumWidgetEvaluation.PASS;
	   
	   String _manifesetPath = "/home/cmk/workspace/WidgetStore/WebContent/property/manifest.xml";
	   String _xmlPath = _widgetRoot + "source/manifest.xml";
	   File _mf = new File(_xmlPath );
	   File _origin = new File(_manifesetPath);
   	
   		

		try {
					
			if(!_mf.exists())
		   		throw new EvaluationException("매니페스트 파일이 없습니다.", enumEvalFailCase.NO_MANIFEST);
		   	
			
			// __는 위젯스토어에 존재하는 최신버전의 매니페스트 값들을 위한 변수
			//_는 사용자의 매니페스트 값을 위한 변수		
			DocumentBuilder _builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document _doc = _builder.parse(new File(_xmlPath));
			XPath  _xpath = XPathFactory.newInstance().newXPath();
			Node _col = (Node)_xpath.evaluate("/WidgetStore", _doc, XPathConstants.NODE);
						
			
			
			DocumentBuilder __builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document __doc = __builder.parse(new File(_manifesetPath));
			XPath  __xpath = XPathFactory.newInstance().newXPath();
			Node __col = (Node)__xpath.evaluate("/WidgetStore", __doc, XPathConstants.NODE);
			
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다." ,   enumEvalFailCase.MANIFEST_ERROR);

			/////////////required 시작/////////////
			
			
			NodeList _cols = (NodeList)_xpath.evaluate("*/required", _doc, XPathConstants.NODESET);
			NodeList __cols = (NodeList)__xpath.evaluate("*/required", __doc, XPathConstants.NODESET);
			
			_col = _cols.item(0);
			__col = __cols.item(0);
			
			System.out.println(_col.getNodeName());
			
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다." ,   enumEvalFailCase.MANIFEST_ERROR);
		

			_col = (Node)_xpath.evaluate("*/required/manifest_version", _doc, XPathConstants.NODE);
			__col = (Node)_xpath.evaluate("*/required/manifest_version", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다." ,   enumEvalFailCase.MANIFEST_ERROR);
			else{
				String __manifest_version = __col.getTextContent();
				String _manifest_version = _col.getTextContent();
				if( !isInteger(_manifest_version) )
					throw new EvaluationException("매니퍼스트 버전의 값이 올바르지 않습니다.(정수만 입력 가능합니다) 매니패스트 메뉴얼을 참조하세요.", enumEvalFailCase.MANIFEST_ERROR);
								
				if(  Integer.valueOf(__manifest_version)!= Integer.valueOf(_manifest_version))
					throw new EvaluationException("매니페스트 버전이 현재 위젯스토어의 버전과 일치하지 않습니다다. 매니패스트 메뉴얼을 참조하세요.", enumEvalFailCase.MANIFEST_ERROR);
			}
			_manifestVersion = Integer.valueOf( _col.getTextContent() );
			

			_col = (Node)_xpath.evaluate("*/required/widget", _doc, XPathConstants.NODE);
			__col = (Node)_xpath.evaluate("*/required/widget", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new SAXException(_col.getTextContent()+"이 존재하지 않습니다.");
			
			_col = (Node)_xpath.evaluate("*/required/widget/version", _doc, XPathConstants.NODE);
			__col = (Node)_xpath.evaluate("*/required/widget/version", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new SAXException(_col.getTextContent()+"이 존재하지 않습니다.");
			else{
				String _ver = _col.getTextContent();
				if(!_ver.contains("."))
					_ver+=".0";
				if(!_ver.matches("[0-9]+(\\.[0-9][0-9]?)?")) {
					throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",   enumEvalFailCase.MANIFEST_ERROR);
				}
						
			}
			

			_col = (Node)_xpath.evaluate("*/required/widget/root", _doc, XPathConstants.NODE);
			__col = (Node)_xpath.evaluate("*/required/widget/root", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",   enumEvalFailCase.MANIFEST_ERROR);
			_rootUrl = _col.getTextContent();
			
			
			
			_col = (Node)_xpath.evaluate("*/required/position", _doc, XPathConstants.NODE);
			__col = (Node)_xpath.evaluate("*/required/position", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",   enumEvalFailCase.MANIFEST_ERROR);
			else{
				String _pos = ((Element)((NodeList)_xpath.evaluate("*/required/position", _doc, XPathConstants.NODESET)).item(0)).getAttribute("kind");
				for(enumWidgetPosition p : enumWidgetPosition.values()){
					if(p.getString().equals(_pos)){
						this._position = p;
						break;
					}
				}
				if(this._position == null){
					throw new EvaluationException("position 값이 시스템에 존재하지 않습니다. 매니패스트 메뉴얼을 참조하세요. ",   enumEvalFailCase.MANIFEST_ERROR);
					
				}

			}

			switch(_position){
				
				
				case NOTHING:
					_position = enumWidgetPosition.NOTHING;
					break;
					
				case GIT:
					
					_col = (Node)_xpath.evaluate("*/required/position/git", _doc, XPathConstants.NODE);
					__col = (Node)_xpath.evaluate("*/required/position/git", __doc, XPathConstants.NODE);
					if(!_col.getNodeName().equals( __col.getNodeName() ))
						throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
					
					_col = (Node)_xpath.evaluate("*/required/position/git/id", _doc, XPathConstants.NODE);
					__col = (Node)_xpath.evaluate("*/required/position/git/id", __doc, XPathConstants.NODE);
					if(!_col.getNodeName().equals( __col.getNodeName() ))
						throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
					else{
						String _id = _col.getTextContent();
						_git._gitId = _id;
					}
					
					_col = (Node)_xpath.evaluate("*/required/position/git/repository", _doc, XPathConstants.NODE);
					__col = (Node)_xpath.evaluate("*/required/position/git/repository", __doc, XPathConstants.NODE);
					if(!_col.getNodeName().equals( __col.getNodeName() ))
						throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
					else{
						String _rep = _col.getTextContent();
						_git._repositoryName = _rep;
					}
					
					//branch와 tag는 둘 중 하나만 기입 될 수 있다. 두개 모두 기입 되어 있으면 불허.
					String _branch="", _tag="";
					Node _branchNode = (Node)_xpath.evaluate("*/required/position/git/branch", _doc, XPathConstants.NODE);
					__col = (Node)_xpath.evaluate("*/required/position/git/branch", __doc, XPathConstants.NODE);
					if(!_branchNode.getNodeName().equals( __col.getNodeName() ))
						throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
					
					
					Node _tagNode = (Node)_xpath.evaluate("*/required/position/git/tag", _doc, XPathConstants.NODE);
					__col = (Node)_xpath.evaluate("*/required/position/git/tag", __doc, XPathConstants.NODE);
					if(!_tagNode.getNodeName().equals( __col.getNodeName() ))
						throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
					
					if( _tagNode!=null && _branchNode!=null)
						throw new EvaluationException("git의 저장소로 tag 또는 branch 하나만 택해야 합니다.",   enumEvalFailCase.MANIFEST_ERROR);
															
					if(_branchNode != null)
						_git._branch = _branchNode.getTextContent();
					else if(_tagNode != null)
						_git._tag = _tag;
					
					
					break;
					//git 끝
					
				default:
					break;
				
			}

			///////////////required 끝/////////////
			
			
			_recommandInfo = new RecommandInfo();
			_col = (Node)_xpath.evaluate("*/recommand", _doc, XPathConstants.NODE);
						
			if(_col == null){
				_recommandInfo.isExistRecommand = false;
				_recommandInfo._iconPath="";
			}else
				eval = checkManifest_Recommand(_col, __col);

			enumWidgetEvaluation e = enumWidgetEvaluation.EVALUATING;
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block			
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
		}
		catch (EvaluationException e1){
			eval.setErrMsg(e1.getMessage());
			eval = enumWidgetEvaluation.UNALLOWANCE;
			eval.setFailCase(e1.getFailCsae());
		}
		
		return eval;
  	}  
   
   /**
    * manifest에 recommand가 기입되어 있으면 파싱한다. required 함수 맨 끝에서 recommand가 있는지 여부를 검사.
    * @param _col   required에서 widget manifest를 파싱하기 위한 node변수 
    * @param __col  origin manifest를 파싱하기 위한 node 변수
    * @param eval   required의 eval변수를 받는다.
    * @return 위젯심사 결과를 리턴한다.
    */
   private enumWidgetEvaluation checkManifest_Recommand(enumWidgetEvaluation eval){ 
	   

		///////////////recommand 시작////////////////
		
	   String _manifesetPath = "/home/cmk/workspace/WidgetStore/WebContent/property/manifest.xml";
	   String _xmlPath = _widgetRoot + "source/manifest.xml";
	   File _mf = new File(_xmlPath );
	   File _origin = new File(_manifesetPath);
	   NodeList _cols, __cols;
	   Node _col, __col;

		try {
					
			if(!_mf.exists())
		   		throw new EvaluationException("매니페스트 파일이 없습니다.", enumEvalFailCase.NO_MANIFEST);
		   	
			
			// __는 위젯스토어에 존재하는 최신버전의 매니페스트 값들을 위한 변수
			//_는 사용자의 매니페스트 값을 위한 변수		
			DocumentBuilder _builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document _doc = _builder.parse(new File(_xmlPath));
			XPath  _xpath = XPathFactory.newInstance().newXPath();			
			
			DocumentBuilder __builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document __doc = __builder.parse(new File(_manifesetPath));
			XPath  __xpath = XPathFactory.newInstance().newXPath();
			
			
			
			_col = (Node)_xpath.evaluate("*/recommand/support_resolution", _doc, XPathConstants.NODE);
			__col = (Node)_xpath.evaluate("*/recommand/support_resolution", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
			
			if(_col.getTextContent().equals("yes")){
				
				_col = (Node)_xpath.evaluate("*/recommand/resolution", _doc, XPathConstants.NODE);
		
				__col = (Node)_xpath.evaluate("*/recommand/resolution", __doc, XPathConstants.NODE);
				if(!_col.getNodeName().equals( __col.getNodeName() ))
					throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
				else{
					String _res = ((Element)((NodeList)_xpath.evaluate("*/recommand/resolution", _doc, XPathConstants.NODESET)).item(0)).getAttribute("method");
					if(_res==null)
						throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",   enumEvalFailCase.MANIFEST_ERROR);
					
					switch( _res){
						case "ratio":
							
							_cols = (NodeList)_xpath.evaluate("*/recommand/resolution/ratio", _doc, XPathConstants.NODESET);
							__cols = (NodeList)_xpath.evaluate("*/recommand/resolution/ratio", __doc, XPathConstants.NODESET);
							
							///수정필요
							ㄴㅁ
							
							if(_firstRatio == null || _firstResolution==null)
								throw new EvaluationException("ratio나 resolution 값이 존재 하지 않습니다.",   /enumEvalFailCase.MANIFEST_ERROR);
							
							
							
							break;
							
						case "free":
							
							_col = (Node)_xpath.evaluate("*/recommand/resolution/free", _doc, XPathConstants.NODE);
							__col = (Node)_xpath.evaluate("*/recommand/resolutio/free", __doc, XPathConstants.NODE);
							if(!_col.getNodeName().equals( __col.getNodeName() ))
								throw new EvaluationException(__col.getNodeName()+"값이 존재하지 않습니다.", enumEvalFailCase.MANIFEST_ERROR);
							
							//추가필요
							
							break;
							
						default:
							
							break;
					}//switch
					 
				}//end support method
				
				
				_col = (Node)_xpath.evaluate("*/recommand/icon-path", _doc, XPathConstants.NODE);
				__col = (Node)_xpath.evaluate("*/recommand/icon-path", __doc, XPathConstants.NODE);
				if(!_col.getNodeName().equals( __col.getNodeName() ))
					throw new EvaluationException(__col.getNodeName()+"값이 존재하지 않습니다.", enumEvalFailCase.MANIFEST_ERROR);
				
				_recommandInfo._iconPath = _col.getTextContent();
			}
		}catch(EvaluationException e1){
			eval.setErrMsg(e1.getMessage());
			eval = enumWidgetEvaluation.UNALLOWANCE;
			eval.setFailCase(e1.getFailCsae());
		}
		
		return eval;
	   
   }

   private enumWidgetEvaluation checkManifestOfOptional(enumWidgetEvaluation eval){ 
	   

	   String _manifesetPath = "/home/cmk/workspace/WidgetStore/WebContent/property/manifest.xml";
	   String _xmlPath = _widgetRoot + "source/manifest.xml";
	   File _mf = new File(_xmlPath );
	   File _origin = new File(_manifesetPath);
	   NodeList _cols, __cols;
	   Node _col, __col;

		try {
					
			if(!_mf.exists())
		   		throw new EvaluationException("매니페스트 파일이 없습니다.", enumEvalFailCase.NO_MANIFEST);
		   	
			
			// __는 위젯스토어에 존재하는 최신버전의 매니페스트 값들을 위한 변수
			//_는 사용자의 매니페스트 값을 위한 변수		
			DocumentBuilder _builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document _doc = _builder.parse(new File(_xmlPath));
			XPath  _xpath = XPathFactory.newInstance().newXPath();			
			
			DocumentBuilder __builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document __doc = __builder.parse(new File(_manifesetPath));
			XPath  __xpath = XPathFactory.newInstance().newXPath();
			

		}catch(EvaluationException e1){
			eval.setErrMsg(e1.getMessage());
			eval = enumWidgetEvaluation.UNALLOWANCE;
			eval.setFailCase(e1.getFailCsae());
		}
	   
		return eval;
   }
   
   private void checkJavaScript(){
	   
	   
   }
   
   public void wholeHtmlPaser(String url){
			
		org.jsoup.nodes.Document doc = null;
		try {
			doc = Jsoup.connect("https://raw.githubusercontent.com/PuppyRush/WidgetStore/master/WebContent/Item.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
   
   public static boolean isInteger(String s) {
		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
		    // only got here if we didn't return false
		    return true;
		}
	

   private void fileMove(String inFileName, String outFileName) {
	try {
	   FileInputStream fis = new FileInputStream(inFileName);
	   FileOutputStream fos = new FileOutputStream(outFileName);
	   
	   int data = 0;
	   while((data=fis.read())!=-1) {
	    fos.write(data);
	   }
	   fis.close();
	   fos.close();
	   
	   //복사한뒤 원본파일을 삭제함
	   fileDelete(inFileName);
	   
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	 }
	 
   private void fileDelete(String deleteFileName) {
		  File I = new File(deleteFileName);
		  I.delete();
		 }
}
	

