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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Creates a Resume object
 *
 * @author Jordan FeldmanStringUtilities.cleanMySqlInsert(
 */
public class Portfolio {

    private String resumeID;
    private int rating;
    private String created;
    private String modified;
    private String userID;
    private ArrayList<Project> projectList = new ArrayList<>();
    private ArrayList<Research> researchList = new ArrayList<>();

    private DbUtilities db;

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates a Resume object based upon information obtained from database by
     * providing resumeID
     *
     * @param resumeID
     */
    public Portfolio(String resumeID) {
        setAllResumeProperties(resumeID);
    }

    /**
     * Creates a Resume object based upon the parameters given and inserts it
     * into the database
     *
     * @param userID
     * @param rating
     */
    public Portfolio(String userID, int rating) {
        this.resumeID = UUID.randomUUID().toString();
        this.userID = StringUtilities.cleanMySqlInsert(userID);
        db = new DbUtilities();
        String sql = "INSERT INTO rms.Resume ";
        sql += "(resumeID,fk_userID,rating,created,modified)";
        sql += " VALUES (";
        sql += "'" + this.resumeID + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(this.userID) + "', ";
        sql += "'" + this.rating + "',NULL,NULL);";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in the insert query inside of the Resume constructor. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            setAllResumeProperties(resumeID);
        }

    }

    /**
     * Creates an Resume object from JSON
     *
     * @param resume JSON object for an Resume object
     */
    public Portfolio(JSONObject resume) {
        try {
//            setAllResumeProperties(resume.getString("resumeID")); 
            this.resumeID = resume.getString("resumeID");
            //setResumeFromJSON(resume);
        } catch (JSONException ex) {
            Logger.getLogger(Portfolio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setAllResumeProperties(String resumeID) {
        String sql1 = "SELECT * FROM rms.Resume R LEFT JOIN rms.ResumeAddress ON resumeID = fk_resumeID LEFT JOIN rms.Address ON fk_addressID = addressID WHERE R.resumeID = '" + StringUtilities.cleanMySqlInsert(resumeID) + "'";
        System.out.println(sql1);
        db = new DbUtilities();
        try {
            ResultSet rs = db.getResultSet(sql1);
            while (rs.next()) {
                this.resumeID = rs.getString("resumeID");
                this.rating = rs.getInt("rating");
                this.userID = rs.getString("fk_userID");
                this.created = rs.getTimestamp("created").toString();
                this.modified = rs.getTimestamp("modified").toString();
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occurred in Resume(String resumeID) constructor of Resume class. " + ex.getMessage());
            ErrorLogger.log(sql1);
        } finally {
        }

        String sql3 = "SELECT * FROM rms.ResumeProject WHERE fk_resumeID = '" + this.resumeID + "'";
        try {
            ResultSet rs3 = db.getResultSet(sql3);
            while (rs3.next()) {
                this.projectList.add(new Project(rs3.getString("fk_projectID")));
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllResumeProperties() method of Resume class. " + ex.getMessage());
            ErrorLogger.log(sql3);

        }

        String sql4 = "SELECT * FROM rms.ResumeResearch WHERE fk_resumeID = '" + this.resumeID + "'";
        try {
            ResultSet rs4 = db.getResultSet(sql4);
            while (rs4.next()) {
                this.researchList.add(new Research(rs4.getString("fk_researchID")));
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllResumeProperties() method of Resume class. " + ex.getMessage());
            ErrorLogger.log(sql4);
        }
        db.closeMySQLConnection();
    }

    public void addProject(Project project) {
        db = new DbUtilities();
        projectList.add(project);
        String sql = "INSERT INTO rms.ResumeProject (fk_resumeID,fk_projectID) VALUES";
        //sql += "('" + this.resumeID + "', '" + project.getProjectID() + "')";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of addProject. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            setModified();
        }
    }

    public void addResearch(Research research) {
        db = new DbUtilities();
        researchList.add(research);
        String sql = "INSERT INTO rms.ResumeResearch (fk_resumeID,fk_researchID) VALUES";
        sql += "('" + this.resumeID + "', '" + research.getResearchID() + "')";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of addResearch. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            setModified();
        }
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        db = new DbUtilities();
        String sql = "UPDATE Resume SET rating = '" + rating + "' WHERE resumeID = '" + this.resumeID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setRating. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            setModified();
        }
        this.rating = rating;
    }

    private void setModified() {
        this.modified = DATE_FORMAT.format(Calendar.getInstance().getTime());
        db = new DbUtilities();
        String sql = "UPDATE Resume SET modified = '" + this.modified + "' WHERE resumeID = '" + this.resumeID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setModified. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
    }

    /**
     * @return the resumeID
     */
    public String getResumeID() {
        return this.resumeID;
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        db = new DbUtilities();
        String sql = "UPDATE Resume SET fk_userID = '" + StringUtilities.cleanMySqlInsert(userID) + "' WHERE resumeID = '" + this.resumeID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setUserID. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            this.userID = StringUtilities.cleanMySqlInsert(userID);
            db.closeMySQLConnection();
        }
    }

    /**
     * Creates and returns a properly formated JSON representation of Resume
     *
     * @return A properly formated JSON representation of Resume
     */
    public JSONObject getPortfolioAsJson() {
        JSONObject resume = new JSONObject();
        JSONArray resumeProjectList = new JSONArray();
        JSONArray resumeResearchList = new JSONArray();

        try {
            resume.put("portfolioID", this.resumeID);
            resume.put("userID", this.userID);
            resume.put("created", this.created);
            resume.put("modified", this.modified);

            if (this.projectList != null) {
                for (Project project : this.projectList) {
                    //resumeProjectList.put(project.getProjectAsJson());
                }
                resume.put("ProjectList", resumeProjectList);
            } else {
                resume.put("ProjectList", "");
            }

            if (this.researchList != null) {
                for (Research research : this.researchList) {
                    resumeResearchList.put(research.getResearchAsJson());
                }
                resume.put("ResearchList", resumeResearchList);
            } else {
                resume.put("ResearchList", "");
            }
        } catch (JSONException ex) {
            ErrorLogger.log("An error has occurred within getResumeAsJSON. " + ex.getMessage());
        }
        return resume;
    }

    /**
     * Sets properties of a Resume given JSON
     *
     * @param resume A properly formated JSON representation of Resume
     */
    public final void setPortfolioFromJSON(JSONObject resume) {
        try {
            setUserID(resume.getString("userID"));
            setRating(resume.getInt("rating"));

            if (resume.has("addresses")) {
                JSONArray resumeAddressList = resume.getJSONArray("addresses");
                int addressListLength = resumeAddressList.length();

                for (int i = 0; i < addressListLength; i++) {
                    // Address address = new Address(resumeAddressList.getJSONObject(i));
                }
            }

            if (resume.has("ProjectList")) {
                JSONArray resumeProjectList = resume.getJSONArray("ProjectList");

                int projectListLength = resumeProjectList.length();

                for (int i = 0; i < projectListLength; i++) {
                    Project project = new Project(resumeProjectList.getJSONObject(i));
                }
            }

            if (resume.has("AwardList")) {
                JSONArray resumeAwardList = resume.getJSONArray("AwardList");

                int awardListLength = resumeAwardList.length();

                for (int i = 0; i < awardListLength; i++) {
                    //Award award = new Award(resumeAwardList.getJSONObject(i));
                }
            }

            if (resume.has("ResearchList")) {
                JSONArray resumeResearchList = resume.getJSONArray("ResearchList");

                int researchLength = resumeResearchList.length();

                for (int i = 0; i < researchLength; i++) {
                    Research research = new Research(resumeResearchList.getJSONObject(i));
                }
            }

        } catch (JSONException ex) {
//            ErrorLogger.log("An error has occurred within getResumeAsJSON. " + ex.getMessage());
            Logger.getLogger(Portfolio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
