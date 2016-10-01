<%@page import="property.enums.member.*, property.enums.*"%>
<%@page
	import="page.VerifyPage, java.util.ArrayList, java.util.EnumMap, java.util.HashMap ,javaBean.*"%>



<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");

	ArrayList<EvaluatingWidget> widgets = new ArrayList<EvaluatingWidget>();
	boolean isFailVerify;
	HashMap<String, Object> results = VerifyPage.Verify(session.getId(), enumPage.WIDGET_MANAGER);

	if (!(boolean) results.get("isSuccessVerify")) {
		isFailVerify = false;
		enumPage to = (enumPage) results.get("to");

		request.setAttribute("message", (String) results.get("message"));
		request.setAttribute("messageKind", results.get("messageKind"));

		return;

	} else {
		isFailVerify = true;

		widgets = ManageWidget.getAllEvaluatingWidgets();

	}
%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Admin Page</title>

<!-- Bootstrap Core CSS -->
<link href="/AdminPage/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="/library/popup/style.css" rel="stylesheet">
<link href="/AdminPage/css/sb-admin.css" rel="stylesheet">
<link href="/AdminPage/css/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="/AdminPage/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.7.0/styles/default.min.css">

<link class="codestyle" rel="prefetch alternate stylesheet" href="/library/highlight/styles/default.css">
<link class="codestyle" rel="prefetch alternate stylesheet" href="/library/highlight/styles/solarized-dark.css">
<link class="codestyle" rel="prefetch alternate stylesheet" href="/library/highlight/styles/solarized-light.css">


<!-- Morris Charts CSS -->
<link href="/AdminPage/css/plugins/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="/AdminPage/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<!-- <script src="/AdminPage/js/jquery.easing.js" type="text/javascript"></script> -->
<!-- <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script> -->


</head>


<script>
  function initSnippet() {
    var snippet = document.querySelector('#snippet pre code');
    hljs.highlightBlock(snippet);
    var style = document.getElementById('style-link').textContent;
    var links = document.querySelectorAll('.codestyle');
    Array.prototype.forEach.call(links, function(link) {
      link.rel = 'stylesheet';
      link.disabled = !link.href.match(style + '\\.css$');
    });
    document.getElementById('language-link').innerHTML = snippet.result.language;
    var controls = document.querySelectorAll('#control a');
    Array.prototype.forEach.call(controls, function(control) {
      control.addEventListener('click', function(e) {
        e.preventDefault();
        var request = new XMLHttpRequest();
        request.open('GET', control.href, true);
        request.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
        request.onreadystatechange = function() {
          if (request.readyState == XMLHttpRequest.DONE && request.status == 200) {
            document.getElementById('snippet').innerHTML = request.responseText;
            initSnippet();
          }
        };
        request.send();
      });
    });
  }

  addEventListener('load', initSnippet);
</script>





