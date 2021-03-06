<%@page import="property.enums.enumCautionKind"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Properties"%>
<%@page import="java.util.Map"%>
<%@page import="javaBean.Member"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>

<%@page import="javaBean.*"%>
<%@page import="java.util.*"%>
<%@page import="handler.*" %>
<%@page import="java.sql.*"%>
<%@page import="page.VerifyPage"%>
<%@page import="property.enums.enumPage"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("UTF-8");
	enumPage to = enumPage.MAIN;
 	boolean isFailVerify;
 	Member member = Member.getMember(session.getId());
	HashMap<String,Object> results =  VerifyPage.Verify(session.getId(), enumPage.MAIN);
	if(!(boolean)results.get("isSuccessVerify")){
		isFailVerify =false;
		to = (enumPage)results.get("to");
	
		
		
	}else{
		isFailVerify = true;
	}
		%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Widget Store - OSS</title>

<script src="https://rawgithub.com/justindomingue/ohSnap/master/ohsnap.js" type="text/javascript" charset="utf-8"></script>
<script language="Javascript" type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script language="Javascript" type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

    <!-- Bootstrap Core CSS -->
    <link href="WidgetClientPage/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
     <link href="library/popup/style.css" rel="stylesheet">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
    <link href="WidgetClientPage/css/stylish-portfolio.css" rel="stylesheet">
	<!-- bootsnipp down -->
	<link href="WidgetClientPage/css/login.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="WidgetClientPage/font-awesome/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<style>
	#qTip {
		padding: 3px;
		border: 1px solid #666;
		display: none;
		background: #999;
		color: #FFF;
		font: bold 11px Verdana, Arial, Helvetica, sans-serif;
		text-align: left;
		position: absolute;
		z-index: 99999;
	}

	i {
		font-style: normal;
		text-decoration: underline;
	}
	</style>

</head>

<body>

<!--  add widget in board -->
<script type="text/javascript">
<%
	if(isFailVerify){
	/*
				유저 id를 받아옴.
	*/
	int u_id = member.getId();
	String sql = "select * from widgetInfo natural join widget where u_id = "+ Integer.toString(u_id) +";";
	DBhandler dbhandler = new DBhandler();
	ResultSet rs;
	rs = dbhandler.executeSQL(sql);
	while(rs.next()){
		int x = rs.getInt("x");
		int y = rs.getInt("y");
		if(x ==-1) continue;
		int w = rs.getInt("width");
		int h = rs.getInt("height");
		String html = rs.getString("HTML");
		%> 
		setWidget(<%=x%>, <%=y%>,<%=w%>,<%=h%>,"<%=html%>");
		<%
	}
	// load info in DB
}
%>

function setWidget(x,y,w,h,src){
	var div = document.createElement("div");
	div.style.left = x + "px";
	div.style.top = y + "px"
	div.style.width = w + "px";
	div.style.height = h + "px";
	div.style.position = "absolute";
	div.innerHTML = src;
	document.body.appendChild(div);
}

