<%@page import="javaBean.*"%>
<%@page import="java.util.*"%>
<%@page import="page.VerifyPage"%>
<%@page import="property.enums.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("UTF-8");

	HashMap<String,Object> results =  VerifyPage.Verify(session.getId(), enumPage.DEVELOPER_UPLOAD_WIDGET);
	if(!(boolean)results.get("isSuccessVerify")){
				
		enumPage to = (enumPage)results.get("to");
		
		request.setAttribute("message",  (String)results.get("message"));
		request.setAttribute("messageKind", results.get("messageKind"));
		request.setAttribute("from", enumPage.DEVELOPER.getString());
		
		response.sendRedirect(to.getString());
		return;
		
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
<style>
.btn-file {
	position: relative;
	overflow: hidden;
}

.btn-file input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	min-width: 100%;
	min-height: 100%;
	font-size: 100px;
	text-align: right;
	filter: alpha(opacity = 0);
	opacity: 0;
	outline: none;
	background: white;
	cursor: inherit;
	display: block;
}

.images {
	height: 140px;
	width: 140px;
	border: 0px;
	margin: 15px 15px 0 0;
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
				<a class="navbar-brand" href="index.html">My Develop Widget</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="ViewWidgetInfo.html"><i
						class="glyphicon glyphicon-list-alt"></i> View My Widget </a></li>
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

		</nav>

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->

				<div class="col-md-9">

					<form id="upload-form" method="post" action="uploadWidget.do"
						enctype="multipart/form-data">
						<input id="isUpdate" name="isUpdate" value="true"		type="hidden"> 
						<input id="sessionId" name="sessionId" value=<%=session.getId() %>
							type="hidden"> <input id="kind" name="kind"
							value="sports" type="hidden">
						<div class="modal-body">
							<div id="div-lost-msg">
								<div id="icon-lost-msg"
									class="glyphicon glyphicon-chevron-right"></div>
								<span id="text-lost-msg">위젯이름</span>
							</div>

							<input id="widget-name" name="widget-name" class="form-control"
								type="text" placeholder="Widget name" required></br>


							<div class="btn-group">
								<a class="btn btn-default dropdown-toggle btn-select" href="#"
									data-toggle="dropdown">Category <span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="javascript:;">Games</a></li>
									<li><a href="javascript:;">Sports</a></li>
									<li><a href="javascript:;">Weather</a></li>
									<li><a href="javascript:;">Developer Tool </a></li>
									<li><a href="javascript:;">Blog</a></li>
									<li><a href="javascript:;">News</a></li>
									<li><a href="javascript:;">Shopping</a></li>
									<li><a href="javascript:;">Security</a></li>				
									<li><a href="javascript:;">Finance</a></li>								
									<li><a href="javascript:;">Video</a></li>
									<li><a href="javascript:;">Utility</a></li>
								</ul>
							</div>
							<br>


							<div id="icon-lost-msg" class="glyphicon glyphicon-chevron-right"></div>
							<span id="text-lost-msg">압축파일</span> <br> <input
								accept=" file/zip, file/7z" type="file" name="upload-zip" />
						</div>



						<div id="icon-lost-msg" class="glyphicon glyphicon-chevron-right"></div>
						<span id="text-lost-msg">이미지파일</span> <br> <input
							accept="image/jpeg,image/jpg,image/png,image/tif,image/png, .zip, .7z"
							type="file" name="upload-image[]" multiple />
				</div>

				<br>
				<textarea class="form-control" name="contents" rows="5"
					placeholder="위젯의 설명을 입력해주세요"></textarea>



				<!-- ì£¼ì ì¬í­ -->
				<div class="modal-footer"></div>
				<div class="alert alert-warning" role="alert">
					1. 업로드 된 위젯에 대한 책임은 개발자 약관을 확인하세요.</br> 2.소스파일은 zip파일로, 이미지는 대표사진 이름을 1
					나머지 상세설명 이미지는 2번부터 명명하여 첨부해주세요.</br> 3. 업로드 후 스토어에 업로드 되기까지 수일이 소요될 수
					있습니다. 심사가 성공하거나 실패할 경우 사유와 함께 메일로 발송됩니다.<br>
				</div>


				<div class="modal-footer">
					<div>
						<button type="submit" class="btn btn-primary btn-lg btn-block">Upload</button>
					</div>
	</form>

				</div>

			</div>
		</div>
		<!-- /.container-fluid -->

	</div>
	<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

	<!-- Morris Charts JavaScript -->
	<script src="js/plugins/morris/raphael.min.js"></script>
	<script src="js/plugins/morris/morris.min.js"></script>
	<script src="js/plugins/morris/morris-data.js"></script>
	<script>


	
 $(function () {
    $(document).on('change', ':file', function () {
        var input = $(this), numFiles = input.get(0).files ? input.get(0).files.length : 1, label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [
            numFiles,
            label
        ]);
    });
    $(document).ready(function () {
        $(':file').on('fileselect', function (event, numFiles, label) {
            var input = $(this).parents('.input-group').find(':text'), log = numFiles > 1 ? numFiles + ' files selected' : label;
            if (input.length) {
                input.val(log);
            } else {
                if (log)
                    alert(log);
            }
        });
    });
});

$(".dropdown-menu li a").click(function(){
  var selText = $(this).text();
  $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
});


function imagesSelected(myFiles) {
  for (var i = 0, f; f = myFiles[i]; i++) {
    var imageReader = new FileReader();
    imageReader.onload = (function(aFile) {
      return function(e) {
        var span = document.createElement('span');
        span.innerHTML = ['<img class="images" src="', e.target.result,'" title="', aFile.name, '"/>'].join('');
        document.getElementById('thumbs').insertBefore(span, null);
      };
    })(f);
    imageReader.readAsDataURL(f);
  }
}

function dropIt(e) {  
   imagesSelected(e.dataTransfer.files); 
   e.stopPropagation();  
   e.preventDefault();   
}  
</script>

</body>

</html>
