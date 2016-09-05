
<form method="post" action="">
			<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Develop - widgetUpload</title>

    <!-- Bootstrap Core CSS -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- Custom CSS -->
    <link href="css/shop-item.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <link rel="stylesheet" href="css/editor.css" type="text/css" />
    <script src="js/editor_loader.js?environment=production" type="text/javascript" charset="utf-8"></script>
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
			<!-- Ã¬ÂÂÃ«Â¡ÂÃ«ÂÂ Ã­ÂÂ¼ Ã¬ÂÂÃ¬ÂÂ -->
            <div class="col-md-9">

                <form id="widget-upload">

					<div class="modal-body">
		    				<div id="div-lost-msg">
                                <div id="icon-lost-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-lost-msg">upload git widget.</span>
							</div>

		    				<input id="git-id" class="form-control" type="text" placeholder="Git ID" required></br>
							<input id="repository-name" class="form-control" type="text" placeholder="Repository name" required></br>
							<input id="git-url" class="form-control" type="text" placeholder="Git url" required></br>
				</form>
	<!-- Ã¬ÂÂÃ«ÂÂÃ­ÂÂ° Ã¬ÂÂÃ¬ÂÂ -->
	<!--
		@decsription
		Ã«ÂÂ±Ã«Â¡ÂÃ­ÂÂÃªÂ¸Â° Ã¬ÂÂÃ­ÂÂ FormÃ¬ÂÂ¼Ã«Â¡Â Ã¬ÂÂÃ­ÂÂ©Ã¬ÂÂ Ã«Â§ÂÃªÂ²Â Ã¬ÂÂÃ¬Â ÂÃ­ÂÂÃ¬ÂÂ¬ Ã¬ÂÂ¬Ã¬ÂÂ©Ã­ÂÂÃ«ÂÂ¤. Form Ã¬ÂÂ´Ã«Â¦ÂÃ¬ÂÂ Ã¬ÂÂÃ«ÂÂÃ­ÂÂ°Ã«Â¥Â¼ Ã¬ÂÂÃ¬ÂÂ±Ã­ÂÂ  Ã«ÂÂ Ã¬ÂÂ¤Ã¬Â ÂÃªÂ°ÂÃ¬ÂÂ¼Ã«Â¡Â Ã¬ÂÂ¤Ã¬Â ÂÃ­ÂÂÃ«ÂÂ¤.
	-->
	
	<script type="text/javascript">
	
	
	</script>
			
			
	<form name="tx_editor_form" id="tx_editor_form" action="../registrationWidget_git.do" method="post" accept-charset="utf-8">
	
	<!--깃 정보를 담아 보내기 위한 필 -->
		<input type="hidden" id="git_id">
		<input type="hidden" id="git_repo">
		<input type="hidden" id="git_url">
	
		<!-- Ã¬ÂÂÃ«ÂÂÃ­ÂÂ° Ã¬Â»Â¨Ã­ÂÂÃ¬ÂÂ´Ã«ÂÂ Ã¬ÂÂÃ¬ÂÂ -->
		<div id="tx_trex_container" class="tx-editor-container">
			<!-- Ã¬ÂÂ¬Ã¬ÂÂ´Ã«ÂÂÃ«Â°Â -->
			<div id="tx_sidebar" class="tx-sidebar">
				<div class="tx-sidebar-boundary">
					<!-- Ã¬ÂÂ¬Ã¬ÂÂ´Ã«ÂÂÃ«Â°Â / Ã¬Â²Â¨Ã«Â¶Â -->
					<ul class="tx-bar tx-bar-left tx-nav-attach">
						<!-- Ã¬ÂÂ´Ã«Â¯Â¸Ã¬Â§Â Ã¬Â²Â¨Ã«Â¶Â Ã«Â²ÂÃ­ÂÂ¼ Ã¬ÂÂÃ¬ÂÂ -->
						<!--
							@decsription
							<li></li> Ã«ÂÂ¨Ã¬ÂÂÃ«Â¡Â Ã¬ÂÂÃ¬Â¹ÂÃ«Â¥Â¼ Ã¬ÂÂ´Ã«ÂÂÃ­ÂÂ  Ã¬ÂÂ Ã¬ÂÂÃ«ÂÂ¤.
						-->
						<li class="tx-list">
							<div unselectable="on" id="tx_image" class="tx-image tx-btn-trans">
								<a href="javascript:;" title="Ã¬ÂÂ¬Ã¬Â§Â" class="tx-text">Ã¬ÂÂ¬Ã¬Â§Â</a>
							</div>
						</li>
						<!-- Ã¬ÂÂ´Ã«Â¯Â¸Ã¬Â§Â Ã¬Â²Â¨Ã«Â¶Â Ã«Â²ÂÃ­ÂÂ¼ Ã«ÂÂ -->
						<li class="tx-list">
							<div unselectable="on" id="tx_file" class="tx-file tx-btn-trans">
								<a href="javascript:;" title="Ã­ÂÂÃ¬ÂÂ¼" class="tx-text">Ã­ÂÂÃ¬ÂÂ¼</a>
							</div>
						</li>
						<li class="tx-list">
							<div unselectable="on" id="tx_media" class="tx-media tx-btn-trans">
								<a href="javascript:;" title="Ã¬ÂÂ¸Ã«Â¶ÂÃ¬Â»Â¨Ã­ÂÂÃ¬Â¸Â " class="tx-text">Ã¬ÂÂ¸Ã«Â¶ÂÃ¬Â»Â¨Ã­ÂÂÃ¬Â¸Â </a>
							</div>
						</li>
						<li class="tx-list tx-list-extra">
							<div unselectable="on" class="tx-btn-nlrbg tx-extra">
								<a href="javascript:;" class="tx-icon" title="Ã«Â²ÂÃ­ÂÂ¼ Ã«ÂÂÃ«Â³Â´ÃªÂ¸Â°">Ã«Â²ÂÃ­ÂÂ¼ Ã«ÂÂÃ«Â³Â´ÃªÂ¸Â°</a>
							</div>
							<ul class="tx-extra-menu tx-menu" style="left:-48px;" unselectable="on">
								<!--
									@decsription
									Ã¬ÂÂ¼Ã«Â¶Â Ã«Â²ÂÃ­ÂÂ¼Ã«ÂÂ¤Ã¬ÂÂ Ã«Â¹Â¼Ã¬ÂÂ Ã«Â ÂÃ¬ÂÂ´Ã¬ÂÂ´Ã«Â¡Â Ã¬ÂÂ¨ÃªÂ¸Â°Ã«ÂÂ ÃªÂ¸Â°Ã«ÂÂ¥Ã¬ÂÂ Ã¬ÂÂÃ­ÂÂ  ÃªÂ²Â½Ã¬ÂÂ° Ã¬ÂÂ´ ÃªÂ³Â³Ã¬ÂÂ¼Ã«Â¡Â Ã¬ÂÂ´Ã«ÂÂÃ¬ÂÂÃ­ÂÂ¬ Ã¬ÂÂ Ã¬ÂÂÃ«ÂÂ¤.
								-->
							</ul>
						</li>
					</ul>
					<!-- Ã¬ÂÂ¬Ã¬ÂÂ´Ã«ÂÂÃ«Â°Â / Ã¬ÂÂ°Ã¬Â¸Â¡Ã¬ÂÂÃ¬ÂÂ­ -->
					<ul class="tx-bar tx-bar-right">
						<li class="tx-list">
							<div unselectable="on" class="tx-btn-lrbg tx-fullscreen" id="tx_fullscreen">
								<a href="javascript:;" class="tx-icon" title="Ã«ÂÂÃªÂ²ÂÃ¬ÂÂ°ÃªÂ¸Â° (Ctrl+M)">Ã«ÂÂÃªÂ²ÂÃ¬ÂÂ°ÃªÂ¸Â°</a>
							</div>
						</li>
					</ul>
					<ul class="tx-bar tx-bar-right tx-nav-opt">
						<li class="tx-list">
							<div unselectable="on" class="tx-switchtoggle" id="tx_switchertoggle">
								<a href="javascript:;" title="Ã¬ÂÂÃ«ÂÂÃ­ÂÂ° Ã­ÂÂÃ¬ÂÂ">Ã¬ÂÂÃ«ÂÂÃ­ÂÂ°</a>
							</div>
						</li>
					</ul>
				</div>
			</div>

			<!-- Ã­ÂÂ´Ã«Â°Â - ÃªÂ¸Â°Ã«Â³Â¸ Ã¬ÂÂÃ¬ÂÂ -->
			<!--
				@decsription
				Ã­ÂÂ´Ã«Â°Â Ã«Â²ÂÃ­ÂÂ¼Ã¬ÂÂ ÃªÂ·Â¸Ã«Â£Â¹Ã­ÂÂÃ¬ÂÂ Ã«Â³ÂÃªÂ²Â½Ã¬ÂÂ´ Ã­ÂÂÃ¬ÂÂÃ­ÂÂ  Ã«ÂÂÃ«ÂÂ Ã¬ÂÂÃ¬Â¹Â(Ã¬ÂÂ¼Ã¬ÂªÂ½, ÃªÂ°ÂÃ¬ÂÂ´Ã«ÂÂ°, Ã¬ÂÂ¤Ã«Â¥Â¸Ã¬ÂªÂ½) Ã¬ÂÂ Ã«ÂÂ°Ã«ÂÂ¼ <li> Ã¬ÂÂÃ«ÂÂÃ¬ÂÂ <div>Ã¬ÂÂ Ã­ÂÂ´Ã«ÂÂÃ¬ÂÂ¤Ã«ÂªÂÃ¬ÂÂ Ã«Â³ÂÃªÂ²Â½Ã­ÂÂÃ«Â©Â´ Ã«ÂÂÃ«ÂÂ¤.
				tx-btn-lbg: Ã¬ÂÂ¼Ã¬ÂªÂ½, tx-btn-bg: ÃªÂ°ÂÃ¬ÂÂ´Ã«ÂÂ°, tx-btn-rbg: Ã¬ÂÂ¤Ã«Â¥Â¸Ã¬ÂªÂ½, tx-btn-lrbg: Ã«ÂÂÃ«Â¦Â½Ã¬Â ÂÃ¬ÂÂ¸ ÃªÂ·Â¸Ã«Â£Â¹

				Ã«ÂÂÃ«Â¡Â­Ã«ÂÂ¤Ã¬ÂÂ´ Ã«Â²ÂÃ­ÂÂ¼Ã¬ÂÂ Ã­ÂÂ¬ÃªÂ¸Â°Ã«Â¥Â¼ Ã«Â³ÂÃªÂ²Â½Ã­ÂÂÃªÂ³Â Ã¬ÂÂ Ã­ÂÂ  ÃªÂ²Â½Ã¬ÂÂ°Ã¬ÂÂÃ«ÂÂ Ã«ÂÂÃ¬ÂÂ´Ã¬ÂÂ Ã«ÂÂ°Ã«ÂÂ¼ <li> Ã¬ÂÂÃ«ÂÂÃ¬ÂÂ <div>Ã¬ÂÂ Ã­ÂÂ´Ã«ÂÂÃ¬ÂÂ¤Ã«ÂªÂÃ¬ÂÂ Ã«Â³ÂÃªÂ²Â½Ã­ÂÂÃ«Â©Â´ Ã«ÂÂÃ«ÂÂ¤.
				tx-slt-70bg, tx-slt-59bg, tx-slt-42bg, tx-btn-43lrbg, tx-btn-52lrbg, tx-btn-57lrbg, tx-btn-71lrbg
				tx-btn-48lbg, tx-btn-48rbg, tx-btn-30lrbg, tx-btn-46lrbg, tx-btn-67lrbg, tx-btn-49lbg, tx-btn-58bg, tx-btn-46bg, tx-btn-49rbg
			-->
			<div id="tx_toolbar_basic" class="tx-toolbar tx-toolbar-basic"><div class="tx-toolbar-boundary">
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div id="tx_fontfamily" unselectable="on" class="tx-slt-70bg tx-fontfamily">
							<a href="javascript:;" title="ÃªÂ¸ÂÃªÂ¼Â´">ÃªÂµÂ´Ã«Â¦Â¼</a>
						</div>
						<div id="tx_fontfamily_menu" class="tx-fontfamily-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div unselectable="on" class="tx-slt-42bg tx-fontsize" id="tx_fontsize">
							<a href="javascript:;" title="ÃªÂ¸ÂÃ¬ÂÂÃ­ÂÂ¬ÃªÂ¸Â°">9pt</a>
						</div>
						<div id="tx_fontsize_menu" class="tx-fontsize-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-font">

					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-bold" id="tx_bold">
							<a href="javascript:;" class="tx-icon" title="ÃªÂµÂµÃªÂ²Â (Ctrl+B)">ÃªÂµÂµÃªÂ²Â</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-underline" id="tx_underline">
							<a href="javascript:;" class="tx-icon" title="Ã«Â°ÂÃ¬Â¤Â (Ctrl+U)">Ã«Â°ÂÃ¬Â¤Â</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-italic" id="tx_italic">
							<a href="javascript:;" class="tx-icon" title="ÃªÂ¸Â°Ã¬ÂÂ¸Ã¬ÂÂ (Ctrl+I)">ÃªÂ¸Â°Ã¬ÂÂ¸Ã¬ÂÂ</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-strike" id="tx_strike">
							<a href="javascript:;" class="tx-icon" title="Ã¬Â·Â¨Ã¬ÂÂÃ¬ÂÂ  (Ctrl+D)">Ã¬Â·Â¨Ã¬ÂÂÃ¬ÂÂ </a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-slt-tbg 	tx-forecolor" id="tx_forecolor">
							<a href="javascript:;" class="tx-icon" title="ÃªÂ¸ÂÃ¬ÂÂÃ¬ÂÂ">ÃªÂ¸ÂÃ¬ÂÂÃ¬ÂÂ</a>
							<a href="javascript:;" class="tx-arrow" title="ÃªÂ¸ÂÃ¬ÂÂÃ¬ÂÂ Ã¬ÂÂ Ã­ÂÂ">ÃªÂ¸ÂÃ¬ÂÂÃ¬ÂÂ Ã¬ÂÂ Ã­ÂÂ</a>
						</div>
						<div id="tx_forecolor_menu" class="tx-menu tx-forecolor-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-slt-brbg 	tx-backcolor" id="tx_backcolor">
							<a href="javascript:;" class="tx-icon" title="ÃªÂ¸ÂÃ¬ÂÂ Ã«Â°Â°ÃªÂ²Â½Ã¬ÂÂ">ÃªÂ¸ÂÃ¬ÂÂ Ã«Â°Â°ÃªÂ²Â½Ã¬ÂÂ</a>
							<a href="javascript:;" class="tx-arrow" title="ÃªÂ¸ÂÃ¬ÂÂ Ã«Â°Â°ÃªÂ²Â½Ã¬ÂÂ Ã¬ÂÂ Ã­ÂÂ">ÃªÂ¸ÂÃ¬ÂÂ Ã«Â°Â°ÃªÂ²Â½Ã¬ÂÂ Ã¬ÂÂ Ã­ÂÂ</a>
						</div>
						<div id="tx_backcolor_menu" class="tx-menu tx-backcolor-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-align">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-alignleft" id="tx_alignleft">
							<a href="javascript:;" class="tx-icon" title="Ã¬ÂÂ¼Ã¬ÂªÂ½Ã¬Â ÂÃ«Â Â¬ (Ctrl+,)">Ã¬ÂÂ¼Ã¬ÂªÂ½Ã¬Â ÂÃ«Â Â¬</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-aligncenter" id="tx_aligncenter">
							<a href="javascript:;" class="tx-icon" title="ÃªÂ°ÂÃ¬ÂÂ´Ã«ÂÂ°Ã¬Â ÂÃ«Â Â¬ (Ctrl+.)">ÃªÂ°ÂÃ¬ÂÂ´Ã«ÂÂ°Ã¬Â ÂÃ«Â Â¬</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-alignright" id="tx_alignright">
							<a href="javascript:;" class="tx-icon" title="Ã¬ÂÂ¤Ã«Â¥Â¸Ã¬ÂªÂ½Ã¬Â ÂÃ«Â Â¬ (Ctrl+/)">Ã¬ÂÂ¤Ã«Â¥Â¸Ã¬ÂªÂ½Ã¬Â ÂÃ«Â Â¬</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-rbg 	tx-alignfull" id="tx_alignfull">
							<a href="javascript:;" class="tx-icon" title="Ã¬ÂÂÃ¬ÂªÂ½Ã¬Â ÂÃ«Â Â¬">Ã¬ÂÂÃ¬ÂªÂ½Ã¬Â ÂÃ«Â Â¬</a>
						</div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-tab">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-indent" id="tx_indent">
							<a href="javascript:;" title="Ã«ÂÂ¤Ã¬ÂÂ¬Ã¬ÂÂ°ÃªÂ¸Â° (Tab)" class="tx-icon">Ã«ÂÂ¤Ã¬ÂÂ¬Ã¬ÂÂ°ÃªÂ¸Â°</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-rbg 	tx-outdent" id="tx_outdent">
							<a href="javascript:;" title="Ã«ÂÂ´Ã¬ÂÂ´Ã¬ÂÂ°ÃªÂ¸Â° (Shift+Tab)" class="tx-icon">Ã«ÂÂ´Ã¬ÂÂ´Ã¬ÂÂ°ÃªÂ¸Â°</a>
						</div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-list">
					<li class="tx-list">
						<div unselectable="on" class="tx-slt-31lbg tx-lineheight" id="tx_lineheight">
							<a href="javascript:;" class="tx-icon" title="Ã¬Â¤ÂÃªÂ°ÂÃªÂ²Â©">Ã¬Â¤ÂÃªÂ°ÂÃªÂ²Â©</a>
							<a href="javascript:;" class="tx-arrow" title="Ã¬Â¤ÂÃªÂ°ÂÃªÂ²Â©">Ã¬Â¤ÂÃªÂ°ÂÃªÂ²Â© Ã¬ÂÂ Ã­ÂÂ</a>
						</div>
						<div id="tx_lineheight_menu" class="tx-lineheight-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="tx-slt-31rbg tx-styledlist" id="tx_styledlist">
							<a href="javascript:;" class="tx-icon" title="Ã«Â¦Â¬Ã¬ÂÂ¤Ã­ÂÂ¸">Ã«Â¦Â¬Ã¬ÂÂ¤Ã­ÂÂ¸</a>
							<a href="javascript:;" class="tx-arrow" title="Ã«Â¦Â¬Ã¬ÂÂ¤Ã­ÂÂ¸">Ã«Â¦Â¬Ã¬ÂÂ¤Ã­ÂÂ¸ Ã¬ÂÂ Ã­ÂÂ</a>
						</div>
						<div id="tx_styledlist_menu" class="tx-styledlist-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-etc">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-emoticon" id="tx_emoticon">
							<a href="javascript:;" class="tx-icon" title="Ã¬ÂÂ´Ã«ÂªÂ¨Ã­ÂÂ°Ã¬Â½Â">Ã¬ÂÂ´Ã«ÂªÂ¨Ã­ÂÂ°Ã¬Â½Â</a>
						</div>
						<div id="tx_emoticon_menu" class="tx-emoticon-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-link" id="tx_link">
							<a href="javascript:;" class="tx-icon" title="Ã«Â§ÂÃ­ÂÂ¬ (Ctrl+K)">Ã«Â§ÂÃ­ÂÂ¬</a>
						</div>
						<div id="tx_link_menu" class="tx-link-menu tx-menu"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-specialchar" id="tx_specialchar">
							<a href="javascript:;" class="tx-icon" title="Ã­ÂÂ¹Ã¬ÂÂÃ«Â¬Â¸Ã¬ÂÂ">Ã­ÂÂ¹Ã¬ÂÂÃ«Â¬Â¸Ã¬ÂÂ</a>
						</div>
						<div id="tx_specialchar_menu" class="tx-specialchar-menu tx-menu"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-table" id="tx_table">
							<a href="javascript:;" class="tx-icon" title="Ã­ÂÂÃ«Â§ÂÃ«ÂÂ¤ÃªÂ¸Â°">Ã­ÂÂÃ«Â§ÂÃ«ÂÂ¤ÃªÂ¸Â°</a>
						</div>
						<div id="tx_table_menu" class="tx-table-menu tx-menu" unselectable="on">
							<div class="tx-menu-inner">
								<div class="tx-menu-preview"></div>
								<div class="tx-menu-rowcol"></div>
								<div class="tx-menu-deco"></div>
								<div class="tx-menu-enter"></div>
							</div>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-rbg 	tx-horizontalrule" id="tx_horizontalrule">
							<a href="javascript:;" class="tx-icon" title="ÃªÂµÂ¬Ã«Â¶ÂÃ¬ÂÂ ">ÃªÂµÂ¬Ã«Â¶ÂÃ¬ÂÂ </a>
						</div>
						<div id="tx_horizontalrule_menu" class="tx-horizontalrule-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-richtextbox" id="tx_richtextbox">
							<a href="javascript:;" class="tx-icon" title="ÃªÂ¸ÂÃ¬ÂÂÃ¬ÂÂ">ÃªÂ¸ÂÃ¬ÂÂÃ¬ÂÂ</a>
						</div>
						<div id="tx_richtextbox_menu" class="tx-richtextbox-menu tx-menu">
							<div class="tx-menu-header">
								<div class="tx-menu-preview-area">
									<div class="tx-menu-preview"></div>
								</div>
								<div class="tx-menu-switch">
									<div class="tx-menu-simple tx-selected"><a><span>ÃªÂ°ÂÃ«ÂÂ¨ Ã¬ÂÂ Ã­ÂÂ</span></a></div>
									<div class="tx-menu-advanced"><a><span>Ã¬Â§ÂÃ¬Â Â Ã¬ÂÂ Ã­ÂÂ</span></a></div>
								</div>
							</div>
							<div class="tx-menu-inner">
							</div>
							<div class="tx-menu-footer">
								<img class="tx-menu-confirm"
									 src="./images/icon/editor/btn_confirm.gif?rv=1.0.1" alt=""/>
								<img class="tx-menu-cancel" hspace="3"
									 src="./images/icon/editor/btn_cancel.gif?rv=1.0.1" alt=""/>
							</div>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-quote" id="tx_quote">
							<a href="javascript:;" class="tx-icon" title="Ã¬ÂÂ¸Ã¬ÂÂ©ÃªÂµÂ¬ (Ctrl+Q)">Ã¬ÂÂ¸Ã¬ÂÂ©ÃªÂµÂ¬</a>
						</div>
						<div id="tx_quote_menu" class="tx-quote-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-background" id="tx_background">
							<a href="javascript:;" class="tx-icon" title="Ã«Â°Â°ÃªÂ²Â½Ã¬ÂÂ">Ã«Â°Â°ÃªÂ²Â½Ã¬ÂÂ</a>
						</div>
						<div id="tx_background_menu" class="tx-menu tx-background-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-rbg 	tx-dictionary" id="tx_dictionary">
							<a href="javascript:;" class="tx-icon" title="Ã¬ÂÂ¬Ã¬Â Â">Ã¬ÂÂ¬Ã¬Â Â</a>
						</div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-undo">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-undo" id="tx_undo">
							<a href="javascript:;" class="tx-icon" title="Ã¬ÂÂ¤Ã­ÂÂÃ¬Â·Â¨Ã¬ÂÂ (Ctrl+Z)">Ã¬ÂÂ¤Ã­ÂÂÃ¬Â·Â¨Ã¬ÂÂ</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-rbg 	tx-redo" id="tx_redo">
							<a href="javascript:;" class="tx-icon" title="Ã«ÂÂ¤Ã¬ÂÂÃ¬ÂÂ¤Ã­ÂÂ (Ctrl+Y)">Ã«ÂÂ¤Ã¬ÂÂÃ¬ÂÂ¤Ã­ÂÂ</a>
						</div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-right">
					<li class="tx-list">
						<div unselectable="on" class="tx-btn-nlrbg tx-advanced" id="tx_advanced">
							<a href="javascript:;" class="tx-icon" title="Ã­ÂÂ´Ã«Â°Â Ã«ÂÂÃ«Â³Â´ÃªÂ¸Â°">Ã­ÂÂ´Ã«Â°Â Ã«ÂÂÃ«Â³Â´ÃªÂ¸Â°</a>
						</div>
					</li>
				</ul>
			</div></div>
			<!-- Ã­ÂÂ´Ã«Â°Â - ÃªÂ¸Â°Ã«Â³Â¸ Ã«ÂÂ -->
			<!-- Ã­ÂÂ´Ã«Â°Â - Ã«ÂÂÃ«Â³Â´ÃªÂ¸Â° Ã¬ÂÂÃ¬ÂÂ -->
			<div id="tx_toolbar_advanced" class="tx-toolbar tx-toolbar-advanced"><div class="tx-toolbar-boundary">
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div class="tx-tableedit-title"></div>
					</li>
				</ul>

				<ul class="tx-bar tx-bar-left tx-group-align">
					<li class="tx-list">
						<div unselectable="on" class="tx-btn-lbg tx-mergecells" id="tx_mergecells">
							<a href="javascript:;" class="tx-icon2" title="Ã«Â³ÂÃ­ÂÂ©">Ã«Â³ÂÃ­ÂÂ©</a>
						</div>
						<div id="tx_mergecells_menu" class="tx-mergecells-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="tx-btn-bg tx-insertcells" id="tx_insertcells">
							<a href="javascript:;" class="tx-icon2" title="Ã¬ÂÂ½Ã¬ÂÂ">Ã¬ÂÂ½Ã¬ÂÂ</a>
						</div>
						<div id="tx_insertcells_menu" class="tx-insertcells-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="tx-btn-rbg tx-deletecells" id="tx_deletecells">
							<a href="javascript:;" class="tx-icon2" title="Ã¬ÂÂ­Ã¬Â Â">Ã¬ÂÂ­Ã¬Â Â</a>
						</div>
						<div id="tx_deletecells_menu" class="tx-deletecells-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>

				<ul class="tx-bar tx-bar-left tx-group-align">
					<li class="tx-list">
						<div id="tx_cellslinepreview" unselectable="on" class="tx-slt-70lbg tx-cellslinepreview">
							<a href="javascript:;" title="Ã¬ÂÂ  Ã«Â¯Â¸Ã«Â¦Â¬Ã«Â³Â´ÃªÂ¸Â°"></a>
						</div>
						<div id="tx_cellslinepreview_menu" class="tx-cellslinepreview-menu tx-menu"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div id="tx_cellslinecolor" unselectable="on" class="tx-slt-tbg tx-cellslinecolor">
							<a href="javascript:;" class="tx-icon2" title="Ã¬ÂÂ Ã¬ÂÂ">Ã¬ÂÂ Ã¬ÂÂ</a>

							<div class="tx-colorpallete" unselectable="on"></div>
						</div>
						<div id="tx_cellslinecolor_menu" class="tx-cellslinecolor-menu tx-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div id="tx_cellslineheight" unselectable="on" class="tx-btn-bg tx-cellslineheight">
							<a href="javascript:;" class="tx-icon2" title="Ã«ÂÂÃªÂ»Â">Ã«ÂÂÃªÂ»Â</a>

						</div>
						<div id="tx_cellslineheight_menu" class="tx-cellslineheight-menu tx-menu"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div id="tx_cellslinestyle" unselectable="on" class="tx-btn-bg tx-cellslinestyle">
							<a href="javascript:;" class="tx-icon2" title="Ã¬ÂÂ¤Ã­ÂÂÃ¬ÂÂ¼">Ã¬ÂÂ¤Ã­ÂÂÃ¬ÂÂ¼</a>
						</div>
						<div id="tx_cellslinestyle_menu" class="tx-cellslinestyle-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div id="tx_cellsoutline" unselectable="on" class="tx-btn-rbg tx-cellsoutline">
							<a href="javascript:;" class="tx-icon2" title="Ã­ÂÂÃ«ÂÂÃ«Â¦Â¬">Ã­ÂÂÃ«ÂÂÃ«Â¦Â¬</a>

						</div>
						<div id="tx_cellsoutline_menu" class="tx-cellsoutline-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div id="tx_tablebackcolor" unselectable="on" class="tx-btn-lrbg tx-tablebackcolor"
							 style="background-color:#9aa5ea;">
							<a href="javascript:;" class="tx-icon2" title="Ã­ÂÂÃ¬ÂÂ´Ã«Â¸Â Ã«Â°Â°ÃªÂ²Â½Ã¬ÂÂ">Ã­ÂÂÃ¬ÂÂ´Ã«Â¸Â Ã«Â°Â°ÃªÂ²Â½Ã¬ÂÂ</a>
						</div>
						<div id="tx_tablebackcolor_menu" class="tx-tablebackcolor-menu tx-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div id="tx_tabletemplate" unselectable="on" class="tx-btn-lrbg tx-tabletemplate">
							<a href="javascript:;" class="tx-icon2" title="Ã­ÂÂÃ¬ÂÂ´Ã«Â¸Â Ã¬ÂÂÃ¬ÂÂ">Ã­ÂÂÃ¬ÂÂ´Ã«Â¸Â Ã¬ÂÂÃ¬ÂÂ</a>
						</div>
						<div id="tx_tabletemplate_menu" class="tx-tabletemplate-menu tx-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
				</ul>

			</div></div>
			<!-- Ã­ÂÂ´Ã«Â°Â - Ã«ÂÂÃ«Â³Â´ÃªÂ¸Â° Ã«ÂÂ -->
			<!-- Ã­ÂÂ¸Ã¬Â§ÂÃ¬ÂÂÃ¬ÂÂ­ Ã¬ÂÂÃ¬ÂÂ -->
				<!-- Ã¬ÂÂÃ«ÂÂÃ­ÂÂ° Start -->
	<div id="tx_canvas" class="tx-canvas">
		<div id="tx_loading" class="tx-loading"><div><img src="images/icon/editor/loading2.png" width="113" height="21" align="absmiddle"/></div></div>
		<div id="tx_canvas_wysiwyg_holder" class="tx-holder" style="display:block;">
			<iframe id="tx_canvas_wysiwyg" name="tx_canvas_wysiwyg" allowtransparency="true" frameborder="0"></iframe>
		</div>
		<div class="tx-source-deco">
			<div id="tx_canvas_source_holder" class="tx-holder">
				<textarea id="tx_canvas_source" rows="30" cols="30"></textarea>
			</div>
		</div>
		<div id="tx_canvas_text_holder" class="tx-holder">
			<textarea id="tx_canvas_text" rows="30" cols="30"></textarea>
		</div>
	</div>
					<!-- Ã«ÂÂÃ¬ÂÂ´Ã¬Â¡Â°Ã¬Â Â Start -->
	<div id="tx_resizer" class="tx-resize-bar">
		<div class="tx-resize-bar-bg"></div>
		<img id="tx_resize_holder" src="images/icon/editor/skin/01/btn_drag01.gif" width="58" height="12" unselectable="on" alt="" />
	</div>
					<div class="tx-side-bi" id="tx_side_bi">
		<div style="text-align: right;">
			<img hspace="4" height="14" width="78" align="absmiddle" src="images/icon/editor/editor_bi.png" />
		</div>
	</div>
				<!-- Ã­ÂÂ¸Ã¬Â§ÂÃ¬ÂÂÃ¬ÂÂ­ Ã«ÂÂ -->
			<!-- Ã¬Â²Â¨Ã«Â¶ÂÃ«Â°ÂÃ¬ÂÂ¤ Ã¬ÂÂÃ¬ÂÂ -->
				<!-- Ã­ÂÂÃ¬ÂÂ¼Ã¬Â²Â¨Ã«Â¶ÂÃ«Â°ÂÃ¬ÂÂ¤ Start -->
	<div id="tx_attach_div" class="tx-attach-div">
		<div id="tx_attach_txt" class="tx-attach-txt">Ã­ÂÂÃ¬ÂÂ¼ Ã¬Â²Â¨Ã«Â¶Â</div>
		<div id="tx_attach_box" class="tx-attach-box">
			<div class="tx-attach-box-inner">
				<div id="tx_attach_preview" class="tx-attach-preview"><p></p><img src="images/icon/editor/pn_preview.gif" width="147" height="108" unselectable="on"/></div>
				<div class="tx-attach-main">
					<div id="tx_upload_progress" class="tx-upload-progress"><div>0%</div><p>Ã­ÂÂÃ¬ÂÂ¼Ã¬ÂÂ Ã¬ÂÂÃ«Â¡ÂÃ«ÂÂÃ­ÂÂÃ«ÂÂ Ã¬Â¤ÂÃ¬ÂÂÃ«ÂÂÃ«ÂÂ¤.</p></div>
					<ul class="tx-attach-top">
						<li id="tx_attach_delete" class="tx-attach-delete"><a>Ã¬Â ÂÃ¬Â²Â´Ã¬ÂÂ­Ã¬Â Â</a></li>
						<li id="tx_attach_size" class="tx-attach-size">
							Ã­ÂÂÃ¬ÂÂ¼: <span id="tx_attach_up_size" class="tx-attach-size-up"></span>/<span id="tx_attach_max_size"></span>
						</li>
						<li id="tx_attach_tools" class="tx-attach-tools">
						</li>
					</ul>
					<ul id="tx_attach_list" class="tx-attach-list"></ul>
				</div>
			</div>
		</div>
	</div>
				<!-- Ã¬Â²Â¨Ã«Â¶ÂÃ«Â°ÂÃ¬ÂÂ¤ Ã«ÂÂ -->
		</div>
		<!-- Ã¬ÂÂÃ«ÂÂÃ­ÂÂ° Ã¬Â»Â¨Ã­ÂÂÃ¬ÂÂ´Ã«ÂÂ Ã«ÂÂ -->
	</form>