</script>
    <!-- Navigation -->
    <a id="menu-toggle" href="#" class="btn btn-dark btn-md toggle"><i class="fa fa-th-large"></i></a>
    <nav id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <a id="menu-close" href="#" class="btn btn-light btn-md pull-right toggle"><i class="fa fa-times"></i></a>
            <li class="sidebar-brand">
                <a href="#top"  onclick = $("#menu-close").click(); >Widget Store</a>
            </li>
            <li>
                <a href="#Sign In" id="signIn" style="display:''" onclick = $("#menu-close").click(); data-toggle="modal" data-target="#login-modal">Sign in</a>
            </li>
			<li>
                <a href="#Sign Out" id="signOut" style="display:none;" onclick ="fun_signOut()" >Sign out</a>
            </li>
            <li>
                <a href="./Store.jsp" data-toggle="modal" >Store</a>
            </li>
            <li>
                <a href="./Custom.jsp"  data-toggle="modal">Custom</a>
            </li>
            <li>
                <a href="Developer/ManageDeveloper.jsp" data-toggle="modal">Developer</a>
            </li>
            <li>
                <a href="./Settings.jsp">Setting</a>
            </li>
            <li>
                <a href="#About" onclick = $("#menu-close").click(); >About</a>
            </li>
        </ul>
    </nav>

	<!-- BEGIN # MODAL LOGIN -->
	<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    	<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" align="center">
					<img class="img-circle" id="img_logo" src="WidgetClientPage/img/test1.jpg">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
				</div>
                
                <!-- Begin # DIV Form -->
                <div id="div-forms">
                
                    <!-- Begin # Login Form -->
                    <form id="login-form" method="POST" ACTION="login.do" >
		                <div class="modal-body">
				    		<div id="div-login-msg">
                                <div id="icon-login-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-login-msg">Type your username and password.</span>
                            </div>
				    		<input id="login_username" name="login_username" class="form-control" type="text" placeholder="Username" required>
				    		<input id="login_password" name="login_password" class="form-control" type="password" placeholder="Password" required>
				    			<input class="sessionId" name="sessionId" type="hidden" />
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Remember me
                                </label>
                            </div>
        		    	</div>
				        <div class="modal-footer">
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block" onClick="innerLogin()">Login</button>
                            </div>
				    	    <div>
                                <button id="login_lost_btn" type="button" class="btn btn-link">Lost Password?</button>
                                <button id="login_register_btn" type="button" class="btn btn-link">Register</button>
                            </div>
				        </div>
				        <input type="hidden" name="idType" value="nothing">
                    </form>
                    <!-- End # Login Form -->
                    
                    <!-- Begin | Lost Password Form -->
                    <form id="lost-form" style="display:none;" method="GET" ACTION="InputMailforAuth.do" >
    	    		    <div class="modal-body">
		    				<div id="div-lost-msg">
                                <div id="icon-lost-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-lost-msg">Type your e-mail.</span>
                            </div>
		    				<input id="lost_email" name="lost_email" class="form-control" type="text" placeholder="E-Mail" required>
            			</div>
		    		    <div class="modal-footer">
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block" onclick="lostpassword()">Send</button>
                            </div>
                            <div>
                                <button id="lost_login_btn" type="button" class="btn btn-link">Log In</button>
                                <button id="lost_register_btn" type="button" class="btn btn-link">Register</button>
                            </div>
		    		    </div>
                    </form>
                    <!-- End | Lost Password Form -->
                    
                    <!-- Begin | Register Form -->
                    <form id="register-form" style="display:none;" method="POST" ACTION="join.do">
            		    <div class="modal-body">
		    				<div id="div-register-msg">
                                <div id="icon-register-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-register-msg">Register an account.</span>
                            </div>
		    				<input id="register_username" name="register_username" class="form-control" title="Username may only contain alphanumeric characters" type="text" placeholder="Username" required>
                            <input id="register_email" name="register_email" class="form-control" type="email" placeholder="E-Mail" required>
                            <input id="register_password" name="register_password" class="form-control" title="minimum is 8 characters. and contain special character, numeric" type="password" placeholder="Password" required>
							<input id="register_password2" onchange="" class="form-control" type="password" placeholder="Rewrite password" required>
            			</div>
		    		    <div class="modal-footer">
                            <div>
                                <button type="submit" onclick="chkNickname()" class="btn btn-primary btn-lg btn-block">Register</button>
                            </div>
                            <div>
                                <button id="register_login_btn" type="button" class="btn btn-link">Log In</button>
                                <button id="register_lost_btn" type="button" class="btn btn-link">Lost Password?</button>
                            </div>
		    		    </div>
		    		   			 <input class="sessionId" name="sessionId" type="hidden" />
		    		  			  <input type="hidden" name="idType" value="nothing">
                    </form>
                    <!-- End | Register Form -->
                </div>
                <!-- End # DIV Form -->
                
			</div>
		</div>
	</div>
    <!-- END # MODAL LOGIN -->

	<div class="modal fade" id="Register-edit-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    	<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" align="center">
					<img class="img-circle" id="img_logo2" src="WidgetClientPage/img/test1.jpg">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
				</div>
                
                <!-- Begin # DIV Form -->
                <div id="div-forms">
					<!-- Begin | Register Edit Form -->
					<form id="register-edit-form"  method="GET" ACTION="changePasswd.do">
            		    <div class="modal-body">
		    				<div id="div-edit-register-msg">
                                <div id="icon-edit-register-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-register-msg">Edit User info</span>
                            </div>
                            <input id="register_edit_password" class="form-control" title="minimum is 8 characters. and contain special character, numeric" type="password" placeholder="Password" required>
            			</div>
		    		    <div class="modal-footer">
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block">submit</button>
                            </div>
		    		    </div>
                    </form>
					<!-- End | Register Edit Form -->
                </div>
                <!-- End # DIV Form -->
			</div>
		</div>
	</div>


<div id="ohsnap" ></div>

