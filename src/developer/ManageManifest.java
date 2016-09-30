package developer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javaBean.ConnectMysql;
import javaBean.Member;
import javaBean.DownloadedWidget.Builder;
import property.enums.enumSystem;
import property.enums.widget.enumWidgetEvaluation;
import property.enums.widget.enumWidgetKind;
import property.enums.widget.enumWidgetPosition;
import sun.net.www.content.image.gif;
import property.enums.widget.enumWidgetEvaluation.enumEvalFailCase;

public class ManageManifest {

	public static class RecommandInfo{
		
		public static class Ratio{
			
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
	
	public class GitInfo{
		
		public String _repositoryName;
		public String _gitId;
		public String _tag;
		public String _branch;
		
		public GitInfo(){
			_repositoryName = "";
			_gitId = "";
			_tag = "";
			_branch = "";			
		}
		
	}
	
	private   int manifestVersion;

	private   float widgetVersion;
	private   enumWidgetPosition position;
	private   String rootUrl;

	private RecommandInfo recommandInfo = null;
	private GitInfo git = null;
	private final String originManifestPath;
	private final String widgetManifestPath;
	private File originManifstFile;
	private File widgetManifstFile;
	private enumWidgetEvaluation result;
	private final Connection conn;

	public RecommandInfo getRecommandInfo() {
		return recommandInfo;
	}

	public void setRecommandInfo(RecommandInfo recommandInfo) {
		this.recommandInfo = recommandInfo;
	}
	
	public GitInfo getGit() {
		return git;
	}

	public void setGit(GitInfo git) {
		this.git = git;
	}

	public int getManifestVersion() {
		return manifestVersion;
	}

	public float getWidgetVersion() {
		return widgetVersion;
	}

	public enumWidgetPosition getPosition() {
		return position;
	}

	public String getRootUrl() {
		return rootUrl;
	}

	
	///////////////method//////////////////

	
	public ManageManifest(String widgetPath) {
		
		conn = ConnectMysql.getConnector();
		
		widgetManifestPath = widgetPath+"source/manifest.xml";
		originManifestPath = enumSystem.MANIFEST_FULLPATH.toString();
		
		
		widgetManifstFile = new File(widgetManifestPath);
		originManifstFile = new File(originManifestPath);
		
		git = new GitInfo();
		recommandInfo = new RecommandInfo();
		
		result = enumWidgetEvaluation.EVALUATING;
			
		
	}

	/*
	 * 	db를 업데이트 하기 위해 메니페스트의 모든 요소를 수집한다.
	 */
	public enumWidgetEvaluation  doCollectAll(){
		
		try{
			getManifestInformation();				
		}catch(EvaluationException e){
			result = enumWidgetEvaluation.UNALLOWANCE;
			result.setFailCase(e.getFailCsae());
			result.setErrMsg(e.getMessage());
		}
		
		return result;
		
	}
	
	/**
	 * 업데이트 될 위젯이 업로드 되면 현재 버전과 비교하여 유효한지 검사한다.
	 * 
	 * @param eval
	 * @return
	 * @throws EvaluationException
	 * @throws SQLException
	 */
	public enumWidgetEvaluation VerifyUpdatedWidget(int wId, enumWidgetEvaluation eval)
		throws EvaluationException, SQLException {


		PreparedStatement _ps = null;
		ResultSet _rs = null;

		try {

			if (!widgetManifstFile.exists())
				throw new EvaluationException("매니페스트 파일이 없습니다.", enumEvalFailCase.NO_MANIFEST);

			// __는 위젯스토어에 존재하는 최신버전의 매니페스트 값들을 위한 변수
			// _는 사용자의 매니페스트 값을 위한 변수
			DocumentBuilder _builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document _doc = _builder.parse(widgetManifestPath);
			XPath _xpath = XPathFactory.newInstance().newXPath();
			Node _col = (Node) _xpath.evaluate("/WidgetStore/required/widget/name", _doc,
					XPathConstants.NODE);

			DocumentBuilder __builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document __doc = __builder.parse(originManifstFile);
			XPath __xpath = XPathFactory.newInstance().newXPath();
			Node __col = (Node) __xpath.evaluate("/WidgetStore/required/widget/name", __doc,
					XPathConstants.NODE);

			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
						enumEvalFailCase.MANIFEST_ERROR);

			String _wName = _col.getTextContent();

			_col = (Node) _xpath.evaluate("/WidgetStore/required/widget/version", _doc,
					XPathConstants.NODE);
			__col = (Node) __xpath.evaluate("/WidgetStore/required/widget/version", __doc,
					XPathConstants.NODE);
			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
						enumEvalFailCase.MANIFEST_ERROR);
			String _ver = _col.getTextContent();
			if (!_ver.contains("."))
				_ver += ".0";
			if (!_ver.matches("[0-9]+(\\.[0-9][0-9]?)?")) {
				throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력하세요.",
						enumEvalFailCase.MANIFEST_ERROR);
			}
			float _newVersion = Float.parseFloat(_ver);

