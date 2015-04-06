<%-- 
    Document   : viewEditUser
    Author     : Jordan
--%>

<%@page import="edu.pitt.utilities.Security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>
<div id="mainContainer" class="container theme-showcase" style="width:900px">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><strong>Add User</strong></h3>
        </div>
        <div class="panel-body">
            <div class="page-header">
                <form class="frmPersonalInfo" method="post" action="../process/processAddUser">
                    <h2 class="form-signin-heading">User Information</h2>
                    <h4>Fill out the required fields to create a user.</h4><br>
                    <div class="row">
                        <div class="col-xs-4">
                            <input type="text" class="form-control" id="txtFirstName" name="txtFirstName" placeholder="First Name"><br>
                            <input type="text" class="form-control" id="txtLastName" name="txtLastName" placeholder="Last Name"><br>
                            <input type="text" class="form-control" id="txtLogin" name="txtLogin" placeholder="Login*" required><br>
                            <input type="text" class="form-control" id="txtEmail" name="txtEmail" placeholder="Email*" required ><br>
                            <input type="text" class="form-control" id="txtPassword" name="txtPassword" placeholder="Password*" required><br>
                            Access Roles:<br>
                            <input type="checkbox" name="chkEmployer" id="chkEmployer" value="employer"> Employer<br>
                            <input type="checkbox" name="chkStaff" id="chkStaff"  value="Staff"> Staff<br>
                            <input type="checkbox" name="chkStudent" id="chkStudent"  value="Student"> Student<br>
                            <br>
                            User Status:<br>
                            <input type="radio" id="radStatusEnabled" name="radStatus" value="enabled"> Enabled<br>
                            <input type="radio" id="radStatusDisabled" name="radStatus" value="disabled"> Disabled
                            <br>
                        </div> 
                    </div>
                    <br>
                    <button class="btn btn-lg btn-default" type="submit" name="btnSubmitPersonalInfo" id="btnSubmitPersonalInfo">Submit Change</button>
                </form><br>
            </div>
        </div>
<%@ include file="../includes/footer.jsp" %>