</div>
<!-- Ã¬ÂÂÃ«ÂÂÃ­ÂÂ° Ã«ÂÂ -->
					<div class="dropdown">
							<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
							Ã¬Â¹Â´Ã­ÂÂÃªÂ³Â Ã«Â¦Â¬
							<span class="caret"></span>
							</button>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="#">Ã¬Â¢ÂÃ«Â¥Â1</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="#">Ã¬Â¢ÂÃ«Â¥Â2</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="#">Ã¬Â¢ÂÃ«Â¥Â3</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="#">Ã¬Â¢ÂÃ«Â¥Â4</a></li>
								</ul>
							</div></br>
						<div class="modal-footer"></div>
								<div class="alert alert-warning" role="alert">
								1.Ã¬Â£Â¼Ã¬ÂÂÃ¬ÂÂ¬Ã­ÂÂ­1</br>
								2.Ã¬Â£Â¼Ã¬ÂÂÃ¬ÂÂ¬Ã­ÂÂ­2</br>
								3.Ã¬Â£Â¼Ã¬ÂÂÃ¬ÂÂ¬Ã­ÂÂ­3
								</div>
							
							
		    		    <div class="modal-footer">
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block">Upload</button>
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
	<script type="text/javascript">
	/* 
	var _checkConfirmWrite = false;
	var _checkUnload = true;
	
	$(window).on('beforeunload', function(){
		
		if(_checkUnload){
			if(document.getElementById("unload_path").value != ''){
				var upload_path_value = documentElementById("upload_path").value;
				var save_file_name_value = document.getElementById("save_file_name").value;
				$.post("/temporaryDeleteImage.do", {upload_path:upload_path_value,save_file_name:save_file_name_value})
				
			}
			
			
		}
		else{
			
		}
		
	});
	 */
	
	
	var config = {
		txHost: '', /* Ã«ÂÂ°Ã­ÂÂÃ¬ÂÂ Ã¬ÂÂ Ã«Â¦Â¬Ã¬ÂÂÃ¬ÂÂ¤Ã«ÂÂ¤Ã¬ÂÂ Ã«Â¡ÂÃ«ÂÂ©Ã­ÂÂ  Ã«ÂÂ Ã­ÂÂÃ¬ÂÂÃ­ÂÂ Ã«Â¶ÂÃ«Â¶ÂÃ¬ÂÂ¼Ã«Â¡Â, ÃªÂ²Â½Ã«Â¡ÂÃªÂ°Â Ã«Â³ÂÃªÂ²Â½Ã«ÂÂÃ«Â©Â´ Ã¬ÂÂ´ Ã«Â¶ÂÃ«Â¶Â Ã¬ÂÂÃ¬Â ÂÃ¬ÂÂ´ Ã­ÂÂÃ¬ÂÂ. ex) http://xxx.xxx.com */
		txPath: '', /* Ã«ÂÂ°Ã­ÂÂÃ¬ÂÂ Ã¬ÂÂ Ã«Â¦Â¬Ã¬ÂÂÃ¬ÂÂ¤Ã«ÂÂ¤Ã¬ÂÂ Ã«Â¡ÂÃ«ÂÂ©Ã­ÂÂ  Ã«ÂÂ Ã­ÂÂÃ¬ÂÂÃ­ÂÂ Ã«Â¶ÂÃ«Â¶ÂÃ¬ÂÂ¼Ã«Â¡Â, ÃªÂ²Â½Ã«Â¡ÂÃªÂ°Â Ã«Â³ÂÃªÂ²Â½Ã«ÂÂÃ«Â©Â´ Ã¬ÂÂ´ Ã«Â¶ÂÃ«Â¶Â Ã¬ÂÂÃ¬Â ÂÃ¬ÂÂ´ Ã­ÂÂÃ¬ÂÂ. ex) /xxx/xxx/ */
		txService: 'sample', /* Ã¬ÂÂÃ¬Â ÂÃ­ÂÂÃ¬ÂÂÃ¬ÂÂÃ¬ÂÂ. */
		txProject: 'sample', /* Ã¬ÂÂÃ¬Â ÂÃ­ÂÂÃ¬ÂÂÃ¬ÂÂÃ¬ÂÂ. Ã­ÂÂÃ«Â¡ÂÃ¬Â ÂÃ­ÂÂ¸ÃªÂ°Â Ã¬ÂÂ¬Ã«ÂÂ¬ÃªÂ°ÂÃ¬ÂÂ¼ ÃªÂ²Â½Ã¬ÂÂ°Ã«Â§Â Ã¬ÂÂÃ¬Â ÂÃ­ÂÂÃ«ÂÂ¤. */
		initializedId: "", /* Ã«ÂÂÃ«Â¶ÂÃ«Â¶ÂÃ¬ÂÂ ÃªÂ²Â½Ã¬ÂÂ°Ã¬ÂÂ Ã«Â¹ÂÃ«Â¬Â¸Ã¬ÂÂÃ¬ÂÂ´ */
		wrapper: "tx_trex_container", /* Ã¬ÂÂÃ«ÂÂÃ­ÂÂ°Ã«Â¥Â¼ Ã«ÂÂÃ«ÂÂ¬Ã¬ÂÂ¸ÃªÂ³Â  Ã¬ÂÂÃ«ÂÂ Ã«Â ÂÃ¬ÂÂ´Ã¬ÂÂ´ Ã¬ÂÂ´Ã«Â¦Â(Ã¬ÂÂÃ«ÂÂÃ­ÂÂ° Ã¬Â»Â¨Ã­ÂÂÃ¬ÂÂ´Ã«ÂÂ) */
		form: 'tx_editor_form'+"", /* Ã«ÂÂ±Ã«Â¡ÂÃ­ÂÂÃªÂ¸Â° Ã¬ÂÂÃ­ÂÂ Form Ã¬ÂÂ´Ã«Â¦Â */
		txIconPath: "images/icon/editor/", /*Ã¬ÂÂÃ«ÂÂÃ­ÂÂ°Ã¬ÂÂ Ã¬ÂÂ¬Ã¬ÂÂ©Ã«ÂÂÃ«ÂÂ Ã¬ÂÂ´Ã«Â¯Â¸Ã¬Â§Â Ã«ÂÂÃ«Â ÂÃ­ÂÂ°Ã«Â¦Â¬, Ã­ÂÂÃ¬ÂÂÃ¬ÂÂ Ã«ÂÂ°Ã«ÂÂ¼ Ã¬ÂÂÃ¬Â ÂÃ­ÂÂÃ«ÂÂ¤. */
		txDecoPath: "images/deco/contents/", /*Ã«Â³Â¸Ã«Â¬Â¸Ã¬ÂÂ Ã¬ÂÂ¬Ã¬ÂÂ©Ã«ÂÂÃ«ÂÂ Ã¬ÂÂ´Ã«Â¯Â¸Ã¬Â§Â Ã«ÂÂÃ«Â ÂÃ­ÂÂ°Ã«Â¦Â¬, Ã¬ÂÂÃ«Â¹ÂÃ¬ÂÂ¤Ã¬ÂÂÃ¬ÂÂ Ã¬ÂÂ¬Ã¬ÂÂ©Ã­ÂÂ  Ã«ÂÂÃ«ÂÂ Ã¬ÂÂÃ¬ÂÂ±Ã«ÂÂ Ã¬Â»Â¨Ã­ÂÂÃ¬Â¸Â Ã«Â¡Â Ã«Â°Â°Ã­ÂÂ¬Ã«ÂÂÃªÂ¸Â° Ã¬ÂÂÃ­ÂÂ´ Ã¬Â ÂÃ«ÂÂÃªÂ²Â½Ã«Â¡ÂÃ«Â¡Â Ã¬ÂÂÃ¬Â ÂÃ­ÂÂÃ«ÂÂ¤. */
		canvas: {
            exitEditor:{
                /*
                desc:'Ã«Â¹Â Ã¬Â Â¸ Ã«ÂÂÃ¬ÂÂ¤Ã¬ÂÂÃ«Â Â¤Ã«Â©Â´ shift+bÃ«Â¥Â¼ Ã«ÂÂÃ«Â¥Â´Ã¬ÂÂ¸Ã¬ÂÂ.',
                hotKey: {
                    shiftKey:true,
                    keyCode:66
                },
                nextElement: document.getElementsByTagName('button')[0]
                */
            },
			styles: {
				color: "#123456", /* ÃªÂ¸Â°Ã«Â³Â¸ ÃªÂ¸ÂÃ¬ÂÂÃ¬ÂÂ */
				fontFamily: "ÃªÂµÂ´Ã«Â¦Â¼", /* ÃªÂ¸Â°Ã«Â³Â¸ ÃªÂ¸ÂÃ¬ÂÂÃ¬Â²Â´ */
				fontSize: "10pt", /* ÃªÂ¸Â°Ã«Â³Â¸ ÃªÂ¸ÂÃ¬ÂÂÃ­ÂÂ¬ÃªÂ¸Â° */
				backgroundColor: "#fff", /*ÃªÂ¸Â°Ã«Â³Â¸ Ã«Â°Â°ÃªÂ²Â½Ã¬ÂÂ */
				lineHeight: "1.5", /*ÃªÂ¸Â°Ã«Â³Â¸ Ã¬Â¤ÂÃªÂ°ÂÃªÂ²Â© */
				padding: "8px" /* Ã¬ÂÂÃ¬Â§ÂÃ¬ÂÂ Ã¬ÂÂÃ¬ÂÂ­Ã¬ÂÂ Ã¬ÂÂ¬Ã«Â°Â± */
			},
			showGuideArea: false
		},
		events: {
			preventUnload: false
		},
		sidebar: {
			attachbox: {
				show: true,
				confirmForDeleteAll: true
			},
			attacher: {
				image : {
						popPageUrl : "/StoreEditor/pages/trex/image.jsp"
				},
				file : {
					popPageUrl : "/StoreEditor/pages/trex/file.jsp"
				}				
			}
		},
		size: {
			contentWidth: 700 /* Ã¬Â§ÂÃ¬Â ÂÃ«ÂÂ Ã«Â³Â¸Ã«Â¬Â¸Ã¬ÂÂÃ¬ÂÂ­Ã¬ÂÂ Ã«ÂÂÃ¬ÂÂ´ÃªÂ°Â Ã¬ÂÂÃ¬ÂÂ ÃªÂ²Â½Ã¬ÂÂ°Ã¬ÂÂ Ã¬ÂÂ¤Ã¬Â Â */
		},
		capacity: {
			available :1048576,
			maximum : 5242880
		}
	};

	EditorJSLoader.ready(function(Editor) {
		var editor = new Editor(config);
	});
	
