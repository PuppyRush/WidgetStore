<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Daum에디터 - 이미지 첨부</title> 
<script src="/StoreEditor/js/popup.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="/StoreEditor/css/popup.css" type="text/css"  charset="utf-8"/>

<!-- 쿼리 링크 -->
<script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="jquery.form.js"></script>

<script type="text/javascript">
// <![CDATA[
	
	$(function(){
        $("#saveBtn").click(function(){
            $("#frm").submit();
        })
        //ajax form submit
        $('#frm').ajaxForm({
            beforeSubmit: function (data,form,option) {
                //validation체크 
                //막기위해서는 return false를 잡아주면됨
                return false;
            },
            success: function(response,status){
                //성공후 서버에서 받은 데이터 처리
                done(response);
            },
            error: function(){
                //에러발생을 위한 code페이지
                alert("error!!");
            }                               
        });
    })
    function done(response) {
        if (typeof(execAttach) == 'undefined') { //Virtual Function
			alert("1");
            return;
        }
        var response_object = new Array();

		for (var i=0;i<response_object.length;i++){
			response_object[i] = $.parseJSON( response );

			execAttach(response_object[i]); 
		}
        closeWindow();
    }
 
    function initUploader(){
        var _opener = PopupUtil.getOpener();
        if (!_opener) {
            alert('잘못된 경로로 접근하셨습니다.');
            return;
        }
         
        var _attacher = getAttacher('image', _opener);
        registerAction(_attacher);
    }

// ]]>
</script>
</head>
<body onload="initUploader();">
<div class="wrapper">
	<div class="header">
		<h1>사진 첨부</h1>
	</div>	
	<div class="body">
		<dl class="alert">
		    <dt>사진 첨부 확인</dt>
		    <dd>
		    	<form id="frm" action="/fileupload.jsp" method="post">
                <input type="file" name="image_file"/>
                </form>
			</dd>
		</dl>
	</div>
	<div class="footer">
		<p><a href="#" onclick="closeWindow();" title="닫기" class="close">닫기</a></p>
		<ul>
			<li class="submit"><a href="#" onclick="done();" title="등록" class="btnlink">등록</a> </li>
			<li class="cancel"><a href="#" onclick="closeWindow();" title="취소" class="btnlink">취소</a></li>
		</ul>
	</div>
</div>
</body>
</html>