/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pusku.smartmonit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Panker_Work
 */
public class Index extends HttpServlet {
    public static UserDB users;
    PrintWriter out;
    public Index() throws IOException {
        this.users = new UserDB();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        Enumeration req=request.getParameterNames();
        while(req.hasMoreElements()){
            String nameParam=(String)req.nextElement();
            System.out.println(nameParam);
            if(nameParam.equals("login"))userCheck(request);
            else if(nameParam.contains("/monitoring"))monitoring(request);
            else if(nameParam.contains("/clientBase"))clientBase(request);
            else if(nameParam.contains("/requestBase"))requestBase(request);
            else out.println("<html>\n" +
                "\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Language\" content=\"en-us\">\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\n" +
                "<title>&#1053;&#1086;&#1074;&#1072;&#1103; &#1089;&#1090;&#1088;&#1072;&#1085;&#1080;&#1094;&#1072; 3</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "<p><span lang=\"ru\">&#1059;&#1096;&#1083;&#1072; &#1074; &#1084;&#1072;&#1075;&#1072;&#1079;&#1080;&#1085;. &#1074;&#1077;&#1088;&#1085;&#1091;&#1089;&#1100; &#1085;&#1077; &#1089;&#1082;&#1086;&#1088;&#1086;....</span></p>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>");
        }
        
        
    
    }
    
    
    
    private String getMainPage(String content){
        String main="<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Language\" content=\"ru\">\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "<title>Wellcome</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "\n" +
                    "<table border=\"0\" width=\"100%\" height=\"100%\">\n" +
                    "	<tr>\n" +
                    "		<td height=\"10%\">\n" +
                    "		<table border=\"0\" width=\"100%\">\n" +
                    "			<tr>\n" +
                    "				<td align=\"center\">\n" +
                    "				<form method=\"POST\" action=\"Index\" value=\"/monitoring\">\n" +
                    "					<p><button name=\"/monitoring\" style=\"width:100%;height:100%\">Мониторинг</button></p>\n" +
                    "				</form>\n" +
                    "				</td>\n" +
                    "				<td align=\"center\">\n" +
                    "				<form method=\"POST\" action=\"Index\" value=\"/clientBase\">\n" +
                    "					<p><button name=\"B3\" style=\"width:100%;height:100%\">База клиентов</button></p>\n" +
                    "				</form>\n" +
                    "				</td>\n" +
                    "				<td align=\"center\">\n" +
                    "				<form method=\"POST\" action=\"Index\" value=\"/requestBase\">\n" +
                    "					<p><button name=\"B3\" style=\"width:100%;height:100%\">База заявок</button></p>\n" +
                    "				</form>\n" +
                    "				</td>\n" +
                    "			</tr>\n" +
                    "		</table>\n" +
                    "		</td>\n" +
                    "	</tr>\n" +
                    "	<tr>\n" +
                    "		<td height=\"80%\">"+
                    /*
                    Сюда добавлять код.
                    */
                    content+
                    "</td>\n" +
                    "	</tr>\n" +
                    "	<tr>\n" +
                    "		<td height=\"10%\">\n" +
                    "		<table border=\"0\" width=\"100%\">\n" +
                    "			<tr>\n" +
                    "				<td>Пользователь:"+
                    users.userNow.name+
                    "  Последний визит: "+users.userNow.lastvisit.get(Calendar.HOUR)+":"+
                    users.userNow.lastvisit.get(Calendar.MINUTE)+" "+
                    users.userNow.lastvisit.get(Calendar.DAY_OF_MONTH)+"."+
                    users.userNow.lastvisit.get(Calendar.MONTH)+"."+
                    users.userNow.lastvisit.get(Calendar.YEAR)+
                    "</td>\n" +
                    "				<td>\n" +
                    "				<p align=\"right\"><span lang=\"en-us\">\n" +
                    "				<a href=\"mailto:nylaet@gmail.com?subject=Мониторинг и База клиентов\">\n" +
                    "				nylaet@gmail.com</a></span></td>\n" +
                    "			</tr>\n" +
                    "		</table>\n" +
                    "		</td>\n" +
                    "	</tr>\n" +
                    "</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";
    return(main);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getErrorPage() {
        String ret="<html>\n" +
"\n" +
"<head>\n" +
"<meta http-equiv=\"Content-Language\" content=\"en-us\">\n" +
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"<meta http-equiv=\"refresh\" content=\"3; url=http://localhost:8084/Smartsys/\">\n" +
"<title>SmartSystems</title>\n" +
"</head>\n" +
"\n" +
"<body>\n" +
"\n" +
"<table border=\"0\" width=\"100%\" height=\"100%\">\n" +
"	<tr>\n" +
"		<td>\n" +
"		<p align=\"center\"><i><span lang=\"ru\"><font size=\"5\" color=\"#FF0000\">Не \n" +
"		верные имя пользователя или пароль!</font></span></i></td>\n" +
"	</tr>\n" +
"</table>\n" +
"\n" +
"</body>\n" +
"\n" +
"</html>";
return(ret);
    }

    private void userCheck(HttpServletRequest request) throws IOException {
        String name=request.getParameter("login");
        String password=request.getParameter("password");
        System.out.println(getServletContext ());
        if(users.checkUser(name, password)){
            out.println(getMainPage(""));
        }
        else{
            out.println(getErrorPage());
        }
    }

    private void monitoring(HttpServletRequest request) {
        String content=
"<table border=\"0\" width=\"100%\">\n" +
"	<tr>\n" +
"		<td width=\"33%\">\n" +
"		<table border=\"0\" width=\"100%\">\n" +
"			<tr>\n" +
"				<td>\n" +
"				<p align=\"center\"><span lang=\"ru\">Узловые</span></td>\n" +
"			</tr>"
              ,baseStations="",clientStations="",otherProblem="";
        ClientDataBase cdb=null;
        cdb=cdb.getClientDataBase();
        for (Client client : cdb.clients) {
            if(!client.cCon.isConnect()){
                if(!client.cCon.isClientType())baseStations+="<tr align=center ><td>"+client.getLowForm("FF3300")+"</td></tr>";
                else clientStations+="<tr align=center ><td>"+client.getLowForm("FF6600")+"</td></tr>";
            }
            else if(client.cCon.getLinkQuality()<40)otherProblem+="<tr align=center ><td>"+client.getLowForm("FFCC33")+"</td></tr>";
        }
        content+=baseStations+"</table>\n" +"		</td>"+
                "<td width=\"33%\">\n" +
"		<table border=\"0\" width=\"100%\">\n" +
"			<tr>\n" +
"				<td>\n" +
"				<p align=\"center\"><span lang=\"ru\">Клиентские</span></td>\n" +
"			</tr>"+
               clientStations+"</table>\n" +"		</td>"+
               "<td width=\"33%\">\n" +
"		<table border=\"0\" width=\"100%\">\n" +
"			<tr>\n" +
"				<td>\n" +
"				<p align=\"center\"><span lang=\"ru\">Прочие проблемы</span></td>\n" +
"			</tr>"+ 
                otherProblem+"</table>\n" +"		</td>"+
                "</table>\n" +
"		</td>\n" +
"	</tr>\n" +
"</table>";
                
        out.println(getMainPage(content));
    }

    private void clientBase(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void requestBase(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