</script>

<!-- Sample: Saving Contents -->
<script type="text/javascript">
	/* Ã¬ÂÂÃ¬Â ÂÃ¬ÂÂ© Ã­ÂÂ¨Ã¬ÂÂ */
	function saveContent() {
		Editor.save(); // Ã¬ÂÂ´ Ã­ÂÂ¨Ã¬ÂÂÃ«Â¥Â¼ Ã­ÂÂ¸Ã¬Â¶ÂÃ­ÂÂÃ¬ÂÂ¬ ÃªÂ¸ÂÃ¬ÂÂ Ã«ÂÂ±Ã«Â¡ÂÃ­ÂÂÃ«Â©Â´ Ã«ÂÂÃ«ÂÂ¤.
	}

	/**
	 * Editor.save()Ã«Â¥Â¼ Ã­ÂÂ¸Ã¬Â¶ÂÃ­ÂÂ ÃªÂ²Â½Ã¬ÂÂ° Ã«ÂÂ°Ã¬ÂÂ´Ã­ÂÂ°ÃªÂ°Â Ã¬ÂÂ Ã­ÂÂ¨Ã­ÂÂÃ¬Â§Â ÃªÂ²ÂÃ¬ÂÂ¬Ã­ÂÂÃªÂ¸Â° Ã¬ÂÂÃ­ÂÂ´ Ã«Â¶ÂÃ«Â¥Â´Ã«ÂÂ Ã¬Â½ÂÃ«Â°Â±Ã­ÂÂ¨Ã¬ÂÂÃ«Â¡Â
	 * Ã¬ÂÂÃ­ÂÂ©Ã¬ÂÂ Ã«Â§ÂÃªÂ²Â Ã¬ÂÂÃ¬Â ÂÃ­ÂÂÃ¬ÂÂ¬ Ã¬ÂÂ¬Ã¬ÂÂ©Ã­ÂÂÃ«ÂÂ¤.
	 * Ã«ÂªÂ¨Ã«ÂÂ  Ã«ÂÂ°Ã¬ÂÂ´Ã­ÂÂ°ÃªÂ°Â Ã¬ÂÂ Ã­ÂÂ¨Ã­ÂÂ  ÃªÂ²Â½Ã¬ÂÂ°Ã¬ÂÂ trueÃ«Â¥Â¼ Ã«Â¦Â¬Ã­ÂÂ´Ã­ÂÂÃ«ÂÂ¤.
	 * @function
	 * @param {Object} editor - Ã¬ÂÂÃ«ÂÂÃ­ÂÂ°Ã¬ÂÂÃ¬ÂÂ Ã«ÂÂÃªÂ²Â¨Ã¬Â£Â¼Ã«ÂÂ editor ÃªÂ°ÂÃ¬Â²Â´
	 * @returns {Boolean} Ã«ÂªÂ¨Ã«ÂÂ  Ã«ÂÂ°Ã¬ÂÂ´Ã­ÂÂ°ÃªÂ°Â Ã¬ÂÂ Ã­ÂÂ¨Ã­ÂÂ  ÃªÂ²Â½Ã¬ÂÂ°Ã¬ÂÂ true
	 */
	function validForm(editor) {
		// Place your validation logic here

		// sample : validate that content exists
		var validator = new Trex.Validator();
		var content = editor.getContent();
		if (!validator.exists(content)) {
			alert('ë´ì©ì ìë ¥í´ì£¼ì¸ì');
			_checkConfirmWrite = false;
			Editor.focus();
			return false;
		}

		return true;
	}

	/**
	 * Editor.save()Ã«Â¥Â¼ Ã­ÂÂ¸Ã¬Â¶ÂÃ­ÂÂ ÃªÂ²Â½Ã¬ÂÂ° validForm callback Ã¬ÂÂ´ Ã¬ÂÂÃ­ÂÂÃ«ÂÂ Ã¬ÂÂ´Ã­ÂÂ
	 * Ã¬ÂÂ¤Ã¬Â Â form submitÃ¬ÂÂ Ã¬ÂÂÃ­ÂÂ´ form Ã­ÂÂÃ«ÂÂÃ«Â¥Â¼ Ã¬ÂÂÃ¬ÂÂ±, Ã«Â³ÂÃªÂ²Â½Ã­ÂÂÃªÂ¸Â° Ã¬ÂÂÃ­ÂÂ´ Ã«Â¶ÂÃ«Â¥Â´Ã«ÂÂ Ã¬Â½ÂÃ«Â°Â±Ã­ÂÂ¨Ã¬ÂÂÃ«Â¡Â
	 * ÃªÂ°ÂÃ¬ÂÂ Ã¬ÂÂÃ­ÂÂ©Ã¬ÂÂ Ã«Â§ÂÃªÂ²Â Ã¬Â ÂÃ¬Â ÂÃ­ÂÂ Ã¬ÂÂÃ¬ÂÂ©Ã­ÂÂÃ¬ÂÂ¬ Ã¬ÂÂ¬Ã¬ÂÂ©Ã­ÂÂÃ«ÂÂ¤.
	 * @function
	 * @param {Object} editor - Ã¬ÂÂÃ«ÂÂÃ­ÂÂ°Ã¬ÂÂÃ¬ÂÂ Ã«ÂÂÃªÂ²Â¨Ã¬Â£Â¼Ã«ÂÂ editor ÃªÂ°ÂÃ¬Â²Â´
	 * @returns {Boolean} Ã¬Â ÂÃ¬ÂÂÃ¬Â ÂÃ¬ÂÂ¸ ÃªÂ²Â½Ã¬ÂÂ°Ã¬ÂÂ true
	 */
	function setForm(editor) {
        var i, input;
        var form = editor.getForm();
        var content = editor.getContent();

        // Ã«Â³Â¸Ã«Â¬Â¸ Ã«ÂÂ´Ã¬ÂÂ©Ã¬ÂÂ Ã­ÂÂÃ«ÂÂÃ«Â¥Â¼ Ã¬ÂÂÃ¬ÂÂ±Ã­ÂÂÃ¬ÂÂ¬ ÃªÂ°ÂÃ¬ÂÂ Ã­ÂÂ Ã«ÂÂ¹Ã­ÂÂÃ«ÂÂ Ã«Â¶ÂÃ«Â¶Â
        var textarea = document.createElement('textarea');
        textarea.name = 'content';
        textarea.value = content;
        form.createField(textarea);

        /* Ã¬ÂÂÃ«ÂÂÃ¬ÂÂ Ã¬Â½ÂÃ«ÂÂÃ«ÂÂ Ã¬Â²Â¨Ã«Â¶ÂÃ«ÂÂ Ã«ÂÂ°Ã¬ÂÂ´Ã­ÂÂ°Ã«Â¥Â¼ Ã­ÂÂÃ«ÂÂÃ«Â¥Â¼ Ã¬ÂÂÃ¬ÂÂ±Ã­ÂÂÃ¬ÂÂ¬ ÃªÂ°ÂÃ¬ÂÂ Ã­ÂÂ Ã«ÂÂ¹Ã­ÂÂÃ«ÂÂ Ã«Â¶ÂÃ«Â¶ÂÃ¬ÂÂ¼Ã«Â¡Â Ã¬ÂÂÃ­ÂÂ©Ã¬ÂÂ Ã«Â§ÂÃªÂ²Â Ã¬ÂÂÃ¬Â ÂÃ­ÂÂÃ¬ÂÂ¬ Ã¬ÂÂ¬Ã¬ÂÂ©Ã­ÂÂÃ«ÂÂ¤.
         Ã¬Â²Â¨Ã«Â¶ÂÃ«ÂÂ Ã«ÂÂ°Ã¬ÂÂ´Ã­ÂÂ° Ã¬Â¤ÂÃ¬ÂÂ Ã¬Â£Â¼Ã¬ÂÂ´Ã¬Â§Â Ã¬Â¢ÂÃ«Â¥Â(image,file..)Ã¬ÂÂ Ã­ÂÂ´Ã«ÂÂ¹Ã­ÂÂÃ«ÂÂ ÃªÂ²ÂÃ«Â§Â Ã«Â°Â°Ã¬ÂÂ´Ã«Â¡Â Ã«ÂÂÃªÂ²Â¨Ã¬Â¤ÂÃ«ÂÂ¤. */
        var images = editor.getAttachments('image');
        for (i = 0; i < images.length; i++) {
            // existStageÃ«ÂÂ Ã­ÂÂÃ¬ÂÂ¬ Ã«Â³Â¸Ã«Â¬Â¸Ã¬ÂÂ Ã¬Â¡Â´Ã¬ÂÂ¬Ã­ÂÂÃ«ÂÂÃ¬Â§Â Ã¬ÂÂ¬Ã«Â¶Â
            if (images[i].existStage) {
                // dataÃ«ÂÂ Ã­ÂÂÃ¬ÂÂÃ¬ÂÂÃ¬ÂÂ execAttach Ã«ÂÂ±Ã¬ÂÂ Ã­ÂÂµÃ­ÂÂ´ Ã«ÂÂÃªÂ¸Â´ Ã«ÂÂ°Ã¬ÂÂ´Ã­ÂÂ°
                alert('attachment information - image[' + i + '] \r\n' + JSON.stringify(images[i].data));
                input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'attach_image';
                input.value = images[i].data.imageurl;  // Ã¬ÂÂÃ¬ÂÂÃ¬ÂÂÃ«ÂÂ Ã¬ÂÂ´Ã«Â¯Â¸Ã¬Â§ÂÃªÂ²Â½Ã«Â¡ÂÃ«Â§Â Ã«Â°ÂÃ¬ÂÂÃ¬ÂÂ Ã¬ÂÂ¬Ã¬ÂÂ©
                form.createField(input);
            }
        }

        var files = editor.getAttachments('file');
        for (i = 0; i < files.length; i++) {
            input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'attach_file';
            input.value = files[i].data.attachurl;
            form.createField(input);
        }
        
        $("#git_id").val( $("#git-id").val() );
        $("#git_repo").val( $("#repository-name").val() );
        $("#git_url").val( $("#git-url").val() );
        
        return true;
	}
