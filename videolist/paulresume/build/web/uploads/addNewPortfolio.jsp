<%-- 
    Document   : addNewPortfolio
    Created on : Feb 2, 2015, 12:16:18 PM
    Author     : ankita
--%>


<!DOCTYPE html>


<%@page import="edu.pitt.utilities.Security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>

<div id="mainContainer" class="container theme-showcase" style="width:900px">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><strong>Create Your Portfolio</strong></h3>
        </div>
        <div class="panel-body">
            <div class="page-header">
                <form class="frmPersonalInfo" method="post" action="../process/processPersonalInfo">
                    <h2 class="form-signin-heading">Project details </h2><br>
                    
                <table>
                     <th>
                      <label> PROJECT TITLE  </label>
                     </th> 
                </table>
                  
                    <table class="padded">
                     <tr> 
                         
                         <td> <input type="text" class="form-control" id="txtFirstName" name="Project" placeholder="PROJECT TITLE" autofocus="" required><br> </td>
                         <td>  <input type="text" class="form-control" id="txtLastName" name="Date" placeholder="start date" required><br> </td>
                           <td>  <input type="text" class="form-control" id="txtLastName" name="Date" placeholder="end date" required><br> </td>
                  </tr>
                       
                  
                      <th>
                          <label> PROJECT LINK </label>
                      </th>
                      
                   <tr>
                      <td> <input type="text" class="form-control" id="txtFirstName" name="Project" placeholder="PROJECT Link" autofocus="" required><br> </td>
                   </tr> 
                   
                    <th>
                          <label> ADD IMAGES </label>
                      </th>
                      
                   <tr>
                      <td> <input type="text" class="form-control" id="txtFirstName" name="Project" placeholder="PROJECT Link" autofocus="" required><br> </td>
                   </tr> 
                       
                   
                      <th>
                          <label> DESCRIPTION </label>
                      </th>   
                  <tr>    
                      <td>
                        <%--   <input type="text" class="form-control" id="txtAddressLine1" name="Description" placeholder="Description" required><br> --%>
                          <textarea name="address" class="form-control" placeholder="Description" cols="50" rows="4" required></textarea>
                      </td>
                  </tr>
                 
                  
                  <th>
                      <label> SUMMARY </label>
                   </th>   
                  <tr>
                      <td>
                          <%--      <input type="text" class="form-control" id="txtAddressLine2" name="Summary" placeholder="Summary"><br> --%>
                    
                     <textarea name="address" class="form-control" name="Summary" placeholder="Summary" cols="50" rows="4" required></textarea>
                      </td>
                  </tr>
                      
                     </table>
                   <br/> 
                          
                    <button class="btn btn-lg btn-default"  type="submit" name="btnSubmitPersonalInfo" id="btnSubmitPersonalInfo"> <a href='addNewResearch.jsp'>Next </a></button>
                    <button class="btn btn-lg btn-default"  type="submit" name="btnSubmitPersonalInfo" id="btnSubmitPersonalInfo"> <a href='AddProject.html'>Add Projects </a></button>
                   
                </form><br>

            </div>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
