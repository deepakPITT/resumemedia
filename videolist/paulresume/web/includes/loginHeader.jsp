<%-- 
    Document   : loginHeader
    Created on : Dec 4, 2014, 7:17:28 PM
    Author     : Mandy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Resume Management System </title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">

        <!--Stylesheet for Resume-->
        <link href="css/style.css" rel="stylesheet">
        <link href="css/custom.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" type="text/css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">

        <!--jquery scripts -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>


        <!--Scripts for Data Binding -->
        <script src="js/knockout-3.2.0.js"></script>
        <script src="js/knockout.mapping.js"></script>
    </head>


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
                        <!--                        <li><a href="personalInfo.jsp">Create Resume</a></li>
                                                <li><a href="listResumes.jsp">View/Edit Resumes</a></li>-->
                    </ul>


                    <!--                    Logout Button
                                        <ul class="nav navbar-nav navbar-right">
                                            <li>
                                                <ul class="nav pull-right">
                                                    <li> <a class="btn btn-default navbar-btn" href="logout">Logout</a></li>
                                                </ul>
                                            </li>
                                        </ul>-->
                </div><!--/.nav-collapse -->
            </div><!--/.container-->
        </div><!--navbar-->