</script>
<div><button onclick='saveContent()'>SAMPLE - submit contents</button></div>
<!-- End: Saving Contents -->

<!-- Sample: Loading Contents -->
<textarea id="sample_contents_source" style="display:none;">
	<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
	<p style="text-align: center;">
		<img src="http://cfile273.uf.daum.net/image/2064CD374EE1ACCB0F15C8" class="tx-daum-image" style="clear: none; float: none;"/>
	</p>Ã¯Â»Â¿
	<p>
		<a href="http://cfile297.uf.daum.net/attach/207C8C1B4AA4F5DC01A644"><img src="snapshot/images/icon/p_gif_s.gif"/> editor_bi.gif</a>
	</p>
</textarea>
<script type="text/javascript">
	function loadContent() {
		var attachments = {};
		attachments['image'] = [];
		attachments['image'].push({
			'attacher': 'image',
			'data': {
				'imageurl': 'http://cfile273.uf.daum.net/image/2064CD374EE1ACCB0F15C8',
				'filename': 'github.gif',
				'filesize': 59501,
				'originalurl': 'http://cfile273.uf.daum.net/original/2064CD374EE1ACCB0F15C8',
				'thumburl': 'http://cfile273.uf.daum.net/P150x100/2064CD374EE1ACCB0F15C8'
			}
		});
		attachments['file'] = [];
		attachments['file'].push({
			'attacher': 'file',
			'data': {
				'attachurl': 'http://cfile297.uf.daum.net/attach/207C8C1B4AA4F5DC01A644',
				'filemime': 'image/gif',
				'filename': 'editor_bi.gif',
				'filesize': 640
			}
		});
		/* Ã¬Â ÂÃ¬ÂÂ¥Ã«ÂÂ Ã¬Â»Â¨Ã­ÂÂÃ¬Â¸Â Ã«Â¥Â¼ Ã«Â¶ÂÃ«ÂÂ¬Ã¬ÂÂ¤ÃªÂ¸Â° Ã¬ÂÂÃ­ÂÂ Ã­ÂÂ¨Ã¬ÂÂ Ã­ÂÂ¸Ã¬Â¶Â */
		Editor.modify({
			"attachments": function () { /* Ã¬Â ÂÃ¬ÂÂ¥Ã«ÂÂ Ã¬Â²Â¨Ã«Â¶ÂÃªÂ°Â Ã¬ÂÂÃ¬ÂÂ ÃªÂ²Â½Ã¬ÂÂ° Ã«Â°Â°Ã¬ÂÂ´Ã«Â¡Â Ã«ÂÂÃªÂ¹Â, Ã¬ÂÂÃ¬ÂÂ Ã«Â¶ÂÃ«Â¶ÂÃ¬ÂÂ Ã¬ÂÂÃ¬Â ÂÃ­ÂÂÃªÂ³Â  Ã¬ÂÂÃ«ÂÂ Ã«Â¶ÂÃ«Â¶ÂÃ¬ÂÂ Ã¬ÂÂÃ¬Â ÂÃ¬ÂÂÃ¬ÂÂ´ Ã¬ÂÂ¬Ã¬ÂÂ© */
				var allattachments = [];
				for (var i in attachments) {
					allattachments = allattachments.concat(attachments[i]);
				}
				return allattachments;
			}(),
			"content": document.getElementById("sample_contents_source") /* Ã«ÂÂ´Ã¬ÂÂ© Ã«Â¬Â¸Ã¬ÂÂÃ¬ÂÂ´, Ã¬Â£Â¼Ã¬ÂÂ´Ã¬Â§Â Ã­ÂÂÃ«ÂÂ(textarea) Ã¬ÂÂÃ«Â¦Â¬Ã«Â¨Â¼Ã­ÂÂ¸ */
		});
		
		$("#git_id").val( "asd" );
        $("#git_repo").val( "asd" );
        $("#git_url").val("asd" );
	}
</script>
<div><button onclick='loadContent()'>SAMPLE - load contents to editor</button></div>
    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

<!-- End: Loading Contents -->
</body>

</html>

        </form>