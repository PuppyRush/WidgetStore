<%@page import="javaBean.*"%>
<%@page import="java.util.*"%>
<%@page import="page.VerifyPage"%>
<%@page import="property.enums.*" %>

<%

	HashMap<String,Object> results =  VerifyPage.Verify(session.getId(), enumPage.DEVELOPER);
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
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

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
    filter: alpha(opacity=0);
    opacity: 0;
    outline: none;
    background: white;
    cursor: inherit;
    display: block;
}
.images { 
        height:140px;
        width:140px; 
        border:0px; 
        margin:15px 15px 0 0; 
}
</style>

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">My Develop Widget</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
				<li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-list-alt"></i> View My Widget </a>
				</li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> [NICKNAME] <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="javascript:;"><i class="glyphicon glyphicon-console"></i> widget001</a>
                    </li>
                    <li>
                        <a href="javascript:;"><i class="glyphicon glyphicon-console"></i> widget002</a>
                    </li>
                    <li>
                        <a href="javascript:;"><i class="glyphicon glyphicon-console"></i> widget003</a>
                    </li>
                    <li>
                        <a href="javascript:;"><i class="glyphicon glyphicon-console"></i> widget004</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                
          <form id="upload-form" method="post" action="updateWidget.do" enctype="multipart/form-data" >
							<input id="sessionId"  name="sessionId" type="hidden">
						
							<input id="kind" name="kind" value="sports" type="hidden">
                <div class="col-md-9">
		    		<h1 class="page-header">
                            [WidgetName] <small>[widget category]</small> <small>[update day]</small> 
                    </h1>
				<!-- 이미지 파일 선택 -->
				<table class="table table-bordered" width="485" border="1" cellspacing=0 cellpadding=5>
					<tr>
						<td align="left" height="105" ondragenter="return false" ondragover="return false" ondrop="dropIt(event)">    
						<output id="thumbs"></output> 
						</td>
					</tr>
					<tr>
						<td align="center">
						<label class="btn btn-block btn-primary">
						Drag & drop or choose images from your local file system<input accept="image/jpeg,image/jpg,image/png,image/tif,image/png, .zip, .7z" type="file" name="upload[]" multiple/>
					</label>
						</td>
					</tr>
				</table>
				<!-- 위젯 파일 선택 -->
				<div class="input-group">
                <label class="input-group-btn">
                    <span class="btn btn-primary">
                        Widget File&hellip; <input type="file" style="display: none;" multiple>
                    </span>
                </label>
                <input type="text" class="form-control" readonly>
            </div>

				<!-- 텍스트 입력 -->
				<p class="help-block">Input text about update</p>
				<textarea class="form-control" rows="5" placeholder="업데이트에 대한 내용을 입력해주세요."></textarea>
				
				</form>
						<!-- 주의 사항 -->
						<div class="modal-footer"></div>
								<div class="alert alert-warning" role="alert">
								1.주의사항1</br>
								2.주의사항2</br>
								3.주의사항3
								</div>
							
							
		    		    <div class="modal-footer">
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block">
								<i class="glyphicon glyphicon-ok"></i> Update</button>
                            </div>
		    		    </div>
						</div>
						
					</div>

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
window.onload=function(){
	var _tempUrl = location.href.split("?"); //url에서 처음부터 '?'까지 삭제
		
	var _keyValuePair = _tempUrl[1].split('='); // '=' 을 기준으로 분리하기

	if(_keyValuePair[0] == 'WidgetName'){ // _keyValuePair[0] : 파라미터 명
		// _keyValuePair[1] : 파라미터 값
		alert(_keyValuePair[1]);
		
	}
}

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
