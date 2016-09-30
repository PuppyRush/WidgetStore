<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@page import="java.io.File, java.io.FilenameFilter, java.util.Arrays, property.enums.enumSystem "%>
    
    <%
	/**
			  * jQuery File Tree JSP Connector
			  * Version 1.0
			  * Copyright 2008 Joshua Gould
			  * 21 April 2008
			*/	
			
		    String dir = request.getParameter("dir");
    			
		    if(dir == null)
			  		return;
		    		
				
				if(dir.charAt(dir.length()-1) == '\\')
			    	dir = dir.substring(0, dir.length()-1) + "/";
				else if(dir.charAt(dir.length()-1) != '/')
				    dir += "/";
				
				
				dir = java.net.URLDecoder.decode(dir, "UTF-8");
					
				String originPath="";	//url로 시작할 fullPath
				String newPath = "";		//WebContent부터 시작하기 위한 임시변수
				if(!dir.contains("/home"))
					dir = enumSystem.PROJECT_PATH.toString()+dir;
				else{
					newPath = dir.substring(enumSystem.PROJECT_PATH.toString().length(),dir.length()-1 );
					originPath = enumSystem.URL_ROOT.toString() +"/"+ newPath+"/";
				}
				System.out.println(originPath+"\n"+newPath+"\n"+dir);

			    if (new File(dir).exists()) {
					String[] files = new File(dir).list(new FilenameFilter() {
					    public boolean accept(File dir, String name) {
							return name.charAt(0) != '.';
					    }
					});
					Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
					out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
					// All dirs
					for (String file : files) {
					    if (new File(dir, file).isDirectory()) {
							out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" + dir + file + "/\">"
								+ file + "</a></li>");
					    }
					}
					// All files
					for (String file : files) {
					    if (!new File(dir, file).isDirectory()) {
							int dotIndex = file.lastIndexOf('.');
							String ext = dotIndex > 0 ? file.substring(dotIndex + 1) : "";
							out.print("<li class=\"file ext_" + ext + "\"><a href=\"#\" rel=\"" + originPath + file + "\">"
								+ file + "</a></li>");
					    	}
					}
					out.print("</ul>");
			    }
			    
			    %>