			// DB와 비교뒤 결정.

			_ps = conn.prepareStatement("select title, currentVersion from widget where developer = ? ");
			_ps.setInt(1, wId);
			_rs = _ps.executeQuery();

			if (_rs.next()) {
				if (!_rs.getString("title").equals(_wName))
					throw new EvaluationException("위젯의 이름은 최초의 매니페스트 등록파일에 등록한 이름과 같아야 합니다.",
							enumEvalFailCase.MANIFEST_ERROR);

				float _maniVer = _rs.getFloat("currentVersion");
				if (_maniVer >= _newVersion)
					throw new EvaluationException("새로운 업데이트 버전은 이전보다 버전보다 상위여야(커야)합니다..",
							enumEvalFailCase.MANIFEST_ERROR);
			} else
				throw new SQLException("DB오류. 관리자에게 문의하세요.");

			//문제가 없다면  widgetManifest tempUpdatingWidget에 저장
			
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
	

	
	private void getManifestInformation() throws EvaluationException {

	
		try {

			if (!originManifstFile.exists())
				throw new EvaluationException("매니페스트 파일이 없습니다.",  enumEvalFailCase.NO_MANIFEST);
			
			// __는 위젯스토어에 존재하는 최신버전의 매니페스트 값들을 위한 변수
			// _는 사용자의 매니페스트 값을 위한 변수
			DocumentBuilder _builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document _doc = _builder.parse(widgetManifestPath);
			XPath _xpath = XPathFactory.newInstance().newXPath();
			Node _col = (Node)_xpath.evaluate("/WidgetStore", _doc, XPathConstants.NODE);
			
			DocumentBuilder __builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document __doc = __builder.parse(originManifstFile);
			XPath __xpath = XPathFactory.newInstance().newXPath();
			Node __col = (Node)__xpath.evaluate("/WidgetStore", __doc, XPathConstants.NODE);
		

			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
						enumEvalFailCase.MANIFEST_ERROR);

			///////////// required 시작/////////////

			NodeList _cols = (NodeList) _xpath.evaluate("*/required", _doc, XPathConstants.NODESET);
			NodeList __cols = (NodeList) __xpath.evaluate("*/required", __doc, XPathConstants.NODESET);

			_col = _cols.item(0);
			__col = __cols.item(0);

			System.out.println(_col.getNodeName());

			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
						enumEvalFailCase.MANIFEST_ERROR);

			_col = (Node) _xpath.evaluate("*/required/manifest_version", _doc, XPathConstants.NODE);
			__col = (Node) _xpath.evaluate("*/required/manifest_version", __doc, XPathConstants.NODE);
			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
						enumEvalFailCase.MANIFEST_ERROR);
			else {
				String __manifest_version = __col.getTextContent();
				String _manifest_version = _col.getTextContent();
				if (!isInteger(_manifest_version))
					throw new EvaluationException(
							"매니퍼스트 버전의 값이 올바르지 않습니다.(정수만 입력 가능합니다) 매니패스트 메뉴얼을 참조하세요.",
							enumEvalFailCase.MANIFEST_ERROR);

				if (Integer.valueOf(__manifest_version) != Integer.valueOf(_manifest_version))
					throw new EvaluationException(
							"매니페스트 버전이 현재 위젯스토어의 버전과 일치하지 않습니다다. 매니패스트 메뉴얼을 참조하세요.",
							enumEvalFailCase.MANIFEST_ERROR);
			}
			manifestVersion = Integer.valueOf(_col.getTextContent());

