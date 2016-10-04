
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="manageCustom.* , property.enums.*, page.VerifyPage, javaBean.*"%>

<%

	request.setCharacterEncoding("UTF-8");
	enumPage to = enumPage.MAIN;
 	boolean isFailVerify;
 	Member member = Member.getMember(session.getId());
	HashMap<String,Object> results =  VerifyPage.Verify(session.getId(), enumPage.MAIN);
	if(!(boolean)results.get("isSuccessVerify")){
		isFailVerify =false;
		to = (enumPage)results.get("to");
		request.setAttribute("message",  (String)results.get("message"));
		request.setAttribute("messageKind", results.get("messageKind"));
		response.sendRedirect(to.getString());
		return;
		
		
	}else{
		isFailVerify = true;
	}
		%>




<HTML>

<HEAD>
<TITLE>Custom Widget</TITLE>
</HEAD>

<body onload="setWidgetList();">
	<script>
		// 보드 위젯 리스트
		var listBoard = [];

		// 유저 정보에 따라 불러와서 유저가 다운받은 위젯을 리모콘에 집어넣음 
		function setWidgetList() {
			<%
			Object obj = null;
			widget w = null;
			int num = 0;
			String name = "";
			String tag = "";
			int pX = 0;
			int pY = 0;
			int width = 0;
			int height = 0;

			Enumeration<String> eNum = request.getAttributeNames();
			while (eNum.hasMoreElements()) {
				obj = request.getAttribute(eNum.nextElement());
				/* 불러오기일 경우 */
				if (null != obj && obj instanceof widget) {
										

					w = (widget) obj;
					%>

					
					num = <%=w.getNum()%>;
					name = "<%=w.getName()%>";
					tag = "<%=w.getTag()%> ";
					pX = <%=w.getPointX()%>;
					pY = <%=w.getPointY()%>;

					width = <%=w.getWidth()%> + "px";
					height = <%=w.getHeight()%> + "px";
					<%/* 이미 배치된 위젯을 불러오는 부분 */
					if (w.getRemote()) {%>
						obMouse.setWidget(num, name, tag, width, height);
						obMouse.setLocation(pX, pY);
					<%} 
					/* 유저가 내려받은 위젯을 리모콘에 추가한다 */
					%> addWidget(num, name, tag, width, height); <%
				}
				/*
				* Save를 했을 경우
				* obj의 내용이 없기 때문에 instanceof를 사용하지 못한다.
				* 그래서 날라오는 함수 이름으로 판단,
				*/
				
				else if (null != obj && obj.equals("/widgetSave.do"))
					{%> document.forms["widgetLoadForm"].submit(); <%}
			} // end of while%>
		setModeOn();

		}

		// 리모콘 폼에 대한 드래그 이벤트
		var x = 0
		var y = 0
		drag = 0
		move = 0
		window.document.onmousemove = mouseMove
		window.document.onmousedown = mouseDown
		window.document.onmouseup = mouseUp
		window.document.ondragstart = mouseStop

		function mouseUp() {
			move = 0
		}

		function mouseDown() {
			if (drag) {
				clickleft = window.event.x - parseInt(dragObj.style.left);
				clicktop = window.event.y - parseInt(dragObj.style.top);
				dragObj.style.zIndex += 1;
				move = 1;
			}
		}

		function mouseMove() {
			if (move) {
				dragObj.style.left = window.event.x - clickleft;
				dragObj.style.top = window.event.y - clicktop;
			}
		}

		function mouseStop() {
			window.event.returnValue = false;
		}
	</script>
</head>
<div id="board" onclick="pos(event);" style="width: 100%; height: 100%;">
	<script type="text/javascript">
		// 보드부분 마우스 클릭 이벤트
		function pos(event) {
			// 마우스에 객체가 없을경우 return;
			if (!obMouse.getHave())
				return;
			obMouse.setLocation(event.pageX, event.pageY);
		}
	</script>
