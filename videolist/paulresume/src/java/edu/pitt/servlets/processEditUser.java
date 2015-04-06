/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.servlets;

import edu.pitt.resumecore.User;
import edu.pitt.utilities.Security;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Processes the changes made to a User
 *
 * @author Jordan
 */
public class processEditUser extends HttpServlet {

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
            HttpSession session = request.getSession(true);

            if (Security.checkHijackedSession(session, request)) {
                response.sendRedirect("index.jsp");
            } else {

                System.out.println(session.getAttribute("selectedUserID"));

                User user = new User((String) session.getAttribute("selectedUserID"));

                if (!request.getParameter("txtFirstName").isEmpty()) {
                    String firstName = request.getParameter("txtFirstName");
                    user.setFirstName(firstName);
                }

                if (!request.getParameter("txtLastName").isEmpty()) {
                    String lastName = request.getParameter("txtLastName");
                    user.setLastName(lastName);

                }

                if (!request.getParameter("txtLogin").isEmpty()) {
                    String login = request.getParameter("txtLogin");
                    user.setLogin(login);
                }

                if (!request.getParameter("txtEmail").isEmpty()) {
                    String email = request.getParameter("txtEmail");
                    user.setEmail(email);
                }

                if (!request.getParameter("txtPassword").isEmpty()) {
                    String password = request.getParameter("txtPassword");
                    user.setPassword(password);
                }

                if (request.getParameter("chkEmployer") != null) {
                    user.setRoleEmployer("", "");
                } else {
                    user.removeRole("employer");
                }

                if (request.getParameter("chkStaff") != null) {
                    user.setRoleStaff("", "");
                } else {
                    user.removeRole("staff");
                }

                if (request.getParameter("chkStudent") != null) {
                    user.setRoleStudent("", new Date());
                } else {
                    user.removeRole("student");
                }

                if (request.getParameter("radStatus") != null) {
                    String status = request.getParameter("radStatus");
                    System.out.println(status);
                    if (status.equals("enabled")) {
                        user.setEnabled();
                    } else if (status.equals("disabled")) {
                        user.setDisabled();
                    }
                }

                response.sendRedirect("../pages/listUsers.jsp");

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

}
