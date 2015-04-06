/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.resumecore;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.StringUtilities;
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
 * Creates an Award objects and maintains it with the DB
 *
 * @author Jordan Feldman
 */
public class Award {

    private String awardID;
    private String name;
    private String description;
    private String created;
    private String modified;

    private DbUtilities db;

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Award(String awardID) {
        setAllAwardProperties(StringUtilities.cleanMySqlInsert(awardID));
    }

    public Award(String name, String description) {
        awardID = UUID.randomUUID().toString();
        db = new DbUtilities();
        String sql = "INSERT INTO rms.Award ";
        sql += "(awardID,name,description,created,modified)";
        sql += " VALUES (";
        sql += "'" + this.awardID + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(name) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(description) + "',NULL,NULL)";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of the Award constructor. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            setAllAwardProperties(awardID);
        }
    }

    /**
     * Creates an Award object from JSON
     *
     * @param award JSON object for an Award object
     */
    public Award(JSONObject award) {
        try {
            this.awardID = award.getString("awardID");
            setAwardFromJSON(award);
        } catch (JSONException ex) {
            Logger.getLogger(Award.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets all Award properties given an awardID
     *
     * @param awardID awardID
     */
    private void setAllAwardProperties(String awardID) {
        db = new DbUtilities();
        String sql = "SELECT * FROM rms.Award WHERE awardID = '" + StringUtilities.cleanMySqlInsert(awardID) + "'";
        try {
            ResultSet rs = db.getResultSet(sql);
            if (rs.next()) {
                this.name = rs.getString("name");
                this.description = rs.getString("description");
                this.created = rs.getTimestamp("created").toString();
                this.modified = rs.getTimestamp("modified").toString();
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occurred in Award(String awardID) constructor of Award class. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            this.awardID = StringUtilities.cleanMySqlInsert(awardID);
        }
    }

    public void setName(String name) {
        db = new DbUtilities();
        String sql = "UPDATE Award SET name = '" + StringUtilities.cleanMySqlInsert(name) + "' WHERE awardID = '" + this.awardID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setName. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.name = StringUtilities.cleanMySqlInsert(name);
        setModified();
    }

    public void setDescription(String description) {
        db = new DbUtilities();
        String sql = "UPDATE Award SET description = '" + StringUtilities.cleanMySqlInsert(description) + "' WHERE awardID = '" + this.awardID + "';";
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
        String sql = "UPDATE Award SET modified = '" + this.modified + "' WHERE awardID = '" + this.awardID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setModified. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
    }

    public String getAwardID() {
        return awardID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get the JSON for an Award
     *
     * @return JSOB Object of an Award
     */
    public JSONObject getAwardAsJson() {

        JSONObject award = new JSONObject();

        try {
            award.put("awardID", this.awardID);
            award.put("name", this.name);
            award.put("description", this.description);
            award.put("created", this.created);
            award.put("modified", this.modified);
        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within getAwardAsJSON. " + ex.getMessage());
        }
        return award;
    }

    /**
     * Given the JSON of an award set its properties
     *
     * @param award JSON Object
     */
    public final void setAwardFromJSON(JSONObject award) {

        try {
            setName(award.getString("name"));
            setDescription(award.getString("description"));
        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within getAwardAsJSON. " + ex.getMessage());
        }
    }

}
