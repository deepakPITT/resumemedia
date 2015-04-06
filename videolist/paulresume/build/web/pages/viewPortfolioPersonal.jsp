<%-- 
    Document   : viewPortfolioPersonal
    Created on : Apr 15, 2014, 7:37:39 PM
    Author     : jordanstevenfeldman
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String portfolioID = request.getParameter("portfolioID");
%>

<%@ include file="../includes/header.jsp" %>

<script language="javascript">
    //Gets resume as JSON from web service based on resumeID
    $(document).ready(function () {
        getJsonFromWebService("../rest/portfolio?portfolioID=<%out.print(portfolioID);%>");
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
        $portfolio = $('#portfolioContainer');
        $projects = $('#projects');
        $research = $('#research');



        for (var i = 0; i < data['ProjectList'].length; i++) {
            var gpa = gpaToDecimal(data['ProjectnList'][i].gpa);

            var minor = (data['ProjectList'][i].minor || "");

            $project.append('<div style="padding: 0 0 30px 0;">');
            $project.append('<h3>' + data['ProjectList'][i].name + '</h3>');
            $project.append('<h4>' + data['ProjectList'][i].type + ', ' + data['ProjectList'][i].field);
            $project.append('<h4> GPA: ' + gpa + '</h4>');
            $project.append('</div>');

        }


        for (var i = 0; i < data['ResearchList'].length; i++) {
            $endDate = data['ResearchList'][i].endDate;
            $currentlyEmployed = data['ResearchList'][i].currentlyEmployed;

            //Changes end date to present if currently employed
            if ($currentlyEmployed === 1) {
                $endDate = "Present";
            }

            $research.append('<div style="padding: 0 0 20px 0;">');
            $research.append('<div style="float:right; display:inline"><h4>' + data['ResearchList'][i].startDate + ' - ' + $endDate + '</h4></div>');
            $research.append('<h3>' + data['ResearchList'][i].businessName + '</h3>');
            $research.append('<div><div style"float:left; display:inline"><h4>' + data['ResearchList'][i].position + '</h4></div>');
            $research.append('<div style:"display:block"><p>' + data['ResearchList'][i].description + '</p></div>');
            $research.append('</div>');
        }


    }

</script>


<section class="portfolio">

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


    <!-- Project -->
    <article class="row-fluid">
        <header class="span3">
            <h2>Project</h2>
        </header>
        <div class="span9 project" id="project">
            <!--Project Information Appears Here-->
        </div>
    </article>

    <!-- Work -->
    <article class="row-fluid">
        <header class="span3">
            <h2>Research Papers</h2>
            <button class="expandButton">
        </header>
        <div class="span9" id="research">
            <!--Research Papers Information Appears Here -->

        </div>
    </article>


    <!-- Footer -->
    <footer>
        <a href="#">Download as PDF</a> | <a href="#" onclick="javascript:window.print();">Print</a>

    </footer>

</section><!--End of container-->
<%@ include file="../includes/footer.jsp" %>
