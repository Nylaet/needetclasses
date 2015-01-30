/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pusku.smartmonit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Panker_Work
 */
public class clientBase extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            out.println(getMainPage());
            String function=request.getParameter("clientBaseHeadButton");
            switch(function){
                case "search":search();break;
                case "new_client":newClient();break;
                case "new_station":newStation();break;
                case "payForm": payForm();break;
            }
        }
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

    private void search() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void newClient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void newStation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void payForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getMainPage(){
        String s="<html>\n" +
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
                    "				<form method=\"POST\" action=\"monitoring\">\n" +
                    "					<p><button name=\"B3\" style=\"width:100%;height:100%\">Мониторинг</button></p>\n" +
                    "				</form>\n" +
                    "				</td>\n" +
                    "				<td align=\"center\">\n" +
                    "				<form method=\"POST\" action=\"clientBase\">\n" +
                    "					<p><button name=\"B3\" style=\"width:100%;height:100%\">База клиентов</button></p>\n" +
                    "				</form>\n" +
                    "				</td>\n" +
                    "				<td align=\"center\">\n" +
                    "				<form method=\"POST\" action=\"requestBase\">\n" +
                    "					<p><button name=\"B3\" style=\"width:100%;height:100%\">База заявок</button></p>\n" +
                    "				</form>\n" +
                    "				</td>\n" +
                    "			</tr>\n" +
                    "		</table>\n" +
                    "		</td>\n" +
                    "	</tr>\n" +
                    "	<tr>\n" +
                    "		<td height=\"80%\">"+
                    //add code here
                    "<table border=\"0\" width=\"100%\" height=\"100%\">\n" +
"                   <tr>\n" +
"                       <td>\n" +
"                           <table border=\"0\" width=\"100%\" height=\"100%\">\n" +
"                       	<tr>\n" +
"				<td>\n" +
"				<table border=\"0\" width=\"100%\">\n" +
"					<tr>\n" +
"						<td>\n" +
"						<form action=\"search\" method=\"POST\">\n" +
"							<p><button style=\"width:100%;height:100%\" name=\"B3\">\n" +
"							Поиск</button></p>\n" +
"						</form>\n" +
"						</td>\n" +
"						<td>\n" +
"						<form action=\"new_client\" method=\"POST\">\n" +
"							<p><button style=\"width:100%;height:100%\" name=\"B3\">\n" +
"							Новый клиент</button></p>\n" +
"						</form>\n" +
"						</td>\n" +
"						<td>\n" +
"						<form action=\"new_station\" method=\"POST\">\n" +
"							<p><button style=\"width:100%;height:100%\" name=\"B3\">\n" +
"							Новая Узловая станция</button></p>\n" +
"						</form>\n" +
"						</td>\n" +
"						<td>\n" +
"						<form action=\"payForm\" method=\"POST\">\n" +
"							<p><button style=\"width:100%;height:100%\" name=\"B3\">\n" +
"							Изменение баланса клиента</button></p>\n" +
"						</form>\n" +
"						</td>\n" +
"					</tr>\n" +
"				</table>\n" +
"				</td>\n" +
"                               </tr>\n" +
"                               <tr height=\"80%\">\n" +
"				<td>"+
                                //Входная строка
                                
                                //
                                "</td>\n" +
"                               </tr>\n" +
"                               <tr height=\"20%\">\n" +
"				<td>&nbsp;</td>\n" +
"                       	</tr>\n" +
"                           </table>\n" +
"                       </td>\n" +
"                       </tr>\n" +
"                   </table>"+
                    //
                
                    "&nbsp;</td>\n" +
                    "	</tr>\n" +
                    "	<tr>\n" +
                    "		<td height=\"10%\">\n" +
                    "		<table border=\"0\" width=\"100%\">\n" +
                    "			<tr>\n" +
                    "				<td>Пользователь:"+
                    Index.users.userNow.name+
                    "  Последний визит: "+Index.users.userNow.lastvisit.get(Calendar.HOUR)+":"+
                    Index.users.userNow.lastvisit.get(Calendar.MINUTE)+" "+
                    Index.users.userNow.lastvisit.get(Calendar.DAY_OF_MONTH)+"."+
                    Index.users.userNow.lastvisit.get(Calendar.MONTH)+"."+
                    Index.users.userNow.lastvisit.get(Calendar.YEAR)+
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
        return s;
    }
}
