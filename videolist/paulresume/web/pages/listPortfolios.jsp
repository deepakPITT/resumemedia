<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>

<div id="mainContainer" class="container theme-showcase" style="width:900px">
    <div class="panel panel-default">
        <div class="panel-heading"><strong>View Portfolios</strong></div>
        <table class="table" id ="tblUserList" border="1" cellpadding="5" cellspacing="0">
            <tr>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Created</th>
                <th>Modified</th>
                <th>Portfolio ID</th>
                <th>View Portfolio</th>
                <th>Edit Portfolio</th>
            </tr>
        </table>
    </div>
    <script>
        $(document).ready(function () {
            getJsonFromWebService("../rest/userlistws?userID=<% out.print(request.getParameter("userID"));%>");
        });
        
        function getJsonFromWebService(targetUrl) {
            jQuery.ajax({
                type: "GET",
                url: targetUrl,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data, status, jqXHR) {
                    jsonToHtmlTable(data);
                },
                error: function (jqXHR, status) {
                    console.log(status);
                }
            });
        }

        function jsonToHtmlTable(data) {
            $table = $('#tblUserList');
            for (var i = 0; i < data.length; i++) {
                for (var j = 0; j < data[i]['portfolios'].length; j++) {
                    var $row = $('<tr />');
                    $row.append('<td>' + data[i].lastName + '</td>');
                    $row.append('<td>' + data[i].firstName + '</td>');
                    $row.append('<td>' + data[i]['portfolios'][j].created + '</td>');
                    $row.append('<td>' + data[i]['portfolios'][j].modified + '</td>');
                    $row.append('<td>' + data[i]['portfolios'][j].portfolioID + '</td>');
                    $row.append('<td><a href="viewPortfolioPersonal.jsp?portfolioID=' + data[i]['portfolios'][j].portfolioID + '">View</a></td>');
                    $row.append('<td><a href="viewEditPortfolio.jsp?portfolioID=' + data[i]['portfolios'][j].portfolioID + '">Edit</a></td>');
                    $row.append('</tr>');
                    $table.append($row);
                }
            }
        }
    </script>
    <%@ include file="../includes/footer.jsp" %>