<!-- 이하는 폼태그만 존재  -->
		
<form method="GET" ACTION="<%=to.getString() %>" id="movePage">
		<input id="message" name="message"  type="hidden" value="<%= (String)results.get("message")%>"/>
		<input id="messageKind" name="messageKind"  type="hidden" value="<%=results.get("messageKind") %>"/>
	</form>
	
	
	<form method="GET" ACTION="logout.do" id="logout-form">
		<input id="sessionId" name="sessionId"  type="hidden" value=<%=session.getId() %> />
	</form>
	

	<form method="GET" ACTION="verifyRegistration.do" id="chkRegister_chk_form">
				<input type = "hidden" name = "nickname" value = "" >
				<input type = "hidden" name = "email" value = "" >
	</form>
	
	<form method="GET" ACTION="entryFromMain.do" id="entryForm">
				<input type="hidden" id = "to"/>
				<input type = "hidden" id="sessionId_entryForm" value=""/>
				<input type = "hidden" id ="from"/>
	</form>
    <!-- jQuery -->
    <script src="WidgetClientPage/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="WidgetClientPage/js/bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script>
    
    //페이지 인증 성공 여부를 담는 변수 
 <%-- var isSuccessVerify = (Boolean)<%=isFailVerify%>; --%>
 	var id = "<%=session.getId()%>";
    
	window.onload=function(){
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

			<%

			try{ 

				if(request.getAttribute("initSession")!=null && request.getAttribute("initSession").equals(true)){
		
					session.removeAttribute("alreadyLogin");
					%> signChange('signIn'); <%
				}
				
				//자동로그인 처리하기    
				if(request.getAttribute("doLogout")!= null && 	((String)request.getAttribute("doLogout")).equals("true")){
					session.removeAttribute("alreayLogin");
				}

				else if(session.getAttribute("alreadyLogin") != null &&	((String) session.getAttribute("alreadyLogin")).equals("true")){
								//response.sendRedirect("main.jsp");
							%>
									signChange('signOut');
							<%
					}
				//로그인 결과 얻기 
				else if( request.getAttribute("isSuccessLogin") != null && ((String)request.getAttribute("isSuccessLogin")).equals("true")){	
					%>
					signChange('signOut');
					<%
					session.setAttribute("alreadyLogin", "true");
					session.setMaxInactiveInterval(60*60);	
					
				}
				else if( ((String)session.getAttribute("isSuccessLogin")).equals("false") == true ){
					if( session.getAttribute("message")!=null)
						%>
							alert(<%=(String)session.getAttribute("message")%>);
						<%	
				}
			}
			catch(Exception e){
				e.printStackTrace();
			} %>
			
	};

	function fun_signOut(){
		 $("#menu-close").click();
	
		$("#sessionId_logout").val(id);
		document.forms["logout-form"].submit();
		return;	
	}
	
	function innerLogin(){
		
		$(".sessionId").val(id);
		document.forms["login-form"].submit();
	}
	function innerJoin(){
	
		$(".sessionId").val(id);
		document.forms["register-form"].submit();
	}
	function lostpassword(){
		document.forms["lost-form"].submit();
	}
	
	function signChange(id){
	var div = document.getElementById(id);
		if(div.style.display=='none'){
          div.style.display='';
		
		  if(id=="signOut"){
		    var div_sp = document.getElementById('signIn');
			div_sp.style.display='none';
		  }else{
		   var div_sp = document.getElementById('signOut');
			div_sp.style.display='none';
		  }
		}
   }
	
    // Closes the sidebar menu
    $("#menu-close").click(function(e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    // Opens the sidebar menu
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    /* #####################################################################
   #
   #   Project       : Modal Login with jQuery Effects
   #   Author        : Rodrigo Amarante (rodrigockamarante)
   #   Version       : 1.0
   #   Created       : 07/29/2015
   #   Last Change   : 08/04/2015
   #
   ##################################################################### */
   
	$(function() {
    
    var $formLogin = $('#login-form');
    var $formLost = $('#lost-form');
    var $formRegister = $('#register-form');
	var $formEditRegister = $('#register-edit-form');
    var $divForms = $('#div-forms');
    var $modalAnimateTime = 300;
    var $msgAnimateTime = 150;
    var $msgShowTime = 2000;

    $("form").submit(function () {
        switch(this.id) {
            case "login-form":
                var $lg_username=$('#login_username').val();
                var $lg_password=$('#login_password').val();

                if (<%=request.getAttribute("innerLogin")%> == "false") {
                    msgChange($('#div-login-msg'), $('#icon-login-msg'), $('#text-login-msg'), "error", "glyphicon-remove", "Login error");
                } else {
                    msgChange($('#div-login-msg'), $('#icon-login-msg'), $('#text-login-msg'), "success", "glyphicon-ok", "Login OK");
			
											//window.location.reload();
											return true;
						                }
				
                return false;
                break;
            case "lost-form":
                var $ls_email=$('#lost_email').val();
                if (<%=request.getAttribute("inputMail")%> == "false") {
                    msgChange($('#div-lost-msg'), $('#icon-lost-msg'), $('#text-lost-msg'), "error", "glyphicon-remove", "Send error");
                } else {
                    msgChange($('#div-lost-msg'), $('#icon-lost-msg'), $('#text-lost-msg'), "success", "glyphicon-ok", "Send OK");
					return true;
				}
                return false;
                break;
            case "register-form":
                var $rg_username=$('#register_username').val();
                var $rg_email=$('#register_email').val();
                var $rg_password=$('#register_password').val();
				var $rg_password2=$('#register_password2').val();
				
				if (!CheckName()) {
                    msgChange($('#div-register-msg'), $('#icon-register-msg'), $('#text-register-msg'), "error", "glyphicon-remove", "CHECK USERNAME");
                } else if(!chkPwd($rg_password, $rg_password2)) {
                    msgChange($('#div-register-msg'), $('#icon-register-msg'), $('#text-register-msg'), "error", "glyphicon-remove", "CHECK PASSWORD");
                } else if(chkRegister()&&<%=request.getAttribute("isDuplicatedNickname")%> == "true"){
					msgChange($('#div-register-msg'), $('#icon-register-msg'), $('#text-register-msg'), "error", "glyphicon-remove", "CHECK NICKNAME");
				} else{
                    msgChange($('#div-register-msg'), $('#icon-register-msg'), $('#text-register-msg'), "success", "glyphicon-ok", "Register OK");
					return true;
                }
                return false;
                break;
			case "register-edit-form":
				if(true){
			
				}
            default:
                return false;
        }
        return false;
    });
    
    $('#login_register_btn').click( function () { modalAnimate($formLogin, $formRegister) });
    $('#register_login_btn').click( function () { modalAnimate($formRegister, $formLogin); });
    $('#login_lost_btn').click( function () { modalAnimate($formLogin, $formLost); });
    $('#lost_login_btn').click( function () { modalAnimate($formLost, $formLogin); });
    $('#lost_register_btn').click( function () { modalAnimate($formLost, $formRegister); });
    $('#register_lost_btn').click( function () { modalAnimate($formRegister, $formLost); });
    
    function modalAnimate ($oldForm, $newForm) {
        var $oldH = $oldForm.height();
        var $newH = $newForm.height();
        $divForms.css("height",$oldH);
        $oldForm.fadeToggle($modalAnimateTime, function(){
            $divForms.animate({height: $newH}, $modalAnimateTime, function(){
                $newForm.fadeToggle($modalAnimateTime);
            });
        });
    }
    
    function msgFade ($msgId, $msgText) {
        $msgId.fadeOut($msgAnimateTime, function() {
            $(this).text($msgText).fadeIn($msgAnimateTime);
        });
    }
    
    function msgChange($divTag, $iconTag, $textTag, $divClass, $iconClass, $msgText) {
        var $msgOld = $divTag.text();
        msgFade($textTag, $msgText);
        $divTag.addClass($divClass);
        $iconTag.removeClass("glyphicon-chevron-right");
        $iconTag.addClass($iconClass + " " + $divClass);
        setTimeout(function() {
            msgFade($textTag, $msgOld);
            $divTag.removeClass($divClass);
            $iconTag.addClass("glyphicon-chevron-right");
            $iconTag.removeClass($iconClass + " " + $divClass);
  		}, $msgShowTime);
    }

	function chkRegister(){
		document.getElementsByName("rg_nickname")[1].value = document.getElementById("register_username").value;
		document.getElementsByName("rg_emali")[1].value = document.getElementById("register_email").value;
		document.forms["chkRegister_chk_form"].submit();
		
		return true;
	}

	function CheckName(){
		var flag = true;
		var specialChars=" ~`!@#$%^&*-=+\|[](){};:'<.,>/?_";
		var wordadded = $('#register_username').val();
		if(wordadded = "") flag = false;
		for (i = 0; i < wordadded.length; i++) {
			for (j = 0; j < specialChars.length; j++) {         
				if (wordadded.charAt(i) == specialChars.charAt(j)){         
					flag=false;
					break;
				}
			}
		}
		return flag;
	}

	function chkPwd(str, str2){
		var pw = str;
		var pw2 = str2;
		var num = pw.search(/[0-9]/g);
		var eng = pw.search(/[a-z]/ig);
		var spe = pw.search(/[`~!@@#$%^&*|Ã¢ÂÂ©Ã¢ÂÂ©Ã¢ÂÂ©'Ã¢ÂÂ©";:Ã¢ÂÂ©/?]/gi);

		 if(pw.length < 8 || pw.length > 20){
			alert("비밀번호는 8자 이상이여야 합니다.");
			return false;
		 }
		 else if(pw.search(/Ã¢ÂÂ©s/) != -1){
			alert("비밀번호에 특수문자가 포합되어야 합니다.");
			return false;
		 } 
		 else if(num < 0 || eng < 0 || spe < 0 ){
			alert("비밀번호에 문자, 숫자가 포함되어야 합니다.");
			return false;
		 }
		 else if(str != str2){
			alert("비밀번호가 각각 일치하지 않습니다.");
			return false;
		 }
		 else {
			return true;
		 }
	}
});



	//------------start tooltip line
	var qTipTag = "i,input"; //Which tags do you want to qTip-ize? Keep it lowercase!//
	var qTipX = 0; //This is qTip's X offset//
	var qTipY = 15; //This is qTip's Y offset//

	//There's no need to edit anything below this line//
	tooltip = {
		name : "qTip",
		offsetX : qTipX,
		offsetY : qTipY,
		tip : null
	}

	tooltip.init = function () {
		var tipNameSpaceURI = "http://www.w3.org/1999/xhtml";
		if(!tipContainerID){ var tipContainerID = "qTip";}
		var tipContainer = document.getElementById(tipContainerID);

		if(!tipContainer) {
			tipContainer = document.createElementNS ? document.createElementNS(tipNameSpaceURI, "div") : document.createElement("div");
			tipContainer.setAttribute("id", tipContainerID);
			document.getElementsByTagName("body").item(0).appendChild(tipContainer);
		}

		if (!document.getElementById) return;
		this.tip = document.getElementById (this.name);
		if (this.tip) document.onmousemove = function (evt) {tooltip.move (evt)};

		var a, sTitle, elements;
    
		var elementList = qTipTag.split(",");
		for(var j = 0; j < elementList.length; j++)
		{    
			elements = document.getElementsByTagName(elementList[j]);
			if(elements)
			{
				for (var i = 0; i < elements.length; i ++)
				{
					a = elements[i];
					sTitle = a.getAttribute("title");                
					if(sTitle)
					{
						a.setAttribute("tiptitle", sTitle);
						a.removeAttribute("title");
						a.removeAttribute("alt");
						a.onmouseover = function() {tooltip.show(this.getAttribute('tiptitle'))};
						a.onmouseout = function() {tooltip.hide()};
					}
				}
			}
		}
	}

	tooltip.move = function (evt) {
		var x=0, y=0;
		if (document.all) {//IE
			x = (document.documentElement && document.documentElement.scrollLeft) ? document.documentElement.scrollLeft : document.body.scrollLeft;
			y = (document.documentElement && document.documentElement.scrollTop) ? document.documentElement.scrollTop : document.body.scrollTop;
			x += window.event.clientX;
			y += window.event.clientY;
			
		} else {//Good Browsers
			x = evt.pageX;
			y = evt.pageY;
		}
		this.tip.style.left = (x + this.offsetX) + "px";
		this.tip.style.top = (y + this.offsetY) + "px";
	}

	tooltip.show = function (text) {
		if (!this.tip) return;
		this.tip.innerHTML = text;
		this.tip.style.display = "block";
	}

	tooltip.hide = function () {
		if (!this.tip) return;
		this.tip.innerHTML = "";
		this.tip.style.display = "none";
	}

	// Multiple onload function created by: Simon Willison
	// http://simonwillison.net/2004/May/26/addLoadEvent/
	function addLoadEvent(func) {
	  var oldonload = window.onload;
	  if (typeof window.onload != 'function') {
		window.onload = func
	  } else {
		window.onload = function() {
		  if (oldonload) {
			oldonload();
		  }
		  func();
		}
	  }
	}

	addLoadEvent(function() {
	  tooltip.init ();
	});

    </script>

</body>

</html>
