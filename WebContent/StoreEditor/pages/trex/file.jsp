<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8"%>



<%

	request.setCharacterEncoding("UTF-8");

%>

<!DOCTYPE html>
<html>
<head>
   <meta charset="utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
    
<title>파일 업로드 </title> 
<script src="/StoreEditor/js/popup.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="/StoreEditor/css/popup.css" type="text/css"/>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="http://malsup.github.io/jquery.form.js"></script>

<script type="text/javascript">
// <![CDATA[
	$(function(){
        $("#saveBtn").click(function(){
            $("#frm").submit();
        })
        //ajax form submit
        $('#frm').ajaxForm({
            beforeSubmit: function (data,form,option) {
                //validationì²´í¬ 
                //ë§ê¸°ìí´ìë return falseë¥¼ ì¡ìì£¼ë©´ë¨
                return true;
            },
            success: function(response,status){
                //ì±ê³µí ìë²ìì ë°ì ë°ì´í° ì²ë¦¬
                done(response);
            },
            error: function(){
                //ìë¬ë°ìì ìí codeíì´ì§
                alert("error!!");
            }                               
        });
    })
    
    
	function done(response) {
		if (typeof(execAttach) == 'undefined') { //Virtual Function
	        return;
	    }
		
		var response_object = $.parseJSON( response );
        execAttach(response_object);
        
/* 	var file = $("#fileBtn").val();
		var _mockdata = {
			'attachurl': file.fileurl,
			'filemime': 'image/gif',
			'filename': file.filename,
			'filesize': file.filesize
		}; 
		execAttach(_mockdata); */
		closeWindow();
	}

	function initUploader(){
	    var _opener = PopupUtil.getOpener();
	    if (!_opener) {
	        alert('잘못된 경로로 접근');
	        return;
	    }
	    
	    var _attacher = getAttacher('file', _opener);
	    registerAction(_attacher);
	}
	
</script>
</head>
<body onload="initUploader();">
<div class="wrapper">
	<div class="header">
		<h1>íì¼ ì²¨ë¶</h1>
	</div>	
	<div class="body">
		<dl class="alert">
		    <dt>íì¼ ì²¨ë¶ íì¸</dt>
		    <dd>		     	
	      	<form id="frm" action="/uploadTemporaryFileFromEditor.do" method="post">
          <input id="fileBtn"  type="file" name="image_file" accept=".zip, .alz, .7z"/>
   					<input type="text" readonley="readonly" id="image_name"/>
      </form>
			</dd>
		</dl>
	</div>
	<div class="footer">
		<p><a href="#" onclick="closeWindow();" title="닫기" class="close">ë«ê¸°</a></p>
		<ul>
			<li class="submit"><a href="#" onclick="done();" title="업로드하기" class="btnlink">ë±ë¡</a> </li>
			<li class="cancel"><a href="#" onclick="closeWindow();" title="ì·¨ì" class="btnlink">ì·¨ì</a></li>
		</ul>
	</div>
</div>
</body>
</html>
