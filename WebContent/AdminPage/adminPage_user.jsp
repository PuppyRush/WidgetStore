<%@page import="property.enums.member.*, property.enums.*"%>
<%@page import="manager.MemberManager, java.util.ArrayList, java.util.EnumMap, java.util.HashMap ,javaBean.Member, page.VerifyPage"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("UTF-8");

	ArrayList<Member> members = new ArrayList<Member>();
	boolean isFailVerify;
	HashMap<String,Object> results =  VerifyPage.Verify(session.getId(), enumPage.MEMBER_MANAGER);
	if(!(boolean)results.get("isSuccessVerify")){
	isFailVerify =false;
	enumPage to = (enumPage)results.get("to");
	
	request.setAttribute("message",  (String)results.get("message"));
	request.setAttribute("messageKind", results.get("messageKind"));
	response.sendRedirect(to.getString());
	return;
	
	}else{
		isFailVerify = true; 
	 
		members = MemberManager.getAllMember();		
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
<link href="/AdminPage/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">

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
					<li class="active"><a href="/AdminPage/adminPage_user.jsp"><i
							class="glyphicon glyphicon-th-list"></i> 유저 관리</a></li>
					<li><a href="/AdminPage/adminPage_widget.jsp"><i
							class="glyphicon glyphicon-list-alt"></i> 위젯 관리</a></li>

					<li><a href="javascript:;"><i
							class="glyphicon glyphicon-cog"></i> 사이트 설정</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</nav>

		<div id="page-wrapper">



			<div id="user">
				<table id="userTable" class="table table-hover">
					<thead>
						<tr>
							<th>NO.</th>
							<th>ID</th>
							<th>이메일</th>
							<th>가입일</th>
							<th>개발자 여부</th>
							<th>유저 상태</th>
							<th>탈퇴 처리</th>
						</tr>
					</thead>
					<tbody>
				
					</tbody>
				</table>

			</div>
			<nav>
				<ul class="pagination">
					<li>
						<a href="#" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li>
						<a href="#" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</ul>
			</nav>
		</div>
	</div>
	<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="/AdminPage/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="/AdminPage/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="/AdminPage/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript"
		src="/AdminPage/js/locales/bootstrap-datetimepicker.fr.js"
		charset="UTF-8"></script>
	<script src="/AdminPage/js/commanJs.js"></script>
</body>


<script>


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
	

$( document ).ready(function() {
	$('#userTable').on( 'over', 'tr', function() {

         var selected = $(this).hasClass("highlight");
            $("#userTable tr").removeClass("highlight");
            if(!selected)
                    $(this).addClass("highlight");

          alert('You clicked row '+ ($(this).index()+1) );
        });
});


window.onload=function(){

	var _members = null;
	
	////member가져오기
	
	<%if(members.size() > 0){
		%>
			_members = new Array(<%=members.size()%>);
			<%
		for(int i=0 ; i < members.size() ; i++){
			
			%>
				var map = new Map();
				map.put("id", "<%=members.get(i).getId()%> " );
				map.put("email","<%=members.get(i).getEmail()%>");
				map.put("nickname","<%=members.get(i).getNickname() %>");
				map.put("isDeveloper", "<%=members.get(i).isDeveloper()%>");
				map.put("date","<%=members.get(i).getRegDate().toString()%>");
				
				<%
				StringBuilder state = new StringBuilder();
				EnumMap<enumMemberAbnormalState, Boolean> e = members.get(i).getAbnormalState();
				if(e.get(enumMemberAbnormalState.ABNORMAL)){
					
					if(e.get(enumMemberAbnormalState.FAILD_LOGIN))
							state.append("로그인 5회 이상 실패 시도 / ");
					if(e.get(enumMemberAbnormalState.JOIN_CERTIFICATION))
						state.append("가입 미인증 /");
					if(e.get(enumMemberAbnormalState.LOST_PASSWORD))
						state.append("비밀번호 분실 상태 /");
					if(e.get(enumMemberAbnormalState.OLD_PASSWORD))
						state.append("비밀번호 3개월 이상 미변경  /");
					if(e.get(enumMemberAbnormalState.SLEEP))
						state.append("3개월 이상 미 로그인/");
					
				}else{
					state.append("이상없음");
				}				
				%>
				map.put("state", "<%=state.toString()%>");
				map.put("dev_id", "<%=members.get(i).getDeveloperId()%>");
				_members[<%=i%>] = map;
		<%
		}
	}	
	%>
	
	///member가져오기
	if(_members!=null)
		for(var i=0; i< _members.length ; i++){
				var m = new Map();
				m = _members[i];
				
				
			 $("#userTable > tbody:last").append('<tr><td>'+ (i+1) +'</td><td><div class="checkbox"><label><input type="checkbox"> </label> </div></td><td>'+
					 m.get("nickname") + '</td><td>' + m.get("email")	+ '</td><td>' + m.get("date") + '</td><td>' + m.get("isDeveloper")
						+ '</td><td>' + m.get("state") + '</td><td> <button type="button" id="withdrawal" name="withdrawal" class="btn btn-primary btn-sm">탈퇴</button> </td></tr> ' ); 
		
		}
	
	}

</script>

</html>
