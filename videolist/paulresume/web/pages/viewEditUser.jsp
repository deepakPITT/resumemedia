<%-- 
    Document   : viewEditUser
    Author     : Jordan
--%>

<%@page import="edu.pitt.utilities.Security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userID = request.getParameter("userID");
    session.setAttribute("selectedUserID", userID);
%>
<%@ include file="../includes/header.jsp" %>

<div class="panel-heading">
    <h3 class="panel-title"><strong>View/Edit</strong></h3>
</div>

<div class="panel-body">

    <div class="page-header">
        <form class="frmPersonalInfo" method="post" action="../process/processEditUser">
            <h2 class="form-signin-heading">User Information</h2>
            <h4>To modify a field please indicate the change on the right side.</h4><br>
            <div class="row">
                <div class="col-xs-4">
                    <input type="text" class="form-control" id="txtFirstNameDis" name="txtFirstNameDis" placeholder="First Name" disabled><br>
                    <input type="text" class="form-control" id="txtLastNameDis" name="txtLastNameDis" placeholder="Last Name" disabled><br>
                    <input type="text" class="form-control" id="txtLoginDis" name="txtLoginDis" placeholder="Login" disabled ><br>
                    <input type="text" class="form-control" id="txtEmailDis" name="txtEmailDis" placeholder="Email" disabled><br>
                    <input type="text" class="form-control" id="txtPasswordDis" name="txtPasswordDis" placeholder="Password" disabled ><br>
                    Access Roles:<br>
                    <input type="checkbox" name="chkEmployer" id="chkEmployerDis" value="employer" disabled> Employer<br>
                    <input type="checkbox" name="chkStaff" id="chkStaffDis"  value="Staff" disabled> Staff<br>
                    <input type="checkbox" name="chkStudent" id="chkStudentDis"  value="Student" disabled> Student<br>

                    <br>

                    User Status:<br>
                    <input type="radio" id="radStatusEnabledDis" name="radStatusDis" value="enabled" disabled> Enabled<br>
                    <input type="radio" id="radStatusDisabledDis" name="radStatusDis" value="disabled" disabled> Disabled
                    <br>
                </div> 
                <div class="col-xs-2">
                </div>    
                <div class="col-xs-4">
                    <input type="text" class="form-control" id="txtFirstName" name="txtFirstName" placeholder="First Name"><br>
                    <input type="text" class="form-control" id="txtLastName" name="txtLastName" placeholder="Last Name"><br>
                    <input type="text" class="form-control" id="txtLogin" name="txtLogin" placeholder="Login"><br>
                    <input type="text" class="form-control" id="txtEmail" name="txtEmail" placeholder="Email" ><br>
                    <input type="text" class="form-control" id="txtPassword" name="txtPassword" placeholder="Password"><br>
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
            <button class="btn btn-lg btn-primary" type="submit" name="btnSubmitPersonalInfo" id="btnSubmitPersonalInfo">Submit Change</button>
        </form><br>

    </div>
    <script language="javascript">
        var $restPath = "../rest/viewEditUser?userID=<%out.print(userID);%>";

        $(document).ready(function () {

            getJsonFromWebService($restPath);

            function getJsonFromWebService(targetUrl) {
                console.log(targetUrl);
                xhr = jQuery.ajax({
                    type: "GET",
                    url: targetUrl,
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data, status, jqXHR) {
                        console.log(data);
                        fillDisabledFields(data);
                    },
                    error: function (jqXHR, status) {
                        console.log(status);
                    }
                });
            }

            function fillDisabledFields(data) {
                $("#txtFirstNameDis").val(data[0].firstName);
                $("#txtLastNameDis").val(data[0].lastName);
                $("#txtLoginDis").val(data[0].login);
                $("#txtEmailDis").val(data[0].email);
                $("#txtPasswordDis").val("***encrypted***");

                if (data[0].userType === "Employer") {
                    $("#chkEmployerDis").prop('checked', true);
                }

                if (data[0].userType === "Staff") {
                    $("#chkStaffrDis").prop('checked', true);
                }

                if (data[0].userType === "Student") {
                    $("#chkStudentDis").prop('checked', true);
                }

                if (data[0].enabled === false) {
                    $("#radStatusDisabledDis").prop('checked', true);
                }
                else {
                    $("#radStatusEnabledDis").prop('checked', true);
                }


            }
        });
    </script> 
    <%@ include file="../includes/footer.jsp" %>
