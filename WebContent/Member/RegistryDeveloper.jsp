<%@page import="property.enums.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("UTF-8");

%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Widget Store - Developer Notice</title>
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/stylish-portfolio.css" rel="stylesheet">
<!-- bootsnipp down -->
<link href="css/login.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="font-awesome/font-awesome.css" rel="stylesheet"
	type="text/css">
<link
	href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div id="accept">
				<div class="modal-body">
					<h2 class="text-center">Notice</h2>
					<div class="alert alert-info" role="alert">
						개발자(사)가 위젯을 등록 하기 위해 관련 항목을 제출하면 스토어는 아래 내용을 검수합니다.
						<p>위젯 등록전에 아래 심의 기준을 확인하시고 사전에 미리 점검해보세요.
					</div>
					<div class="panel panel-primary">
						<div class="panel-heading">1. 기능 </div>
						<div class="panel-body">
							위젯 기능의 검수 기준입니다. 아래에 해당하는 경우 위젯을 등록 할 수 없습니다.
							</p>
							- [ ] 웹 표준이 아닌 기술을 활용한 애니메이션을 사용할 경우 (Adobe Flash 등)<br> 
							- [ ] 특정 사이트에게 과도한 트레픽을 보낼 경우<br> - [ ] 바이러스 등에 노출되어 있거나
							유포하는 경우<br> - [ ] 사용자의 컴퓨터에 무단으로 연결 혹은 변경을 시도하려는 경우<br>
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">2. 컨텐츠  </div>
						<div class="panel-body">
							위젯 및 위젯스토어에 노출되는 모든 콘텐츠에 대한 심의 기준입니다.
							</p>
							- [ ] 선정적인 내용을 노출할 경우<br> - [ ] 불쾌감을 유발시킬 경우<br>
							- [ ] 광고를 포함할 경우<br> + - [ ] 법적문제가 발생할 우려가 있을 경우<br> 
							- [ ] 위젯에서 이동 할 수 있는 사이트도 위젯과 동일한 규정에 의해 검토하며, 해당 사이트에서 약관에 위배 혹은
							위배가 우려되는 경우<br>
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">3. 기타</div>
						<div class="panel-body">
							- [ ] 위젯의 성격과 카테고리가 일치하지 않는 경우<br>  - [ ] 상표권관련 문제가 발생이
							우려되는 경우<br> - [ ] 사용자에 의해서 일정수준 이상의 신고가 접수된 경우 삭제될 수 도
							있습니다.<br>
						</div>
					</div>
					
							<div class="panel panel-primary">
						<div class="panel-heading">4. 등록 해지</div>
						<div class="panel-body">
							- [ ] 위젯에 보안 침해사고, 저작권 등의 문제가 발생 시 위젯이 삭제 되며 개발자의 자격이 사전 경고 없이 박탈될 수 있습니다.
						</div>
					</div>
					
					<form method="get" action="/registryDeveloper.do" id="agreeForm"></form>
					<div class="modal-footer">
						<div class="text-center">
							<button type="submit" class="btn btn-success btn-lg " id="agree" >
								<i class="glyphicon glyphicon-ok"></i> 동의합니다.
							</button>
							<button type="submit" class="btn btn-danger btn-lg" id="dontAgree">
								<i class="glyphicon glyphicon-remove"></i> 동의하지 않습니다.
							</button>
						</div>
					</div>
				</div>

			</div>
		</div>

		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.min.js"></script>
</body>


<script>

$("#agree").click(function(){
	$("#agreeForm").submit();
});

$("#dontAgree").click(function(){
	
	location.replace("<%= enumPage.MAIN.toString() %>");  

	
});


</script>

</html>

