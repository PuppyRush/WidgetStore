
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@page import="java.util.*"%>
<%@page import="javaBean.*"%>
<%@page import="page.VerifyPage"%>
<%@page import="property.enums.enumPage"%>


<%
	request.setCharacterEncoding("UTF-8");
	boolean isFailVerify;
	HashMap<String,Object> results =  VerifyPage.Verify(session.getId(), enumPage.DEVELOPER);
	if(!(boolean)results.get("isSuccessVerify")){
		isFailVerify =false;
		enumPage to = (enumPage)results.get("to");
		
		request.setAttribute("message",  (String)results.get("message"));
		request.setAttribute("messageKind", results.get("messageKind"));
	
		return;
		
	}else
		isFailVerify = true;

%>

<HTML>

<HEAD>
    <TITLE> Custom Widget </TITLE>
</HEAD>

<body onload="setWidgetList();">
    <script>
    // ���� ���� ����Ʈ
    var listBoard = [];

    // ���� ������ ��� �ҷ��ͼ� ���� �ٿ���� ������ �����ܿ� ������ 
    function setWidgetList() {
        /* �̹� ��ġ�� ������ �ҷ����� �κ� */
        obMouse.setWidget('Twice', '<iframe width=100% height=100% src="https://www.youtube.com/embed/hs3lIhlTRJM" frameborder="0" allowfullscreen></iframe>');
        obMouse.setLocation(100, 150);
        setModeOn();
       
        /* ���� �������� ������ �����ܿ� �߰��Ѵ� */
        addWidget('Choa', "<iframe width=100% height=100% src='https://www.youtube.com/embed/G9zL-A78oRg' frameborder='0' allowfullscreen></iframe>");
        addWidget('Teayoun', "<iframe width=100% height=100% src='https://www.youtube.com/embed/Ri6wvGjuoOg' frameborder='0' allowfullscreen></iframe>");
    }

    // ������ �� ���� �巡�� �̺�Ʈ
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
    <div id="board" onclick="pos(event);" style="width:100%; height:100%;">
        <script type="text/javascript">
        // ����κ� ���콺 Ŭ�� �̺�Ʈ
        function pos(event) {
            // ���콺�� ��ü�� ������� return;
            if (!obMouse.getHave()) return;
            obMouse.setLocation(event.pageX, event.pageY);
        }
        </script>
    </div>
    <div id="Notice" style="width:200px; height:300px; position:absolute; left:950px; top:50px; z-index:99999; filter:revealTrans(transition=23,duration=0.5) blendTrans(duration=0.5);">
        <div id="moveDIV" style="width:200px; height:20px; background-color:black;" onmouseover="dragObj=Notice; drag=1;move=0" onmouseout="drag=0">
        </div>
        <table border="1" cellspacing="0" width="200" bordercolordark="white" bordercolorlight="#990000" bgcolor="white">
            <tr>
                <td width="173" bgcolor="white" align="center">
                    <form name="form">
                        <select name="widgetSel" onchange="javascript:getWidget()" style="width:140px;margin:10px;">
                            <option value='' selected>
                            </OPTION>
                            <script type="text/javascript">
                            /**
                             * select control�� ���� ���ִ� �κ�
                             */
                            function addWidget(name, tag) {
                                var op = document.createElement("option");

                                op.value = tag;
                                op.text = name;

                                document.form.widgetSel.options.add(op);
                            }

                            // select box ���� ���� ���ý�
                            function getWidget() {

                                var f = document.form.widgetSel;
                                var idx = f.selectedIndex; //�ε���
                                var widgetTag = f.options[idx].value; //�ε��� ��
                                var name = f.options[idx].text; // ���ؽ� �̸�

                                if (widgetTag != '') {
                                    obMouse.setWidget(name, widgetTag);
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
                        <input type=button value="�����Ϸ�" onclick="setModeOn()" style="width:140px;margin:10px 0px 5px 0px;">
                        <script type="text/javascript">
                        function setModeOn() {
                            // listBorad�� ����� �±׿�
                            // ��ǥ�� �̿��Ͽ� div�� iframe���� �ٲ�
                            // 
                            // 
                            // Ű�� �� ��� �������� ���̰� �� ���̰� ������ ��
                            // 
                            // 
                            for (var i in listBoard) {
                                // div���� ��ǥ�� �޾ƿ�
                                var div = listBoard[i];

                                // ��� �ִºκ� 
                                div.innerHTML = div.getObjectTag();
                            }
                        }
                        </script>
                        <br>
                        <input type=button value="����" onclick="setModeOff()" style="width:140px;margin:0px 0px 10px 0px;">
                        <script type="text/javascript">
                        function setModeOff() {
                            // listBorad�� ����� �±׿�
                            // ��ǥ�� �̿��Ͽ� div�� iframe���� �ٲ�
                            for (var i in listBoard) {
                                // div���� ��ǥ�� �޾ƿ�
                                var div = listBoard[i];

                                // ������ ���ºκ�
                                div.innerHTML = div.id;
                                div.innerText = div.id + "widget (Click to remove)";
                            }
                        }
                        </script>
                        <input type=button value="��" onclick="" style="width:65px;margin:0px 3px 10px 0px;">
                        <script type="text/javascript">
                        // ���� �ϴ� �κ� �ۼ�
                        </script>
                        <input type=button value="����" onclick="localSave()" style="width:65px;margin:0px 0px 10px 3px;">
                        <!-- ������ ���� �ӽ� iframe -->
                        <script type="text/javascript">
                        function localSave() {
                            alert(document.documentElement.outerHTML);

                            var txt = document.getElementById("board").innerHTML;
                            var fileName = "C:\\text.txt";
                            var obFile = new ActiveXObject("Scripting.FileSystemObject");

                            obFile.CreateTextFile(filename, true);

                            var f = obFile.OpenTextFile(filename, 2, true);
                            alert(txt);
                            f.Write(txt);
                            f.Close();

                            /*
                            Some.document.open("outPut/html", "replace")
                            Some.document.write(document.documentElement.outerHTML)
                            Some.document.close()
                            Some.focus()
                            Some.document.execCommand('SaveAs');
                            */
                        }
                        </script>
                </td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
    
    //페이지 인증 성공 여부를 담는 변수 
    var isSuccessVerify = (Boolean)<%=isFailVerify%>;
    
    /*************************************************************************************/

    // ���콺 Ŭ����
    var obMouse = new mouse();
    /**
     * ���콺 Ŭ����
     * �����ܿ��� ������ �����ϸ�
     * �� �������� ������ �ִٰ� ��ġ����
     */
    function mouse() {
        // ���콺�� ���� ��ü�� ������ �ִ����� ����
        var have = 0;
        var widgetTag;
        var name;

        //��ǥ
        var px, py;

        this.setWidget = function(name, tag) {
            have = 1;
            this.name = name;
            this.widgetTag = tag;
        }

        this.setLocation = function(x, y) {
            // �ʱ�ȭ
            var div = document.createElement("div");
            var objectTag = this.widgetTag;

            div.id = this.name;
            div.innerText = div.id + "widget (����Ŭ���ϸ� ����)";
            div.style.width = "560px";
            div.style.height = "315px";
            div.style.backgroundColor = "red";
            div.style.position = "absolute";
            div.style.left = x + "px";
            div.style.top = y + "px";

            div.getObjectTag = function() {
                return objectTag;
            };
            // ������ �巡�� �̺�Ʈ
            div.onmousedown = function(event) {
                // offsetLeft,offsetTop : �θ��Ҹ� ������ ��ǥ����.
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

            // ������ 
            div.ondblclick = function(event) {
                document.getElementById('board').removeChild(this);
            };

            div.onclick = function(event){
                div.style.width = "780px";
                div.style.height = "515px";
            };

            document.getElementById('board').appendChild(div);

            // div�� ��ġ�߱� ������ �ʱ�ȭ
            have = 0;

            // ��¥ div �±׸� ����Ʈ�� ����
            listBoard.push(div);
        }

        this.getHave = function() {
            return have;
        }
    }
    </script>
</BODY>

</HTML>
