<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Daumìëí° - ì´ë¯¸ì§ ì²¨ë¶</title> 
<script src="/StoreEditor/js/popup.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="/StoreEditor/css/popup.css" type="text/css"  charset="utf-8"/>

<!-- ì¿¼ë¦¬ ë§í¬ -->
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
                //validation체크 
                //막기위해서는 return false를 잡아주면됨
                return true;
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
    
    function changeValue(object){
    			var value = $(object).val();
    			if(value != ''){
    					var ext = value.split('.').pop().toLowerCase();
  						if($.inArray(ext, ['gif','png','jpg','jpeg','tif']) == -1){
  								alert("gif,png,jpg,jpeg,tif만 업로드 가능합니다 ");
  								$(object).val("");
 									$("#image_name").val("");
 									return;  						
  							}		
  						
							var array = value.split("\\");
							var a = 0 ;
							if(array.length > 1){
								document.getElementById('image_name').value == array[array.length-1];
							}else{
								document.getElementById('image_name').vlaue = value;
							}
							
    			}    	    	
    		}

// ]]>
</script>
</head>
<body onload="initUploader();">
<div class="wrapper">
	<div class="header">
		<h1>ì¬ì§ ì²¨ë¶</h1>
	</div>	
	<div class="body">
		<dl class="alert">
		    <dt>사진첨부 확인 git, png, jpg, jpeg, tif만 업로드 가능합니다.</dt>
		    <dd>
		    	<form id="frm" action="/uploadTemporaryImageFromEditor.do" method="post">
          <input onchange="javascript:changeValue(this);" id="image_file"  type="file" name="image_file"/>
   					<input type="text" readonley="readonly" id="image_name"/>
      </form>
			</dd>
		</dl>
	</div>
	<div class="footer">
		<p><a href="#" onclick="closeWindow();" title="ë«ê¸°" class="close">ë«ê¸°</a></p>
		<ul>
 								<li class="submit"><a href="#" id="saveBtn" title="등록" class="btnlink">등록</a> </li>
            <li class="cancel"><a href="#" onclick="closeWindow();" title="취소" class="btnlink">취소</a></li>
		</ul>
	</div>
</div>
</body>
</html>