/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.portfoliocore;

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
public class Project {

    private String projectID;
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

    public Project(String projectID) {
        setAllEducationProperties(projectID);
    }

    public Project(String name, String type, String field, String minor, Double gpa, String graduationDate) {
        projectID = UUID.randomUUID().toString();
        db = new DbUtilities();
        String sql = "INSERT INTO rms.Education ";
        sql += "(projectID,name,type,field,minor,gpa,graduationDate,created,modified)";
        sql += " VALUES (";
        sql += "'" + projectID + "', ";
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
            setAllEducationProperties(projectID);
        }

    }

    /**
     * Creates an Education object from JSON
     *
     * @param project JSON object for an Education object
     */
    public Project(JSONObject project) {
        try {
            this.projectID = project.getString("projectID");
            setEducationFromJSON(project);
        } catch (JSONException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setAllEducationProperties(String projectID) {
        db = new DbUtilities();
        String sql = "SELECT * FROM rms.Education WHERE projectID = '" + StringUtilities.cleanMySqlInsert(projectID) + "'";
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
            ErrorLogger.log("An error has occurred in Education(String projectID) constructor of Education class. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            this.projectID = projectID;
        }
    }

    public void setName(String name) {
        db = new DbUtilities();
        String sql = "UPDATE Education SET name = '" + StringUtilities.cleanMySqlInsert(name) + "' WHERE projectID = '" + this.projectID + "';";
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
        String sql = "UPDATE Education SET type = '" + StringUtilities.cleanMySqlInsert(type) + "' WHERE projectID = '" + this.projectID + "';";
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
        String sql = "UPDATE Education SET field = '" + StringUtilities.cleanMySqlInsert(field) + "' WHERE projectID = '" + this.projectID + "';";
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
        String sql = "UPDATE Education SET minor = '" + StringUtilities.cleanMySqlInsert(minor) + "' WHERE projectID = '" + this.projectID + "';";
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
        String sql = "UPDATE Education SET gpa = '" + gpa.toString() + "' WHERE projectID = '" + this.projectID + "';";
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
        String sql = "UPDATE Education SET graduationDate = '" + StringUtilities.cleanMySqlInsert(graduationDate) + "' WHERE projectID = '" + this.projectID + "';";
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
        String sql = "UPDATE Education SET modified = '" + this.modified + "' WHERE projectID = '" + this.projectID + "';";
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
        return projectID;
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

        JSONObject project = new JSONObject();

        try {
            project.put("projectID", this.projectID);
            project.put("name", this.name);
            project.put("type", this.type);
            project.put("field", this.field);
            project.put("minor", this.minor);
            project.put("gpa", this.gpa);
            project.put("graduationDate", this.graduationDate);
            project.put("created", this.created);
            project.put("modified", this.modified);

        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within getEducationAsJSON. " + ex.getMessage());
        }
        return project;
    }

    /**
     * Sets an Education object's properties given JSON
     *
     * @param project JSON Object
     */
    public final void setEducationFromJSON(JSONObject project) {

        try {
            setName(project.getString("name"));
            setType(project.getString("type"));
            setField(project.getString("field"));
            setGPA(project.getDouble("gpa"));
            setGraduationDate(project.getString("graduationDate"));
            setMinor(project.getString("minor"));
        } catch (JSONException ex) {
            ErrorLogger.log("An error occurred within setEducationFromJSON. " + ex.getMessage());
        }
    }
}
