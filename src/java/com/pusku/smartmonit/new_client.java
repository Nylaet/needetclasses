/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Готовый код. выводит форму ввода нового клиента в базу.
package com.pusku.smartmonit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFrame;

/**
 *
 * @author Panker_Work
 */
public class new_client extends HttpServlet {
    
    
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
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            new NewClient().setVisible(true);
            //out.println(startPage());
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

    private String startPage(){
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
                                "<table border=\"0\" width=\"100%\">\n" +
"	<tr>\n" +
"		<td>\n" +
"		<form method=\"POST\" action=\"add_client\" >\n" +
"			<table border=\"1\" width=\"100%\" bgcolor=\"#C0C0C0\">\n" +
"				<tr>\n" +
"					<td width=\"10%\">Фамилия</td>\n" +
"					<td width=\"40%\"><input type=\"text\" name=\"lastName\" size=\"38\"></td>\n" +
"					<td width=\"10%\">Название станции</td>\n" +
"					<td><input type=\"text\" name=\"stationName\" size=\"38\"></td>\n" +
"				</tr>\n" +
"				<tr>\n" +
"					<td width=\"10%\">Имя</td>\n" +
"					<td width=\"40%\"><input type=\"text\" name=\"firstName\" size=\"38\"></td>\n" +
"					<td width=\"10%\">Тип станции</td>\n" +
"					<td><select size=\"1\" name=\"stationType\">\n" +
"					<option selected value=\"NanoLoco M5\">NanoLoco M5</option>\n" +
"					</select></td>\n" +
"				</tr>\n" +
"				<tr>\n" +
"					<td width=\"10%\">Отчество</td>\n" +
"					<td width=\"207\"><input type=\"text\" name=\"surrName\" size=\"38\"></td>\n" +
"					<td width=\"10%\">Адрес узла</td>\n" +
"					<td><input type=\"text\" name=\"host\" size=\"38\"></td>\n" +
"				</tr>\n" +
"				<tr>\n" +
"					<td width=\"10%\"><span lang=\"en-us\">e-mail</span></td>\n" +
"					<td width=\"207\"><input type=\"text\" name=\"email\" size=\"38\"></td>\n" +
"					<td width=\"10%\">Тарифный пакет</td>\n" +
"					<td><select size=\"1\" name=\"tarif\">\n" +
                                        new TarifGrid().ToString()
                                        +
"					</select></td>\n" +
"				</tr>\n" +
"				<tr>\n" +
"					<td width=\"10%\">Город(село)</td>\n" +
"					<td width=\"207\"><input type=\"text\" name=\"site\" size=\"38\"></td>\n" +
"					<td width=\"10%\">&nbsp;</td>\n" +
"					<td>&nbsp;</td>\n" +
"				</tr>\n" +
"				<tr>\n" +
"					<td width=\"10%\">Улица</td>\n" +
"					<td width=\"207\"><input type=\"text\" name=\"street\" size=\"38\"></td>\n" +
"					<td width=\"10%\">&nbsp;</td>\n" +
"					<td>&nbsp;</td>\n" +
"				</tr>\n" +
"				<tr>\n" +
"					<td width=\"10%\">Дом</td>\n" +
"					<td width=\"207\"><input type=\"text\" name=\"house\" size=\"38\"></td>\n" +
"					<td width=\"10%\">&nbsp;</td>\n" +
"					<td>&nbsp;</td>\n" +
"				</tr>\n" +
"				<tr>\n" +
"					<td width=\"10%\">Телефон</td>\n" +
"					<td width=\"207\"><input type=\"text\" name=\"phone\" size=\"38\"></td>\n" +
"					<td width=\"10%\">&nbsp;</td>\n" +
"					<td>&nbsp;</td>\n" +
"				</tr>\n" +
"			</table>\n" +
"			<p align=\"right\"><input type=\"submit\" value=\"Отправить\" name=\"Send\"></p>\n" +
"		</form>\n" +
"		</td>\n" +
"	</tr>\n" +
"</table>"+
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

