/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.servlets;

import edu.pitt.resumecore.Address;
import edu.pitt.resumecore.User;
import edu.pitt.utilities.Security;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Adds a user to the DB
 *
 * @author Jordan
 */
public class processAddUser extends HttpServlet {

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
                String firstName = "";
                String lastName = "";
                String middleInitial = "";
                String login = "";
                String password = "";
                ArrayList<Address> addresses = new ArrayList();
                String email = "";
                String phoneNumber = "";

                if (!request.getParameter("txtFirstName").isEmpty()) {
                    firstName = request.getParameter("txtFirstName");
                }

                if (!request.getParameter("txtLastName").isEmpty()) {
                    lastName = request.getParameter("txtLastName");
                }

                if (!request.getParameter("txtLogin").isEmpty()) {
                    login = request.getParameter("txtLogin");
                }

                if (!request.getParameter("txtEmail").isEmpty()) {
                    email = request.getParameter("txtEmail");
                }

                if (!request.getParameter("txtPassword").isEmpty()) {
                    password = request.getParameter("txtPassword");
                }

                User user = new User(firstName, lastName, middleInitial, login, password, addresses, email, phoneNumber);

                if (request.getParameter("chkEmployer") != null) {
                    user.setRoleEmployer("", "");
                }

                if (request.getParameter("chkStaff") != null) {
                    user.setRoleStaff("", "");
                }

                if (request.getParameter("chkStudent") != null) {
                    user.setRoleStudent("", new Date());
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