<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">관리자페이지</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><i class="fa fa-user"></i>
						[Administrator] <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
						</li>
						<li><a href="#"><i class="fa fa-fw fa-envelope"></i>
								Inbox</a></li>
						<li><a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
						</li>
						<li class="divider"></li>
						<li><a href="#"><i class="fa fa-fw fa-power-off"></i> Log
								Out</a></li>
					</ul></li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="/AdminPage/adminPage_user.jsp"> <i
							class="glyphicon glyphicon-th-list"></i> 유저 관리
					</a></li>
					<li class="active"><a href="/AdminPage/adminPage_widget.jsp">
							<i class="glyphicon glyphicon-list-alt"></i> 위젯 관리
					</a></li>


					<li><a href="javascript:;"><i
							class="glyphicon glyphicon-cog"></i> 사이트 설정</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</nav>

		<div id="page-wrapper">

			<ul class="nav nav-tabs">
				<li role="presentation" class="active"><a href="#">심사중</a></li>
				<li role="presentation"><a href="#">심사 실패</a></li>
				<li role="presentation"><a href="#">심사 완료</a></li>
			</ul>

			<div id="evaluation-ing">
				<table id="widgetTable" class="table table-hover">
					<thead>
						<tr>
							<th>NO.</th>
							<th></th>
							<th>개발자</th>
							<th>위젯 이름</th>
							<th>
								<div class="input-group date allowDate " id='datetimepicker10'
									data-date="" data-date-format="dd MM yyyy"
									data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
									<input class="form-control" size="16" type="text" readonly>
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</th>
							<th>위젯 종류</th>
							<th>위젯 등록 방식</th>
						</tr>
					</thead>
					<tbody>
						<tr>

						</tr>
						<tr>

						</tr>
						<tr>

						</tr>
					</tbody>
				</table>

				<div class="container">

					<div class="row">
						<div class="col-md-3">
							<div class="jqueryFileTree">
								<div id="sourceTree" class="demo">
								</div>
							</div>
						</div>

						<div id="snippet">
							<pre>
								<code class="javascript">/**
	
							
						</code>
							</pre>
							<div id="control">
								<p>
									language: <a id="language-link" href="./snippet=10&amp;style=9">...</a>
								<p>
									style: <a id="style-link" href="/AdminPage/adminPage_widget.jsp?snippet=9&amp;style=10">zenburn</a>
							</div>


						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->


<form action="acceptWidget.do" method="post" id="acceptForm">

	<input type="hidden" id="widgetId" name="widgetId">
	<input type="hidden" id="widgetRoot" name="widgetRoot">
	<input type="hidden" id="evalId" name="evalId">
	

</form>


	<!-- jQuery -->
	<!-- <script src="/AdminPage/js/jquery.js"></script> -->
	<script src="http://code.jquery.com/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="/AdminPage/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="/AdminPage/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript"
		src="/AdminPage/js/locales/bootstrap-datetimepicker.ko.js"
		charset="UTF-8"></script>
		<script type="text/javascript"	src="/AdminPage/js/commanJs.js"		charset="UTF-8"></script>
	
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.7.0/highlight.min.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
	<script src="/AdminPage/js/jqueryFileTree.js" type="text/javascript"></script>


</body>



<script>

var _widgets = null;
var _selectedWidgetIdx;
var _sourceRoot;

function showIdx(){

	 _selectedWidgetIdx = $("#widgetTable").closest("tr").index();
	 
		var m = new Map();
		m = _widgets[_selectedWidgetIdx];
		
	 $("#widgetId").val(_widgets[_selectedWidgetIdx].get("widgetId"));
	 $("#widgetRoot").val(_widgets[_selectedWidgetIdx].get("root"));
	 $("#evalId").val(_widgets[_selectedWidgetIdx].get("evalId"));	 
	 $("#acceptForm").submit();

}


function viewSource(){

	 
	 _selectedWidgetIdx = $("#widgetTable").closest("tr").index();
	 
		var m = new Map();
		m = _widgets[_selectedWidgetIdx];
		_sourceRoot = m.get("root");

}

$(document).ready( function() {
	$('#sourceTree').fileTree({
				root: _sourceRoot,
				script: 'SourceTree.jsp',
				multiFolder: false },
				function(file){
				
					$("pre code").load(file);
				}); 						
});

window.onload=function(){
	
	
	////member가져오기
	
	<%if (widgets.size() > 0) {%>
			_widgets = new Array(<%=widgets.size()%>);
			<%for (int i = 0; i < widgets.size(); i++) {%>
				var map = new Map();
				map.put("evalId", "<%=widgets.get(i).getEvalId()%> ");
				map.put("developerId", "<%=widgets.get(i).getDevId()%> ");
				map.put("developer", "<%=widgets.get(i).getNickname()%> ");
				map.put("widgetName","<%=widgets.get(i).getWidgetName()%>");
				map.put("uploadedDate","<%=widgets.get(i).getUploadedDate()%>");
				map.put("kind","<%=widgets.get(i).getKind()%>");
				map.put("widgetId","<%=widgets.get(i).getWidgetId()%>");
				map.put("position","<%=widgets.get(i).getPosition()%>");
				map.put("root","<%=widgets.get(i).getWidgetRoot()%>");
				_widgets[<%=i%>] = map;
		<%}
			}%>
	
	///member가져오기
	if(_widgets!=null)
		for(var i=0; i< _widgets.length ; i++){
			var m = new Map();
			m = _widgets[i];
			 	 
				 $("#widgetTable > tbody:last").append('<tr><td>'+ (i+1) +'</td><td>' + '<div class="checkbox"><label><input type="checkbox"> </label> </div></td><td>' +
						 m.get("developer") + '</td><td>' + m.get("widgetName")	+ '</td><td>' + m.get("uploadedDate") + '</td><td>' + m.get("kind") + '</td><td>' + m.get("position") +
						'</td><td> <button type="button" id="acceptWidget" class="btn btn-primary btn-sm  onClick="showIdx()">승인</button> <button type="button" id="viewSource" class="btn btn-primary btn-sm" onClick="viewSource()" >소스 보기</button> </td></tr> ');
			if(i==0)
				_sourceRoot = m.get("root");
		}
			 
	 
	}


 

</script>

</html>
