
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
  
    %>
    
    
    <body>         
        <form action="../ViewEditProject">
        
        <table>  
            
            <tr>   
               <th> Image </th>
                <th> Project Name </th>
                <th> Project ID </th>
            </tr>
     <%
       DbUtilities db = new DbUtilities();
    String query1="select Project.Name, Project.ProjectID ,ProjectMedia.fileName from Project,ProjectMedia,User where User.userID='"+userID1+"'and Project.fk_userID= User.userID ";
       ResultSet rs = db.getResultSet(query1);  
         while(rs.next())
           { 
                 
       String filepath1=rs.getString(3);  
       //String[] relativePathArray = filepath1.split("/");
      // String relativeFilePath = "../uploads/" + relativePathArray[relativePathArray.length - 1];
           String relativeFilePath = "../uploads/" + filepath1;   
               
     %>  
         
           
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
                      <input type="hidden" name="project" id="projectID" value="<%=rs.getString(3)%>" </input>
                           
                 </td>  
                 
            </tr>
       
             <% } %>
             
        </table>
       </form>
        </body>  
       
</html>

