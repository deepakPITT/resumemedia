<%-- 
    Document   : education
    Created on : Apr 15, 2014, 9:07:58 PM
    Author     : Mandy
--%>

<%@page import="edu.pitt.utilities.StringUtilities"%>
<%@page import="edu.pitt.utilities.Security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>
<script src="../js/educationFunctions.js"></script>

<div id="mainContainer" class="container theme-showcase" style="width:900px">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><strong>Create Your Resume</strong></h3>
        </div>
        <div class="panel-body">
            <div class="page-header">
                <h2 class="form-signin-heading">Education</h2><br>
                <div id="eduForm">
                </div>
                <div id="additionalEducation">
                </div><!--End of Div additionalEducation-->
                <br>
                <button class="btn btn-lg btn-default" type="button" id="btnEducation" onclick="window.location.href = 'personalInfo.jsp'">Back</button>
                <button class="btn btn-lg btn-default" type="button" id="btnAddEducation">Add Education</button>
                <button class="btn btn-lg btn-default" type="submit" onclick="submitEdu();" id="btnSubmit" name="btnSubmit">Next</button>
                <br>
            </div><!--End of div page-header-->
        </div><!-- /container -->
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>