</div>
<div id="Notice"
	style="width: 200px; height: 300px; position: absolute; left: 950px; top: 50px; z-index: 99999; filter: revealTrans(transition = 23, duration = 0.5) blendTrans(duration = 0.5);">
	<div id="moveDIV"
		style="width: 200px; height: 20px; background-color: black;"
		onmouseover="dragObj=Notice; drag=1;move=0" onmouseout="drag=0">
	</div>
	<table border="1" cellspacing="0" width="200" bordercolordark="white"
		bordercolorlight="#990000" bgcolor="white">
		<tr>
			<td width="173" bgcolor="white" align="center">
				<form name="form">
					<select name="widgetSel" onchange="javascript:getWidget()"
						style="width: 140px; margin: 10px;">
						<option value='' selected></OPTION>
						<script type="text/javascript">
							/**
							 * select control에 정보 집어넣는 부분
							 */


							function addWidget(id, name, tag, width, height) {

								var op = document.createElement("option");

								op.value = tag;
								op.text = name;
								op.id = id;
								op.width = width;
								op.height = height;

								
								document.form.widgetSel.options.add(op);
							}

							// select box 에서 위젯 선택시
							function getWidget() {

								var f = document.form.widgetSel;
								var idx = f.selectedIndex; //인덱스
								
								var widgetTag = f.options[idx].value; //인덱스 값
								var name = f.options[idx].text; // 인텍스 이름
								var id = f.options[idx].id // 인덱스 id
								var width = f.options[idx].width;
								var height = f.options[idx].height;

								if (widgetTag != '') {
									obMouse.setWidget(id, name, tag, width, height);
								}
							}
						</script>
					</select>
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<p align="center" class="page">
					<input type=button value="수정완료" onclick="setModeOn()"
						style="width: 140px; margin: 10px 0px 5px 0px;">
					<script type="text/javascript">
						function setModeOn() {
							// listBorad에 저장된 태그와
							// 좌표를 이용하여 div를 iframe으로 바꿈
							for ( var i in listBoard) {
								// div에서 좌표를 받아옴
								var div = listBoard[i];
								// 태그를 넣음.                          
								div.innerHTML = div.getObjectTag();
							}
						}
					</script>
					<br> <input type=button value="수정" onclick="setModeOff()"
						style="width: 140px; margin: 0px 0px 10px 0px;">
					<script type="text/javascript">
						function setModeOff() {
							// listBorad에 저장된 태그와
							// 좌표를 이용하여 div를 iframe으로 바꿈
							for ( var i in listBoard) {
								// div에서 좌표를 받아옴
								var div = listBoard[i];

								// 넣은거 빼는부분
								div.innerHTML = div.getName();
							}
						}
					</script>
					<input type=button value="로드" onclick="loadWidget()"
						style="width: 65px; margin: 0px 3px 10px 0px;">
					<script type="text/javascript">
						function loadWidget() {
							document.forms["widgetLoadForm"].submit();
						}
					</script>
					<input type=button value="저장" onclick="localSave()"
						style="width: 65px; margin: 0px 0px 10px 3px;">
					<script type="text/javascript">
						function localSave() {
							setModeOn();

							var name = [];
							var tag = [];
							var x = [];
							var y = [];

							var f = document.forms.widgetSaveForm;
							for ( var i in listBoard) {
								var div = listBoard[i];

								name.push(div.id);
								tag.push(div.innerHTML);
								x.push(div.style.left);
								y.push(div.style.top);

							} // end of for

							f.widgetNum.value = name;
							f.widgetPx.value = x;
							f.widgetPy.value = y;

							document.forms["widgetSaveForm"].submit();
						} // end of function
					</script>
			</td>
		</tr>
	</table>
</div>
<form id="widgetLoadForm" method="POST" ACTION="widgetLoad.do"></form>
<form id="widgetSaveForm" method="POST" ACTION="widgetSave.do">
	<input type="hidden" name="widgetNum" value="">
	<input type="hidden" name="widgetPx" value="">
	<input type="hidden" name="widgetPy" value="">
</form>
<script type="text/javascript">
	/*************************************************************************************/

	// 마우스 클래스
	var obMouse = new mouse();
	/**
	 * 마우스 클래스
	 * 리모콘에서 위젯을 선택하면
	 * 그 위젯값을 가지고 있다가 배치해줌
	 */
	function mouse() {
		// 마우스가 현제 객체를 가지고 있는지의 여부
		var have = 0;
		var widgetTag;
		var name;
		var id;


		// size
		var width, height;
		
		// widget point
		var px, py;

		this.setWidget = function(id, name, tag, width, height) {
			have = 1;
			this.id = id;
			this.name = name;
			this.widgetTag = tag;
			this.width = width;
			this.height = height;
		}

		this.setLocation = function(x, y) {
			// 중복확인
			for ( var i in listBoard) {
				var div = listBoard[i];
				if(div.id == this.id) {
					have = 0;
					alert("there is same widget in the board");
					return;
				}
			}
			
			// 초기화
			var div = document.createElement("div");
			var objectTag = this.widgetTag;
			var name = this.name;
			
			div.id = this.id;
			div.innerText = this.name;

			div.style.width = this.width;
			div.style.height = this.height;
			div.style.backgroundColor = "red";
			div.style.position = "absolute";
			div.style.left = x + "px";
			div.style.top = y + "px";

			div.getObjectTag = function() {
				return objectTag;
			};
			
			div.getName = function(){
				return name;
			};
			
			// 위잿의 드래그 이벤트
			div.onmousedown = function(event) {
				// offsetLeft,offsetTop : 부모요소를 기준한 좌표지점.
				px = event.clientX - event.srcElement.offsetLeft;
				py = event.clientY - event.srcElement.offsetTop;
				flag = 1;
			};

			div.onmouseup = function(event) {
				flag = 0;
			};

			div.onmousemove = function(event) {
				if (flag == 0)
					return;

				if (x > 0)
					event.srcElement.style.left = event.clientX - px + "px";
				if (y > 0)
					event.srcElement.style.top = event.clientY - py + "px";
			};

			// 삭제함 
			div.ondblclick = function(event) {
				
				// 보드의 리스트에서 삭제함.
				var index = listBoard.indexOf(this);
				listBoard.splice(index,1);
				
				// 실제로 삭제
				document.getElementById('board').removeChild(this);
			};
			
			document.getElementById('board').appendChild(div);

			// div를 배치했기 때문에 초기화
			have = 0;

			// 진짜 div 태그를 리스트에 저장
			listBoard.push(div);
		}

		this.getHave = function() {
			return have;
		}
	}
</script>
</BODY>
</HTML>
