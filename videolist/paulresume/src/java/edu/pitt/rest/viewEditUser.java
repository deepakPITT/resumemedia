/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.rest;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.Security;
import edu.pitt.utilities.StringUtilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Gets user info for the viewEditUser section of site
 *
 * @author Jordan Feldman
 */
public class viewEditUser extends HttpServlet {

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
            String sql = "SELECT      lastName,     firstName,     login,     email,     IF(peoplesoftID IS NOT NULL,         'Student',         IF(employeeID IS NOT NULL,             'Staff',             IF(placeOfWork IS NOT NULL,                 'Employer',                 'N/A'))) AS userType,     userID, enabled FROM     rms.User U         LEFT JOIN     Student S ON U.userID = S.fk_userID         LEFT JOIN     Staff ST ON U.userID = ST.fk_userID         LEFT JOIN     Employer E ON U.userID = E.fk_userID";

            if (request.getParameter("userID") != null) {
                String userID = StringUtilities.cleanMySqlInsert(request.getParameter("userID"));
                sql += String.format(" WHERE userID   = '%s'", userID);
            }

            db = new DbUtilities();

            JSONArray ja;
            ja = null;
            try {
                ja = db.getJsonDataTable(sql);
            } catch (JSONException ex) {
                Logger.getLogger(viewEditUser.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.print(ja);
                System.out.println(sql);
            }

        } catch (SQLException ex) {
            Logger.getLogger(viewEditUser.class.getName()).log(Level.SEVERE, null, ex);
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
