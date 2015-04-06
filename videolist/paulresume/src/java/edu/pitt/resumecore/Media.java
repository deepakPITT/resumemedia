/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.resumecore;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.StringUtilities;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/*   

userID required to match in Project table and get P_ID ;
push file name accrodingly if userID matches into P_ID 

*/
public class Media {
    
    private DbUtilities db;
    public String userID;
    public int projectID;
    public String fileName;

    public Media(String userID, String fileName) throws Exception  {
        
          db = new DbUtilities();
          // this.userID = userID;
           this.fileName= fileName;
         /* This takes the last projectID submitted by user and adds mediac fileName (audio/video)  
           
           */
        String sql = "SELECT Project.ProjectID from Project where fk_userID= '" + userID + "'";
        try {
            ResultSet rs = db.getResultSet(sql);
            while (rs.next()) {
                projectID = rs.getInt(1);
                
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occurred in Education(String educationID) constructor of Education class. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally { 
            
             String sql1 = "INSERT INTO rms.ProjectMedia";
             sql1 += "( fk_projectID, fileName)";
             sql1 += " VALUES (";
             sql1 += "'" + projectID + "', ";
             sql1 += "'" + this.fileName + "');";
           
             db.executeQuery(sql1);
            db.closeMySQLConnection();
            
            
          //  throw new UnsupportedOperationException("Not supported yet.");
           
           
        }
    } 
       // fileName of image / video is added in corresponding projectID
      public Media(int projectID, String savepath) throws SQLException
     {
          db = new DbUtilities();
          this.projectID = projectID;
         fileName=savepath;
         
         /* 
          String sql1= "insert into file values(?)";
          pst = con.prepareStatement(sql1);
          pst.setString(1, this.savepath);
          pst.executeUpdate();sss
           */
        
         String sql1 = "INSERT INTO rms.ProjectMedia ";
                sql1 += "(fk_projectID,fileName)";
                sql1 += " VALUES (";
                sql1 += "'" + this.projectID + "', ";
                sql1 += "'" + fileName+ "'";
                sql1 += ");";
       
                try {
            db.executeQuery(sql1);
            
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of the Education constructor. " + ex.getMessage());
            ErrorLogger.log(sql1);
        } finally {
            db.closeMySQLConnection();
        // pst.setInt(1,projectid);
        // pst.setString(2,this.savepath);
       
     }
      
   } 
}       
       

        //To change body of generated methods, choose Tools | Templates.
 



    
    

