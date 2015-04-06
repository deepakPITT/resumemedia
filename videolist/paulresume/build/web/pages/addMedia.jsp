<%-- 
    Document   : addMedia
    Created on : Mar 22, 2015, 5:59:59 PM
    Author     : Deepak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% //String userID= (request.getParameter("userID"));
   //String projectID =(request.getParameter("projectID")); 
   
  //session = request.getSession(true); 
  // session.setAttribute("userID", userID); 
   //session.setAttribute("projectID", projectID);
   
   String userID= (String) session.getAttribute("userID");
   
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
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
                
                    <form class="form-signin" method="post" action="../processAddMedia" enctype="multipart/form-data"> 
                        <h3 style="color:red">  The video format supported is ".ogv" , ".webm" ,".mp4" </h3>          
                        <%--     <input type="text" class="form-control" id="txtPortfolioNameName" name="txtProjectName" placeholder="Project Name" required autofocus=""><br> --%>
                                     
                               <label> Add Image/Video </label> <br/>
                               
                            <input type="file" class="form-control datepicker" id="txtStartDateDate" name="txtStartDate" ><br> 
                           <button class="btn btn-lg btn-default" type="submit" >Submit</button> 
                           <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = '../pages/addProject.jsp'">Back</button>
                    </form>
               
        </div>
    </div>
    </div>
    </body>
</html>

   
    </body>
</html>



<%--      <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = '#'">Add More Media </button> 
                     <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = 'AddProject.html'">Submit</button>  
                    <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = '../index.html'">Back</button> --%>
               <%--   <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = '#'">Add Images</button> <br>
                                             
                            <label> Add Videos </label>
                            <input type="text" size="12" class="form-control datepicker" id="youtube" name="txtStartDate" placeholder="Name " required><br>
                            <button class="btn btn-lg btn-default" type="button" onclick="window.location.href = '#'">Add Videos</button>
                  <%--          <input type="text" class="form-control datepicker" id="txtStartDateDate" name="txtEndDate" placeh older="Project End Date" required> --%>