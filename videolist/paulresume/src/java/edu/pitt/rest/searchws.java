/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.rest;

import edu.pitt.utilities.DbUtilities;
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
 * Searches for a Resume given various params
 *
 * @author Jordan Feldman
 */
public class searchws extends HttpServlet {

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
        DbUtilities db = null;
        try (PrintWriter out = response.getWriter()) {
            String sql = "";

            if (request.getParameter("resumeID") != null) {
                String resumeID = StringUtilities.cleanMySqlInsert(request.getParameter("resumeID"));
                sql = String.format("SELECT lastName,firstName,resumeID,R.created,R.modified,resumeID FROM User U JOIN Resume R ON U.userID = R.fk_userID WHERE resumeID = %s", resumeID);

            }
            if (request.getParameter("field") != null) {
                String field = StringUtilities.cleanMySqlInsert(request.getParameter("field"));
                sql = String.format("SELECT lastName,firstName,resumeID,R.created,R.modified,field FROM User U JOIN Resume R ON U.userID = R.fk_userID JOIN ResumeEducation RE ON R.resumeID = RE.fk_resumeID JOIN Education E ON E.educationID = RE.fk_educationID WHERE field LIKE '%s%%'", field);
            }
            if (request.getParameter("gpa") != null) {
                String gpa = StringUtilities.cleanMySqlInsert(request.getParameter("gpa"));
                sql = String.format("SELECT lastName,firstName,resumeID,R.created,R.modified,gpa FROM User U JOIN Resume R ON U.userID = R.fk_userID JOIN ResumeEducation RE ON R.resumeID = RE.fk_resumeID JOIN Education E ON E.educationID = RE.fk_educationID WHERE gpa > %s", gpa);
            }
            if (request.getParameter("description") != null) {
                String description = StringUtilities.cleanMySqlInsert(request.getParameter("description"));
                sql = String.format("SELECT lastName,firstName,resumeID,R.created,R.modified,description FROM User U JOIN Resume R ON U.userID = R.fk_userID JOIN ResumeWorkExperience RWE ON R.resumeID = RWE.fk_resumeID JOIN WorkExperience WE ON WE.workExperienceID = RWE.fk_workExperienceID WHERE description LIKE '%%s%%'", description);
            }
            if (request.getParameter("lastName") != null) {
                String lastName = StringUtilities.cleanMySqlInsert(request.getParameter("lastName"));
                sql = String.format("SELECT lastName,firstName,resumeID,R.created,R.modified,lastName FROM User U JOIN Resume R ON U.userID = R.fk_userID WHERE lastName LIKE '%s%%'", lastName);
            }

            db = new DbUtilities();
            JSONArray ja;
            ja = null;
            try {
                ja = db.getJsonDataTable(sql);
            } catch (JSONException ex) {
                Logger.getLogger(searchws.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.print(ja);
                db.closeMySQLConnection();
            }

        } catch (SQLException ex) {
            Logger.getLogger(searchws.class.getName()).log(Level.SEVERE, null, ex);
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
