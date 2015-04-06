<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes/loginHeader.jsp" %>
<div id="mainContainer" class="container theme-showcase" style="width:900px">
    <div class="panel panel-default">

        <div class="panel-heading panel-default">
            <h2 class="panel-title panel-default"><strong>Login</strong></h2>
        </div><div class="panel-body"><div class="page-header">
                <form class="form-signin" method="post" action="login">
                    <input type="text" name="txtEmailAddress" id="txtEmailAddress" class="form-control" placeholder="Username" required="required" autofocus>
                    <br>
                    <input type="password" name="txtPassword" id="txtPassword" class="form-control" placeholder="Password" required="required">
                    <label class="checkbox">
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                    <hr>
                    <button class="btn btn-lg btn-default btn-block" type="submit">Sign in</button></form><br> </div>





            <script>
                $(document).ready(function () {
                    $("#mainContainer").width("300px");
                });
            </script>
            <%@ include file="../includes/footer.jsp" %>

