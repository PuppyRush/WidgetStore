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

    <link rel="stylesheet" href="css/editor.css" type="text/css" charset="utf-8"/>
    <script src="js/editor_loader.js" type="text/javascript" charset="utf-8"></script>
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

                <form id="widget-upload">

					<div class="modal-body">
		    				<div id="div-lost-msg">
                                <div id="icon-lost-msg" class="glyphicon glyphicon-chevron-right"></div>
                                <span id="text-lost-msg">upload widget.</span>
							</div>

		    				<input id="widget-name" class="form-control" type="text" placeholder="Widget name" required></br>
				</form>

	<!-- ìëí° ìì -->
	<!--
		@decsription
		ë±ë¡íê¸° ìí Formì¼ë¡ ìí©ì ë§ê² ìì íì¬ ì¬ì©íë¤. Form ì´ë¦ì ìëí°ë¥¼ ìì±í  ë ì¤ì ê°ì¼ë¡ ì¤ì íë¤.
	-->
	<form name="tx_editor_form" id="tx_editor_form" action="registrationWidget_our.do" method="post" accept-charset="utf-8">
		<!-- ìëí° ì»¨íì´ë ìì -->
		<div id="tx_trex_container" class="tx-editor-container">
			<!-- ì¬ì´ëë° -->
			<div id="tx_sidebar" class="tx-sidebar">
				<div class="tx-sidebar-boundary">
					<!-- ì¬ì´ëë° / ì²¨ë¶ -->
					<ul class="tx-bar tx-bar-left tx-nav-attach">
						<!-- ì´ë¯¸ì§ ì²¨ë¶ ë²í¼ ìì -->
						<!--
							@decsription
							<li></li> ë¨ìë¡ ìì¹ë¥¼ ì´ëí  ì ìë¤.
						-->
						<li class="tx-list">
							<div unselectable="on" id="tx_image" class="tx-image tx-btn-trans">
								<a href="javascript:;" title="ì¬ì§" class="tx-text">ì¬ì§</a>
							</div>
						</li>
						<!-- ì´ë¯¸ì§ ì²¨ë¶ ë²í¼ ë -->
						<li class="tx-list">
							<div unselectable="on" id="tx_file" class="tx-file tx-btn-trans">
								<a href="javascript:;" title="íì¼" class="tx-text">íì¼</a>
							</div>
						</li>
						<li class="tx-list">
							<div unselectable="on" id="tx_media" class="tx-media tx-btn-trans">
								<a href="javascript:;" title="ì¸ë¶ì»¨íì¸ " class="tx-text">ì¸ë¶ì»¨íì¸ </a>
							</div>
						</li>
						<li class="tx-list tx-list-extra">
							<div unselectable="on" class="tx-btn-nlrbg tx-extra">
								<a href="javascript:;" class="tx-icon" title="ë²í¼ ëë³´ê¸°">ë²í¼ ëë³´ê¸°</a>
							</div>
							<ul class="tx-extra-menu tx-menu" style="left:-48px;" unselectable="on">
								<!--
									@decsription
									ì¼ë¶ ë²í¼ë¤ì ë¹¼ì ë ì´ì´ë¡ ì¨ê¸°ë ê¸°ë¥ì ìí  ê²½ì° ì´ ê³³ì¼ë¡ ì´ëìí¬ ì ìë¤.
								-->
							</ul>
						</li>
					</ul>
					<!-- ì¬ì´ëë° / ì°ì¸¡ìì­ -->
					<ul class="tx-bar tx-bar-right">
						<li class="tx-list">
							<div unselectable="on" class="tx-btn-lrbg tx-fullscreen" id="tx_fullscreen">
								<a href="javascript:;" class="tx-icon" title="ëê²ì°ê¸° (Ctrl+M)">ëê²ì°ê¸°</a>
							</div>
						</li>
					</ul>
					<ul class="tx-bar tx-bar-right tx-nav-opt">
						<li class="tx-list">
							<div unselectable="on" class="tx-switchtoggle" id="tx_switchertoggle">
								<a href="javascript:;" title="ìëí° íì">ìëí°</a>
							</div>
						</li>
					</ul>
				</div>
			</div>

			<!-- í´ë° - ê¸°ë³¸ ìì -->
			<!--
				@decsription
				í´ë° ë²í¼ì ê·¸ë£¹íì ë³ê²½ì´ íìí  ëë ìì¹(ì¼ìª½, ê°ì´ë°, ì¤ë¥¸ìª½) ì ë°ë¼ <li> ìëì <div>ì í´ëì¤ëªì ë³ê²½íë©´ ëë¤.
				tx-btn-lbg: ì¼ìª½, tx-btn-bg: ê°ì´ë°, tx-btn-rbg: ì¤ë¥¸ìª½, tx-btn-lrbg: ëë¦½ì ì¸ ê·¸ë£¹

				ëë¡­ë¤ì´ ë²í¼ì í¬ê¸°ë¥¼ ë³ê²½íê³ ì í  ê²½ì°ìë ëì´ì ë°ë¼ <li> ìëì <div>ì í´ëì¤ëªì ë³ê²½íë©´ ëë¤.
				tx-slt-70bg, tx-slt-59bg, tx-slt-42bg, tx-btn-43lrbg, tx-btn-52lrbg, tx-btn-57lrbg, tx-btn-71lrbg
				tx-btn-48lbg, tx-btn-48rbg, tx-btn-30lrbg, tx-btn-46lrbg, tx-btn-67lrbg, tx-btn-49lbg, tx-btn-58bg, tx-btn-46bg, tx-btn-49rbg
			-->
			<div id="tx_toolbar_basic" class="tx-toolbar tx-toolbar-basic"><div class="tx-toolbar-boundary">
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div id="tx_fontfamily" unselectable="on" class="tx-slt-70bg tx-fontfamily">
							<a href="javascript:;" title="ê¸ê¼´">êµ´ë¦¼</a>
						</div>
						<div id="tx_fontfamily_menu" class="tx-fontfamily-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div unselectable="on" class="tx-slt-42bg tx-fontsize" id="tx_fontsize">
							<a href="javascript:;" title="ê¸ìí¬ê¸°">9pt</a>
						</div>
						<div id="tx_fontsize_menu" class="tx-fontsize-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-font">

					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-bold" id="tx_bold">
							<a href="javascript:;" class="tx-icon" title="êµµê² (Ctrl+B)">êµµê²</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-underline" id="tx_underline">
							<a href="javascript:;" class="tx-icon" title="ë°ì¤ (Ctrl+U)">ë°ì¤</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-italic" id="tx_italic">
							<a href="javascript:;" class="tx-icon" title="ê¸°ì¸ì (Ctrl+I)">ê¸°ì¸ì</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-strike" id="tx_strike">
							<a href="javascript:;" class="tx-icon" title="ì·¨ìì  (Ctrl+D)">ì·¨ìì </a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-slt-tbg 	tx-forecolor" id="tx_forecolor">
							<a href="javascript:;" class="tx-icon" title="ê¸ìì">ê¸ìì</a>
							<a href="javascript:;" class="tx-arrow" title="ê¸ìì ì í">ê¸ìì ì í</a>
						</div>
						<div id="tx_forecolor_menu" class="tx-menu tx-forecolor-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-slt-brbg 	tx-backcolor" id="tx_backcolor">
							<a href="javascript:;" class="tx-icon" title="ê¸ì ë°°ê²½ì">ê¸ì ë°°ê²½ì</a>
							<a href="javascript:;" class="tx-arrow" title="ê¸ì ë°°ê²½ì ì í">ê¸ì ë°°ê²½ì ì í</a>
						</div>
						<div id="tx_backcolor_menu" class="tx-menu tx-backcolor-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-align">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-alignleft" id="tx_alignleft">
							<a href="javascript:;" class="tx-icon" title="ì¼ìª½ì ë ¬ (Ctrl+,)">ì¼ìª½ì ë ¬</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-aligncenter" id="tx_aligncenter">
							<a href="javascript:;" class="tx-icon" title="ê°ì´ë°ì ë ¬ (Ctrl+.)">ê°ì´ë°ì ë ¬</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-alignright" id="tx_alignright">
							<a href="javascript:;" class="tx-icon" title="ì¤ë¥¸ìª½ì ë ¬ (Ctrl+/)">ì¤ë¥¸ìª½ì ë ¬</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-rbg 	tx-alignfull" id="tx_alignfull">
							<a href="javascript:;" class="tx-icon" title="ììª½ì ë ¬">ììª½ì ë ¬</a>
						</div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-tab">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-indent" id="tx_indent">
							<a href="javascript:;" title="ë¤ì¬ì°ê¸° (Tab)" class="tx-icon">ë¤ì¬ì°ê¸°</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-rbg 	tx-outdent" id="tx_outdent">
							<a href="javascript:;" title="ë´ì´ì°ê¸° (Shift+Tab)" class="tx-icon">ë´ì´ì°ê¸°</a>
						</div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-list">
					<li class="tx-list">
						<div unselectable="on" class="tx-slt-31lbg tx-lineheight" id="tx_lineheight">
							<a href="javascript:;" class="tx-icon" title="ì¤ê°ê²©">ì¤ê°ê²©</a>
							<a href="javascript:;" class="tx-arrow" title="ì¤ê°ê²©">ì¤ê°ê²© ì í</a>
						</div>
						<div id="tx_lineheight_menu" class="tx-lineheight-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="tx-slt-31rbg tx-styledlist" id="tx_styledlist">
							<a href="javascript:;" class="tx-icon" title="ë¦¬ì¤í¸">ë¦¬ì¤í¸</a>
							<a href="javascript:;" class="tx-arrow" title="ë¦¬ì¤í¸">ë¦¬ì¤í¸ ì í</a>
						</div>
						<div id="tx_styledlist_menu" class="tx-styledlist-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-etc">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-emoticon" id="tx_emoticon">
							<a href="javascript:;" class="tx-icon" title="ì´ëª¨í°ì½">ì´ëª¨í°ì½</a>
						</div>
						<div id="tx_emoticon_menu" class="tx-emoticon-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-link" id="tx_link">
							<a href="javascript:;" class="tx-icon" title="ë§í¬ (Ctrl+K)">ë§í¬</a>
						</div>
						<div id="tx_link_menu" class="tx-link-menu tx-menu"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-specialchar" id="tx_specialchar">
							<a href="javascript:;" class="tx-icon" title="í¹ìë¬¸ì">í¹ìë¬¸ì</a>
						</div>
						<div id="tx_specialchar_menu" class="tx-specialchar-menu tx-menu"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-table" id="tx_table">
							<a href="javascript:;" class="tx-icon" title="íë§ë¤ê¸°">íë§ë¤ê¸°</a>
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
							<a href="javascript:;" class="tx-icon" title="êµ¬ë¶ì ">êµ¬ë¶ì </a>
						</div>
						<div id="tx_horizontalrule_menu" class="tx-horizontalrule-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-richtextbox" id="tx_richtextbox">
							<a href="javascript:;" class="tx-icon" title="ê¸ìì">ê¸ìì</a>
						</div>
						<div id="tx_richtextbox_menu" class="tx-richtextbox-menu tx-menu">
							<div class="tx-menu-header">
								<div class="tx-menu-preview-area">
									<div class="tx-menu-preview"></div>
								</div>
								<div class="tx-menu-switch">
									<div class="tx-menu-simple tx-selected"><a><span>ê°ë¨ ì í</span></a></div>
									<div class="tx-menu-advanced"><a><span>ì§ì  ì í</span></a></div>
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
							<a href="javascript:;" class="tx-icon" title="ì¸ì©êµ¬ (Ctrl+Q)">ì¸ì©êµ¬</a>
						</div>
						<div id="tx_quote_menu" class="tx-quote-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-bg 	tx-background" id="tx_background">
							<a href="javascript:;" class="tx-icon" title="ë°°ê²½ì">ë°°ê²½ì</a>
						</div>
						<div id="tx_background_menu" class="tx-menu tx-background-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-rbg 	tx-dictionary" id="tx_dictionary">
							<a href="javascript:;" class="tx-icon" title="ì¬ì ">ì¬ì </a>
						</div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left tx-group-undo">
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-lbg 	tx-undo" id="tx_undo">
							<a href="javascript:;" class="tx-icon" title="ì¤íì·¨ì (Ctrl+Z)">ì¤íì·¨ì</a>
						</div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="		 tx-btn-rbg 	tx-redo" id="tx_redo">
							<a href="javascript:;" class="tx-icon" title="ë¤ìì¤í (Ctrl+Y)">ë¤ìì¤í</a>
						</div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-right">
					<li class="tx-list">
						<div unselectable="on" class="tx-btn-nlrbg tx-advanced" id="tx_advanced">
							<a href="javascript:;" class="tx-icon" title="í´ë° ëë³´ê¸°">í´ë° ëë³´ê¸°</a>
						</div>
					</li>
				</ul>
			</div></div>
			<!-- í´ë° - ê¸°ë³¸ ë -->
			<!-- í´ë° - ëë³´ê¸° ìì -->
			<div id="tx_toolbar_advanced" class="tx-toolbar tx-toolbar-advanced"><div class="tx-toolbar-boundary">
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div class="tx-tableedit-title"></div>
					</li>
				</ul>

				<ul class="tx-bar tx-bar-left tx-group-align">
					<li class="tx-list">
						<div unselectable="on" class="tx-btn-lbg tx-mergecells" id="tx_mergecells">
							<a href="javascript:;" class="tx-icon2" title="ë³í©">ë³í©</a>
						</div>
						<div id="tx_mergecells_menu" class="tx-mergecells-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="tx-btn-bg tx-insertcells" id="tx_insertcells">
							<a href="javascript:;" class="tx-icon2" title="ì½ì">ì½ì</a>
						</div>
						<div id="tx_insertcells_menu" class="tx-insertcells-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div unselectable="on" class="tx-btn-rbg tx-deletecells" id="tx_deletecells">
							<a href="javascript:;" class="tx-icon2" title="ì­ì ">ì­ì </a>
						</div>
						<div id="tx_deletecells_menu" class="tx-deletecells-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>

				<ul class="tx-bar tx-bar-left tx-group-align">
					<li class="tx-list">
						<div id="tx_cellslinepreview" unselectable="on" class="tx-slt-70lbg tx-cellslinepreview">
							<a href="javascript:;" title="ì  ë¯¸ë¦¬ë³´ê¸°"></a>
						</div>
						<div id="tx_cellslinepreview_menu" class="tx-cellslinepreview-menu tx-menu"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div id="tx_cellslinecolor" unselectable="on" class="tx-slt-tbg tx-cellslinecolor">
							<a href="javascript:;" class="tx-icon2" title="ì ì">ì ì</a>

							<div class="tx-colorpallete" unselectable="on"></div>
						</div>
						<div id="tx_cellslinecolor_menu" class="tx-cellslinecolor-menu tx-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div id="tx_cellslineheight" unselectable="on" class="tx-btn-bg tx-cellslineheight">
							<a href="javascript:;" class="tx-icon2" title="ëê»">ëê»</a>

						</div>
						<div id="tx_cellslineheight_menu" class="tx-cellslineheight-menu tx-menu"
							 unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div id="tx_cellslinestyle" unselectable="on" class="tx-btn-bg tx-cellslinestyle">
							<a href="javascript:;" class="tx-icon2" title="ì¤íì¼">ì¤íì¼</a>
						</div>
						<div id="tx_cellslinestyle_menu" class="tx-cellslinestyle-menu tx-menu" unselectable="on"></div>
					</li>
					<li class="tx-list">
						<div id="tx_cellsoutline" unselectable="on" class="tx-btn-rbg tx-cellsoutline">
							<a href="javascript:;" class="tx-icon2" title="íëë¦¬">íëë¦¬</a>

						</div>
						<div id="tx_cellsoutline_menu" class="tx-cellsoutline-menu tx-menu" unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div id="tx_tablebackcolor" unselectable="on" class="tx-btn-lrbg tx-tablebackcolor"
							 style="background-color:#9aa5ea;">
							<a href="javascript:;" class="tx-icon2" title="íì´ë¸ ë°°ê²½ì">íì´ë¸ ë°°ê²½ì</a>
						</div>
						<div id="tx_tablebackcolor_menu" class="tx-tablebackcolor-menu tx-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
				</ul>
				<ul class="tx-bar tx-bar-left">
					<li class="tx-list">
						<div id="tx_tabletemplate" unselectable="on" class="tx-btn-lrbg tx-tabletemplate">
							<a href="javascript:;" class="tx-icon2" title="íì´ë¸ ìì">íì´ë¸ ìì</a>
						</div>
						<div id="tx_tabletemplate_menu" class="tx-tabletemplate-menu tx-menu tx-colorpallete"
							 unselectable="on"></div>
					</li>
				</ul>

			</div></div>
			<!-- í´ë° - ëë³´ê¸° ë -->
			<!-- í¸ì§ìì­ ìì -->
				<!-- ìëí° Start -->
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
					<!-- ëì´ì¡°ì  Start -->
	<div id="tx_resizer" class="tx-resize-bar">
		<div class="tx-resize-bar-bg"></div>
		<img id="tx_resize_holder" src="images/icon/editor/skin/01/btn_drag01.gif" width="58" height="12" unselectable="on" alt="" />
	</div>
					<div class="tx-side-bi" id="tx_side_bi">
		<div style="text-align: right;">
			<img hspace="4" height="14" width="78" align="absmiddle" src="images/icon/editor/editor_bi.png" />
		</div>
	</div>
				<!-- í¸ì§ìì­ ë -->
			<!-- ì²¨ë¶ë°ì¤ ìì -->
				<!-- íì¼ì²¨ë¶ë°ì¤ Start -->
	<div id="tx_attach_div" class="tx-attach-div">
		<div id="tx_attach_txt" class="tx-attach-txt">íì¼ ì²¨ë¶</div>
		<div id="tx_attach_box" class="tx-attach-box">
			<div class="tx-attach-box-inner">
				<div id="tx_attach_preview" class="tx-attach-preview"><p></p><img src="images/icon/editor/pn_preview.gif" width="147" height="108" unselectable="on"/></div>
				<div class="tx-attach-main">
					<div id="tx_upload_progress" class="tx-upload-progress"><div>0%</div><p>íì¼ì ìë¡ëíë ì¤ìëë¤.</p></div>
					<ul class="tx-attach-top">
						<li id="tx_attach_delete" class="tx-attach-delete"><a>ì ì²´ì­ì </a></li>
						<li id="tx_attach_size" class="tx-attach-size">
							íì¼: <span id="tx_attach_up_size" class="tx-attach-size-up"></span>/<span id="tx_attach_max_size"></span>
						</li>
						<li id="tx_attach_tools" class="tx-attach-tools">
						</li>
					</ul>
					<ul id="tx_attach_list" class="tx-attach-list"></ul>
				</div>
			</div>
		</div>
	</div>
				<!-- ì²¨ë¶ë°ì¤ ë -->
		</div>
		<!-- ìëí° ì»¨íì´ë ë -->
	</form>
