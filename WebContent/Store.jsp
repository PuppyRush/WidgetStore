
<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>

<%

	request.setCharacterEncoding("UTF-8");

%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>WidgetStore - STORE</title>
    <!-- Bootstrap Core CSS -->
    <link href="WidgetClientPage/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="WidgetClientPage/css/shop-homepage.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body onload="init()">
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
            <!-- ìì ë©ë´ -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
               
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
        <div>
            <div class="row">
                <div class="col-md-3">
                    <p class="lead">WidgetStore</p>
                    <div class="list-group">
                        <a href="#" class="list-group-item">ìí</a>
                        <a href="#" class="list-group-item">ì¤í¬ì¸ </a>
                        <a href="#" class="list-group-item">ê²ì</a>
                    </div>
                </div>
                <div class="col-md-9">
                    <!--
                    <div class="row carousel-holder">
            
                        <div class="col-md-12">
                            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                                <ol class="carousel-indicators">
                                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                                </ol>
                                <div class="carousel-inner">
                                    <div class="item active">
                                        <img class="slide-image" src="over.jpg" alt="">
                                    </div>
                                    <div class="item">
                                        <img class="slide-image" src="closer.jpg" alt="">
                                    </div>
                                    <div class="item">
                                        <img class="slide-image" src="deon.jpg" alt="">
                                    </div>
                                </div>
                                <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                </a>
                                <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right"></span>
                                </a>
                            </div>
                        </div>
            
                    </div>
                    -->
                    <!-- ìì¸ë³´ê¸° ë¶ë¶ -->
                    <div class='widgetInfo' id='widgetInfo' style="display: none;">
                        <div>
                            <!-- ìì ¯ì ë©ì¸ ì¬ì§ê³¼ ìì±ì -->
                            <div class='goodsMain'>
                                <img src='http://placehold.it/150x150' alt=''>
                                <h1 id='widgetName'></h1>
                                <h4 id='widgetDeveloperName'></h4>
                                <h4 id='star'></h4>
                                <h5 id='grade'></h5>
                                <div class='btnBuy'>Download</div>
                            </div>
                            <!-- ìì ¯ì ì¤ëªì© ì¬ì§ê³¼ ì¤ëª -->
                            <div class='goodsSub'>
                                <img src='http://placehold.it/550x150' alt=''>
                                <h5 id='widgetExplain'></h5>
                            </div>
                            <!-- ë¦¬ë·° -->
                            <div class="well">
                                <div class="row">
                                    <div class="col-md-12">
                                        <strong><ins>Comments</ins></strong>
                                        <span class="glyphicon glyphicon-pencil"></span>
                                        <div class='goodsReview'>
                                            <div id='widgetReview'></div>
                                            <div>
                                                <!-- ë§ê¸ ë¤ë ë¶ë¶ -->
                                                <div class="dropdown">
												<label>[ì¬ì©ì ID íì ë¶ë¶]</label>
                                                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
    âââââ
    <span class="caret"></span>
  </button>
                                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">âââââ</a></li>
                                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">âââââ</a></li>
                                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">âââââ</a></li>
                                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">âââââ</a></li>
                                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">âââââ</a></li>
                                                    </ul>
													
                                                </div>
												
                                                <div class="input-group">
                                                    <textarea id='msg' class="form-control" rows="2"></textarea>
                                                    <span class="input-group-btn">
        <button class="btn btn-default btn-lg" type="submit">ëê¸ë±ë¡</button>
      </span>
                                                </div>
                                                <!-- /input-group -->


                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="field" class="row">
                        <script type="text/javascript">
                            function init() {
                                // dbìì ë¶ë¬ì¨ ìì ¯ì idì ë°ë¼ ì ì í ìì ¯ì ë¶ì.
                                addItem(1);
                                addItem(2);
                                addItem(3);
                                addItem(4);
                                addItem(5);
                                addItem(6);
                            }

                            // ë§ê¸ì ë¤ë í¨ì
                            function submit() {
                                //
                                // ìë²ì ì ì¥íë ë¶ë¶ì´ íìí¨.
                                // 
                                // 
                                // 
                                // 
                                // 
                                var text = document.getElementById('msg');
                                if (text.value == "") {
                                    alert("ë´ì©ì´ ìì");
                                    return;
                                }

                                showReview("ìì´ë", text.value);
                                text.value = "";
                            }

                            // ì ì²´ ë´ì©ì íìíë í¨ì
                            function open_detail(widgetId) {
                                // ì¨ê²¨ì¡ë í¼ì ë¡ëí¨.
                                if (!document.getElementById('widgetInfo').style.display == "") {
                                    document.getElementById('widgetInfo').style.display = '';
                                }

                                if (typeof(reviews) != 'undefined') {
                                    document.getElementById('reviews').remove();
                                }

                                document.getElementById("widgetName").innerText = "" + widgetId + "ì ìì ¯ ì´ë¦ì";
                                document.getElementById('widgetDeveloperName').innerText = "" + widgetId + "ì ê°ë°ì ì´ë¦ì";
                                document.getElementById('grade').innerText = "íì  :  4.5";
                                document.getElementById('star').innerText = "âââââ";
                                document.getElementById('widgetExplain').innerText =

                                    "ì¤ëë ë ì°ë¦¬ ìíì´ ë§ ì«ê¸°ìë¤.\n"

                                +
                                "ë´ê° ì ì¬ì ë¨¹ê³  ëë¬´ë¥¼ íë¬ ê° ìì¼ë¡ ëì¬ ëì´ìë¤.\n"

                                +
                                "ì°ì¼ë¡ ì¬ë¼ìë ¤ëê¹ ë±ë¤ìì í¸ë¥´ëí¸ëë, íê³  ë­ì íìë¦¬ê° ì¼ë¨ì´ë¤.\n"

                                +
                                "ê¹ì§ ëë¼ì ê³ ê°ë¥¼ ëë ¤ë³´ë ìëëë¤ë¥´ë´, ë ëì´ ë ì¼ë¦¬ìë¤.\n"

                                +
                                "ì ìë¤ ìí(ì ëê°ì´ê° í¬ê³  ë ì¤ìë¦¬ê°ì´ ì¤ííê² ìê¸´ ë)"

                                +
                                "ì´ ë©ì ë¦¬ ìì ì°ë¦¬ ìíì í¨ë¶ë¡ í´ë´ë ê²ì´ë¤.\n"

                                +
                                " ê·¸ê²ë ê·¸ë¥ í´ë´ë ê²ì´ ìëë¼ í¸ëë íê³  ë©´ëë¥¼ ìª¼ê³  ë¬¼ë¬ì°ë¤ê° ì¢ ì¬ì´ë¥¼ ëê³  ë í¸ëë íê³  ëª¨ê°ì§ë¥¼ ìª¼ìë¤. \n\n"

                                +
                                "ê¹ì ì \n"

                                +
                                "<ëë°±ê½>\n";

                                // ëê¸ì íë²ì ì§ì°ê¸° ìí´
                                // ëê¸ ë¶ë¸ì ëì ì¼ë¡ ì¶ê°í¨


                                var div = document.createElement('div');
                                div.id = 'reviews';
                                document.getElementById('widgetReview').appendChild(div);

                                // ë§ê¸ì ë¡ëí¨
                                showReview("ëë°", "ì´ê²ë ëíí´ë³´ìì§");
                                showReview("ë¼ì¸íë¥´í¸", "ë°ì¤í°ì¨ì´ ëê°ì ë¤");
                                showReview("ì í¬ë«", "í­í ë°ìë¼");
                            }

                            function showReview(name, text) {
                                // ìì ¯ ë²í¸ì ë°ë¥¸ ë§ê¸ì íìí¨

                                var div = document.createElement('div');

                                var strHTML =
                                    "<h4 id='userName'> " + name + " </h4>" + "<h5 id='userReview'>" + text + "</h5><hr>";

                                div.innerHTML = strHTML;
                                document.getElementById('reviews').appendChild(div);
                            }

                            function addItem(widgetID) {
                                var div = document.createElement('div');
                                // ë°©ë² 1.
                                // div.innerHTML = document.getElementById('aaa').outerHTML;
                                // document.getElementById('field').appendChild(div);

                                // ë°©ë² 2.
                                // divId : field ë´ë¶ì div ID
                                // document.getElementById('divId').innerHTML = document.getElementById('aaa').outerHTML;

                                // ë°©ë² 3.
                                var strHTML =
                                    "<div class='col-sm-4 col-lg-4 col-md-4' id ='testID" + widgetID + "'>" + "<div class='thumbnail'>" + "<img src='http://placehold.it/320x150' alt=''>" + "<div class='caption'>" + "<h4 class='pull-right'></h4>" +

                                    '<h4 ><a href="JavaScript:;" onclick="JavaScript: open_detail(' + widgetID + ');">' + widgetID + "</a></h4>" +

                                    "<p>ì´ê²ì ì¤íì©ì´ë¤.</p></div>" + "<div class='ratings'>" + "<p class='pull-right'>18 reviews</p>" + "</div></div></div>";

                                div.innerHTML = strHTML;
                                document.getElementById('field').appendChild(div);
                            }
                        </script>
                        <!--
                        <div class="col-sm-4 col-lg-4 col-md-4" id="aaa">
                            <div class="thumbnail">
                                <img src="http://placehold.it/320x150" alt="">
                                <div class="caption">
                                    <h4 class="pull-right"></h4>
                                    <h4><a href="JavaScript:;" onclick="JavaScript: open_detail('1');">Fifth Product</a></h4>
                                    <p>ìí</p>
                                </div>
                                <div class="ratings">
                                    <p class="pull-right">18 reviews</p>
                                </div>
                            </div>
                        </div>
                        -->
                        <!--
                        <div class="col-sm-4 col-lg-4 col-md-4">
                            <h4><a href="#">View more Widget?</a>
                            </h4>
                            <p>If you like this template, then check out <a target="_blank" href="http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/">this tutorial</a> on how to build a working review system for your online store!</p>
                            <a class="btn btn-primary" target="_blank" href="http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/">View Tutorial</a>
                        </div>
                        -->
                        <!-- íì´ì§ ì»¨í¸ë¡¤ -->
                    </div>
                    <div class="pageWrap">
                        <ul class="pageControl">
                            <li><a href=""> ì´ì </a></li>
                            <li><a href=""> 1</a></li>
                            <li><a href=""> 2</a></li>
                            <li><a href=""> 3</a></li>
                            <li><a href=""> 4</a></li>
                            <li><a href=""> 5</a></li>
                            <li><a href=""> 6</a></li>
                            <li><a href=""> 7</a></li>
                            <li><a href=""> 8</a></li>
                            <li><a href=""> 9</a></li>
                            <li><a href=""> 10</a></li>
                            <li><a href=""> ì´í</a></li>
                        </ul>
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
                    ì ìì : ããã
                    <!-- <p>Copyright &copy; Your Website 2014</p> -->
                </div>
            </div>
        </footer>
    </div>
    <!-- /.container -->
    <!-- jQuery -->
    <script src="WidgetClientPage/js/jquery.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="WidgetClientPage/js/bootstrap.min.js"></script>
    

</body>
 
 <script>

	
 
 </script>
 
</html>