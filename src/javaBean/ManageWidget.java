package javaBean;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import developer.ManageEvaluation;
import developer.ManageManifest;
import developer.ManageManifest.RecommandInfo.Ratio;
import page.PageException;
import property.enums.enumPageError;
import property.enums.enumSystem;
import property.enums.widget.enumResolutionKind;
import property.enums.widget.enumWidgetEvaluation;


public class ManageWidget {

	private static Connection conn = ConnectMysql.getConnector();


	/**
	 * widget테이블을 삭제하면 나머지는 모두 DB의 프로시져가 알아서 제거한다.
	 * 
	 * @param devId
	 *                개발자 id
	 * @param wId
	 *                위젯 id
	 * @throws SQLException
	 * @throws PageException
	 */
	public static void removeWidget(int devId, int wId) throws SQLException, PageException {

		PreparedStatement _ps = null;
		ResultSet _rs = null;

		try {
			conn.setAutoCommit(false);

			// 삭제하려는 위젯이 유저가 갖고 있는 위젯과 일치하는지 검사.
			_ps = conn.prepareStatement("select developerId from widget where widget_num = ?");
			_ps.setInt(1, wId);
			_rs = _ps.executeQuery();
			_rs.next();
			if (devId != _rs.getInt(1))
				throw new PageException("파라메터가 일치하지 안습니다.", enumPageError.UNKNOWN_PARA_VALUE);
			_ps.close();
			_rs.close();

			_ps = conn.prepareStatement("delete from widget where widget_num = ?");
			_ps.setInt(1, wId);
			_ps.executeUpdate();

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

	}


	/**
	 * 
	 * 업데이트 될 위젯이 평가 될 수 있도록 DB를 갱신한다.
	 * 
	 * @param eval
	 * @return
	 * @throws SQLException
	 */
	public static enumWidgetEvaluation addUpdatingWidget(ManageEvaluation widget, enumWidgetEvaluation eval, int oldWidgetKey)
			throws SQLException {

		ResultSet _rs = null;
		PreparedStatement _ps = null;
		try {
			if (conn.isClosed()) {
				eval = enumWidgetEvaluation.UNALLOWANCE;
				throw new SQLException("DB오류. 개발자에게 문의하세요. ");
			}
			conn.setAutoCommit(false);


			/////////////// widgetEvaluation Table
			_ps = conn.prepareStatement(
					"insert into widgetEvaluation ( evalState, d_id) values (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

			int evalState = Integer.valueOf(eval.getString());
			_ps.setInt(1, evalState);
			_ps.setInt(2, widget.getMember().getDeveloperId());
			_ps.executeUpdate();

			_rs = _ps.getGeneratedKeys();
			_rs.next();
			int eval_id = _rs.getInt(1);
		
			_ps.close();
			_rs.close();

			////////////////updatingWidget   필요
			
			_ps = conn.prepareStatement(
					"insert into updatingWidget(eval_id, old_widget_key) values (?,?)");
			
			_ps.setInt(1, eval_id);
			_ps.setInt(2, oldWidgetKey);
			_ps.executeUpdate();	
			
			
			///////////////evaluationManifest
			
			
			_ps = conn.prepareStatement("insert into evaluationManifest ( eval_id, title, kind, contents, version, main_image, sub_image, widget_root ,position ) values (?,?,?,?, ?,?,?,?, ?)");
			_ps.setInt(1,eval_id);
			_ps.setString(2, widget.getWidgetName());
			_ps.setString(3,widget.getKind().toString() );
			_ps.setString(4,widget.getContents() );
			_ps.setFloat(5,widget.getManifest().getWidgetVersion() );
			_ps.setString(6, widget.getMainImageFullPath() );
			_ps.setString(7, widget.getSubImageFullPath());
			_ps.setString(8, widget.getWidgetRoot());
			_ps.setString(9, widget.getManifest().getPosition().toString());
			_ps.executeUpdate();

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

	/**
	 * 새로 업로드 된 위젯의 자동검사가 끝나면 이를 DB에 업데이트한다 evaluationManifest,
	 * widgetEvaluation, widget, widgetDetail 의 테이블에 모두 insert된다.
	 * 
	 * @param eval
	 * @throws SQLException
	 */
	public static enumWidgetEvaluation addEvaluatingWidget(ManageEvaluation widget, enumWidgetEvaluation eval) throws SQLException {

		ResultSet _rs = null;
		PreparedStatement _ps = null;
		try {
			if (conn.isClosed()) {
				eval = enumWidgetEvaluation.UNALLOWANCE;
				throw new SQLException("DB오류. ");
			}
			conn.setAutoCommit(false);

	

			/////////////// widgetEvaluation Table
			_ps = conn.prepareStatement(
					"insert into widgetEvaluation ( evalState, d_id) values (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

			int evalState = Integer.valueOf(eval.getString());
			_ps.setInt(1, evalState);
			_ps.setInt(2, widget.getMember().getDeveloperId());
			_ps.executeUpdate();

			_rs = _ps.getGeneratedKeys();
			_rs.next();
			int eval_id = _rs.getInt(1);
		
			_ps.close();
			_rs.close();
			
			///////////////evaluationManifest
			
			
			_ps = conn.prepareStatement("insert into evaluationManifest ( eval_id, title, kind, contents, version, main_image, sub_image, widget_root ,position , main_html) values (?,?,?,?, ?,?,?,?, ?,?)");
			_ps.setInt(1,eval_id);
			_ps.setString(2, widget.getWidgetName());
			_ps.setString(3,widget.getKind().toString() );
			_ps.setString(4,widget.getContents() );
			_ps.setFloat(5,widget.getManifest().getWidgetVersion() );
			_ps.setString(6, widget.getMainImageFullPath() );
			_ps.setString(7, widget.getSubImageFullPath());
			_ps.setString(8, widget.getWidgetRoot());
			_ps.setString(9, widget.getManifest().getPosition().toString());
			_ps.setString(10, widget.getManifest().getMainHTML());
			_ps.executeUpdate();
			
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
		
		return enumWidgetEvaluation.PASS;
	}

	/**
	 *  	새로 업로드된 위젯의 평가가 모두 끝나면 DB를 갱신하고 해당 멤버가 로그인 중이면 이를 리스트에 추가한다.
	 *     
	 * 	
	 * @param widget manifest정보가 포함된 변수 
	 * @param evalId db의 evalId
	 * @throws Exception 
	 */
	public static void addEvaluatedWidget(ManageManifest mani,String sId, int uId, int evalId) throws Exception{
		
		
		ResultSet _rs = null;
		PreparedStatement _ps = null;
		try {
			if (conn.isClosed()) 
				throw new SQLException("DB오류. ");
			else if(mani == null)
				throw new NullPointerException("manifest 변수가 null입니다.");
			
			conn.setAutoCommit(false);

//////////////get
			
			///////////get evaluationManifest
			
			_ps = conn.prepareStatement("select * from evaluationManifest where eval_id = ?");
			_ps.setInt(1, evalId);
			_rs = _ps.executeQuery();
			_rs.next();
			String _widgetName = _rs.getString("title");
			String _kind = _rs.getString("kind");
			String _contents = _rs.getString("contents");
			float _version = _rs.getFloat("version");
			String _mainImageFullPath = _rs.getString("main_image");
			String _subImageFullPath = _rs.getString("sub_image");
			String _widgetRoot =_rs.getString("widget_root");
			
			String relativeWidgetRoot = new StringBuilder("/").append(enumSystem.UPLOAD_FOLDER_NAME.toString()).append("/").append(uId).append("/").
					append(mani.getWidgetName()).append("/").append(enumSystem.SOURCE_FOLDER_NAME.toString()).append("/").toString();
			
			
			
			String _main_html =   "<iframe width='"+mani.getRecommandInfo().fixedWidth+"' height='"+mani.getRecommandInfo().fixedHeight+"' src='" + relativeWidgetRoot+ _rs.getString("main_html" ) + "'></iframe>" ;
			String _position = _rs.getString("position");
			
			_ps.close();
			_rs.close();
			
			
			/////////////get widgetEvaluation
			_ps = conn.prepareStatement("select * from widgetEvaluation where eval_id = ?");
			_ps.setInt(1, evalId);
			_rs = _ps.executeQuery();
			_rs.next();
			int _dId = _rs.getInt("d_id");
			Timestamp _updatedDate = _rs.getTimestamp("evaluationBeginDate");
			
			_ps.close();
			_rs.close();
			////////////get 
			
			_ps = conn.prepareStatement("select * from developer where d_id = ?");
			_ps.setInt(1, _dId);
			_rs = _ps.executeQuery();
			_rs.next();
			int _uId = _rs.getInt("u_id");
			
			_ps.close();
			_rs.close();
			
			////////////get developerName
			
			_ps = conn.prepareStatement("select nickname from user where u_id = ?");
			_ps.setInt(1, _uId);
			_rs = _ps.executeQuery();
			_rs.next();
			String nickname = _rs.getString("nickname");

			_ps.close();
			_rs.close();
			
/////////////set			
			
			//////widgetManifest
			_ps = conn.prepareStatement("insert into widgetManifest (manifest_version,   widget_version,    widget_kind,   git_tag,   git_branch, "
					+ " resolution_method, maxHeight, maxWidth, minWidth, minHeight) values (?,?,?,?,?, ?,?,?,?,? )", PreparedStatement.RETURN_GENERATED_KEYS);
			
			_ps.setInt(1, mani.getManifestVersion()); 
			_ps.setFloat(2, mani.getWidgetVersion());
			_ps.setString(3, _kind );
			_ps.setString(4, mani.getGit()._tag);
			_ps.setString(5, mani.getGit()._branch);
			if(mani.getRecommandInfo()._isSupportResolution){
				_ps.setInt(6, 1);
			}
			else
				_ps.setInt(6, 0);
			
			switch(mani.getRecommandInfo().resolutionKind){
				
				case FIXED:
					_ps.setInt(7, mani.getRecommandInfo().fixedHeight);
					_ps.setInt(8, mani.getRecommandInfo().fixedWidth);
					_ps.setInt(9, mani.getRecommandInfo().fixedWidth);
					_ps.setInt(10, mani.getRecommandInfo().fixedHeight);
					
					break;
					
				case RATIO:
					//ration저장은 manifestId가 빌도로 필요 하므로 이후에 다시 저장한다.
					break;
					
				case FREE:
					_ps.setInt(7, mani.getRecommandInfo().maxHeightSize);
					_ps.setInt(8, mani.getRecommandInfo().maxWidthSize);
					_ps.setInt(9, mani.getRecommandInfo().minWidthSize);
					_ps.setInt(10, mani.getRecommandInfo().minHeightSize);
					
					break;
					
				case AUTO:
					//아직 미지원
					break;
								
			
			
			}
	
			_ps.executeUpdate();
		
			_rs = _ps.getGeneratedKeys();
			_rs.next();
			int _manifestId = _rs.getInt(1);
			
			/////////////////widget Ratio
			
			if(mani.getRecommandInfo().resolutionKind == enumResolutionKind.RATIO){
				for(Ratio ary : mani.getRecommandInfo()._ratioArray){
					_ps = conn.prepareStatement("insert into widgetRatio (manifest_id, widthRatio, heightRatio, widthSize, heightSize) values (?,?,?,?,?) ");
					_ps.setInt(1,_manifestId);
					_ps.setInt(2,ary.widthRatio);
					_ps.setInt(3,ary.heightRatio);
					_ps.setInt(4,ary.widthSize);
					_ps.setInt(5,ary.heightSize);
					
					_ps.executeUpdate();
				}
			}
			
			_ps.close();
			_rs.close();
			//////////// widget table
		  	
			_ps = conn.prepareStatement("insert into widget (d_id,title, kind, currentVersion, position,  registrationDate, HTML, manifest_id)"
					+ " values(?,?,?,?,  ?,?,?,?) ", PreparedStatement.RETURN_GENERATED_KEYS);
			
			_ps.setInt(1, _dId);
			_ps.setString(2, _widgetName);
			_ps.setString(3, _kind);
			_ps.setFloat(4, mani.getWidgetVersion());
			_ps.setString(5, mani.getPosition().toString());
			_ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()) );
			_ps.setString(7, _main_html);
			_ps.setInt(8, _manifestId);
			
			_ps.executeUpdate();
			_rs = _ps.getGeneratedKeys();
			_rs.next();
			int _wId= _rs.getInt(1);
			
			_ps.close();
			_rs.close();
			//	///////////////widgetDetail Table
			
			_ps = conn.prepareStatement("insert into widgetDetail (widget_id, main_Image, sub_Image, contents,  widget_root) values(?,?,?,?,?)");
			_ps.setInt(1, _wId);
			_ps.setString(2,_mainImageFullPath);
			_ps.setString(3, _subImageFullPath);
			_ps.setString(4, _contents);
			_ps.setString(5, _widgetRoot);
			_ps.executeUpdate();
			
			_ps.close();
			_rs.close();
			
	
/////////////////delete
			
			_ps = conn.prepareStatement("delete from widgetEvaluation where eval_id = ?");
			_ps.setInt(1, evalId);
			_ps.executeUpdate();
			
			
			
///////////////////user
			
			
			
			if(Member.isContainsMember(uId)){
				Member member = Member.getMember(uId,sId);
		
				////멤버가 로그인 중이라 객체에 위젯 정보 추가
				
				if(member.getDevelopedWidget()==null)
					throw new NullPointerException("devlopedWideget list가 null입니다.");
					
				DevelopedWidget widget = new DevelopedWidget.Builder(_wId, _widgetName, _kind)
						.contents(_contents)
						.developerId(_dId)
						.developer(nickname)
						.mainImagePath( _mainImageFullPath)
						.subImagePath(_subImageFullPath)
						.position(_position)
						.sourceRoot(_widgetRoot)
						.updatedDate(_updatedDate).build();

				member.addDevelopedWidget(widget);
				
			}
			
			
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
		
	}
	
	/**
	 * 
	 *   업데이트 신청한 위젯의 평가가 모두 끝나면 DB를 갱신하고 해당 멤버가 로그인 중이면 이를 추가한다.
	 * 
	 * @param widget
	 * @throws Exception 
	 */
	public static void addEvaludatedUpdatedWidget(ManageManifest mani, String sId, int uId, int evalId) throws Exception{
		
	
		ResultSet _rs = null;
		PreparedStatement _ps = null;
		try {
			if (conn.isClosed()) 
				throw new SQLException("DB오류. ");
			else if(mani == null)
				throw new NullPointerException("manifest 변수가 null입니다.");
			
			conn.setAutoCommit(false);

		//////////////get
					
					//////////get oldWidgetKey
			
					_ps = conn.prepareStatement("select * from updatingWidget where eval_id = ?");
					_ps.setInt(1, evalId);
					_rs = _ps.executeQuery();
					_rs.next();
					int _oldWidgetKey = _rs.getInt("old_widget_key");
		
					_rs.close();
					_ps.close();
					
					//////////get widgetManifesId
			
					_ps = conn.prepareStatement("select * from widget where widget_id = ?");
					_ps.setInt(1, _oldWidgetKey);
					_rs = _ps.executeQuery();
					_rs.next();
					int _widgetManifestId = _rs.getInt("manifest_id");
		
					_rs.close();
					_ps.close();
					
							
					
					///////////get evaluationManifest
					
					_ps = conn.prepareStatement("select * from evaluationManifest where eval_id = ?");
					_ps.setInt(1, evalId);
					_rs = _ps.executeQuery();
					_rs.next();
					
					String _kind = _rs.getString("kind");
					String _widgetName = _rs.getString("title");
					String _contents = _rs.getString("contents");
					float _version = _rs.getFloat("version");
					String _mainImageFullPath = _rs.getString("main_image");
					String _subImageFullPath = _rs.getString("sub_image");
					String _widgetRoot =_rs.getString("widget_root");
					String _main_html =    "<iframe width='100%' height='100%' src=" + _widgetRoot+ _rs.getString("main_html" ) + "></iframe>" ;
					String _position = _rs.getString("position");
					
					_ps.close();
					_rs.close();
					
					/////////////get widgetEvaluation
					_ps = conn.prepareStatement("select * from widgetEvaluation where eval_id = ?");
					_ps.setInt(1, evalId);
					_rs = _ps.executeQuery();
					_rs.next();
					int _dId = _rs.getInt("d_id");
					Timestamp _updatedDate = _rs.getTimestamp("evaluationBeginDate");
					
					_ps.close();
					_rs.close();
					
					////////////get u_id 
					
					_ps = conn.prepareStatement("select * from developer where d_id = ?");
					_ps.setInt(1, _dId);
					_rs = _ps.executeQuery();
					_rs.next();
					int _uId = _rs.getInt("u_id");
					
					_ps.close();
					_rs.close();
					
					////////////get developerName
					
					_ps = conn.prepareStatement("select nickname from userwhere u_id = ?");
					_ps.setInt(1, _uId);
					_rs = _ps.executeQuery();
					_rs.next();
					String nickname = _rs.getString("nickname");

					_ps.close();
					_rs.close();
																			
	
//////////////////set
					
				//////widgetManifest
					_ps = conn.prepareStatement("update widgetManifest  set manifest_version =? ,"
							+ " widget_version = ? ,  git_tag =? , git_branch = ? ,"
							+ "is_support_resolution = ?, resolution_method = ? , maxHeight = ?, maxWidth = ?"
							+ ", minWidth=? , minHeight =?, manifest_id = ?");
					
					_ps.setInt(1, mani.getManifestVersion()); 
					_ps.setFloat(2, mani.getWidgetVersion());
					_ps.setString(3, mani.getGit()._tag);
					_ps.setString(4, mani.getGit()._branch);
					_ps.setInt(5, mani.getRecommandInfo()._isSupportResolution ? 1 : 0);
					
					_ps.setString(6, mani.getRecommandInfo()._resolutionMethod);
					_ps.setInt(7, mani.getRecommandInfo().maxHeightSize);
					_ps.setInt(8, mani.getRecommandInfo().maxWidthSize);
					_ps.setInt(9, mani.getRecommandInfo().minWidthSize);
					_ps.setInt(10, mani.getRecommandInfo().minHeightSize);
					_ps.setInt(11, _widgetManifestId);
					_ps.executeUpdate();
					
					/////////////////widget Ratio
					
					_ps = conn.prepareStatement("delete from widgetRatio where manifest_id = ?");
					_ps.setInt(1, _widgetManifestId);
					_ps.executeQuery();
					_ps.close();
					_rs.close();
					
					if(mani.getRecommandInfo()._isSupportResolution){
						for(Ratio ary : mani.getRecommandInfo()._ratioArray){
							_ps = conn.prepareStatement("insert into widgetRatio (manifest_id, widthRatio, heightRatio, widthSize, heightSize) values (?,?,?,?,?) ");
							_ps.setInt(1,_widgetManifestId);
							_ps.setInt(2,ary.widthRatio);
							_ps.setInt(3,ary.heightRatio);
							_ps.setInt(4,ary.widthSize);
							_ps.setInt(5,ary.heightSize);
							
							_ps.executeUpdate();
						}
					}
					
					_ps.close();
					_rs.close();
					//////////// widget table
				  	
					_ps = conn.prepareStatement("update widget set d_id = ?,currentVersion = ? , "
							+ "registrationPosition = ? , HTML= ? where widget_id ");
					
					_ps.setInt(1, _dId);
					_ps.setFloat(2, mani.getWidgetVersion());
					_ps.setString(3, mani.getPosition().toString());
					_ps.setString(4, _main_html);
					_ps.setInt(5, _oldWidgetKey);
					
					_ps.executeUpdate();
					
					_ps.close();
					_rs.close();
					//	///////////////widgetDetail Table
					
					_ps = conn.prepareStatement("update widgetDetail set main_image = ? , sub_image = ?, "
							+ "explain = ? , widgetRoot= ? where widget_id");

					_ps.setString(1, _mainImageFullPath);
					_ps.setString(2, _subImageFullPath);
					_ps.setString(3, _contents);
					_ps.setString(4,_widgetRoot);
					_ps.setInt(5, _oldWidgetKey);
					_ps.executeUpdate();
					
					_ps.close();
					_rs.close();
					
		/////////////////delete
					
					_ps = conn.prepareStatement("delete from widgetEvaluation where eval_id = ?");
					_ps.setInt(1, evalId);
					_ps.executeUpdate();
										

		///////////////////user
					
					if(Member.isContainsMember(uId)){
						
						Member member = Member.getMember(_uId, sId);
						if(member.isJoin() && member.isLogin() && member.isDeveloper()){
						////멤버가 로그인 중이라 객체에 위젯 정보 추가
								
								
							if(member.getDevelopedWidget()==null)
								throw new NullPointerException("devlopedWideget list가 null입니다.");

							boolean isEmppy = true;
							for(DevelopedWidget w : member.getDevelopedWidget()){
								if(w.getWidgetId() == _oldWidgetKey){
									w.setVersion(_version);
									w.setContents(_contents);
									w.setPosition(_position);
									w.setUpdatedDate(_updatedDate);
									
								}

							}
							
							DevelopedWidget widget = new DevelopedWidget.Builder(_oldWidgetKey, _widgetName, _kind)
									.contents(_contents)
									.developerId(_dId)
									.developer(nickname)
									.mainImagePath( _mainImageFullPath)
									.subImagePath(_subImageFullPath)
									.position(_position)
									.sourceRoot(_widgetRoot)
									.updatedDate(_updatedDate).build();
	
							member.addDevelopedWidget(widget);
						}							
					}
			
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
		
	}
	
	public static boolean isUpdatingWidget(int evalId) throws SQLException{
	
		ResultSet _rs = null;
		PreparedStatement _ps = null;
		try {

			_ps = conn.prepareStatement("select * from updatingWidget where eval_id = ?");
			_ps.setInt(1, evalId);
			_rs = _ps.executeQuery();
			
			boolean _isEmpty = _rs.next();
			return _isEmpty;
						
			
		}finally {
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
	
	public static ArrayList<EvaluatingWidget> getAllEvaluatingWidgets() {

		ArrayList<EvaluatingWidget> _widgets = new ArrayList<EvaluatingWidget>();
		PreparedStatement _ps = null, __ps = null;
		ResultSet _rs = null, __rs = null;

		try {

			// _ps = conn.prepareStatement("select developer,
			// widget_num, evalNum, evalState,
			// evaluationBeginDate,developer,isUpdate, title, kind,
			// registartionPosition from widgetEvaluation join
			// widget");
			_ps = conn.prepareStatement("select * from widgetEvaluation join evaluationManifest using(eval_id)");
			_rs = _ps.executeQuery();

			
			while(_rs.next()) {

				String name = _rs.getString("title");
				int evalId = _rs.getInt("eval_id");
				String kind = _rs.getString("kind");
				Timestamp date = _rs.getTimestamp("evaluationBeginDate");
				String pos = _rs.getString("position");
				int developerId = _rs.getInt("d_id");
				boolean isUpdate = _rs.getInt("isUpdate") == 1 ? true : false;

				__ps = conn.prepareStatement(
						"select widget_root from evaluationManifest where eval_id = ?");
				__ps.setInt(1, evalId);
				__rs = __ps.executeQuery();
				__rs.next();
				String widgetRoot = __rs.getString("widget_root");
				__ps.close();
				__rs.close();

				__ps = conn.prepareStatement(
						"select nickname,u_id from user join developer using(u_id) where d_id = ?");
				__ps.setInt(1, developerId);
				__rs = __ps.executeQuery();
				__rs.next();
				String nickname = __rs.getString("nickname");
				int uId = __rs.getInt("u_id");
				
				
				
				EvaluatingWidget w = new EvaluatingWidget( name, kind, date, widgetRoot, pos,
						evalId, isUpdate, developerId, uId, nickname);
				_widgets.add(w);
			
				__ps.close();
				__rs.close();
			}
			

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (Exception e) {
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
			if (__ps != null)
				try {
					__ps.close();
				} catch (SQLException ex) {
				}
			if (__rs != null)
				try {
					__rs.close();
				} catch (SQLException ex) {
				}
		}
		return _widgets;
	}

	/**
	 *  개발자가 등록한 평가가 끝난  위젯을 모두 반환한다. 단, 최초에 한번만 DB를 통해 모두 가져온다. *
	 * 
	 * @param sId
	 *                개발자의 id
	 * @return 개발자가 등록한 위젯 정보가 들은 객체의 배열이 반환된다.
	 * @throws Throwable
	 */
	public static ArrayList<DevelopedWidget> getMyDevelopedWidget(String sId) throws Throwable {

		Member member = null;
		try {
			member = Member.getMember(sId);
			if (member.getDevelopedWidget() != null && member.getDevelopedWidget().size() != 0)
				return member.getDevelopedWidget();

			ArrayList<DevelopedWidget> ary = new ArrayList<>();

			PreparedStatement _ps = conn.prepareStatement(
					"select * from widget join widgetDetail using(widget_id) where d_id = ?");
			_ps.setInt(1, member.getDeveloperId());
			ResultSet _rs = _ps.executeQuery();

			while (_rs.next()) {

				DevelopedWidget _widget = new DevelopedWidget.Builder(_rs.getInt("widget_id"),
						_rs.getString("title"), _rs.getString("kind"))
								.version(_rs.getFloat("currentVersion"))
								.updatedDate(_rs.getTimestamp("updatedDate"))
								.sourceRoot(_rs.getString("HTML"))
								.contents(_rs.getString("contents"))
								.subImagePath(_rs.getString("sub_image"))
								.mainImagePath(_rs.getString("main_image"))
								.totalReview(_rs.getFloat("totalReview"))
								.reviewCount(_rs.getInt("count")).build();

				_widget.setWidgetId(member.getDeveloperId());

				ary.add(_widget);

			}

			member.addDevelopedWidget(ary);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return Member.getMember(sId).getDevelopedWidget();

	}


}
