package edu.pitt.servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import edu.pitt.portfoliocore.Portfolio;
import edu.pitt.portfoliocore.Project;
import edu.pitt.resumecore.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Processes creation of new Project object
 *
 * @author Mandy
 */
public class processProject extends HttpServlet {

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

            User user = (User) session.getAttribute("authenticatedUser");
            Portfolio portfolio = new Portfolio(user.getUserID(), 0);
            String schoolName = "";
            String degreeType = "";
            String major = "";
            String minor = "";
            Double gpa = null;
            String stringGPA = "";
            String graduationDate = "";

            stringGPA = request.getParameter("txtGPA");

            schoolName = request.getParameter("txtSchoolName");
            degreeType = request.getParameter("txtDegreeType");
            major = request.getParameter("txtMajorType");
            minor = request.getParameter("txtMinorType");
            gpa = Double.parseDouble(stringGPA);
            graduationDate = request.getParameter("txtGraduationDate");

            Project project = new Project(schoolName, degreeType, major, minor, gpa, graduationDate);
            portfolio.addProject(project);

            session.setAttribute("currentPortfolio", portfolio);

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
