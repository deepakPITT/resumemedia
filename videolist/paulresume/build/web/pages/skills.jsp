<%-- 
    Document   : skills
    Created on : Apr 15, 2014, 9:08:49 PM
    Author     : Mandy
--%>

<%@page import="edu.pitt.utilities.Security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>
<div class="container theme-showcase" style="width:900px">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title"><strong>Create Your Resume</strong></h3>
        </div>
        <div class="panel-body">
            <div id="page-header">
                <h2 class="form-signin-heading">Work Experience</h2><br>
                <div id="workExpForm">
                </div>
                <br>
                <div id="additionalWorkExp">
                </div>
                <button class="btn btn-lg btn-primary" type="button" onclick="window.location.href = 'education.jsp'">Back</button>
                <button class="btn btn-lg btn-primary" type="button" id="btnAddWorkExp">Add Work Experience</button>
                <button class="btn btn-lg btn-primary" type="submit" onclick="submitEdu();">Next</button> <!--Where do we go from here? -->
            </div>
        </div><!-- /container -->
    </div>
</div>

<!--Presents a date picker for the Start and End Date fields-->
<script>
    $(function () {
        $(".datepicker").datepicker();
    });
</script>

<!--Loads the WorkExperience Form-->
<script>
    $(document).ready(function () {
        $("#workExpForm").load("workExpForm.html");
    });
</script>

<!--When "Add Work Experience" button is pressed, adds an additional form to allow the user to input multiple work entries-->
<script type='text/javascript'>
    $(document).ready(function () {
        $('#btnAddWorkExp').click(function () {
            $('#additionalWorkExp').append($('<div>').load('workExpForm.html'));
            return false;
        });
    });
</script>


<!--Submits all forms on page -->
<script>
    function post_form_data(data) {
        $.ajax({
            type: 'POST',
            url: '../process/processWorkExperience',
            data: data,
            async: false
                    //success: ,
                    //error:
        });
    }//end of post_form_data


    //When Next button is clicked, all forms on page are submitted for processing
    function submitEdu() {
        //$('btnSubmit').on('click', function () {
        $('form').each(function () {
            post_form_data($(this).serialize());
        });
        window.location.href = 'menu.jsp';

    }
</script>
<%@ include file="../includes/footer.jsp" %>
