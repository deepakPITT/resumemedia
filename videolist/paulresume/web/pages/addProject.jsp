
<%@page import="java.lang.String"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String userID= (request.getParameter("userID"));           // receiving userID from menu.jsp and creating session 
   //String projectID =(request.getParameter("projectID"));   // to use it in other pages
   
  session = request.getSession(true); 
   session.setAttribute("userID", userID); 
   //session.setAttribute("projectID", projectID);

%>



<!DOCTYPE html>
  
<html lang="en">
    
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Resume Management System </title>
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">

        <!--Style-sheet for Resume-->
        <link href="../css/style.css" rel="stylesheet">
        <link href="../css/custom.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" type="text/css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">

        <!--jquery scripts -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>


        <!--Scripts for Data Binding -->
        <script src="js/knockout-3.2.0.js"></script>
        <script src="js/knockout.mapping.js"></script>
    
    <body>

        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"><span class="sr-only">Toggle navigation</span></button> <a class="navbar-brand" href="#">Resume Management System</a>
                </div> 
                <div class="navbar-collapse collapse">

                    <!--Main Navigation-->
                    <ul class="nav navbar-nav" id="mainNavigation">
                        <li><a href="menu.jsp">Home</a></li>
                        <li><a href='pages/NewPortfolio.html'>Create Portfolio</a></li>
                        <li><a href='pages/portfolioInfo.html'>View/Edit Portfolio</a></li>
                    </ul>
                    <!--Logout Button-->
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <ul class="nav pull-right">
                                <li> <a class="btn btn-default navbar-btn" href="../logout">Logout</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div><!--/.container-->
        </div><!--navbar-->
<div id="mainContainer" class="container theme-showcase">
    <div class="panel panel-default">
        <div class="panel-body">
            <div id="page-header">
                <h2 class="form-signin-heading">Project</h2><br>
                    <form class="form-signin" method="post" action="../process/processPortfolio">
                                    <input type="text" class="form-control" id="txtPortfolioNameName" name="txtProjectName" placeholder="Project Name" required autofocus=""><br>
                                    <label> Project Description</label>
                                    <textarea class="form-control" rows="10" id="txtProjectDescription"></textarea><br>
                                    <input type="text" class="form-control datepicker" id="txtStartDateDate" name="txtStartDate" placeholder="Project Start Date" required><br>
                                    <input type="text" class="form-control datepicker" id="txtStartDateDate" name="txtEndDate" placeholder="Project End Date" required>
                                
                                   
                                       <a href="addMedia.jsp"> Media Project </a>
                                       <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = '../pages/menu.jsp'">Back</button>
                                 </form>            
                <div>
               
                <br>
                </div>
        </div>
    </div>
    </div>
    </body>
</html>

  
    
     <!--    <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = 'addMedia.jsp?userID="+userID+"'">Project Media</button> 
                    <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = 'AddProject.html'">Submit</button>
                    <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = '../index.html'">Back</button> -->
                 <!--/. <a href="addMedia.jsp?userID= <%--<% out.print(request.getParameter("userID"));%>" --%>   Media Project </a> -->
                                   
    
    
    
    
    
    
    

