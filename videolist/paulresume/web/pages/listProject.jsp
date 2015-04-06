
<%@page import="edu.pitt.utilities.DbUtilities"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head> 
 <html lang="en">
    
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Resume Management System </title>
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">

        <!--Style-sheet for Resume-->
        <link href="../css/style.css" rel="stylesheet">
        <link href="../css/custom.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" type="text/css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">

        <!--jquery scripts -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>


        <!--Scripts for Data Binding -->
        <script src="js/knockout-3.2.0.js"></script>
        <script src="js/knockout.mapping.js"></script>
    
    <body>

        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"><span class="sr-only">Toggle navigation</span></button> <a class="navbar-brand" href="#">Resume Management System</a>
                </div> 
                <div class="navbar-collapse collapse">

                    <!--Main Navigation-->
                    <ul class="nav navbar-nav" id="mainNavigation">
                        <li><a href="menu.jsp">Home</a></li>
                        <li><a href='pages/NewPortfolio.html'>Create Portfolio</a></li>
                        <li><a href='pages/portfolioInfo.html'>View/Edit Portfolio</a></li>
                    </ul>
                    <!--Logout Button-->
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <ul class="nav pull-right">
                                <li> <a class="btn btn-default navbar-btn" href="../logout">Logout</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div><!--/.container-->
        </div><!--navbar-->
    
    <% //String userID= request.getParameter("userID"); 
       session = request.getSession();
       String userID1= (String) session.getAttribute("userID");        
       System.out.println( "value is" + userID1);
    %>
    
 
    <body>  
    
      
           
           <h2>  LIST OF PROJECTS </h2>
             <table class="table" id ="tblUserList" border="1" cellpadding="5" cellspacing="1">   
                 
            <tr>   
               <th> Image </th>
                <th> Project ID </th>
                <th> Project Name </th>
                 <th> Description </th>
                <th> Start date </th>
                <th> End date </th>
                 <th> Created </th>
                <th> Modified </th>
                
            </tr>
            
            
     <%
       DbUtilities db = new DbUtilities();
    String query1="select Project.ProjectID, Project.name, Project.description,Project.startdate,Project.enddate,Project.created,Project.modified,ProjectMedia.fileName from Project,ProjectMedia,User where User.userID='"+userID1+"' and Project.fk_userID= User.userID and ProjectMedia.fk_projectId=Project.ProjectID";
       ResultSet rs = db.getResultSet(query1);  
         while(rs.next())
           { 
                 
       String filepath1=rs.getString(8);  
       //String[] relativePathArray = filepath1.split("/");
      // String relativeFilePath = "../uploads/" + relativePathArray[relativePathArray.length - 1];
           String relativeFilePath = "../uploads/" + filepath1;   
               
     %>  
         
           
          <tr>
                     <%-- substring after "." is taken and checked --%>
              <%  int dotindex= filepath1.indexOf(".");%>
           
               <%--string matching for corresponding image or video format --%>
               <% if(filepath1.substring(dotindex).matches(".ogv|.webm|.mp4")) {%>
                     
                 <td>     
                     <video width="200" height="200" src= <%=relativeFilePath %>  controls> 
                     Your browser does not support the <code>video</code> element.
                     </video> 
                 </td>  
                 
                 <% } else if(filepath1.substring(dotindex).matches(".jpg|.png|.jpeg|.gif")) { %>
                 
                          <td>     
                              <img src = "<%=relativeFilePath%>" alt="sorry no pic" height="100" width="100" />  
                           </td>  
                   
                 <% } else { %>
                 <td>     
                     <img src = "<%=relativeFilePath%>" alt="sorry no pic" width="200" height="200"  />  
                </td>  
                          
                <% } %> 
                 <td>     
                     <h4> <%=rs.getString(1)%></h4> 
                 </td>  
                 
                  <td>     
                     <h4> <%=rs.getString(2)%></h4>   
                 </td>  
                 
                  <td>     
                     <h4> <%=rs.getString(3)%></h4>   
                 </td>
                 
                  <td>     
                     <h4> <%=rs.getString(4)%></h4>   
                 </td>  
                 
                  <td>     
                     <h4> <%=rs.getString(5)%></h4>   
                 </td>  
                 
                  <td>     
                     <h4> <%=rs.getString(6)%></h4>   
                 </td>  
                
                  <td>     
                     <h4> <%=rs.getString(7)%></h4>   
                 </td>  
                 
                  <td>     
                      <a class="btn btn-default" href="../viewEditProject?projectID=<%=rs.getString(1)%>"> EDIT </a>
                      
                 </td>  
                 
            </tr>
       
             <% } %>
        </table>
      </div>       
       </body>      
</html>


<%--
onclick="window.location.href = 'addMedia.jsp?userID="+userID+"'"

<a href="../viewEditProject?projectID=<%=rs.getString(1)%>" >

 <h1> <button class="btn btn-lg btn-default" type="button" onclick= a href="../viewEditProject?projectID=<%=rs.getString(1)%>" >  Edit  </a> </button> </h1> // how to retrieve  this value on connecting page  


            <tr>
                
                <td> 
                    
                <img src = "<%=relativeFilePath%>" alt="sorry no pic" height="100" width="100" />  
                 </td>  
                
                 <td>     
                     <h1> <%=rs.getString(1)%></h1> 
                 </td>  
                 
                  <td>     
                     <h1> <%=rs.getString(2)%></h1>   
                 </td>  
                 
                  <td>     
                     <h1> <%=rs.getString(3)%></h1>   
                 </td>
                 
                  <td>     
                     <h1> <%=rs.getString(4)%></h1>   
                 </td>  
                 
                  <td>     
                     <h1> <%=rs.getString(5)%></h1>   
                 </td>  
                 
                  <td>     
                     <h1> <%=rs.getString(6)%></h1>   
                 </td>  
                
                  <td>     
                     <h1> <%=rs.getString(7)%></h1>   
                 </td>  
                 
                  <td>     
                      <a class="btn btn-default" href="../viewEditProject?projectID=<%=rs.getString(1)%>"> EDIT </a>
                      
                 </td>  
                 
            </tr>


  <%--      
     <div id="mainContainer" class="container theme-showcase" style="width:900px">
          <div class="panel panel-default">  --%>
--%>