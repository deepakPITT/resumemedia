/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.rest;

import edu.pitt.resumecore.User;
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.StringUtilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

/**
 * Validates User login
 *
 * @author jordanstevenfeldman
 */
public class userlistloginws extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        DbUtilities db = new DbUtilities();
        try (PrintWriter out = response.getWriter()) {
            String login = "";
            String password = "";
            if (request.getParameter("login") != null && request.getParameter("password") != null) {
                login = StringUtilities.cleanMySqlInsert(request.getParameter("login"));
                password = StringUtilities.cleanMySqlInsert(request.getParameter("password"));
            }

            if (!login.equals("") && !password.equals("")) {

                db = new DbUtilities();
                String sql = "SELECT * FROM rms.User ";
                sql += "WHERE login = '" + login + "' AND password = '" + password + "'";

                JSONArray fullUserList = new JSONArray();
                ResultSet rs = db.getResultSet(sql);
                while (rs.next()) {
                    User user = new User(rs.getString("userId"));
                    fullUserList.put(user.getUserAsJSON());
                }

                out.print(fullUserList.toString());
            }

        } catch (SQLException ex) {
            Logger.getLogger(userlistloginws.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeMySQLConnection();
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
