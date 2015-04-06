/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.resumecore;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.StringUtilities;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Naveen
 */
public class WorkExperience {

    private String workExperienceID;
    private String businessName;
    private String position;
    private String startDate;
    private String endDate;
    private String description;
    private String created;
    private String modified;
    private int currentlyEmployed;

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private DbUtilities db;

    public WorkExperience(String workExperienceID) {
        setAllWorkExperienceProperties(StringUtilities.cleanMySqlInsert(workExperienceID));
    }

    public WorkExperience(String businessName, String position, String startDate, String endDate, int currentlyEmployed, String description) {
        workExperienceID = UUID.randomUUID().toString();
        db = new DbUtilities();
        String sql = "INSERT INTO rms.WorkExperience ";

        sql += "(workExperienceID,businessName,position,startDate,endDate,currentlyEmployed,description,created,modified)";
        sql += " VALUES (";
        sql += "'" + this.workExperienceID + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(businessName) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(position) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(startDate) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(endDate) + "', ";
        sql += "" + currentlyEmployed + ", ";
        sql += "'" + StringUtilities.cleanMySqlInsert(description) + "',NULL,NULL);";
        System.out.println(sql);
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of the WorkExperience constructor. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            setAllWorkExperienceProperties(workExperienceID);
            db.closeMySQLConnection();
        }
    }

    /**
     * Creates an Education object from JSON
     *
     * @param workExperience JSON object for an Education object
     */
    public WorkExperience(JSONObject workExperience) {
        try {
            this.workExperienceID = workExperience.getString("workExperienceID");
            setWorkExperienceFromJSON(workExperience);
        } catch (JSONException ex) {
            Logger.getLogger(WorkExperience.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setAllWorkExperienceProperties(String workExperienceID) {
        this.workExperienceID = workExperienceID;
        db = new DbUtilities();
        String sql = "SELECT * FROM rms.WorkExperience WHERE workExperienceID = '" + workExperienceID + "'";
        try {
            ResultSet rs = db.getResultSet(sql);
            if (rs.next()) {
                this.businessName = rs.getString("businessName");
                this.position = rs.getString("position");
                this.startDate = rs.getString("startDate");
                this.endDate = rs.getString("endDate");
                this.currentlyEmployed = rs.getInt("currentlyEmployed");
                this.description = rs.getString("description");
                this.created = rs.getTimestamp("created").toString();
                this.modified = rs.getTimestamp("modified").toString();
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occurred in WorkExperience(String workExperienceID) constructor of WorkExperience class. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            this.workExperienceID = workExperienceID;
            db.closeMySQLConnection();
        }
    }

    public void setBusinessName(String businessName) {
        db = new DbUtilities();
        String sql = "UPDATE WorkExperience SET businessName = '" + StringUtilities.cleanMySqlInsert(businessName) + "' WHERE workExperienceID = '" + this.workExperienceID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setBusinessName. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.businessName = StringUtilities.cleanMySqlInsert(businessName);
        setModified();
    }

    public void setPosition(String position) {
        db = new DbUtilities();
        String sql = "UPDATE WorkExperience SET position = '" + StringUtilities.cleanMySqlInsert(position) + "' WHERE workExperienceID = '" + this.workExperienceID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setPosition. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.position = StringUtilities.cleanMySqlInsert(position);
        setModified();
    }

    public void setStartDate(String startDate) {
        db = new DbUtilities();
        String sql = "UPDATE WorkExperience SET startDate = '" + StringUtilities.cleanMySqlInsert(startDate) + "' WHERE workExperienceID = '" + this.workExperienceID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setStartDate. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.startDate = StringUtilities.cleanMySqlInsert(startDate);
        setModified();
    }

    public void setEndDate(String endDate) {
        db = new DbUtilities();
        String sql = "UPDATE WorkExperience SET endDate = '" + StringUtilities.cleanMySqlInsert(endDate) + "' WHERE workExperienceID = '" + this.workExperienceID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setEndDate. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.endDate = StringUtilities.cleanMySqlInsert(endDate);
        setModified();
    }

    public void setCurrentlyEmployed(int currentlyEmployed) {
        db = new DbUtilities();
        String sql = "UPDATE WorkExperience SET currentlyEmployed = " + currentlyEmployed + " WHERE workExperienceID = '" + this.workExperienceID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setEndDate. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.currentlyEmployed = currentlyEmployed;
        setModified();
    }

    public void setDescription(String description) {
        db = new DbUtilities();
        String sql = "UPDATE WorkExperience SET description = '" + StringUtilities.cleanMySqlInsert(description) + "' WHERE workExperienceID = '" + this.workExperienceID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setDescription. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.description = StringUtilities.cleanMySqlInsert(description);
        setModified();
    }

    private void setModified() {
        this.modified = DATE_FORMAT.format(Calendar.getInstance().getTime());
        db = new DbUtilities();
        String sql = "UPDATE WorkExperience SET modified = '" + this.modified + "' WHERE workExperienceID = '" + this.workExperienceID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setModified. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
    }

    public String getWorkExperienceID() {
        return workExperienceID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getPosition() {
        return position;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int currentlyEmployed() {
        return currentlyEmployed;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Creates a JSON Object representing a WorkExperience object
     *
     * @return JSON Object
     */
    public JSONObject getWorkExperienceAsJson() {

        JSONObject workExperience = new JSONObject();

        try {
            workExperience.put("workExperienceID", this.workExperienceID);
            workExperience.put("businessName", this.businessName);
            workExperience.put("position", this.position);
            workExperience.put("startDate", this.startDate);
            workExperience.put("endDate", this.endDate);
            workExperience.put("description", this.description);
            workExperience.put("created", this.created);
            workExperience.put("modified", this.modified);
            workExperience.put("currentlyEmployed", this.currentlyEmployed);
        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within getWorkExperiencenAsJSON. " + ex.getMessage());
        }
        return workExperience;
    }

    /**
     * Sets a workExperience objects properties given JSON
     *
     * @param workExperience JSON Object
     */
    public final void setWorkExperienceFromJSON(JSONObject workExperience) {

        try {
            setBusinessName(workExperience.getString("businessName"));
            setPosition(workExperience.getString("position"));
            setStartDate(workExperience.getString("startDate"));
            setEndDate(workExperience.getString("endDate"));
            setDescription(workExperience.getString("description"));
            setCurrentlyEmployed(workExperience.getInt("currentlyEmployed"));
        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within setWorkExperienceFromJSON. " + ex.getMessage());
        }
    }
}
