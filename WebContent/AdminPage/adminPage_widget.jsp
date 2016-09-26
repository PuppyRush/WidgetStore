<%@page import="property.enums.member.*, property.enums.*"%>
<%@page import="page.VerifyPage, manager.WidgetManager, java.util.ArrayList, java.util.EnumMap, java.util.HashMap ,javaBean.EvaluatingWidget"%>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("UTF-8");

	ArrayList<EvaluatingWidget> widgets = new ArrayList<EvaluatingWidget>();
 	boolean isFailVerify;
	HashMap<String,Object> results =  VerifyPage.Verify(session.getId(), enumPage.WIDGET_MANAGER);
	
	if(!(boolean)results.get("isSuccessVerify")){
		isFailVerify =false;
		enumPage to = (enumPage)results.get("to");
		
		request.setAttribute("message",  (String)results.get("message"));
		request.setAttribute("messageKind", results.get("messageKind"));

		return;
		
	}else{
		isFailVerify = true; 
		
		widgets = WidgetManager.getAllWidgets();
				
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
	
	
    <!-- Morris Charts CSS -->
    <link href="/AdminPage/css/plugins/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/AdminPage/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<!-- <script src="/AdminPage/js/jquery.easing.js" type="text/javascript"></script> -->
<!-- <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script> -->
<script src="/AdminPage/js/jqueryFileTree.js" type="text/javascript"></script>
<link href="/AdminPage/css/jqueryFileTree.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="/AdminPage/css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">

<style type="text/css">
.example {
	margin: 15px;
}

.demo {
	width: 200px;
	height: 400px;
	border-top: solid 1px #BBB;
	border-left: solid 1px #BBB;
	border-bottom: solid 1px #FFF;
	border-right: solid 1px #FFF;
	background: #FFF;
	overflow: scroll;
	padding: 5px;
}
</style>
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
				<a class="navbar-brand" href="index.html">Admin Page</a>
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
							<li><a href="/AdminPage/adminPage_user.jsp">
					<i	class="glyphicon glyphicon-th-list"></i> 유저 관리</a></li>
					<li class="active"><a href="/AdminPage/adminPage_widget.jsp">
					<i	class="glyphicon glyphicon-list-alt"></i> 위젯 관리</a></li>
					
		
					<li><a href="javascript:;"><i class="glyphicon glyphicon-cog"></i> 사이트 설정</a></li>
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
							<th>개발자</th>
							<th>위젯 이름</th>

								<div class="input-group date allowDate " data-date=""
									data-date-format="dd MM yyyy" data-link-field="dtp_input2"
									data-link-format="yyyy-mm-dd">
									<input class="form-control" size="16" type="text"
										value="등록 날짜 " readonly>
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
							<div class="example">
								<h3>Widget File Tree</h3>
								<div id="sourceTree" class="demo"></div>
							</div>
						</div>
						<div class="col-md-9">
							<iframe width="600" height="400" src="http://www.daum.net/"
								scrolling="yes"> </iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<!-- <script src="/AdminPage/js/jquery.js"></script> -->
	  <script src="http://code.jquery.com/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="/AdminPage/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/AdminPage/js/bootstrap-datetimepicker.js"
		charset="UTF-8"></script>
	<script type="text/javascript"
		src="/AdminPage/js/locales/bootstrap-datetimepicker.ko.js" charset="UTF-8"></script>
	<script>
	$(document).ready( function() {
				
				$('#sourceTree').fileTree({ 
							root: '/iamges', 
							script: 'SourceTree.jsp' }, 
							function(file) { 
								alert(file);
							});
		});
	$('.uploadDate').datetimepicker({
        language:  'eu',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });

	$('.allowDate').datetimepicker({
        language:  'eu',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
 
	</script>

</body>

<script>



window.onload=function(){
	
	var _widgets = null;
	
	////member가져오기
	
	<%if(widgets.size() > 0){
		%>
			_widgets = new Array(<%=widgets.size()%>);
			<%
		for(int i=0 ; i < widgets.size() ; i++){
			
			%>
				var map = new Map();
				map.put("developer", <%= widgets.get(i).getNickname()%> );
				map.put("widgetName","<%=widgets.get(i).getWidgetName()%>");
				map.put("uploadedDate","<%=widgets.get(i).getUploadedDate() %>");
				map.put("kind","<%=widgets.get(i).getKind() %>");
				map.put("position","<%=widgets.get(i).getPosition() %>");
				map.put("root","<%=widgets.get(i).getWidgetRoot()%>");
				_widgets[<%=i%>] = map;
		<%
		}
	}	
	%>
	
	///member가져오기
	if(_widgets!=null)
		while(_widgets.length > 0){
	   var i=1;
			var m = new Map();
	   m = _widgets.pop();
			 	 
				 $("#widgetTable > tbody:last").append('<tr><td>'+ i++ +'</td><td>' + m.get("developer") + '</td><td>' + m.get("widgetName")
						+ '</td><td>' + m.get("uploadedDate") + '</td><td>' + m.get("kind") + '</td><td>' + m.get("position") +
						'</td><td> <button type="button" class="acceptWidget" value="" class="btn btn-primary btn-sm">승인</button> </td><r> '
				  	+ '</td><td><div class="checkbox"><label><input type="checkbox"> </label> </div></td></tr> ' );
				 $("widgetTable = tbody:last").val(m.get("developer"));
		}
			 
	}




Map = function(){
	 this.map = new Object();
	};   
	Map.prototype = {   
	    put : function(key, value){   
	        this.map[key] = value;
	    },   
	    get : function(key){   
	        return this.map[key];
	    },
	    containsKey : function(key){    
	     return key in this.map;
	    },
	    containsValue : function(value){    
	     for(var prop in this.map){
	      if(this.map[prop] == value) return true;
	     }
	     return false;
	    },
	    isEmpty : function(key){    
	     return (this.size() == 0);
	    },
	    clear : function(){   
	     for(var prop in this.map){
	      delete this.map[prop];
	     }
	    },
	    remove : function(key){    
	     delete this.map[key];
	    },
	    keys : function(){   
	        var keys = new Array();   
	        for(var prop in this.map){   
	            keys.push(prop);
	        }   
	        return keys;
	    },
	    values : function(){   
	     var values = new Array();   
	        for(var prop in this.map){   
	         values.push(this.map[prop]);
	        }   
	        return values;
	    },
	    size : function(){
	      var count = 0;
	      for (var prop in this.map) {
	        count++;
	      }
	      return count;
	    }
	};
	
	$(".acceptWidget").click(function () {
        var data = "";
        var id = $("").val();
   
        //jQuery의 Post함수로 입력받은 id값 전달 
        $.post("IdCheckProc.asp?id=" + id, data, function (data) {
            if (data.code) {
                $("#idchk").val("N");
                alert("이미 사용중인 아이디입니다.");
            } else {
                $("#idchk").val("Y");
                alert("사용가능한 아이디입니다.");
            }
        }, "json");

        return false;
    });
   

</script>

</html>
