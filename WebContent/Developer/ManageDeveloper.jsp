<%@page import="javaBean.*"%>
<%@page import="java.util.*"%>
<%@page import="page.VerifyPage"%>
<%@page import="property.enums.*" %>


<%

	HashMap<String,Object> results =  VerifyPage.Verify(session.getId(), enumPage.DEVELOPER);
	if(!(boolean)results.get("isSuccessVerify")){
				
		enumPage to = (enumPage)results.get("to");
		
		request.setAttribute("message",  (String)results.get("message"));
		request.setAttribute("messageKind", results.get("messageKind"));
		request.setAttribute("from", enumPage.DEVELOPER.getString());
		
		response.sendRedirect(to.getString());
		return;
		
	}
		
		
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>My Widget Set</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../library/popup/style.css" rel="stylesheet">
    <link href="css/sb-admin.css" rel="stylesheet">
	
	
    <!-- Morris Charts CSS -->
    <link href="css/plugins/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">



    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="Developer/ManageDeveloper.jsp">My Develop Widget</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
				<li class="dropdown"> <a href="./Uploadform.jsp"><i class="glyphicon glyphicon-share"></i> WidgetUpload</a>
				</li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> [NICKNAME] <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div id="widgetList" class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="javascript:;"><i class="glyphicon glyphicon-console"></i> [WidgetName01]</a>
                    </li>
                    <li>
                        <a href="javascript:;"><i class="glyphicon glyphicon-console"></i> [WidgetName02]</a>
                    </li>
                    <li>
                        <a href="javascript:;"><i class="glyphicon glyphicon-console"></i> [WidgetName03]</a>
                    </li>
                    <li>
                        <a href="javascript:;"><i class="glyphicon glyphicon-console"></i> [WidgetName04]</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            [WidgetName] <small>[widget category]</small> <small>[update day]</small> 
                        </h1>

						<img class="img-responsive" src="http://placehold.it/500x300" alt="">
						<hr>
				
						<ol class="breadcrumb">
							<li class="active">
                                Repository : [Repository Name]
                            </li>
                            <li class="active">
                                <i class="fa fa-dashboard"></i> ./widgetCategory/widget.html
                            </li>
                        </ol>
						<div class="panel panel-default">
							<div class="panel-body">
								plz,comment line input this area</br>
								EX) This widget is view baseball score
							</div>
						</div>
						<div class="modal-footer">
                            <div>
								<a href="updateform.jsp?WidgetName=<%=WidgetName%>" class="btn btn-success">
								<i class="glyphicon glyphicon-ok"></i> Do Update</a>
								<button class="btn btn-danger" onclick="alert('Delete Success!');">
								<i class="glyphicon glyphicon-trash"></i> Delete</button>
                            </div>
		    		    </div>

                    </div>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

<div id=ohsnap></div>

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script>

	<script src="https://rawgithub.com/justindomingue/ohSnap/master/ohsnap.js" type="text/javascript" charset="utf-8"></script>

</body>

<script>
	
	var widgetsArray;
	
	window.onload=function(){
			
	<%
	try{
 		String uId = session.getId();
		Member m = Member.getMember(uId);
		String uName = m.getNickname();
		ArrayList<HashMap<String,String>> widgets = ManageWidget.getUserWidgets(uId);

		if(widgets.size()>0){
			%>
			
			widgetsArray = new Array("<%=widgets.size()%>") ;
			for(int i=0 ; widgetsArray.length ; i++){
					widgetArray[i] = new Array(4);
					widgetArray
			}			
			<%
			}
		
		Iterator<HashMap<String,String>> it = widgets.iterator();
		while(it.hasNext()){
			
			HashMap<String,String> widget = it.next();
			String wName = widget.get("wName");
			String wContents= widget.get("wContents");
			String updatedDate= widget.get("updatedDate");
			String repImagePath = widget.get("repImagePath");
			
			//꺼내올때마다 js배열에 저장하여 li에 append한다
		
		
		}
	}catch(Throwable e){
		e.printStackTrace();
	}

	%>
		
		
	}
</script>

</html>

