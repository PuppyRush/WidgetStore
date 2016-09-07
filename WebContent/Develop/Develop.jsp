<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>

<%

	request.setCharacterEncoding("UTF-8");

%>



<!doctype html>
<html lang="en">
 <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
  <title>Develop</title>
  <link href="../WidgetClientPage/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../WidgetClientPage/css/stylish-portfolio.css" rel="stylesheet">
	<!-- bootsnipp down -->
	<link href="../WidgetClientPage/css/login.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="../WidgetClientPage/font-awesome/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">
 </head>
 <body>
	<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" align="center">
					<img class="img-circle" id="img_logo2" src="../WidgetClientPage/img/test1.jpg">
					
				</div>
    <div id="div-forms">
			<div class="modal-body">
				<div class="modal-footer">
                    <div>
         	          <button type="button" class="btn btn-primary btn-lg btn-block" onclick="fun_our()">직접 파일 업로드</button>
						 									<button type="button" class="btn btn-primary btn-lg btn-block" onclick="fun_git()">깃을 통한 업로드</button>
					</div>
      </div>
			</div>
       </form>
    </div>

    <script src="../WidgetClientPage/js/jquery.js"></script>
    <script src="../WidgetClientPage/js/bootstrap.min.js"></script>
 </body>
 
 <script>
 
	function fun_git(){
		location.replace("/Develop/Registration/RegistrationGit.jsp");
		
	}
	
	function fun_our(){
		location.replace("/Develop/Registration/RegistrationOur.jsp");
		
	}
 
 </script>
 
</html>