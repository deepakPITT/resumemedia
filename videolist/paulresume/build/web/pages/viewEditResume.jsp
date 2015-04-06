<%-- 
    Document   : viewEditResume
    Created on : Nov 28, 2014, 4:46:44 PM
    Author     : Mandy
--%>

<%@page import="edu.pitt.utilities.Security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>

<script>
    var resumeID = "<%out.print(request.getParameter("resumeID"));%>";
</script>
<script src="../js/editResumeFunctions.js"></script>
<script src="../js/koDatePickerBinding.js"></script>
<script>

    function submitJSON(data) {
        $.ajax({
            type: 'POST',
            url: '../process/processResume',
            data: {'resume': data},
            async: false
                    //success: ,
                    //error:
        });
    }
</script>

<div id="mainContainer" class="container theme-showcase" style="width:900px">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><strong>Edit Your Resume</strong></h3>
        </div>
        <div class="panel-body">
            <div id="page-header">
                <h2 class="form-signin-heading resumeHeader">Education</h2><br>
                <button class="expandButton" type="button" id="editEduButton"><span class="glyphicon glyphicon-chevron-down" aria-hidden="true" ></span></button><br><br>
            </div>
            <div data-bind="foreach: resumes">
                <div id="editEduList">
                    <div data-bind="foreach: EducationList">
                        <div>
                            <label> University</label>
                            <input data-bind="value: name" type="text" class="form-control" id="txtSchoolName" name="txtSchoolName" required autofocus=""><br>
                            <div style="float:left; margin-right:7px;">
                                <label style="display:block"> Degree Type </label>
                                <select style="display:inline" data-bind="value: type" class="form-control" id="txtDegreeType" name="txtDegreeType" required >
                                    <option value="Associates">Associates</option>
                                    <option value="Bachelors">Bachelors</option>
                                    <option value="Masters">Masters</option>
                                    <option value="Doctorate">Doctorate</option>
                                    <option value="Certificate">Certificate</option>
                                    <option value="MA">Diploma</option>                              
                                </select>
                            </div>
                            <div style="float:left; margin-right:8px;">
                                <label style="display:block"> Major </label><input style="display:block" data-bind="value: field" type="text" class="form-control" id="txtMajorType" name="txtMajorType" placeholder="Major" required>
                            </div>
                            <div style="float:left; margin-right:8px;">
                                <label style="display:block"> Minor/Related Area </label><input style="display:block" data-bind="value: minor" type="text" class="form-control" id="txtMinorType" name="txtMinorType" placeholder="Minor or Related Area"><br><br>
                            </div>
                            <div style="float:left;  margin-right:7px;">
                                <label style="display:block"> GPA </label><input style="display:block" data-bind="value: gpa" type="number" class="form-control" id="txtGPA" name="txtGPA" placeholder="GPA" min="0" max="4" step="0.1" required>
                            </div>
                            <div style="float:left; margin-right:8px;">
                                <label style="display:block"> Graduation Date </label><input style="display:block" data-bind="datepicker: graduationDate" type="text" class="form-control datepicker" id="txtGraduationDate" name="txtGraduationDate" placeholder="Graduation Date" required>
                            </div>
                            <div style="clear:both"></div>
                        </div>
                        <br>
                    </div><!--End editeduList-->
                </div><!--End Education Data Bind-->
                <div id="page-header">
                    <h2 class="form-signin-heading resumeHeader">Work Experience</h2><br>
                    <button class="expandButton" id="editWorkButton"><span class="glyphicon glyphicon-chevron-down" aria-hidden="true" ></span></button><br><br>
                </div>
                <div id="editWorkList">
                    <div data-bind="foreach: WorkExperienceList">
                        <div style="margin-bottom:0px">
                            <form>
                                <label style="display:inline"> Business Name </label><br><input data-bind="value: businessName" type="text" class="form-control" id="txtBusinessName" name="txtBusinessName" placeholder="Employer" required autofocus=""><br>
                                <label style="display:inline"> Position </label><br><input data-bind="value: position" type="text" class="form-control" id="txtPosition" name="txtPosition" placeholder="Position" required><br>
                                <label style="display:inline"> Start Date </label><input data-bind="datepicker: startDate,  datepickerOptions: { maxDate: '+1M +1D'}" type="date" class="form-control datepicker" style="width:20%; display:inline">
                                <label style="display:inline"> End Date </label><input data-bind="datepicker: endDate, datepickerOptions: { maxDate: '+1M +1D'}" type="date" class="form-control datepicker" style="width:20%; display:inline">
                                <!-- How do I deal with the Radio Button??-->
                                <label style="display:inline">Currently Employed? </label>
                                <input data-bind="checked: currentlyEmployed" type="radio"  id="rdoCurrentlyEmployed" name="rdoCurrentlyEmployed" value="1" style="display:inline" required> Yes
                                <input data-bind="checked: currentlyEmployed" type="radio"  id="rdoCurrentlyEmployed" name="rdoCurrentlyEmployed" value="0" style="display:inline" required> No<br><br>
                                <label style="display:inline"> Description of Work </label><br>
                                <textarea data-bind="value: description" class="form-control" rows="6" id="txtWorkDescription" name="txtWorkDescription" placeholder="Description of Work Experience" required></textarea><br>
                            </form>
                        </div>
                    </div><!--End editWorkList -->
                </div>
            </div>
        </div>
        <!-- Displays the JSON - for Testing purposes-->
        <!--    <pre data-bind="text: ko.toJSON($root.resumes, null, 2)"></pre>-->
        <button class="btn" data-bind="click:  this.submitJSON(ko.toJSON($root.resumes, null, 2))" onclick="window.location.href = 'menu.jsp'" style="margin: 5px;">Submit</button>

        <!--Allows the user to show/hide each section of the resume when editing-->
        <script>
            
            $(document).on('click', '#editEduButton', function(){
                $("#editEduList").toggle();
            });
            
            $(document).on('click', '#editWorkButton', function(){
                $("#editWorkList").toggle();
            });
           
        </script>

        <%@ include file="../includes/footer.jsp" %>