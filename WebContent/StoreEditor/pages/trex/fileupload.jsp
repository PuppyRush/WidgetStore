<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.UUID" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
//주석
<%
if (ServletFileUpload.isMultipartContent(request)){
    ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
    //UTF-8 ì¸ì½ë© ì¤ì 
    uploadHandler.setHeaderEncoding("UTF-8");
    List<FileItem> items = uploadHandler.parseRequest(request);
    String realname = "";
    Long size = 0L;
    //ê° íëíê·¸ë¤ì FORë¬¸ì ì´ì©íì¬ ë¹êµë¥¼ í©ëë¤.
    for (FileItem item : items) {
        //image.html ìì file íê·¸ì name ëªì "image_file"ë¡ ì§ì í´ ì£¼ìì¼ë¯ë¡ 
        if(item.getFieldName().equals("image_file")) {
            if(item.getSize() > 0) {
                String ext = item.getName().substring(item.getName().lastIndexOf(".")+1);
                //íì¼ ê¸°ë³¸ê²½ë¡
                String defaultPath = request.getServletContext().getRealPath("/");
                //íì¼ ê¸°ë³¸ê²½ë¡ _ ìì¸ê²½ë¡
                String path = defaultPath + "upload" + File.separator;
                 
                File file = new File(path);
                 
                //ëë í ë¦¬ ì¡´ì¬íì§ ììê²½ì° ëë í ë¦¬ ìì±
                if(!file.exists()) {
                    file.mkdirs();
                }
                //ìë²ì ìë¡ë í  íì¼ëª(íê¸ë¬¸ì ë¡ ì¸í´ ìë³¸íì¼ì ì¬ë¦¬ì§ ìëê²ì´ ì¢ì)
                realname = UUID.randomUUID().toString() + "." + ext;
                size = item.getSize();
                ///////////////// ìë²ì íì¼ì°ê¸° ///////////////// 
                InputStream is = item.getInputStream();
                OutputStream os=new FileOutputStream(path + realname);
                int numRead;
                byte b[] = new byte[(int)item.getSize()];
                while((numRead = is.read(b,0,b.length)) != -1){
                    os.write(b,0,numRead);
                }
                if(is != null)  is.close();
                os.flush();
                os.close();
                ///////////////// ìë²ì íì¼ì°ê¸° /////////////////
            }
        }
    }
    response.setContentType("text/plain; charset=UTF-8");
    PrintWriter pw = response.getWriter();
    //json string ê°ì¼ë¡ callback
    //json ê°ì¼ë¡ ëê¸°ë íì ê°
    //imageurl, filename,filesize,imagealign
    pw.print("{\"imageurl\" : \"/upload/"+realname+"\",\"filename\":\""+realname+"\",\"filesize\": 600,\"imagealign\":\"C\"}");
    pw.flush();
    pw.close();
}
%>