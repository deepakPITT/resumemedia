<%-- 
    Document   : viewResumePersonal
    Created on : Apr 15, 2014, 7:37:39 PM
    Author     : jordanstevenfeldman
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String resumeID = request.getParameter("resumeID");
%>

<%@ include file="../includes/header.jsp" %>

<script language="javascript">
    //Gets resume as JSON from web service based on resumeID
    $(document).ready(function () {
        getJsonFromWebService("../rest/resumews?resumeID=<%out.print(resumeID);%>");
    });


    function gpaToDecimal(gpa) {
        var gpa = gpa;
        var gpaDecimal = gpa.toFixed(1);
        return gpaDecimal;
    }

    function setMinor(minor) {
        var minor;
        if (minor != "") {

        }
    }


    //Gets JSON from web service
    function getJsonFromWebService(targetUrl) {
        console.log(targetUrl);
        jQuery.ajax({
            type: "GET",
            url: targetUrl,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, status, jqXHR) {
                getJSONForUser(data.userID);
                jsonToHtmlLayout(data);
                console.log(data);
                console.log("success");
            },
            error: function (jqXHR, status) {
                console.log(status);
                console.log("Error");
            }
        });
    }

    //Gets user as JSON from web service
    function getJSONForUser(userID) {
        console.log(userID);
        jQuery.ajax({
            type: "GET",
            url: "../rest/userInfows?userID=" + userID,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, status, jqXHR) {
                userJSONToHTMLLayout(data);
                console.log(data);
                console.log("success");
            },
            error: function (jqXHR, status) {
                console.log(status);
                console.log("Error");
            }
        });
    }


    function userJSONToHTMLLayout(data) {
        $userName = $('#userName');
        $emailPhoneInfo = $('#emailPhoneInfo');

        $userName.append('<h1>' + data.firstName + ' ' + data.lastName + '</h1>');
        $emailPhoneInfo.append('<h4 style="display:inline"> <strong>Phone: </strong>' + data.phoneNumber + '&nbsp &nbsp<strong>   Email: </strong>' + data.email + '</h4>');

    }
    ;

    //Builds HTML layout using user and resume information from JSON
    function jsonToHtmlLayout(data) {
        $resume = $('#resumeContainer');
        $contactInfo = $('#contactInfo');
        $education = $('#education');
        $workExperience = $('#workExperience');



        for (var i = 0; i < data['addresses'].length; i++) {
            $contactInfo.append('<h4 style="display:inline">' + data['addresses'][i].addressLine1 + ' ' + data['addresses'][i].addressLine2 + ', '
                    + data['addresses'][i].city + ', ' + data['addresses'][i].state + ' ' + data['addresses'][i].postalCode + '</h4>');
        }



        for (var i = 0; i < data['EducationList'].length; i++) {
            var gpa = gpaToDecimal(data['EducationList'][i].gpa);

            var minor = (data['EducationList'][i].minor || "");

            $education.append('<div style="padding: 0 0 30px 0;">');
            $education.append('<div style="float:right; display:inline"><h4>' + data['EducationList'][i].graduationDate + '</h4></div>');
            $education.append('<h3>' + data['EducationList'][i].name + '</h3>');
            $education.append('<h4>' + data['EducationList'][i].type + ', ' + data['EducationList'][i].field);

            $education.append('<h4> GPA: ' + gpa + '</h4>');
            $education.append('</div>');

        }


        for (var i = 0; i < data['WorkExperienceList'].length; i++) {
            $endDate = data['WorkExperienceList'][i].endDate;
            $currentlyEmployed = data['WorkExperienceList'][i].currentlyEmployed;

            //Changes end date to present if currently employed
            if ($currentlyEmployed === 1) {
                $endDate = "Present";
            }

            $workExperience.append('<div style="padding: 0 0 20px 0;">');
            $workExperience.append('<div style="float:right; display:inline"><h4>' + data['WorkExperienceList'][i].startDate + ' - ' + $endDate + '</h4></div>');
            $workExperience.append('<h3>' + data['WorkExperienceList'][i].businessName + '</h3>');
            $workExperience.append('<div><div style"float:left; display:inline"><h4>' + data['WorkExperienceList'][i].position + '</h4></div>');
            $workExperience.append('<div style:"display:block"><p>' + data['WorkExperienceList'][i].description + '</p></div>');
            $workExperience.append('</div>');
        }


    }

</script>


<section class="resume">

    <!-- Header -->
    <header class="row-fluid">
        <div class="title span10" id="userName">
            <!--            <h1>John Smith</h1>-->
        </div>
        <div class="title span10">
            <span id="contactInfo"></span> - 
            <span id="emailPhoneInfo"></span>

        </div>

        <!--        <div class="title span9" id="emailPhoneInfo">
                    Phone and Email Information Appears Here
                </div>	-->
    </header>


    <!-- Education -->
    <article class="row-fluid">
        <header class="span3">
            <h2>Education</h2>
        </header>
        <div class="span9 education" id="education">
            <!--Education Information Appears Here-->
        </div>
    </article>

    <!-- Work -->
    <article class="row-fluid">
        <header class="span3">
            <h2>Work Experience</h2>
            <button class="expandButton">
        </header>
        <div class="span9" id="workExperience">
            <!--Work Experience Information Appears Here -->

        </div>
    </article>


    <!-- Footer -->
    <footer>
        <a href="#">Download as PDF</a> | <a href="#" onclick="javascript:window.print();">Print</a>

    </footer>

</section><!--End of container-->
<%@ include file="../includes/footer.jsp" %>
