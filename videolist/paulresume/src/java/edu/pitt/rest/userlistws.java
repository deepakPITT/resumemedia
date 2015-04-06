/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.rest;

import edu.pitt.resumecore.User;
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.Security;
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
 * Searches for User info based on given parameters
 *
 * @author Jordan Feldman
 */
public class userlistws extends HttpServlet {

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
        if (Security.checkHijackedSession(request.getSession(false), request)) {
            response.sendRedirect("rest/index.jsp");
        }
        try (PrintWriter out = response.getWriter()) {

            String lastName = "";
            String userID = "";
            String field = "";
            String gpa = "";
            String jobDescription = "";

            String sql = "";

            if (request.getParameter("lastName") != null) {
                lastName = StringUtilities.cleanMySqlInsert(request.getParameter("lastName"));
            }
            if (request.getParameter("userID") != null) {
                userID = StringUtilities.cleanMySqlInsert(request.getParameter("userID"));
            }
            if (request.getParameter("field") != null) {
                field = StringUtilities.cleanMySqlInsert(request.getParameter("field"));
            }
            if (request.getParameter("gpa") != null) {
                gpa = StringUtilities.cleanMySqlInsert(request.getParameter("gpa"));
            }
            if (request.getParameter("jobDescription") != null) {
                jobDescription = StringUtilities.cleanMySqlInsert(request.getParameter("jobDescription"));
            }

            if (!lastName.equals("")) {

                sql = "SELECT * FROM rms.User ";
                sql += String.format("WHERE lastName LIKE '%s%%'", lastName);

                System.out.println(sql);

            } else if (!userID.equals("")) {

                if (userID.equalsIgnoreCase("null")) {
                    sql = "SELECT fk_userID AS userID FROM rms.Student;";
                } else {
                    sql = "SELECT * FROM rms.User ";
                    sql += String.format("WHERE userID = '%s'", userID);
                }

                System.out.println(sql);

            } else if (!field.equals("")) {
                sql = "SELECT fk_userID AS userID FROM rms.Education JOIN rms.ResumeEducation ON educationID = fk_educationID JOIN rms.Resume ON resumeID = fk_resumeID ";
                sql += String.format("WHERE field COLLATE UTF8_GENERAL_CI LIKE '%s%%'", field);

                System.out.println(sql);

            } else if (!gpa.equals("")) {

                sql = "SELECT fk_userID AS userID FROM rms.Education JOIN rms.ResumeEducation ON educationID = fk_educationID JOIN rms.Resume ON resumeID = fk_resumeID ";
                sql += String.format("WHERE gpa > '%s'", gpa);

                System.out.println(sql);

            } else if (!jobDescription.equals("")) {

                sql = "SELECT fk_userID AS userID FROM rms.WorkExperience JOIN rms.ResumeWorkExperience ON workExperienceID = fk_workExperienceID JOIN rms.Resume ON resumeID = fk_resumeID ";
                sql += String.format("WHERE position COLLATE UTF8_GENERAL_CI LIKE '%s%%'", jobDescription);

                System.out.println(sql);
            } else {
                sql = "SELECT resumeID, rating, fk_userID as userID FROM rms.Resume";
            }

            JSONArray fullUserList = new JSONArray();
            ResultSet rs = db.getResultSet(sql);
            while (rs.next()) {
                User user = new User(rs.getString("userID"));
                fullUserList.put(user.getUserAsJSON());
            }
            out.print(fullUserList.toString());

        } catch (SQLException ex) {
            Logger.getLogger(userlistws.class.getName()).log(Level.SEVERE, null, ex);
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
