
			<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="euc-kr">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Develop - widgetUpload</title>

    <!-- Bootstrap Core CSS -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- Custom CSS -->

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

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">WIDGET STORE</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="#">Develop</a>
                    </li>
                    <li>
                        <a href="#">Services</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

        <div class="row">

            <div class="col-md-3">
                <p class="lead">WidgetStore</p>
                <div class="list-group">
                    <a href="#" class="list-group-item active">Category 1</a>
                    <a href="#" class="list-group-item">Category 2</a>
                    <a href="#" class="list-group-item">Category 3</a>
                </div>
            </div>
			<!-- ìë¡ë í¼ ìì -->
            <div class="col-md-9">

                <form id="upload-form" method="post" action="uploadWidget.do" enctype="multipart/form-data" >

					<div class="modal-body">
		    				<div id="div-lost-msg">
                                <div id="icon-lost-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-lost-msg">upload git widget.</span>
							</div>
								<input id="widget-name" name="widget-name" class="form-control" type="text" placeholder="Widget Name" required></br>
													<div class="btn-group"> <a class="btn btn-default dropdown-toggle btn-select" href="#" data-toggle="dropdown">등록 위치 <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="javascript:;">WidgetStore</a></li>
                <li><a href="javascript:;">GitHub</a></li>
            </ul>
        </div></br>
							<input id="sessionId" name="sessionId" type="hidden">
							<input id="position" name="position" value="git" type="hidden">
							<input id="kind" name="kind" value="sports" type="hidden">
				Drag & drop or choose images from your local file system
						<input accept="image/jpeg,image/jpg,image/png,image/tif,image/png, .zip, .7z" type="file" name="upload[]" multiple/>
				<!-- ì´ë¯¸ì§ íì¼ ì í -->
				<table class="table table-bordered" width="485" border="1" cellspacing=0 cellpadding=5>
					<tr>
						<td align="left" height="105" ondragenter="return false" ondragover="return false" ondrop="dropIt(event)">    
						<output id="thumbs"></output> 
						</td>
					</tr>
					<tr>
						<td align="center">
						<label class="btn btn-block btn-primary">
					
					</label>
						</td>
					</tr>
				</table>
				

				<!-- íì¤í¸ ìë ¥ -->
				<p class="help-block">Input text about this widget</p>
				<textarea class="form-control" id="contents" name="contents" rows="5" placeholder="ì´ ìì ¯ì ëí ì¤ëªì í´ì£¼ì¸ì."></textarea>

					<!-- ì¹´íê³ ë¦¬ -->
					<div class="btn-group"> <a class="btn btn-default dropdown-toggle btn-select" href="#" data-toggle="dropdown">Category <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="javascript:;">ì¢ë¥1</a></li>
                <li><a href="javascript:;">ì¢ë¥2</a></li>
                <li><a href="javascript:;">ì¢ë¥3</a></li>
				<li><a href="javascript:;">ì¢ë¥4</a></li>
            </ul>
        </div></br>

						<!-- ì£¼ì ì¬í­ -->
						<div class="modal-footer"></div>
								<div class="alert alert-warning" role="alert">
								1.ì£¼ìì¬í­1</br>
								2.ì£¼ìì¬í­2</br>
								3.ì£¼ìì¬í­3
								</div>
							
							
		    		    <div class="modal-footer">
                            <div>
                                			<button type="submit" class="btn btn-primary btn-lg btn-block" onclick="uploadsubmit()" >Upload</button>
                                	
                                	</form>
                            </div>
		    		    </div>
						</div>
					</div>

                </div>

            </div>

        </div>

    </div>
    <!-- /.container -->

    <div class="container">

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Your Website 2014</p>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.container -->
	
    <!-- jQuery -->
<script language="Javascript" type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>

    <!-- Bootstrap Core JavaScript -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- End: Loading Contents -->
</body>
<script>

function uploadsubmit(){
	var id = "<%= session.getId() %>";
	$("#sessionId").val(id);
	document.forms["upload-form"].submit();
		
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
</html>