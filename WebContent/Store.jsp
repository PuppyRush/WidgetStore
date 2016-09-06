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
            <!-- 상위 메뉴 -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="#" onClick="fun_develop()">Develop</a>
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
        <div>
            <div class="row">
                <div class="col-md-3">
                    <p class="lead">WidgetStore</p>
                    <div class="list-group">
                        <a href="#" class="list-group-item">생활</a>
                        <a href="#" class="list-group-item">스포츠</a>
                        <a href="#" class="list-group-item">검색</a>
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
                    <!-- 상세보기 부분 -->
                    <div class='widgetInfo' id='widgetInfo' style="display: none;">
                        <div>
                            <!-- 위젯의 메인 사진과 작성자 -->
                            <div class='goodsMain'>
                                <img src='http://placehold.it/150x150' alt=''>
                                <h1 id='widgetName'></h1>
                                <h4 id='widgetDeveloperName'></h4>
                                <h4 id='star'></h4>
                                <h5 id = 'grade'></h5>
                                <div class='btnBuy'>Download</div>
                            </div>
                            <!-- 위젯의 설명용 사진과 설명 -->
                            <div class='goodsSub'>
                                <img src='http://placehold.it/550x150' alt=''>
                                <h5 id='widgetExplain'></h5>
                            </div>
                            <!-- 리뷰 -->
                            <div class='goodsReview'>
                                <div id = 'widgetReview'></div>
                                <div>
                                    <label>로그인한 작성자의 이름을 표시함</label>
                                    <!-- 덧글 다는 부분 -->
                                    <textarea id='msg'></textarea>
                                    <button onclick="submit()">댓글달기</button>
                                </div>
                            </div>
                            <div class='goodsRank'>
                                <select name="rank" onchange="javascript:getWidget()" style="width:140px;margin:10px;">
                                    <option value='5' selected>★★★★★</option>
                                    <option value='4' selected>★★★★☆</option>
                                    <option value='3' selected>★★★☆☆</option>
                                    <option value='2' selected>★★☆☆☆</option>
                                    <option value='1' selected>★☆☆☆☆</option>
                                    <option value='' selected></option>
                                    
                                    </select>
                            </div>
                        </div>
                    </div>
                    <div id="field" class="row">
                        <script type="text/javascript">
                        function init() {
                            // db에서 불러온 위젯의 id에 따라 적절한 위젯을 붙임.
                            addItem(1);
                            addItem(2);
                            addItem(3);
                            addItem(4);
                            addItem(5);
                            addItem(6);
                        }

                        // 덧글을 다는 함수
                        function submit() {
                            //
                            // 서버에 저장하는 부분이 필요함.
                            // 
                            // 
                            // 
                            // 
                            // 
                            var text = document.getElementById('msg');
                            if (text.value == "") {
                                alert("내용이 없음");
                                return;
                            }

                            showReview("아이디", text.value);
                            text.value = "";
                        }

                        // 전체 내용을 표시하는 함수
                        function open_detail(widgetId) {
                            // 숨겨졌던 폼을 로드함.
                            if (!document.getElementById('widgetInfo').style.display == "") {
                                document.getElementById('widgetInfo').style.display = '';
                            }

                            if(typeof(reviews)!='undefined')
                            {
                                document.getElementById('reviews').remove();
                            }

                            document.getElementById("widgetName").innerText = "" + widgetId + "의 위젯 이름임";
                            document.getElementById('widgetDeveloperName').innerText = "" + widgetId + "의 개발자 이름임";
                            document.getElementById('grade').innerText = "평점 :  4.5";
                            document.getElementById('star').innerText = "★★★★☆";
                            document.getElementById('widgetExplain').innerText =

                                "오늘도 또 우리 수탉이 막 쫓기었다.\n"

                            + "내가 점심을 먹고 나무를 하러 갈 양으로 나올 때이었다.\n"

                            + "산으로 올라서려니까 등뒤에서 푸르득푸드득, 하고 닭의 횃소리가 야단이다.\n"

                            + "깜짝 놀라서 고개를 돌려보니 아니나다르랴, 두 놈이 또 얼리었다.\n"

                            + "점순네 수탉(은 대강이가 크고 똑 오소리같이 실팍하게 생긴 놈)"

                            + "이 덩저리 작은 우리 수탉을 함부로 해내는 것이다.\n"

                            + " 그것도 그냥 해내는 것이 아니라 푸드득 하고 면두를 쪼고 물러섰다가 좀 사이를 두고 또 푸드득 하고 모가지를 쪼았다. \n\n"

                            + "김유정\n"

                            + "<동백꽃>\n";

                            // 댓글을 한번에 지우기 위해
                            // 댓글 부븐을 동적으로 추가함
                            

                            var div = document.createElement('div');
                            div.id = 'reviews';
                            document.getElementById('widgetReview').appendChild(div);

                            // 덧글을 로드함
                            showReview("디바", "이것도 너프해보시지");
                            showReview("라인하르트", "바스티온이 나가신다");
                            showReview("정크랫", "폭탄 받아라");
                        }

                        function showReview(name, text) {
                            // 위젯 번호에 따른 덧글을 표시함

                            var div = document.createElement('div');

                            var strHTML =
                                "<h4 id='userName'> " + name + " </h4>" + "<h5 id='userReview'>" + text + "</h5>";

                            div.innerHTML = strHTML;
                            document.getElementById('reviews').appendChild(div);
                        }

                        function addItem(widgetID) {
                            var div = document.createElement('div');
                            // 방법 1.
                            // div.innerHTML = document.getElementById('aaa').outerHTML;
                            // document.getElementById('field').appendChild(div);

                            // 방법 2.
                            // divId : field 내부의 div ID
                            // document.getElementById('divId').innerHTML = document.getElementById('aaa').outerHTML;

                            // 방법 3.
                            var strHTML =
                                "<div class='col-sm-4 col-lg-4 col-md-4' id ='testID" + widgetID + "'>" + "<div class='thumbnail'>" + "<img src='http://placehold.it/320x150' alt=''>" + "<div class='caption'>" + "<h4 class='pull-right'></h4>" +

                                '<h4 ><a href="JavaScript:;" onclick="JavaScript: open_detail(' + widgetID + ');">' + widgetID + "</a></h4>" +

                                "<p>이것은 실험용이다.</p></div>" + "<div class='ratings'>" + "<p class='pull-right'>18 reviews</p>" + "</div></div></div>";

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
                                    <p>시험</p>
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
                        <!-- 페이지 컨트롤 -->
                    </div>
                    <div class="pageWrap">
                        <ul class="pageControl">
                            <li><a href=""> 이전</a></li>
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
                            <li><a href=""> 이후</a></li>
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
                    제작자 : ㅇㅅㅇ
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
	
	function fun_develop(){
		location.replace("/Develop.jsp");
		
	}

</script>

</html>
