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
 * Creates an Education object and maintains it with the DB
 *
 * @author Jordan Feldman
 */
public class Education {

    private String educationID;
    private String name;
    private String type;
    private String field;
    private String minor;
    private double gpa;
    private String graduationDate;
    private String created;
    private String modified;

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private DbUtilities db;

    public Education(String educationID) {
        setAllEducationProperties(educationID);
    }

    public Education(String name, String type, String field, String minor, Double gpa, String graduationDate) {
        educationID = UUID.randomUUID().toString();
        db = new DbUtilities();
        String sql = "INSERT INTO rms.Education ";
        sql += "(educationID,name,type,field,minor,gpa,graduationDate,created,modified)";
        sql += " VALUES (";
        sql += "'" + educationID + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(name) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(type) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(field) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(minor) + "', ";
        sql += "'" + gpa.toString() + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(graduationDate) + "',NULL,NULL); ";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of the Education constructor. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            setAllEducationProperties(educationID);
        }

    }

    /**
     * Creates an Education object from JSON
     *
     * @param education JSON object for an Education object
     */
    public Education(JSONObject education) {
        try {
            this.educationID = education.getString("educationID");
            setEducationFromJSON(education);
        } catch (JSONException ex) {
            Logger.getLogger(Education.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setAllEducationProperties(String educationID) {
        db = new DbUtilities();
        String sql = "SELECT * FROM rms.Education WHERE educationID = '" + StringUtilities.cleanMySqlInsert(educationID) + "'";
        try {
            ResultSet rs = db.getResultSet(sql);
            if (rs.next()) {
                this.name = rs.getString("name");
                this.type = rs.getString("type");
                this.field = rs.getString("field");
                this.minor = rs.getString("minor");
                this.gpa = rs.getDouble("gpa");
                this.graduationDate = rs.getString("graduationDate");
                this.created = rs.getTimestamp("created").toString();
                this.modified = rs.getTimestamp("modified").toString();
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occurred in Education(String educationID) constructor of Education class. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            this.educationID = educationID;
        }
    }

    public void setName(String name) {
        db = new DbUtilities();
        String sql = "UPDATE Education SET name = '" + StringUtilities.cleanMySqlInsert(name) + "' WHERE educationID = '" + this.educationID + "';";
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

    public void setType(String type) {
        db = new DbUtilities();
        String sql = "UPDATE Education SET type = '" + StringUtilities.cleanMySqlInsert(type) + "' WHERE educationID = '" + this.educationID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setType. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.type = StringUtilities.cleanMySqlInsert(type);
        setModified();
    }

    public void setField(String field) {
        db = new DbUtilities();
        String sql = "UPDATE Education SET field = '" + StringUtilities.cleanMySqlInsert(field) + "' WHERE educationID = '" + this.educationID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setField. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.field = StringUtilities.cleanMySqlInsert(field);
        setModified();
    }

    public void setMinor(String minor) {
        db = new DbUtilities();
        String sql = "UPDATE Education SET minor = '" + StringUtilities.cleanMySqlInsert(minor) + "' WHERE educationID = '" + this.educationID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setField. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.minor = StringUtilities.cleanMySqlInsert(minor);
        setModified();
    }

    public void setGPA(Double gpa) {
        db = new DbUtilities();
        String sql = "UPDATE Education SET gpa = '" + gpa.toString() + "' WHERE educationID = '" + this.educationID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setGPA. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.gpa = gpa;
        setModified();
    }

    public void setGraduationDate(String graduationDate) {
        db = new DbUtilities();
        String sql = "UPDATE Education SET graduationDate = '" + StringUtilities.cleanMySqlInsert(graduationDate) + "' WHERE educationID = '" + this.educationID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setGraduationDate. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.graduationDate = StringUtilities.cleanMySqlInsert(graduationDate);
        setModified();
    }

    private void setModified() {
        this.modified = DATE_FORMAT.format(Calendar.getInstance().getTime());
        db = new DbUtilities();
        String sql = "UPDATE Education SET modified = '" + this.modified + "' WHERE educationID = '" + this.educationID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setModified. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
    }

    public String getEducationID() {
        return educationID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public String getMinor() {
        return minor;
    }

    public double getGPA() {
        return gpa;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    /**
     * Gets an Education object as JSON
     *
     * @return Education object JSON
     */
    public JSONObject getEducationAsJson() {

        JSONObject education = new JSONObject();

        try {
            education.put("educationID", this.educationID);
            education.put("name", this.name);
            education.put("type", this.type);
            education.put("field", this.field);
            education.put("minor", this.minor);
            education.put("gpa", this.gpa);
            education.put("graduationDate", this.graduationDate);
            education.put("created", this.created);
            education.put("modified", this.modified);

        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within getEducationAsJSON. " + ex.getMessage());
        }
        return education;
    }

    /**
     * Sets an Education object's properties given JSON
     *
     * @param education JSON Object
     */
    public final void setEducationFromJSON(JSONObject education) {

        try {
            setName(education.getString("name"));
            setType(education.getString("type"));
            setField(education.getString("field"));
            setGPA(education.getDouble("gpa"));
            setGraduationDate(education.getString("graduationDate"));
            setMinor(education.getString("minor"));
        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within setEducationFromJSON. " + ex.getMessage());
        }
    }
}
