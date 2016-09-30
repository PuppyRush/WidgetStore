
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="javaBean.*"%>
<%@page import="java.sql.*"%>
<%@page import="manageStore.*"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>WidgetStore - STORE</title>
<!-- Bootstrap Core CSS -->
<link href="WidgetClientPage/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="WidgetClientPage/css/shop-homepage.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body onload="onload()">
	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">WIDGET STORE</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<!-- ìì ë©ë´ -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="Develop/Develop.jsp">Develop</a></li>
					<li><a href="#">Services</a></li>
					<li><a href="#">Contact</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>
	<!-- Page Content -->
	<div class="container">
		<div>
			<div class="row">
				<div class="col-md-3">
					<p class="lead">WidgetStore</p>
					<div class="list-group">
						<a href="#" class="list-group-item">분류1</a> <a href="#"
							class="list-group-item">분류2</a> <a href="#"
							class="list-group-item">분류3</a>
					</div>
				</div>
				<div class="col-md-9">
					<!-- more info -->
					<div class='widgetInfo' id='widgetInfo' style="display: none;">
						<div>
							<!--  main image of widget n Developer name -->
							<div class='goodsMain'>
								<img id = 'mainIMG' src='' alt=''>
								<h1 id='widgetName'></h1>
								<h1 id='widgetId'></h1>
								<h4 id='widgetDeveloperName'></h4>
								<h4 id='star'></h4>
								<h5 id='grade'></h5>
								<div class='btnBuy' onclick = 'btnDownload()'>Download</div>
							</div>
							<!-- image of widget -->
							<div class='goodsSub'>
								<img id = 'subIMG' src='' alt=''>
								<h5 id='widgetExplain'></h5>
							</div>
							<!-- review -->
							<div class="well">
								<div class="row">
									<div class="col-md-12">
										<strong><ins>Comments</ins></strong> <span
											class="glyphicon glyphicon-pencil"></span>
										<div class='goodsReview'>
											<div id='widgetReview'></div>
											<div>
												<!-- review -->
												<div class="dropdown">
												<!-- 
													<label>set ID</label>
													<button class="btn btn-default dropdown-toggle"
														type="button" id="dropdownMenu1" data-toggle="dropdown"
														aria-expanded="true">
														5.0 <span class="caret"></span>
													</button>
													<ul class="dropdown-menu" role="menu"
														aria-labelledby="dropdownMenu1">
														<li role="presentation"><a role="menuitem"
															tabindex="-1" href="#">5.0</a></li>
														<li role="presentation"><a role="menuitem"
															tabindex="-1" href="#">4.0</a></li>
														<li role="presentation"><a role="menuitem"
															tabindex="-1" href="#">3.0</a></li>
														<li role="presentation"><a role="menuitem"
															tabindex="-1" href="#">2.0</a></li>
														<li role="presentation"><a role="menuitem"
															tabindex="-1" href="#">1.0</a></li>
													</ul>
													-->
													<select id="selGrade">
														<option value = '0'>0.0</option>
														<option value = '1'>1.0</option>
														<option value = '2'>2.0</option>
														<option value = '3'>3.0</option>
														<option value = '4'>4.0</option>
														<option value = '5'>5.0</option>
													</select>
												</div>
												<!-- comment -->
												<div class="input-group">
													<textarea id='msg' class="form-control" rows="2"></textarea>
													<span class="input-group-btn">
														<button class="btn btn-default btn-lg" onclick="btn_addComment()">ATTACH MY REVIEW</button>
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="field" class="row">
					<!-- 글을 추가 -->
					<% 
					Connection conn = ConnectMysql.getConnector();
					PreparedStatement ps = null;
					ResultSet rs = null;
					
					/*
					whole sql
					CREATE TABLE temp as (SELECT nickname, d_id FROM developer natural join user);
					SELECT * FROM temp natural join widget natural join widgetDetail order by updatedDate desc;
					DROP TABLE temp;
					
					create temporary table
					it was drop at this bottom
					*/
					String sql = "CREATE TABLE temp as (SELECT nickname, d_id FROM developer natural join user);";
					ps = conn.prepareStatement(sql);
					ps.executeUpdate();
					
					sql = "SELECT title, nickname, widget_id, count, main_Image FROM temp natural join widget natural join widgetDetail order by updatedDate desc";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					
					while(rs.next()){
					// for(int i =0; i<8; i++){
						String widgetName = rs.getString("title");
						String widgetDeveloper = rs.getString("nickname");
						int widgetId = rs.getInt("widget_id");
						int reviewsNum = rs.getInt("count");
						String imgSRC = rs.getString("main_Image");
					%>
					<div class="col-sm-4 col-lg-4 col-md-4" id ="testID + <%=widgetName%> + ">
					<div class="thumbnail">
					<img src="<%=imgSRC%>">
					<div class="caption">
					<h4 class='pull-right'></h4>
					<h4><a href="JavaScript:;" onclick = "JavaScript : call_detail( <%=widgetId%> );">
					<%=widgetName%>
					</a></h4>
					<p> <%=widgetDeveloper%> </p></div>
					<div class='ratings'>
					<p class='pull-right'><%=reviewsNum%> reviews</p>
					</div></div></div>
					<%
					} // end of while
					%>
					<!--  글 추가 끝  -->
						<script type="text/javascript">
						var WIDGET_ID;
						function onload(){
							<%
							Object obj = null;
							Enumeration<String> eNum = request.getAttributeNames();
							while (eNum.hasMoreElements()){
								String ele = eNum.nextElement();
								if(null != ele && ele.equals("overlap")){%>alert("already down load")<%}
								if(null != ele && ele.equals("id")){
									String id = (String)request.getAttribute(ele);
									%>WIDGET_ID = <%=id%>; <% 
									System.out.println(id);
									
									// set comment
									String nickName = "";
									String com = "";
									sql ="SELECT nickname, comment FROM post join user using (u_id) WHERE widget_id =" + id + " order by post.registrationDate asc;";
									
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									
									while(rs.next()){
										nickName = rs.getString("nickname");
										com = rs.getString("comment");
										%>addComment("<%=nickName%>", "<%=com%>");<%
									}
									
									// set explain and open post
									String widgetName = "";
									String developer = "";
									String mainExplain = "";
									String mainIMG = "";
									String subIMG = "";
									Double grade = 0.0;
									
									sql = "SELECT * FROM widgetDetail natural join widget natural join temp where widget_id = "+id+";";
									
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									
									if(rs.next()){
										widgetName = rs.getString("title");
										developer = rs.getString("nickname");
										mainExplain = rs.getString("explain");
										mainIMG = rs.getString("main_Image");
										subIMG = rs.getString("sub_Image");
										// set avg of review
										grade = rs.getDouble("totalReview") / rs.getDouble("count");
									}
									%>
									open_detail();
									setExplain("<%=widgetName%>", "<%=developer%>", <%=grade%>, "<%=mainExplain%>", "<%=mainIMG%>", "<%=subIMG%>");
									<%
								}
							}
							%>
						}
							function btn_addComment() {
								// need to
								// save text of review in server
								var text = document.getElementById('msg');
								if (text.value == "") {
									alert("nothing in the box");
									return;
								}
								var f = document.getElementById("selGrade");
								var grade = f.selectedIndex;
								if(grade == 0){
									alert("set Grade");
									return
								}
								
								f = document.forms.commentSaveForm;
								f.widgetId.value = WIDGET_ID;
								f.comment.value = text.value;
								f.userId.value = "3";
								f.grade.value = grade;
								
								document.forms["commentSaveForm"].submit();
							}
							
							// widget post onclick event listener
							function call_detail(id){
								var f = document.forms.postLoadForm;
								f.widgetId.value = id;
								document.forms["postLoadForm"].submit();
							}
							
							// show post
							function open_detail() {
								if (document.getElementById('widgetInfo').style.display != "") {
									document.getElementById('widgetInfo').style.display = '';
								}
							}
							
							// set explain in the post
							function setExplain(name, developer, grade, explain, mainIMG, subIMG){
								
								var img = document.getElementById("mainIMG");
								img.src = mainIMG;
								img = document.getElementById("subIMG");
								img.src = subIMG;
								
								document.getElementById("widgetName").innerText = name;
								document.getElementById('widgetDeveloperName').innerText = developer;
								document.getElementById('widgetExplain').innerText = explain;
								document.getElementById('grade').innerText = "별점 : "+ grade;
								document.getElementById('widgetId').innerText = id;
							}
							
							// set comment
							function addComment(name, text) {
								var div = document.createElement('div');
								var strHTML = "<h4 id='userName'> " + name
										+ " </h4>" + "<h5 id='userReview'>"
										+ text + "</h5><hr>";

								div.innerHTML = strHTML;
								document.getElementById('widgetReview').appendChild(div);
							}
							
							// download btn event leaner
							function btnDownload(){
								var f = document.forms.downloadForm;
								f.widgetId.value = WIDGET_ID;
								document.forms["downloadForm"].submit();
							}
						</script>
					</div>
					<div class="pageWrap">
						<ul class="pageControl">
							<li><a href=""> back </a></li>
							<li><a href=""> 1</a></li>
							<li><a href=""> 2</a></li>
							<li><a href=""> 3</a></li>
							<li><a href=""> 4</a></li>
							<li><a href=""> 5</a></li>
							<li><a href=""> 6</a></li>
							<li><a href=""> 7</a></li>
							<li><a href=""> 8</a></li>
							<li><a href=""> 9</a></li>
							<li><a href=""> 10</a></li>
							<li><a href=""> front </a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /.container -->
	<div class="container">
		<hr>
		<!-- Footer -->
		<footer>
			<div class="row">
				<div class="col-lg-12">
					ì ìì : ããã
					<!-- <p>Copyright &copy; Your Website 2014</p> -->
				</div>
			</div>
		</footer>
	</div>
	<!-- /.container -->
	<!-- jQuery -->
	<script src="WidgetClientPage/js/jquery.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="WidgetClientPage/js/bootstrap.min.js"></script>
</body>
<form id="postLoadForm" method="POST" ACTION="storeLoad.do">
	<input type="hidden" name="widgetId" value="">
</form>
<form id="commentSaveForm" method="POST" ACTION="commentSave.do">
	<input type="hidden" name="widgetId" value="">
	<input type="hidden" name="comment" value="">
	<input type="hidden" name="userId" value="">
	<input type="hidden" name="grade" value="">
</form>
<form id="downloadForm" method="POST" ACTION="widgetDonwload.do">
	<input type="hidden" name="widgetId" value="">
</form>
</html>
<%
// drop temp table
sql = "DROP TABLE temp;";
ps = conn.prepareStatement(sql);
ps.executeUpdate();
%>