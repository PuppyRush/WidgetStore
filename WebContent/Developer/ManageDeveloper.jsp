<%@page import="property.enums.member.*, property.enums.*"%>
<%@page
	import="page.VerifyPage, javaBean.ManageWidget , java.util.* ,javaBean.DevelopedWidget"%>



<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("UTF-8");

	ArrayList<DevelopedWidget> widgets = new ArrayList<DevelopedWidget>();
 	boolean isFailVerify;
	HashMap<String,Object> results =  VerifyPage.Verify(session.getId(), enumPage.DEVELOPER);
	
	if(!(boolean)results.get("isSuccessVerify")){
		isFailVerify =false;
		enumPage to = (enumPage)results.get("to");
		
		request.setAttribute("message",  (String)results.get("message"));
		request.setAttribute("messageKind", results.get("messageKind"));
		response.sendRedirect(to.getString());
		return;
		
	}else{
		isFailVerify = true; 
		
		try{
			widgets = ManageWidget.getMyDevelopedWidget(session.getId());
		}catch(Throwable e){
				e.printStackTrace();
				response.sendRedirect(enumPage.ERROR404.toString());
				
		}
				
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

<title>My Widget Set</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../library/popup/style.css" rel="stylesheet">
<link href="css/sb-admin.css" rel="stylesheet">


<!-- Morris Charts CSS -->
<link href="css/plugins/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">



<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

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
				<a class="navbar-brand" href="Developer/ManageDeveloper.jsp">My
					Develop Widget</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="./UploadForm.jsp"><i
						class="glyphicon glyphicon-share"></i> WidgetUpload</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><i class="fa fa-user"></i> [NICKNAME] <b
						class="caret"></b></a>
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
				<ul id="widgetList" class="nav navbar-nav side-nav">

				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</nav>

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->

				<div class="col-lg-12">
					<h1 class="page-header" id="widgetTitle"></h1>

					<img id="widgetImage"
						src="http://placehold.it/500x300" alt="representive Image of a Widget">
					<hr>

					<ol class="breadcrumb">
						<li class="active">Repository : [Repository Name]</li>
						<li class="active"><i class="fa fa-dashboard"></i>
							./widgetCategory/widget.html</li>
					</ol>
					
					
					<div class="panel panel-default">
						<div class="panel-body" id="widgetContents">
					
						</div>
					</div>
					<div class="modal-footer">
						<div>
							<a id="updateSrc" href="" class="btn btn-success"> <i
								class="glyphicon glyphicon-ok"></i> Do Update
							</a>
							<button class="btn btn-danger"
								onclick="doDelete()">
								<i class="glyphicon glyphicon-trash"></i> Delete
							</button>
						</div>
					</div>

				</div>
			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<div id="ohsnap"></div>

<form action="deleteWidget.do" id="deleteWidgetForm" method="get"> 
	<input type="hidden" id="widgetId" name="widgetId" value="">	
	<input type="hidden" id="devId" name="devId" value="">
</form>


	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

	<!-- Morris Charts JavaScript -->
	<script src="js/plugins/morris/raphael.min.js"></script>
	<script src="js/plugins/morris/morris.min.js"></script>
	<script src="js/plugins/morris/morris-data.js"></script>
	<script src="js/commanJs.js"></script>
	<script
		src="https://rawgithub.com/justindomingue/ohSnap/master/ohsnap.js"
		type="text/javascript" charset="utf-8"></script>

</body>

<script>
	
	
	
var _selectedWidgetIdx;
var _widgets = null;

window.onload=function(){
	
	
	
	
	
	//메세지
	var message;
	var popup_color;
	<% if(request.getAttribute("message")!=null && request.getAttribute("messageKind") !=null){
		enumCautionKind kind = (enumCautionKind)request.getAttribute("messageKind");	
	%>
	  message = "<%=(String)request.getAttribute("message")%>";
	  popup_color = "<%=(String)kind.getString()%>";
	  ohSnap(message,{color:popup_color});
	<%
	}
	%>

	
	
	
	////member가져오기
	
	<%if(widgets.size() > 0){
		%>
			_widgets = new Array(<%=widgets.size()%>);
			<%
		for(int i=0 ; i < widgets.size() ; i++){
			
			%>
				var map = new Map();
				map.put("widgetId", "<%=widgets.get(i).getWidgetId() %>");
				map.put("developerId", "<%= widgets.get(i).getDeveloperId()%> ");
				map.put("widgetName","<%=widgets.get(i).getWidgetName()%>");
				map.put("uploadedDate","<%=widgets.get(i).getUpdatedDate() %>");
				map.put("kind","<%=widgets.get(i).getKind() %>");
				map.put("position","<%=widgets.get(i).getPosition() %>");
				map.put("sourceRoot","<%=widgets.get(i).getWidgetRoot()%>");
				map.put("imageRoot","<%=widgets.get(i).getMainImagePath()%>");
				map.put("contents","<%=widgets.get(i).getContents()%>");
				_widgets[<%=i%>] = map;
		<%
		}
	}	
	%>
	
	///member가져오기
	if(_widgets!=null)
		for(var i=0; i< _widgets.length ; i++){
			var m = new Map();
			m = _widgets[i];
			 	  
			if(i==0){
				 $("#widgetList").append('<li class="active" name='+m.get("developerId")+'><a href="javascript:;"> '+
				 '<i class="glyphicon glyphicon-console"></i>'+m.get("widgetName")+'</a></li>');
				 
				 $("#widgetTitle").append(m.get("widgetName")+' / <small>'+m.get("kind")+'</small> / <small>'+ m.get("position")+'</small><br><small>'+
						 	m.get("uploadedDate")+'</small>');
				 
				 $("#widgetImage").attr("src",m.get("imageRoot"));
				 if(m.get("position")=="git"){
				 		
				   }
				 
				 $("#widgetContents").html( m.get("contents"));
					 
				 
			}else{
			$("#widgetList").append('<li name='+m.get("developerId")+'><a href="javascript:;"> '+
					 '<i class="glyphicon glyphicon-console"></i>'+m.get("widgetName")+'</a></li>');
			}
		}
			 
	}
	

function doDelete(){
		log(_selectedWidgetIdx);
		log(_widgets[_selectedWidgetIdx].get("widgetId"))
		$("#widgetId").val(_widgets[_selectedWidgetIdx].get("widgetId") );
		$("#widgetId").val(_widgets[_selectedWidgetIdx].get("developerId") );
		$("deleteWidgetForm").submit();
}
	
	

</script>

</html>


