<%@page import="edu.pitt.resumecore.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp" %>

<div id="mainContainer" class="container theme-showcase">
    <div class="panel panel-default">


        <div class="panel-heading">
            <h3 class="panel-title"><strong>Menu</strong></h3>
        </div>

        <div class="panel-body">
            <ul>
                <%
                    if (user.getRoles().contains("student")) {
                        out.print("<li><a href='listResumes.jsp?userID=" + user.getUserID() + "'>Your Resumes</a></li>");
                        out.print("<li><a href='personalInfo.jsp'>Enter a new Resume</a></li>");
                        out.print("<li><a href='listPortfolios.jsp?userID=" + user.getUserID() + "'>Your Portfolio</a></li>");
                        out.print("<li><a href='personalInfo.jsp'>Enter a new Portfolio</a></li>");
                        out.print("<li><a href='listProjects.jsp?userID=" + user.getUserID() + "'>Your Projects</a></li>");
                        out.print("<li><a href='addProject.jsp?userID="+ user.getUserID()+ "'>Enter a new Project</a></li>");
                        out.print("<li><a href='listResearch.jsp?userID=" + user.getUserID() + "'>Your Research Papers</a></li>");
                        out.print("<li><a href='personalInfo.jsp'>Enter a new Research Paper</a></li>");   
                        
                    } else if (user.getRoles().contains("staff")) {
                        out.print("<li><a href='listResumes.jsp'>List all Resumes</a></li>");
                        out.print("<li><a href='searchResumes.jsp'>Search through Resumes</a></li>");
                        out.print("<li><a href='listPortfolios.jsp'>List all Portfolios</a></li>");
                        out.print("<li><a href='searchPortfolios.jsp'>Search through Portfolios</a></li>");
                        out.print("<li><a href='listProjects.jsp'>List all Projects</a></li>");
                        out.print("<li><a href='searchProjects.jsp'>Search through Projects</a></li>"); 
                        out.print("<li><a href='listResearch.jsp'>List all Research Papers</a></li>");
                        out.print("<li><a href='searchResearch.jsp'>Search through Research Papers</a></li>");                         
                        out.print("<li><a href='listUsers.jsp'>List Users</a></li>");
                        out.print("<li><a href='addUser.jsp'>Add User</a></li>");
                    } else if (user.getRoles().contains("employer")) {
                        out.print("<li><a href='listResumes.jsp'>List all Resumes</a></li>");
                        out.print("<li><a href='searchResumes.jsp'>Search through Resumes</a></li>");
                        out.print("<li><a href='listPortfolios.jsp'>List all Portfolios</a></li>");
                        out.print("<li><a href='searchPortfolios.jsp'>Search through Portfolios</a></li>");
                        out.print("<li><a href='listProjects.jsp'>List all Projects</a></li>");
                        out.print("<li><a href='searchProjects.jsp'>Search through Projects</a></li>"); 
                        out.print("<li><a href='listResearch.jsp'>List all Research Papers</a></li>");
                        out.print("<li><a href='searchResearch.jsp'>Search through Research Papers</a></li>");                                                  
                    }
                %>
            </ul>
        </div>
    </div>

    <%@ include file="../includes/footer.jsp" %>