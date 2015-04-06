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
public class Resume {

    private String resumeID;
    private int rating;
    private String created;
    private String modified;
    private String userID;
    private ArrayList<Education> educationList = new ArrayList<>();
    private ArrayList<Award> awardList = new ArrayList<>();
    private ArrayList<WorkExperience> workExperienceList = new ArrayList<>();
    private ArrayList<Address> addresses = new ArrayList<>();

    private DbUtilities db;

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates a Resume object based upon information obtained from database by
     * providing resumeID
     *
     * @param resumeID
     */
    public Resume(String resumeID) {
        setAllResumeProperties(resumeID);
    }

    /**
     * Creates a Resume object based upon the parameters given and inserts it
     * into the database
     *
     * @param userID
     * @param rating
     */
    public Resume(String userID, int rating) {
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
    public Resume(JSONObject resume) {
        try {
//            setAllResumeProperties(resume.getString("resumeID")); 
            this.resumeID = resume.getString("resumeID");
            setResumeFromJSON(resume);
        } catch (JSONException ex) {
            Logger.getLogger(Resume.class.getName()).log(Level.SEVERE, null, ex);
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
                if (rs.getString("addressID") != null) {

                    Address address = new Address(rs.getString("addressID"));
                    this.addresses.add(address);
                }
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occurred in Resume(String resumeID) constructor of Resume class. " + ex.getMessage());
            ErrorLogger.log(sql1);
        } finally {
        }

        String sql2 = "SELECT * FROM rms.ResumeAward WHERE fk_resumeID = '" + this.resumeID + "'";
        try {
            ResultSet rs2 = db.getResultSet(sql2);
            while (rs2.next()) {
                this.awardList.add(new Award(rs2.getString("fk_awardID")));
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllResumeProperties() method of Resume class. " + ex.getMessage());
            ErrorLogger.log(sql2);
            System.out.println(sql2);
        }

        String sql3 = "SELECT * FROM rms.ResumeEducation WHERE fk_resumeID = '" + this.resumeID + "'";
        try {
            ResultSet rs3 = db.getResultSet(sql3);
            while (rs3.next()) {
                this.educationList.add(new Education(rs3.getString("fk_educationID")));
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllResumeProperties() method of Resume class. " + ex.getMessage());
            ErrorLogger.log(sql3);

        }

        String sql4 = "SELECT * FROM rms.ResumeWorkExperience WHERE fk_resumeID = '" + this.resumeID + "'";
        try {
            ResultSet rs4 = db.getResultSet(sql4);
            while (rs4.next()) {
                this.workExperienceList.add(new WorkExperience(rs4.getString("fk_workExperienceID")));
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllResumeProperties() method of Resume class. " + ex.getMessage());
            ErrorLogger.log(sql4);
        }
        db.closeMySQLConnection();
    }

    public void addEducation(Education education) {
        db = new DbUtilities();
        educationList.add(education);
        String sql = "INSERT INTO rms.ResumeEducation (fk_resumeID,fk_educationID) VALUES";
        sql += "('" + this.resumeID + "', '" + education.getEducationID() + "')";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of addEducation. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            setModified();
        }
    }

    public void addAward(Award award) {
        db = new DbUtilities();
        awardList.add(award);
        String sql = "INSERT INTO rms.ResumeAward (fk_resumeID,fk_awardID) VALUES";
        sql += "('" + this.resumeID + "', '" + award.getAwardID() + "')";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of addAward. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
            setModified();
        }
    }

    public void addWorkExperience(WorkExperience workExperience) {
        db = new DbUtilities();
        workExperienceList.add(workExperience);
        String sql = "INSERT INTO rms.ResumeWorkExperience (fk_resumeID,fk_workExperienceID) VALUES";
        sql += "('" + this.resumeID + "', '" + workExperience.getWorkExperienceID() + "')";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of addWorkExperience. " + ex.getMessage());
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
     * Adds an address to this list of resume addresses
     *
     * @param address
     */
    public void addAddress(Address address) {
        db = new DbUtilities();
        String sql = "INSERT INTO rms.ResumeAddress (fk_userID,fk_addressID) VALUES";
        sql += "('" + this.resumeID + "', '" + address.getAddressID() + "')";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in the insert query inside of addAddress method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        addresses.add(address);

    }

    /**
     * Removes an address to this list of resume addresses
     *
     * @param address
     */
    public void removeAddress(Address address) {
        db = new DbUtilities();
        String sql = "DELETE FROM rms.ResumeAddress WHERE fk_resumeID = '" + this.resumeID + "' AND fk_addressID = '" + address.getAddressID() + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in the insert query inside of addAddress method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }

        addresses.remove(address);
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
    public JSONObject getResumeAsJson() {
        JSONObject resume = new JSONObject();
        JSONArray resumeAddressList = new JSONArray();
        JSONArray resumeEducationList = new JSONArray();
        JSONArray resumeAwardList = new JSONArray();
        JSONArray resumeWorkExperienceList = new JSONArray();

        try {
            resume.put("resumeID", this.resumeID);
            resume.put("userID", this.userID);
            resume.put("rating", this.rating);
            resume.put("created", this.created);
            resume.put("modified", this.modified);

            for (Address address : addresses) {
                resumeAddressList.put(address.getAddressAsJson());
            }

            resume.put("addresses", resumeAddressList);

            if (this.educationList != null) {
                for (Education education : this.educationList) {
                    resumeEducationList.put(education.getEducationAsJson());
                }
                resume.put("EducationList", resumeEducationList);
            } else {
                resume.put("EducationList", "");
            }

            if (this.awardList != null) {
                for (Award award : this.awardList) {
                    resumeAwardList.put(award.getAwardAsJson());
                }
                resume.put("AwardList", resumeAwardList);
            } else {
                resume.put("AwardList", "");
            }

            if (this.workExperienceList != null) {
                for (WorkExperience workExperience : this.workExperienceList) {
                    resumeWorkExperienceList.put(workExperience.getWorkExperienceAsJson());
                }
                resume.put("WorkExperienceList", resumeWorkExperienceList);
            } else {
                resume.put("WorkExperienceList", "");
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
    public final void setResumeFromJSON(JSONObject resume) {
        try {
            setUserID(resume.getString("userID"));
            setRating(resume.getInt("rating"));

            if (resume.has("addresses")) {
                JSONArray resumeAddressList = resume.getJSONArray("addresses");
                int addressListLength = resumeAddressList.length();

                for (int i = 0; i < addressListLength; i++) {
                    Address address = new Address(resumeAddressList.getJSONObject(i));
                }
            }

            if (resume.has("EducationList")) {
                JSONArray resumeEducationList = resume.getJSONArray("EducationList");

                int educationListLength = resumeEducationList.length();

                for (int i = 0; i < educationListLength; i++) {
                    Education education = new Education(resumeEducationList.getJSONObject(i));
                }
            }

            if (resume.has("AwardList")) {
                JSONArray resumeAwardList = resume.getJSONArray("AwardList");

                int awardListLength = resumeAwardList.length();

                for (int i = 0; i < awardListLength; i++) {
                    Award award = new Award(resumeAwardList.getJSONObject(i));
                }
            }

            if (resume.has("WorkExperienceList")) {
                JSONArray resumeWorkExperienceList = resume.getJSONArray("WorkExperienceList");

                int workExperienceLength = resumeWorkExperienceList.length();

                for (int i = 0; i < workExperienceLength; i++) {
                    WorkExperience workExperience = new WorkExperience(resumeWorkExperienceList.getJSONObject(i));
                }
            }

        } catch (JSONException ex) {
//            ErrorLogger.log("An error has occurred within getResumeAsJSON. " + ex.getMessage());
            Logger.getLogger(Resume.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
