
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
     <% //String userID= request.getParameter("userID"); 
       session = request.getSession();
       String userID1= (String) session.getAttribute("userID");        
       System.out.println( "value is" + userID1);
    %>                    
    <body>                
        <table>  
            
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
       //String query1="select fileName from ProjectMedia";
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
             
                <%  int dotindex= filepath1.indexOf(".");%>
                
                <% if(filepath1.substring(dotindex).matches(".ogv|.webm")) {%>
                     
                        <td>     
                          <video src= <%=relativeFilePath %>  controls> 
                          Your browser does not support the <code>video</code> element.
                          </video> 
                       </td>  
                 
                        <% } else  { %>
                     
                         
                          <td>     
                              <img src = "<%=relativeFilePath%>" alt="sorry no pic" height="500" width="500" />  
                           </td>  
                          
                         <% } %> 
                 
                 <%--
                  <td>
                     <h1> <a href="./viewEditProject?projectID=<%=rs.getString(2)%>"> Edit  </a> </h1>   
                     <% if(filepath1.substring(dotindex).equalsIgnoreCase(".ogv|.webm")) {%>
                 
                 --%>
                 
                 
            </tr>
       
             <% } %>
             
        </table>
       </body>      
</html>

