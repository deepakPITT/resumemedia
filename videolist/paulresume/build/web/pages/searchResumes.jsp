<%@page import="edu.pitt.utilities.Security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>

<div>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading"><strong>Search</strong></div>
            <div class="col-xs-12">
                <br>
                <div class="input-group">
                    <div class="row">
                        <div class="col-xs-2">
                            Search by:
                        </div>
                        <div class="col-xs-3">
                            <select id="searchOption" class="form-control">
                                <option value="lastName" selected>Last Name</option>
                                <option value="field">Major/Minor</option>
                                <option value="gpa">GPA is greater than</option>
                                <option value="jobDescription">Job Description</option>
                            </select>
                        </div>
                        <div class="col-xs-6">
                            <input type="text" id="txtSearchbox" name="txtSearchbox" class="form-control" placeholder="Enter search terms here">
                        </div>
                        <div class="col-xs-1">
                            <button class="btn btn-default" type="button">Search</button>
                        </div>
                        <br>
                    </div>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
            <br>
            <br>
            <hr>
            <table class="table" id ="tblUserList" border="1" cellpadding="5" cellspacing="0">
                <tr>
                    <th>Last Name</th>
                    <th>First Name</th>
                    <th>Created</th>
                    <th>Modified</th>
                    <th>Resume ID</th>
                    <th id="searchCol"></th>
                    <th>Resume(s)</th>
                </tr>
            </table>
        </div>
    </div>
</div>

<script src="../js/searchResume.js" type="text/javascript"></script>

<%@ include file="../includes/footer.jsp" %>