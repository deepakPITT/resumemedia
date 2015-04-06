/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.portfoliocore;

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
public class Research {

    private String researchID;
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

    public Research(String researchID) {
        setAllResearchProperties(StringUtilities.cleanMySqlInsert(researchID));
    }

    public Research(String businessName, String position, String startDate, String endDate, int currentlyEmployed, String description) {
        researchID = UUID.randomUUID().toString();
        db = new DbUtilities();
        String sql = "INSERT INTO rms.Research ";

        sql += "(researchID,businessName,position,startDate,endDate,currentlyEmployed,description,created,modified)";
        sql += " VALUES (";
        sql += "'" + this.researchID + "', ";
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
            ErrorLogger.log("An error has occurred in with the insert query inside of the Research constructor. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            setAllResearchProperties(researchID);
            db.closeMySQLConnection();
        }
    }

    /**
     * Creates an Education object from JSON
     *
     * @param research JSON object for an Education object
     */
    public Research(JSONObject research) {
        try {
            this.researchID = research.getString("researchID");
            setResearchFromJSON(research);
        } catch (JSONException ex) {
            Logger.getLogger(Research.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setAllResearchProperties(String researchID) {
        this.researchID = researchID;
        db = new DbUtilities();
        String sql = "SELECT * FROM rms.Research WHERE researchID = '" + researchID + "'";
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
            ErrorLogger.log("An error has occurred in Research(String researchID) constructor of Research class. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            this.researchID = researchID;
            db.closeMySQLConnection();
        }
    }

    public void setBusinessName(String businessName) {
        db = new DbUtilities();
        String sql = "UPDATE Research SET businessName = '" + StringUtilities.cleanMySqlInsert(businessName) + "' WHERE researchID = '" + this.researchID + "';";
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
        String sql = "UPDATE Research SET position = '" + StringUtilities.cleanMySqlInsert(position) + "' WHERE researchID = '" + this.researchID + "';";
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
        String sql = "UPDATE Research SET startDate = '" + StringUtilities.cleanMySqlInsert(startDate) + "' WHERE researchID = '" + this.researchID + "';";
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
        String sql = "UPDATE Research SET endDate = '" + StringUtilities.cleanMySqlInsert(endDate) + "' WHERE researchID = '" + this.researchID + "';";
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
        String sql = "UPDATE Research SET currentlyEmployed = " + currentlyEmployed + " WHERE researchID = '" + this.researchID + "';";
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
        String sql = "UPDATE Research SET description = '" + StringUtilities.cleanMySqlInsert(description) + "' WHERE researchID = '" + this.researchID + "';";
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
        String sql = "UPDATE Research SET modified = '" + this.modified + "' WHERE researchID = '" + this.researchID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setModified. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
    }

    public String getResearchID() {
        return researchID;
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
     * Creates a JSON Object representing a Research object
     *
     * @return JSON Object
     */
    public JSONObject getResearchAsJson() {

        JSONObject research = new JSONObject();

        try {
            research.put("researchID", this.researchID);
            research.put("businessName", this.businessName);
            research.put("position", this.position);
            research.put("startDate", this.startDate);
            research.put("endDate", this.endDate);
            research.put("description", this.description);
            research.put("created", this.created);
            research.put("modified", this.modified);
            research.put("currentlyEmployed", this.currentlyEmployed);
        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within getResearchnAsJSON. " + ex.getMessage());
        }
        return research;
    }

    /**
     * Sets a research objects properties given JSON
     *
     * @param research JSON Object
     */
    public final void setResearchFromJSON(JSONObject research) {

        try {
            setBusinessName(research.getString("businessName"));
            setPosition(research.getString("position"));
            setStartDate(research.getString("startDate"));
            setEndDate(research.getString("endDate"));
            setDescription(research.getString("description"));
            setCurrentlyEmployed(research.getInt("currentlyEmployed"));
        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within setResearchFromJSON. " + ex.getMessage());
        }
    }
}