</div>

<!--임시 이미지 업로드 삭제 및 페이지 이  -->


<!-- ìëí° ë -->
					<div class="dropdown">
							<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
							ì¹´íê³ ë¦¬
							<span class="caret"></span>
							</button>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="#">ì¢ë¥1</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="#">ì¢ë¥2</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="#">ì¢ë¥3</a></li>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="#">ì¢ë¥4</a></li>
								</ul>
							</div></br>
						<div class="modal-footer"></div>
								<div class="alert alert-warning" role="alert">
								1.ì£¼ìì¬í­1</br>
								2.ì£¼ìì¬í­2</br>
								3.ì£¼ìì¬í­3
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
	var config = {
		txHost: '', /* ë°íì ì ë¦¬ìì¤ë¤ì ë¡ë©í  ë íìí ë¶ë¶ì¼ë¡, ê²½ë¡ê° ë³ê²½ëë©´ ì´ ë¶ë¶ ìì ì´ íì. ex) http://xxx.xxx.com */
		txPath: '', /* ë°íì ì ë¦¬ìì¤ë¤ì ë¡ë©í  ë íìí ë¶ë¶ì¼ë¡, ê²½ë¡ê° ë³ê²½ëë©´ ì´ ë¶ë¶ ìì ì´ íì. ex) /xxx/xxx/ */
		txService: 'sample', /* ìì íììì. */
		txProject: 'sample', /* ìì íììì. íë¡ì í¸ê° ì¬ë¬ê°ì¼ ê²½ì°ë§ ìì íë¤. */
		initializedId: "", /* ëë¶ë¶ì ê²½ì°ì ë¹ë¬¸ìì´ */
		wrapper: "tx_trex_container", /* ìëí°ë¥¼ ëë¬ì¸ê³  ìë ë ì´ì´ ì´ë¦(ìëí° ì»¨íì´ë) */
		form: 'tx_editor_form'+"", /* ë±ë¡íê¸° ìí Form ì´ë¦ */
		txIconPath: "images/icon/editor/", /*ìëí°ì ì¬ì©ëë ì´ë¯¸ì§ ëë í°ë¦¬, íìì ë°ë¼ ìì íë¤. */
		txDecoPath: "images/deco/contents/", /*ë³¸ë¬¸ì ì¬ì©ëë ì´ë¯¸ì§ ëë í°ë¦¬, ìë¹ì¤ìì ì¬ì©í  ëë ìì±ë ì»¨íì¸ ë¡ ë°°í¬ëê¸° ìí´ ì ëê²½ë¡ë¡ ìì íë¤. */
		canvas: {
            exitEditor:{
                /*
                desc:'ë¹ ì ¸ ëì¤ìë ¤ë©´ shift+bë¥¼ ëë¥´ì¸ì.',
                hotKey: {
                    shiftKey:true,
                    keyCode:66
                },
                nextElement: document.getElementsByTagName('button')[0]
                */
            },
			styles: {
				color: "#123456", /* ê¸°ë³¸ ê¸ìì */
				fontFamily: "êµ´ë¦¼", /* ê¸°ë³¸ ê¸ìì²´ */
				fontSize: "10pt", /* ê¸°ë³¸ ê¸ìí¬ê¸° */
				backgroundColor: "#fff", /*ê¸°ë³¸ ë°°ê²½ì */
				lineHeight: "1.5", /*ê¸°ë³¸ ì¤ê°ê²© */
				padding: "8px" /* ìì§ì ìì­ì ì¬ë°± */
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
			contentWidth: 700 /* ì§ì ë ë³¸ë¬¸ìì­ì ëì´ê° ìì ê²½ì°ì ì¤ì  */
		}
	};

	EditorJSLoader.ready(function(Editor) {
		var editor = new Editor(config);
	});
	
</script>

<!-- Sample: Saving Contents -->
<script type="text/javascript">
	/* ìì ì© í¨ì */
	function saveContent() {
		Editor.save(); // ì´ í¨ìë¥¼ í¸ì¶íì¬ ê¸ì ë±ë¡íë©´ ëë¤.
	}

	/**
	 * Editor.save()를 호출한 경우 데이터가 유효한지 검사하기 위해 부르는 콜백함수로
	 * 상황에 맞게 수정하여 사용한다.
	 * 모든 데이터가 유효할 경우에 true를 리턴한다.
	 * @function
	 * @param {Object} editor - 에디터에서 넘겨주는 editor 객체
	 * @returns {Boolean} 모든 데이터가 유효할 경우에 true
	 */
	function validForm(editor) {
		// Place your validation logic here

		// sample : validate that content exists
		var validator = new Trex.Validator();
		var content = editor.getContent();
		if (!validator.exists(content)) {
			alert('ë´ì©ì ìë ¥íì¸ì');
			return false;
		}

		return true;
	}

	 /**
		 * Editor.save()를 호출한 경우 validForm callback 이 수행된 이후
		 * 실제 form submit을 위해 form 필드를 생성, 변경하기 위해 부르는 콜백함수로
		 * 각자 상황에 맞게 적절히 응용하여 사용한다.
		 * @function
		 * @param {Object} editor - 에디터에서 넘겨주는 editor 객체
		 * @returns {Boolean} 정상적인 경우에 true
		 */
	function setForm(editor) {
        var i, input;
        var form = editor.getForm();
        var content = editor.getContent();

        // ë³¸ë¬¸ ë´ì©ì íëë¥¼ ìì±íì¬ ê°ì í ë¹íë ë¶ë¶
        var textarea = document.createElement('textarea');
        textarea.name = 'content';
        textarea.value = content;
        form.createField(textarea);

        /* ìëì ì½ëë ì²¨ë¶ë ë°ì´í°ë¥¼ íëë¥¼ ìì±íì¬ ê°ì í ë¹íë ë¶ë¶ì¼ë¡ ìí©ì ë§ê² ìì íì¬ ì¬ì©íë¤.
         ì²¨ë¶ë ë°ì´í° ì¤ì ì£¼ì´ì§ ì¢ë¥(image,file..)ì í´ë¹íë ê²ë§ ë°°ì´ë¡ ëê²¨ì¤ë¤. */
        var images = editor.getAttachments('image');
        for (i = 0; i < images.length; i++) {
            // existStageë íì¬ ë³¸ë¬¸ì ì¡´ì¬íëì§ ì¬ë¶
            if (images[i].existStage) {
                // dataë íììì execAttach ë±ì íµí´ ëê¸´ ë°ì´í°
                alert('attachment information - image[' + i + '] \r\n' + JSON.stringify(images[i].data));
                input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'attach_image';
                input.value = images[i].data.imageurl;  // ìììë ì´ë¯¸ì§ê²½ë¡ë§ ë°ìì ì¬ì©
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
	</p>ï»¿
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
		/* ì ì¥ë ì»¨íì¸ ë¥¼ ë¶ë¬ì¤ê¸° ìí í¨ì í¸ì¶ */
		Editor.modify({
			"attachments": function () { /* ì ì¥ë ì²¨ë¶ê° ìì ê²½ì° ë°°ì´ë¡ ëê¹, ìì ë¶ë¶ì ìì íê³  ìë ë¶ë¶ì ìì ìì´ ì¬ì© */
				var allattachments = [];
				for (var i in attachments) {
					allattachments = allattachments.concat(attachments[i]);
				}
				return allattachments;
			}(),
			"content": document.getElementById("sample_contents_source") /* ë´ì© ë¬¸ìì´, ì£¼ì´ì§ íë(textarea) ìë¦¬ë¨¼í¸ */
		});
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