			_col = (Node) _xpath.evaluate("*/required/widget", _doc, XPathConstants.NODE);
			__col = (Node) _xpath.evaluate("*/required/widget", __doc, XPathConstants.NODE);
			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new SAXException(_col.getTextContent() + "이 존재하지 않습니다.");

			_col = (Node) _xpath.evaluate("*/required/widget/version", _doc, XPathConstants.NODE);
			__col = (Node) _xpath.evaluate("*/required/widget/version", __doc, XPathConstants.NODE);
			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new SAXException(_col.getTextContent() + "이 존재하지 않습니다.");
			else {
				String _ver = _col.getTextContent();
				if (!_ver.contains("."))
					_ver += ".0";
				if (!_ver.matches("[0-9]+(\\.[0-9][0-9]?)?")) {
					throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력하세요.",
							enumEvalFailCase.MANIFEST_ERROR);
				}

				widgetVersion = Float.parseFloat(_ver);
			}

			_col = (Node) _xpath.evaluate("*/required/widget/root", _doc, XPathConstants.NODE);
			__col = (Node) _xpath.evaluate("*/required/widget/root", __doc, XPathConstants.NODE);
			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",
						enumEvalFailCase.MANIFEST_ERROR);
			rootUrl = _col.getTextContent();

			_col = (Node) _xpath.evaluate("*/required/position", _doc, XPathConstants.NODE);
			__col = (Node) _xpath.evaluate("*/required/position", __doc, XPathConstants.NODE);
			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",
						enumEvalFailCase.MANIFEST_ERROR);
			else {
				String _pos = ((Element) ((NodeList) _xpath.evaluate("*/required/position", _doc,
						XPathConstants.NODESET)).item(0)).getAttribute("kind");
				for (enumWidgetPosition p : enumWidgetPosition.values()) {
					if (p.getString().equals(_pos)) {
						this.position = p;
						break;
					}
				}
				if (this.position == null) {
					throw new EvaluationException(
							"유저 매니페스트의 position 값이 시스템에 존재하지 않습니다. 매니패스트 메뉴얼을 참조하세요. ",
							enumEvalFailCase.MANIFEST_ERROR);

				}

			}

			switch (position) {

				case NOTHING:
					position = enumWidgetPosition.NOTHING;
					break;

				case GIT:

					_col = (Node) _xpath.evaluate("*/required/position/git", _doc,
							XPathConstants.NODE);
					__col = (Node) _xpath.evaluate("*/required/position/git", __doc,
							XPathConstants.NODE);
					if (!_col.getNodeName().equals(__col.getNodeName()))
						throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
								enumEvalFailCase.MANIFEST_ERROR);

					_col = (Node) _xpath.evaluate("*/required/position/git/id", _doc,
							XPathConstants.NODE);
					__col = (Node) _xpath.evaluate("*/required/position/git/id", __doc,
							XPathConstants.NODE);
					if (!_col.getNodeName().equals(__col.getNodeName()))
						throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
								enumEvalFailCase.MANIFEST_ERROR);
					else {
						String _id = _col.getTextContent();
						git._gitId = _id;
					}

					_col = (Node) _xpath.evaluate("*/required/position/git/repository", _doc,
							XPathConstants.NODE);
					__col = (Node) _xpath.evaluate("*/required/position/git/repository", __doc,
							XPathConstants.NODE);
					if (!_col.getNodeName().equals(__col.getNodeName()))
						throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
								enumEvalFailCase.MANIFEST_ERROR);
					else {
						String _rep = _col.getTextContent();
						git._repositoryName = _rep;
					}

					// branch와 tag는 둘 중 하나만 기입 될 수 있다. 두개 모두
					// 기입 되어 있으면 불허.
					String _branch = "", _tag = "";
					Node _branchNode = (Node) _xpath.evaluate("*/required/position/git/branch",
							_doc, XPathConstants.NODE);
					__col = (Node) _xpath.evaluate("*/required/position/git/branch", __doc,
							XPathConstants.NODE);
					if (_branchNode != null)
						if (!_branchNode.getNodeName().equals(__col.getNodeName()))
							throw new EvaluationException(
									__col.getNodeName() + "이 존재하지 않습니다.",
									enumEvalFailCase.MANIFEST_ERROR);

					Node _tagNode = (Node) _xpath.evaluate("*/required/position/git/tag", _doc,
							XPathConstants.NODE);
					__col = (Node) _xpath.evaluate("*/required/position/git/tag", __doc,
							XPathConstants.NODE);
					if (_tagNode != null)
						if (!_tagNode.getNodeName().equals(__col.getNodeName()))
							throw new EvaluationException(
									__col.getNodeName() + "이 존재하지 않습니다.",
									enumEvalFailCase.MANIFEST_ERROR);

					if (_tagNode != null && _branchNode != null)
						throw new EvaluationException("git의 저장소로 tag 또는 branch 하나만 택해야 합니다.",
								enumEvalFailCase.MANIFEST_ERROR);

					if (_branchNode != null)
						git._branch = _branchNode.getTextContent();
					else if (_tagNode != null)
						git._tag = _tag;

					break;
				// git 끝

				default:
					throw new EvaluationException(
							"유저 매니페스트의 position 값이 시스템에 존재하지 않습니다. 매니패스트 메뉴얼을 참조하세요. ",
							enumEvalFailCase.MANIFEST_ERROR);
				
			}

			/////////////// required 끝/////////////

			//////////////////// recommand가 존재하면 태그의 값들을 파싱하기 위해 함수
			//////////////////// 수행
			recommandInfo = new RecommandInfo();
			_col = (Node) _xpath.evaluate("*/recommanded", _doc, XPathConstants.NODE);

			if (_col == null) {
				recommandInfo.isExistRecommand = false;
				recommandInfo._iconPath = "";
			} else
				getManifestOfRecommand();

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
		/*
		 * catch (EvaluationException e1){
		 * eval.setErrMsg(e1.getMessage()); eval =
		 * enumWidgetEvaluation.UNALLOWANCE;
		 * eval.setFailCase(e1.getFailCsae());
		 * 
		 * }
		 */

		result = enumWidgetEvaluation.PASS;
	}

	/**
	 * manifest에 recommand가 기입되어 있으면 파싱한다. required 함수 맨 끝에서 recommand가 있는지
	 * 여부를 검사.
	 * 
	 * @param _col
	 *                required에서 widget manifest를 파싱하기 위한 node변수
	 * @param __col
	 *                origin manifest를 파싱하기 위한 node 변수
	 * @param eval
	 *                required의 eval변수를 받는다.
	 * @return 위젯심사 결과를 리턴한다.
	 * @throws EvaluationException
	 */
	private void getManifestOfRecommand() throws EvaluationException {

		/////////////// recommand 시작////////////////

		NodeList _cols, __cols;
		Node _col, __col;

		try {

			if (!originManifstFile.exists())	
				throw new EvaluationException("매니페스트 파일이 없습니다.", enumEvalFailCase.NO_MANIFEST);

			// __는 위젯스토어에 존재하는 최신버전의 매니페스트 값들을 위한 변수
			// _는 사용자의 매니페스트 값을 위한 변수
			DocumentBuilder _builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document _doc = _builder.parse(widgetManifestPath);
			XPath _xpath = XPathFactory.newInstance().newXPath();

			DocumentBuilder __builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document __doc = __builder.parse(originManifstFile);
			XPath __xpath = XPathFactory.newInstance().newXPath();

			_col = (Node) _xpath.evaluate("*/recommanded/isSupportResolution", _doc, XPathConstants.NODE);
			__col = (Node) _xpath.evaluate("*/recommanded/isSupportResolution", __doc, XPathConstants.NODE);
			if (!_col.getNodeName().equals(__col.getNodeName()))
				throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
						enumEvalFailCase.MANIFEST_ERROR);

			if (_col.getTextContent().equals("yes")) {

				_col = (Node) _xpath.evaluate("*/recommanded/resolution", _doc, XPathConstants.NODE);

				__col = (Node) _xpath.evaluate("*/recommanded/resolution", __doc, XPathConstants.NODE);
				if (!_col.getNodeName().equals(__col.getNodeName()))
					throw new EvaluationException(__col.getNodeName() + "이 존재하지 않습니다.",
							enumEvalFailCase.MANIFEST_ERROR);
				else {
					String _res = ((Element) ((NodeList) _xpath.evaluate("*/recommanded/resolution",
							_doc, XPathConstants.NODESET)).item(0)).getAttribute("method");
					if (_res == null)
						throw new EvaluationException("버전의 값이 올바르지 않습니다. 정수 혹은 소수만 입력 바랍니다",
								enumEvalFailCase.MANIFEST_ERROR);

					switch (_res) {
						case "ratio":

							NodeList _ratioNodes = (NodeList) _xpath.evaluate(
									"//recommanded/resolution/ratio/*", _doc,
									XPathConstants.NODESET);
							for (int i = 0; i < _ratioNodes.getLength(); i++) {
								Element e = (Element) _ratioNodes.item(i);
								String[] preSplitValue = e.getAttribute("value")
										.split(":");
								String[] preSplitSize = e.getAttribute("size")
										.split(",");

								// w는 width h는
								// height
								int wRatio, hRatio, wSize, hSize;
								if (preSplitSize.length != 2
										&& preSplitValue.length != 2)
									throw new EvaluationException(
											"ratio노드의 value, size값이 올바르지 않습니다. 매니페스트를 확인하세요.",
											enumEvalFailCase.MANIFEST_ERROR);

								wRatio = Integer.valueOf(preSplitValue[0]);
								hRatio = Integer.valueOf(preSplitValue[1]);
								wSize = Integer.valueOf(preSplitSize[0]);
								hSize = Integer.valueOf(preSplitSize[1]);

								recommandInfo._ratioArray
										.add(new RecommandInfo.Ratio(
												wRatio, hRatio, wSize,
												hSize));
							}

							break;

						case "free":

							_col = (Node) _xpath.evaluate("*/recommanded/resolution/free",
									_doc, XPathConstants.NODE);
							__col = (Node) _xpath.evaluate("*/recommanded/resolutio/free",
									__doc, XPathConstants.NODE);
							if (!_col.getNodeName().equals(__col.getNodeName()))
								throw new EvaluationException(
										__col.getNodeName() + "값이 존재하지 않습니다.",
										enumEvalFailCase.MANIFEST_ERROR);

							NodeList __freeNodes = (NodeList) __xpath.evaluate(
									"//recommanded/resolution/free/*", __doc,
									XPathConstants.NODESET);
							NodeList _freeNodes = (NodeList) _xpath.evaluate(
									"//recommanded/resolution/free/*", _doc,
									XPathConstants.NODESET);
							if (_freeNodes.getLength() != 4)
								throw new EvaluationException(
										"resolution/free의 하위노드는 총 4개여야 합니다.  매니페스트를 확인하세요.",
										enumEvalFailCase.MANIFEST_ERROR);

							// widget, height size 총
							// 4번 순회 해야함.
							// minWidthSize
							Element _e = (Element) _freeNodes.item(0);
							Element __e = (Element) __freeNodes.item(0);
							if (_e.getNodeName().equals(__e.getNodeName()) == false)
								throw new EvaluationException(
										__col.getNodeName() + "이 존재하지 않습니다.",
										enumEvalFailCase.MANIFEST_ERROR);

							String _temp;
							_temp = _e.getTextContent();
							if (isInteger(_temp))
								recommandInfo.minWidthSize = Integer.parseInt(_temp);
							else
								throw new EvaluationException("크기의 값은 정수만 허용됩니다.",
										enumEvalFailCase.MANIFEST_ERROR);

							// minHeightSize
							_e = (Element) _freeNodes.item(1);
							__e = (Element) __freeNodes.item(1);
							if (_e.getNodeName().equals(__e.getNodeName()) == false)
								throw new EvaluationException(
										__col.getNodeName() + "이 존재하지 않습니다.",
										enumEvalFailCase.MANIFEST_ERROR);

							_temp = _e.getTextContent();
							if (isInteger(_temp))
								recommandInfo.minHeightSize = Integer.parseInt(_temp);
							else
								throw new EvaluationException("크기의 값은 정수만 허용됩니다.",
										enumEvalFailCase.MANIFEST_ERROR);

							// maxWidthSize
							_e = (Element) _freeNodes.item(2);
							__e = (Element) __freeNodes.item(2);
							if (_e.getNodeName().equals(__e.getNodeName()) == false)
								throw new EvaluationException(
										__col.getNodeName() + "이 존재하지 않습니다.",
										enumEvalFailCase.MANIFEST_ERROR);

							_temp = _e.getTextContent();
							if (isInteger(_temp))
								recommandInfo.maxWidthSize = Integer.parseInt(_temp);
							else
								throw new EvaluationException("크기의 값은 정수만 허용됩니다.",
										enumEvalFailCase.MANIFEST_ERROR);

							// maxHeightSize
							_e = (Element) _freeNodes.item(3);
							__e = (Element) __freeNodes.item(3);
							if (_e.getNodeName().equals(__e.getNodeName()) == false)
								throw new EvaluationException(
										__col.getNodeName() + "이 존재하지 않습니다.",
										enumEvalFailCase.MANIFEST_ERROR);

							_temp = _e.getTextContent();
							if (isInteger(_temp))
								recommandInfo.maxHeightSize = Integer.parseInt(_temp);
							else
								throw new EvaluationException("크기의 값은 정수만 허용됩니다.",
										enumEvalFailCase.MANIFEST_ERROR);

							break;

						default:
							throw new EvaluationException(
									"resolution에는 free,auto,ratio만 가능합니다. 매니페스트를 확인하세요.",
									enumEvalFailCase.MANIFEST_ERROR);

					}// switch

				} // end resolution method

				_col = (Node) _xpath.evaluate("*/recommanded/icon-path", _doc, XPathConstants.NODE);
				__col = (Node) _xpath.evaluate("*/recommanded/icon-path", __doc, XPathConstants.NODE);
				if (!_col.getNodeName().equals(__col.getNodeName()))
					throw new EvaluationException(__col.getNodeName() + "값이 존재하지 않습니다.",
							enumEvalFailCase.MANIFEST_ERROR);

				recommandInfo._iconPath = _col.getTextContent();

				/////// recommanded 끝

				//// optional 시작

				_col = (Node) _xpath.evaluate("*/optional", _doc, XPathConstants.NODE);

				if (_col == null) {
					;
				} else
					getManifestOfOptional();

			}
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


	}

	private void getManifestOfOptional() throws EvaluationException {

	
		NodeList _cols, __cols;
		Node _col, __col;

		try {

			if (!originManifstFile.exists())	
				throw new EvaluationException("매니페스트 파일이 없습니다.", enumEvalFailCase.NO_MANIFEST);

			// __는 위젯스토어에 존재하는 최신버전의 매니페스트 값들을 위한 변수
			// _는 사용자의 매니페스트 값을 위한 변수
			DocumentBuilder _builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document _doc = _builder.parse(widgetManifestPath);
			XPath _xpath = XPathFactory.newInstance().newXPath();

			DocumentBuilder __builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document __doc = __builder.parse(originManifstFile);
			XPath __xpath = XPathFactory.newInstance().newXPath();

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

	
	}


	private static boolean isInteger(String s) {
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

	
	
}

