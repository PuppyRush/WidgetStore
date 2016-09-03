<!DOCTYPE html>
<html>
<head>
   <meta charset="utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
    
<title>파일 업로드 </title> 
<script src="/StoreEditor/js/popup.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="/StoreEditor/css/popup.css" type="text/css"/>
<script type="text/javascript">
// <![CDATA[
	
	function done() {
		if (typeof(execAttach) == 'undefined') { //Virtual Function
	        return;
	    }
		
		var _mockdata = {
			'attachurl': 'http://cfile297.uf.daum.net/attach/207C8C1B4AA4F5DC01A644',
			'filemime': 'image/gif',
			'filename': 'editor_bi.gif',
			'filesize': 640
		};
		execAttach(_mockdata);
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
		     	<form id="frm" action="/uploadTemporaryImageFromEditor.do" method="post">
          <input onchange="javascript:changeValue(this);" id="image_file"  type="file" name="image_file"/>
   					<input type="text" readonley="readonly" id="image_name"/>
      </form>
			    확인 누르면 파일 추가
			</dd>
		</dl>
	</div>
	<div class="footer">
		<p><a href="#" onclick="closeWindow();" title="ë«ê¸°" class="close">ë«ê¸°</a></p>
		<ul>
			<li class="submit"><a href="#" onclick="done();" title="ë±ë¡" class="btnlink">ë±ë¡</a> </li>
			<li class="cancel"><a href="#" onclick="closeWindow();" title="ì·¨ì" class="btnlink">ì·¨ì</a></li>
		</ul>
	</div>
</div>
</body>
</html>
