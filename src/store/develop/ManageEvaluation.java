package store.develop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;

import javaBean.ConnectMysql;
import javaBean.Member;
import mail.PostMan;
import property.enums.enumSystem;
import property.enums.widget.enumWidgetEvaluation;
import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;
import property.enums.widget.enumWidgetKind;
import property.enums.widget.enumWidgetPosition;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.portlet.WindowStateException;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**	최초로 위젯으 업로드하거나 업데이트 위젯이 올라온 경우 이를 평가하기 위한 클래스. 
 *     평가가 진행되는데 일정 시간이 소요 되므로 클라이언트에게는 바로 응답하게 하기 위해 스레드를 이용한다.
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
	
	private static class RecommandInfo{
		
		private static class Ratio{
			
			public final int heightRatio;
			public final int widthRatio;
			public final int heightSize;
			public final int widthSize;
			
			
			/**
			 *w는 width h는 height 
			 */
			public Ratio(int hRatio,int wRatio, int wSize, int hSize){
								
				heightRatio = hRatio;
				widthRatio = wRatio;
				heightSize = hSize;
				widthSize = wSize;
			}
		}
		
		public int minWidthSize;
		public int maxWidthSize;
		public int minHeightSize;
		public int maxHeightSize;
	
		public boolean isExistRecommand;
		public boolean _isSupportResolution;
		public String _resolutionMethod;
		public ArrayList<Ratio> _ratioArray;
	
		public String _iconPath;
		
		public RecommandInfo(){
			_ratioArray = new ArrayList<Ratio>();
			minWidthSize=0;
			maxWidthSize=0;
			maxHeightSize=0;
			minHeightSize=0;
			_resolutionMethod="unsupported";
		}
	}
	
	private class GitInfo{
		
		public String _repositoryName;
		public String _gitId;
		public String _tag;
		public String _branch;
		
	}
	

	
	private RecommandInfo recommandInfo = null;
	private GitInfo git = null;
	
	private int manifestVersion;
	private float version;
	private final String contents;
	private final String defaultPath;
	private final String zipFileName;
	private final String widgetRoot;
	private final String widgetName;
	private final Member member;
	private final boolean isUpdate;
	
	private float widgetVersion;
	private enumWidgetKind kind;
	private enumWidgetPosition position;

	private String rootUrl;
	private final HashMap<Integer, String> imagesNames;
	private enumWidgetEvaluation evaluationResult;

	Connection conn = ConnectMysql.getConnector();
	
	public ManageEvaluation(Member member, String widgetName,String contents, String zipFileName, HashMap<Integer, String> images, String kind, boolean isUpdate) throws Exception{
	
		if(member==null || widgetName == null || zipFileName == null || images ==null || kind==null || contents==null)
			throw new NullPointerException("ManageEvaluation 생성자의 파라메타에 null 존재합니다.");
		
	
		this.kind = null;		
		for(enumWidgetKind k : enumWidgetKind.values()){
			if(k.getString().equals(kind)){
				this.kind = k;
				break;
			}
		}
		if(this.kind == null){
			throw new Exception("파라미터로 넘어온 position 값이 시스템에 존재하지 않습니다. ");
		}
		
		this.isUpdate = isUpdate;
		this.contents = contents;
		this.imagesNames = images;
		this.zipFileName = zipFileName;	
		this.defaultPath = "/home/cmk/workspace/WidgetStore/WebContent/upload/"; 
		this.widgetName = widgetName;
		this.member = member;
		this.widgetRoot = (new StringBuilder(defaultPath).append(member.getId()).append("/").append(widgetName).append("/")).toString();
		this.git = new GitInfo();
		
		conn = ConnectMysql.getConnector();
	}


	public void run(){
		
		enumWidgetEvaluation eval = enumWidgetEvaluation.EVALUATING;
		
		try {			
			
			zipDecompress(zipFileName);
			
			if(isUpdate){
				eval = VerifyUpdateWidget(eval);
			}
			
			checkJavaScript(eval);
			checkManifestOfRequired(eval);
			
			if(isUpdate){
				addUpdatingWidget(eval);		
			}
			else
				addEvaluatingWidget(eval);
			
		}catch(EvaluationException e){
			PostMan.sendFailEvaluation(widgetName, member.getNickname(), e.getMessage(), member.getEmail());
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
   private void zipDecompress(String zipFileName) throws Throwable {
	   
	   String directory = widgetRoot +"source/";
	   	   
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
	      
  
   private enumWidgetEvaluation checkManifestOfRequired(enumWidgetEvaluation eval) throws EvaluationException{
	   
	  
	   
	   String _manifesetPath = "/home/cmk/workspace/WidgetStore/WebContent/property/manifest.xml";
	   String _xmlPath = widgetRoot + "source/manifest.xml";
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
			manifestVersion = Integer.valueOf( _col.getTextContent() );
			

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
					throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력하세요.",   enumEvalFailCase.MANIFEST_ERROR);
				}
				
				widgetVersion = Float.parseFloat(_ver);
			}
			

			_col = (Node)_xpath.evaluate("*/required/widget/root", _doc, XPathConstants.NODE);
			__col = (Node)_xpath.evaluate("*/required/widget/root", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",   enumEvalFailCase.MANIFEST_ERROR);
			rootUrl = _col.getTextContent();
			
			
			
			_col = (Node)_xpath.evaluate("*/required/position", _doc, XPathConstants.NODE);
			__col = (Node)_xpath.evaluate("*/required/position", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",   enumEvalFailCase.MANIFEST_ERROR);
			else{
				String _pos = ((Element)((NodeList)_xpath.evaluate("*/required/position", _doc, XPathConstants.NODESET)).item(0)).getAttribute("kind");
				for(enumWidgetPosition p : enumWidgetPosition.values()){
					if(p.getString().equals(_pos)){
						this.position = p;
						break;
					}
				}
				if(this.position == null){
					throw new EvaluationException("유저 매니페스트의 position 값이 시스템에 존재하지 않습니다. 매니패스트 메뉴얼을 참조하세요. ",   enumEvalFailCase.MANIFEST_ERROR);
					
				}

			}

			switch(position){
				
				
				case NOTHING:
					position = enumWidgetPosition.NOTHING;
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
						git._gitId = _id;
					}
					
					_col = (Node)_xpath.evaluate("*/required/position/git/repository", _doc, XPathConstants.NODE);
					__col = (Node)_xpath.evaluate("*/required/position/git/repository", __doc, XPathConstants.NODE);
					if(!_col.getNodeName().equals( __col.getNodeName() ))
						throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
					else{
						String _rep = _col.getTextContent();
						git._repositoryName = _rep;
					}
					
					//branch와 tag는 둘 중 하나만 기입 될 수 있다. 두개 모두 기입 되어 있으면 불허.
					String _branch="", _tag="";
					Node _branchNode = (Node)_xpath.evaluate("*/required/position/git/branch", _doc, XPathConstants.NODE);
					__col = (Node)_xpath.evaluate("*/required/position/git/branch", __doc, XPathConstants.NODE);
					if(_branchNode != null)
						if(!_branchNode.getNodeName().equals( __col.getNodeName() ))
							throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
					
					
					Node _tagNode = (Node)_xpath.evaluate("*/required/position/git/tag", _doc, XPathConstants.NODE);
					__col = (Node)_xpath.evaluate("*/required/position/git/tag", __doc, XPathConstants.NODE);
					if(_tagNode != null)
						if(!_tagNode.getNodeName().equals( __col.getNodeName() ))
							throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
					
					if( _tagNode!=null && _branchNode!=null)
						throw new EvaluationException("git의 저장소로 tag 또는 branch 하나만 택해야 합니다.",   enumEvalFailCase.MANIFEST_ERROR);
															
					if(_branchNode != null)
						git._branch = _branchNode.getTextContent();
					else if(_tagNode != null)
						git._tag = _tag;
					
					
					break;
					//git 끝
					
				default:
					break;
				
			}

			///////////////required 끝/////////////
			
			
			////////////////////recommand가 존재하면 태그의 값들을 파싱하기 위해 함수 수행
			recommandInfo = new RecommandInfo();
			_col = (Node)_xpath.evaluate("*/recommanded", _doc, XPathConstants.NODE);
						
			if(_col == null){
				recommandInfo.isExistRecommand = false;
				recommandInfo._iconPath="";
			}else
				eval = checkManifestOfRecommand( eval);

		
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
	/*	catch (EvaluationException e1){
			eval.setErrMsg(e1.getMessage());
			eval = enumWidgetEvaluation.UNALLOWANCE;
			eval.setFailCase(e1.getFailCsae());

		}*/
		
		return eval;
  	}  
   
   /**
    * manifest에 recommand가 기입되어 있으면 파싱한다. required 함수 맨 끝에서 recommand가 있는지 여부를 검사.
    * @param _col   required에서 widget manifest를 파싱하기 위한 node변수 
    * @param __col  origin manifest를 파싱하기 위한 node 변수
    * @param eval   required의 eval변수를 받는다.
    * @return 위젯심사 결과를 리턴한다.
 * @throws EvaluationException 
    */
   private enumWidgetEvaluation checkManifestOfRecommand(enumWidgetEvaluation eval) throws EvaluationException{ 
	   

		///////////////recommand 시작////////////////
		
	   String _manifesetPath = "/home/cmk/workspace/WidgetStore/WebContent/property/manifest.xml";
	   String _xmlPath = widgetRoot + "source/manifest.xml";
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
			
			
			
			_col = (Node)_xpath.evaluate("*/recommanded/isSupportResolution", _doc, XPathConstants.NODE);
			__col = (Node)_xpath.evaluate("*/recommanded/isSupportResolution", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
			
			if(_col.getTextContent().equals("yes")){
				
				_col = (Node)_xpath.evaluate("*/recommanded/resolution", _doc, XPathConstants.NODE);
		
				__col = (Node)_xpath.evaluate("*/recommanded/resolution", __doc, XPathConstants.NODE);
				if(!_col.getNodeName().equals( __col.getNodeName() ))
					throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
				else{
					String _res = ((Element)((NodeList)_xpath.evaluate("*/recommanded/resolution", _doc, XPathConstants.NODESET)).item(0)).getAttribute("method");
					if(_res==null)
						throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",   enumEvalFailCase.MANIFEST_ERROR);
					
					switch( _res){
						case "ratio":
											
							NodeList _ratioNodes = (NodeList)_xpath.evaluate("//recommanded/resolution/ratio/*", _doc, XPathConstants.NODESET);
							for(int i=0 ; i < _ratioNodes.getLength() ; i++){
								Element e = (Element)_ratioNodes.item(i);
								String []preSplitValue = e.getAttribute("value").split(":");
								String []preSplitSize = e.getAttribute("size").split(",");
								
								//w는 width h는 height
								int wRatio, hRatio, wSize, hSize;
								if(preSplitSize.length!=2 && preSplitValue.length!=2)
									throw new EvaluationException("ratio노드의 value, size값이 올바르지 않습니다. 매니페스트를 확인하세요.",   enumEvalFailCase.MANIFEST_ERROR);
								
								wRatio = Integer.valueOf(preSplitValue[0]);
								hRatio = Integer.valueOf(preSplitValue[1]);
								wSize = Integer.valueOf(preSplitSize[0]);
								hSize = Integer.valueOf(preSplitSize[1]);
								
								recommandInfo._ratioArray.add(new store.develop.ManageEvaluation.RecommandInfo.Ratio(wRatio, hRatio, wSize, hSize));
							}

							break;
							
						case "free":
							
							_col = (Node)_xpath.evaluate("*/recommanded/resolution/free", _doc, XPathConstants.NODE);
							__col = (Node)_xpath.evaluate("*/recommanded/resolutio/free", __doc, XPathConstants.NODE);
							if(!_col.getNodeName().equals( __col.getNodeName() ))
								throw new EvaluationException(__col.getNodeName()+"값이 존재하지 않습니다.", enumEvalFailCase.MANIFEST_ERROR);
							
							NodeList __freeNodes = (NodeList)__xpath.evaluate("//recommanded/resolution/free/*", __doc, XPathConstants.NODESET);
							NodeList _freeNodes = (NodeList)_xpath.evaluate("//recommanded/resolution/free/*", _doc, XPathConstants.NODESET);
							if(_freeNodes.getLength()!=4)
								throw new EvaluationException("resolution/free의 하위노드는 총 4개여야 합니다.  매니페스트를 확인하세요.",   enumEvalFailCase.MANIFEST_ERROR);
							
							//widget, height size 총 4번 순회 해야함.
							//minWidthSize
							Element _e = (Element)_freeNodes.item(0);
							Element __e = (Element)__freeNodes.item(0);
							if(_e.getNodeName().equals(__e.getNodeName()) == false)
								throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
							
							String _temp;
							_temp = _e.getTextContent();
							if(isInteger(_temp))
								recommandInfo.minWidthSize = Integer.parseInt(_temp);
							else
								throw new EvaluationException("크기의 값은 정수만 허용됩니다.",   enumEvalFailCase.MANIFEST_ERROR);
							
							//minHeightSize
							_e = (Element)_freeNodes.item(1);
							__e = (Element)__freeNodes.item(1);
							if(_e.getNodeName().equals(__e.getNodeName()) == false)
								throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
							
							_temp = _e.getTextContent();
							if(isInteger(_temp))
								recommandInfo.minHeightSize = Integer.parseInt(_temp);
							else
								throw new EvaluationException("크기의 값은 정수만 허용됩니다.",   enumEvalFailCase.MANIFEST_ERROR);
							
							//maxWidthSize
							_e = (Element)_freeNodes.item(2);
							__e = (Element)__freeNodes.item(2);
							if(_e.getNodeName().equals(__e.getNodeName()) == false)
								throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
							
							_temp = _e.getTextContent();
							if(isInteger(_temp))
								recommandInfo.maxWidthSize = Integer.parseInt(_temp);
							else
								throw new EvaluationException("크기의 값은 정수만 허용됩니다.",   enumEvalFailCase.MANIFEST_ERROR);
							
							//maxHeightSize
							_e = (Element)_freeNodes.item(3);
							__e = (Element)__freeNodes.item(3);
							if(_e.getNodeName().equals(__e.getNodeName()) == false)
								throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다.",   enumEvalFailCase.MANIFEST_ERROR);
							
							_temp = _e.getTextContent();
							if(isInteger(_temp))
								recommandInfo.maxHeightSize = Integer.parseInt(_temp);
							else
								throw new EvaluationException("크기의 값은 정수만 허용됩니다.",   enumEvalFailCase.MANIFEST_ERROR);

							break;
							
						default:
							throw new EvaluationException("resolution에는 free,auto,ratio만 가능합니다. 매니페스트를 확인하세요.",   enumEvalFailCase.MANIFEST_ERROR);
														
					}//switch
					 
				}//end resolution method
				
				
				_col = (Node)_xpath.evaluate("*/recommanded/icon-path", _doc, XPathConstants.NODE);
				__col = (Node)_xpath.evaluate("*/recommanded/icon-path", __doc, XPathConstants.NODE);
				if(!_col.getNodeName().equals( __col.getNodeName() ))
					throw new EvaluationException(__col.getNodeName()+"값이 존재하지 않습니다.", enumEvalFailCase.MANIFEST_ERROR);
								
				recommandInfo._iconPath = _col.getTextContent();
				
				///////recommanded 끝
				
				////optional 시작
				
				_col = (Node)_xpath.evaluate("*/optional", _doc, XPathConstants.NODE);
							
				if(_col == null){
					;
				}else
					eval = checkManifestOfOptional(eval);

				
				
			}
		}catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eval;
	   
   }
 

   private enumWidgetEvaluation checkManifestOfOptional(enumWidgetEvaluation eval) throws EvaluationException{ 
	   

	   String _manifesetPath = "/home/cmk/workspace/WidgetStore/WebContent/property/manifest.xml";
	   String _xmlPath = widgetRoot + "source/manifest.xml";
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
			

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return eval;
   }
   	
   
   private enumWidgetEvaluation checkJavaScript(enumWidgetEvaluation eval){
	   
	   
	   
	   return eval;
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
	


   private void fileDelete(String deleteFileName) {

	   
		  File I = new File(deleteFileName);
		  I.delete();
		 }


   private enumWidgetEvaluation VerifyUpdateWidget(enumWidgetEvaluation eval) throws EvaluationException, SQLException{
	
	   
	   String _manifesetPath = "/home/cmk/workspace/WidgetStore/WebContent/property/manifest.xml";
	   String _xmlPath = widgetRoot + "temp/source/manifest.xml";
	   File _mf = new File(_xmlPath );
	   File _origin = new File(_manifesetPath);
   	
	   PreparedStatement _ps = null;
	   ResultSet _rs = null;
   		

		try {
					
			if(!_mf.exists())
		   		throw new EvaluationException("매니페스트 파일이 없습니다.", enumEvalFailCase.NO_MANIFEST);
		   	
			
			// __는 위젯스토어에 존재하는 최신버전의 매니페스트 값들을 위한 변수
			//_는 사용자의 매니페스트 값을 위한 변수		
			DocumentBuilder _builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document _doc = _builder.parse(new File(_xmlPath));
			XPath  _xpath = XPathFactory.newInstance().newXPath();
			Node _col = (Node)_xpath.evaluate("/WidgetStore/required/widget/name", _doc, XPathConstants.NODE);
			
			DocumentBuilder __builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document __doc = __builder.parse(new File(_manifesetPath));
			XPath  __xpath = XPathFactory.newInstance().newXPath();
			Node __col = (Node)__xpath.evaluate("/WidgetStore/required/widget/name", __doc, XPathConstants.NODE);
			
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다." ,   enumEvalFailCase.MANIFEST_ERROR);
			
			String _wName = _col.getTextContent();
			
			
			_col = (Node)_xpath.evaluate("/WidgetStore/required/widget/version", _doc, XPathConstants.NODE);
			__col = (Node)__xpath.evaluate("/WidgetStore/required/widget/version", __doc, XPathConstants.NODE);
			if(!_col.getNodeName().equals( __col.getNodeName() ))
				throw new EvaluationException(__col.getNodeName()+"이 존재하지 않습니다." ,   enumEvalFailCase.MANIFEST_ERROR);
			String _ver = _col.getTextContent();
			if(!_ver.contains("."))
				_ver+=".0";
			if(!_ver.matches("[0-9]+(\\.[0-9][0-9]?)?")) {
				throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력하세요.",   enumEvalFailCase.MANIFEST_ERROR);
			}
			float _newVersion = Float.parseFloat(_ver);	
			
			//DB와 비교뒤 결정.
									
			_ps = conn.prepareStatement("select title, currentVersion from widget where developer = ? ");
			_ps.setInt(1, member.getDeveloperId());
			_rs = _ps.executeQuery();
			
			if(_rs.next()){
				if(!_rs.getString("title").equals(_wName))					
					throw new EvaluationException("위젯의 이름은 최초의 매니페스트 등록파일에 등록한 이름과 같아야 합니다." ,   enumEvalFailCase.MANIFEST_ERROR);
				
				float _maniVer = _rs.getFloat("currentVersion");
				if(_maniVer >= _newVersion)
					throw new EvaluationException("새로운 업데이트 버전은 이전보다 버전보다 상위여야(커야)합니다.." ,   enumEvalFailCase.MANIFEST_ERROR);
			}else
				throw new SQLException("DB오류. 관리자에게 문의하세요.");
								
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally {
				if (_ps != null)
					try {
						_ps.close();
					} catch (SQLException ex) {
					}
				if (_rs != null)
					try {
						_rs.close();
					} catch (SQLException ex) {
					}
			
			}
			  
	   return eval;
   	
   	}
   
   private enumWidgetEvaluation addUpdatingWidget(enumWidgetEvaluation  eval) throws SQLException{
   		
	   ResultSet _rs = null;
	   PreparedStatement _ps = null;
		try{
		  if(conn.isClosed()){
				eval = enumWidgetEvaluation.UNALLOWANCE;
			  throw new SQLException("DB오류. 개발자에게 문의하세요. ");
		  	}
		  	conn.setAutoCommit(false);
		  	
		  	
		  	////////////to get oldManifest key
		  	int _oldManifestId;
		  	
		  	_ps = conn.prepareStatement("select manifest_id from widget where developer = ? ");
		  	_ps.setInt(1, member.getDeveloperId());
		  	
		  	_rs = _ps.executeQuery();
		  	if(_rs.next())
		  		_oldManifestId = _rs.getInt("manifest_id");
		  	else
		  		throw new SQLException();
		  		
		  	
		  	
		  	//////////// evaluationManifest table
		  	
			_ps = conn.prepareStatement("insert into evaluationManifest (manifest_version,   widget_version,    widget_kind,   git_tag,   git_branch, "
					+ "is_support_resolution, resolution_method, maxHeight, maxWidth, minWidth, minHeight) values (?,?,?,?,?, ?,?,?,?,? ,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			
			_ps.setInt(1, manifestVersion); 
			_ps.setFloat(2, widgetVersion);
			_ps.setString(3, kind.getString());
			_ps.setString(4, git._tag);
			_ps.setString(5, git._branch);
			if(recommandInfo._isSupportResolution){
				_ps.setInt(6, 1);
			}
			else
				_ps.setInt(6, 0);
			
			_ps.setString(7, recommandInfo._resolutionMethod);
			_ps.setInt(8, recommandInfo.maxHeightSize);
			_ps.setInt(9, recommandInfo.maxWidthSize);
			_ps.setInt(10, recommandInfo.minWidthSize);
			_ps.setInt(11, recommandInfo.minHeightSize);
			
			_ps.executeUpdate();
		
			_rs = _ps.getGeneratedKeys();
			_rs.next();
			int _newManifestId = _rs.getInt(1);
		  	
			if(recommandInfo._isSupportResolution){
				for(store.develop.ManageEvaluation.RecommandInfo.Ratio ary : recommandInfo._ratioArray){
					_ps = conn.prepareStatement("insert into widgetRatio (manifest_id, widthRatio, heightRatio, widthSize, heightSize) values (?,?,?,?,?) ");
					_ps.setInt(1,_newManifestId);
					_ps.setInt(2,ary.widthRatio);
					_ps.setInt(3,ary.heightRatio);
					_ps.setInt(4,ary.widthSize);
					_ps.setInt(5,ary.heightSize);
					
					_ps.executeUpdate();
				}
			}
			
			///////////////widgetEvaluation Table
			
		  	_ps = conn.prepareStatement("insert into widgetEvaluation ( evalState, developer, manifest_id) values (?,?,?)");
		  	 	  	
		  	int evalState = Integer.valueOf(eval.getString());
		  	_ps.setInt(1, evalState);
		  	_ps.setInt(2, member.getDeveloperId());
		  	_ps.setInt(3, _newManifestId);
		  	_ps.executeUpdate();

		  	//////////// widget table
		  	
			_ps = conn.prepareStatement("insert into widget (developer,title, kind, currentVersion, registrationPosition, HTML, manifest_id)"
					+ " values(?,?,?,  ?,?,?,?) ", PreparedStatement.RETURN_GENERATED_KEYS);
			
			_ps.setInt(1, member.getDeveloperId());
			_ps.setString(2, widgetName);
			_ps.setString(3, kind.getString());
			_ps.setFloat(4, widgetVersion);
			_ps.setString(5,position.getString());
			
			_ps.setString(6, new StringBuilder(widgetRoot).append(enumSystem.SOURCE_FOLDER_NAME).append("/").append(rootUrl).toString());
			_ps.setInt(7, _newManifestId);
			
			_ps.executeUpdate();
			_rs = _ps.getGeneratedKeys();
			_rs.next();
			int _wId= _rs.getInt(1);
			
			/////////////////widgetDetail Table
			
			_ps = conn.prepareStatement("insert into widgetDetail (widget_num, main_image, sub_image, explane,widgetRoot) values(?,?,?)");
			_ps.setInt(1, _wId);
			_ps.setString(2, new StringBuilder(widgetRoot).append("/").append(enumSystem.IMAGE_FOLDER_NAME).append("/").toString());
			_ps.setString(3, new StringBuilder(widgetRoot).append("/").append(enumSystem.IMAGE_FOLDER_NAME).append("/").toString());
			_ps.setString(4, contents);
			_ps.setString(5, widgetRoot);
			_ps.executeUpdate();
			
			///////////////////tempUpdatingWidget Table
			
			_ps = conn.prepareStatement("insert into tempUpdatingWidget (old_id,new_id,contents) values(?,?,?)");
			_ps.setInt(1, _oldManifestId);
			_ps.setInt(2, _newManifestId);
			_ps.setString(3, contents);
			
			
		  	conn.commit();
		
		} finally {
			if (_ps != null)
				try {
					_ps.close();
				} catch (SQLException ex) {
				}
			if (_rs != null)
				try {
					_rs.close();
				} catch (SQLException ex) {
				}
		
		}
		  
		
	   return eval;
   	}
   
   private void addEvaluatingWidget(enumWidgetEvaluation eval) throws SQLException{
	    
   ResultSet _rs = null;
   PreparedStatement _ps = null;
	try{
	  if(conn.isClosed()){
			eval = enumWidgetEvaluation.UNALLOWANCE;
		  throw new SQLException("DB오류. 개발자에게 문의하세요. ");
	  	}
	  	conn.setAutoCommit(false);
	  	
	  	//////////// evaluationManifest table
	  	
		_ps = conn.prepareStatement("insert into evaluationManifest (manifest_version,   widget_version,    widget_kind,   git_tag,   git_branch, "
				+ "is_support_resolution, resolution_method, maxHeight, maxWidth, minWidth, minHeight) values (?,?,?,?,?, ?,?,?,?,? ,?)", PreparedStatement.RETURN_GENERATED_KEYS);
		
		_ps.setInt(1, manifestVersion); 
		_ps.setFloat(2, widgetVersion);
		_ps.setString(3, kind.getString());
		_ps.setString(4, git._tag);
		_ps.setString(5, git._branch);
		if(recommandInfo._isSupportResolution){
			_ps.setInt(6, 1);
		}
		else
			_ps.setInt(6, 0);
		
		_ps.setString(7, recommandInfo._resolutionMethod);
		_ps.setInt(8, recommandInfo.maxHeightSize);
		_ps.setInt(9, recommandInfo.maxWidthSize);
		_ps.setInt(10, recommandInfo.minWidthSize);
		_ps.setInt(11, recommandInfo.minHeightSize);
		
		_ps.executeUpdate();
	
		_rs = _ps.getGeneratedKeys();
		_rs.next();
		int _maniId = _rs.getInt(1);
	  	
		if(recommandInfo._isSupportResolution){
			for(store.develop.ManageEvaluation.RecommandInfo.Ratio ary : recommandInfo._ratioArray){
				_ps = conn.prepareStatement("insert into widgetRatio (manifest_id, widthRatio, heightRatio, widthSize, heightSize) values (?,?,?,?,?) ");
				_ps.setInt(1,_maniId);
				_ps.setInt(2,ary.widthRatio);
				_ps.setInt(3,ary.heightRatio);
				_ps.setInt(4,ary.widthSize);
				_ps.setInt(5,ary.heightSize);
				
				_ps.executeUpdate();
			}
		}
		
		///////////////widgetEvaluation Table
		
	  	_ps = conn.prepareStatement("insert into widgetEvaluation ( evalState, developer, manifest_id) values (?,?,?)");
	  	 	  	
	  	int evalState = Integer.valueOf(eval.getString());
	  	_ps.setInt(1, evalState);
	  	_ps.setInt(2, member.getDeveloperId());
	  	_ps.setInt(3, _maniId);
	  	_ps.executeUpdate();

	  	//////////// widget table
	  	
		_ps = conn.prepareStatement("insert into widget (developer,title, kind, currentVersion, registrationPosition, registrationDate, HTML, manifest_id)"
				+ " values(?,?,?,?,  ?,?,?,?) ", PreparedStatement.RETURN_GENERATED_KEYS);
		
		_ps.setInt(1, member.getDeveloperId());
		_ps.setString(2, widgetName);
		_ps.setString(3, kind.getString());
		_ps.setFloat(4, widgetVersion);
		_ps.setString(5,position.getString());
		_ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()) );
		_ps.setString(7, new StringBuilder(widgetRoot).append(enumSystem.SOURCE_FOLDER_NAME).append("/").append(rootUrl).toString());
		_ps.setInt(8, _maniId);
		
		_ps.executeUpdate();
		_rs = _ps.getGeneratedKeys();
		_rs.next();
		int _wId= _rs.getInt(1);
		
		/////////////////widgetDetail Table
		
		_ps = conn.prepareStatement("insert into widgetDetail (widget_num, main_image, sub_image, explane, widgetRoot) values(?,?,?)");
		_ps.setInt(1, _wId);
		_ps.setString(2, new StringBuilder(widgetRoot).append("/").append(enumSystem.IMAGE_FOLDER_NAME).append("/").toString());
		_ps.setString(3, new StringBuilder(widgetRoot).append("/").append(enumSystem.IMAGE_FOLDER_NAME).append("/").toString());
		_ps.setString(4, contents);
		_ps.setString(5, widgetRoot);
		_ps.executeUpdate();
		
	  	conn.commit();
	  	
	}
	 finally {
			if (_ps != null)
				try {
					_ps.close();
				} catch (SQLException ex) {
				}
			if (_rs != null)
				try {
					_rs.close();
				} catch (SQLException ex) {
				}
		
		}
		  
		
	   
   	}
}

	

