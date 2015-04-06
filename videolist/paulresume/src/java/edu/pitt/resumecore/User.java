/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.resumecore;

/**
 *
 * @author Jordan Feldman
 */
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.Security;
import edu.pitt.utilities.StringUtilities;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private DbUtilities db;
    private String inputPassword;
    private String userID;
    private String lastName;
    private String firstName;
    private String middleInitial;
    private String phoneNumber;
    private String email;
    private String login;
    private String password;
    private String peopleSoftID;
    private Date graduationYear;
    private String placeOfWork;
    private String industry;
    private String employeeID;
    private String position;
    private ArrayList<Address> addresses = new ArrayList<>();
    private ArrayList<Resume> resumes = new ArrayList<>();
    private ArrayList<String> roles = new ArrayList<>();
    private String created;
    private String modified;
    private int status;
    private int usProof;
    private int usEligible;

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates a new instance of User by retrieving information from the
     * database
     *
     * @param userID The ID associated with the user whose information is being
     * retrieved
     */
    public User(String userID) {
        String sql = "SELECT * FROM rms.User LEFT JOIN rms.UserAddress ON userID = fk_userID ";
        sql += "LEFT JOIN rms.Address ON fk_addressID = addressID ";
        sql += "WHERE userID = '" + userID + "'";
        setAllUserProperties(sql);
    }

    /**
     * Creates a new instance of User based on user's login name and password
     *
     * @param login
     * @param password
     */
    public User(String login, String password) {
        String sql = "SELECT * FROM rms.User LEFT JOIN rms.UserAddress ON userID = fk_userID ";
        sql += "LEFT JOIN rms.Address ON fk_addressID = addressID ";
        sql += "WHERE login = '" + login + "';";
        this.inputPassword = password;
        setAllUserProperties(sql);
    }

    /**
     * Creates a User object based upon the parameters given and inserts it into
     * the database
     *
     * @param firstName
     * @param lastName
     * @param middleInitial
     * @param login
     * @param password
     * @param addresses
     * @param email
     * @param phoneNumber
     */
    public User(String firstName, String lastName, String middleInitial, String login, String password, ArrayList<Address> addresses, String email, String phoneNumber) {
        userID = UUID.randomUUID().toString();
        db = new DbUtilities();
        String sql = "INSERT INTO rms.User";
        sql += "(userID,firstName,lastName,middleInitial,login,password,email,phoneNumber)";
        sql += " VALUES (";
        sql += "'" + userID + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(firstName) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(lastName) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(middleInitial) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(login) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(password) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(email) + "', ";
        sql += "'" + StringUtilities.cleanMySqlInsert(phoneNumber) + "') ";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of the User constructor. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            String sql2 = "SELECT * FROM rms.User LEFT JOIN rms.UserAddress ON userID = fk_userID ";
            sql2 += "LEFT JOIN rms.Address ON fk_addressID = addressID ";
            sql2 += "WHERE userID = '" + userID + "'";
            setAllUserProperties(sql2);
            db.closeMySQLConnection();
        }

    }

    /**
     * Sets the User properties based on information retrieved from the database
     *
     * @param sql The SQL query used to retrieve the information from the
     * database
     */
    private void setAllUserProperties(String sql1) {
        db = new DbUtilities();
        System.out.println(sql1);
        try {
            ResultSet rs1 = db.getResultSet(sql1);
            while (rs1.next()) {
                if (this.userID == null || Security.checkPassword(this.inputPassword, rs1.getString("password"))) {
                    this.userID = (rs1.getString("userID"));
                    this.lastName = (rs1.getString("lastName"));
                    this.firstName = (rs1.getString("firstName"));
                    this.middleInitial = (rs1.getString("middleInitial"));
                    this.login = (rs1.getString("login"));
                    this.password = (rs1.getString("password"));
                    this.email = (rs1.getString("email"));
                    this.phoneNumber = (rs1.getString("phoneNumber"));
                    if (rs1.getString("addressID") == null) {
                    } else {
                        Address address = new Address(rs1.getString("addressID"));
                        this.addresses.add(address);
                    }
                    this.created = rs1.getTimestamp("created").toString();
                    this.modified = rs1.getTimestamp("modified").toString();
                    this.status = rs1.getInt("enabled");
                } else {
                    return;
                }

            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllUserProperties() method of User class. " + ex.getMessage());
            ErrorLogger.log(sql1);
        }

        String sql2 = "SELECT * FROM rms.Student WHERE fk_userID = '" + this.userID + "';";
        try {
            ResultSet rs2 = db.getResultSet(sql2);
            if (rs2.next()) {
                this.roles.add("student");
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllUserProperties() method of User class. " + ex.getMessage());
            ErrorLogger.log(sql2);
        }

        String sql3 = "SELECT * FROM rms.Staff WHERE fk_userID = '" + this.userID + "';";
        try {
            ResultSet rs3 = db.getResultSet(sql3);
            while (rs3.next()) {
                this.roles.add("staff");
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllUserProperties() method of User class. " + ex.getMessage());
            ErrorLogger.log(sql3);
        }
        String sql4 = "SELECT * FROM rms.Employer WHERE fk_userID = '" + this.userID + "';";
        try {
            ResultSet rs4 = db.getResultSet(sql4);
            while (rs4.next()) {
                this.roles.add("employer");
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllUserProperties() method of User class. " + ex.getMessage());
            ErrorLogger.log(sql4);
        }

        String sql5 = "SELECT * FROM rms.Resume WHERE fk_userID = '" + this.userID + "';";
        try {
            ResultSet rs5 = db.getResultSet(sql5);
            while (rs5.next()) {
                Resume resume = new Resume(rs5.getString("resumeID"));
                this.resumes.add(resume);
            }
        } catch (SQLException ex) {
            ErrorLogger.log("An error has occured in setAllUserProperties() method of User class. " + ex.getMessage());
            ErrorLogger.log(sql4);
        }
        db.closeMySQLConnection();
    }

    /**
     * Adds an address to this list of user addresses
     *
     * @param address
     */
    public void addAddress(Address address) {
        db = new DbUtilities();
        String sql = "INSERT INTO rms.UserAddress (fk_userID,fk_addressID) VALUES";
        sql += "('" + this.userID + "', '" + address.getAddressID() + "')";
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
     * Removes an address to this list of user addresses
     *
     * @param address
     */
    public void removeAddress(Address address) {
        db = new DbUtilities();
        String sql = "DELETE FROM rms.UserAddress WHERE fk_userID = '" + this.userID + "' AND fk_addressID = '" + address.getAddressID() + "';";
        sql += "('" + this.userID + "', '" + address.getAddressID() + "')";
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
     * Returns the user information as a JSON Object
     *
     * @return user The user info as a JSON Object
     */
    public JSONObject getUserAsJSON() {
        JSONObject user = new JSONObject();
        JSONArray userAddressList = new JSONArray();
        JSONArray userResumeList = new JSONArray();
        JSONArray userRoleList = new JSONArray();
        try {
            user.put("userID", this.getUserID());
            user.put("lastName", this.getLastName());
            user.put("firstName", this.getFirstName());
            user.put("middleInital", this.getMiddleInitial());
            user.put("login", this.getLogin());
            user.put("password", this.getPassword());
            user.put("email", this.getEmail());
            user.put("phoneNumber", this.getPhoneNumber());
            user.put("peopleSoftID", this.getPeopleSoftID());
            user.put("graduationYear", this.getGraduationYear());
            user.put("placeOfWork", this.getPlaceOfWork());
            user.put("industry", this.getIndustry());
            user.put("employeeID", this.getEmployeeID());
            user.put("position", this.getPosition());
            user.put("created", this.created);
            user.put("modified", this.modified);
            user.put("status", this.status);

            for (Address address : addresses) {
                userAddressList.put(address.getAddressAsJson());
            }

            user.put("addresses", userAddressList);

            for (String role : roles) {
                userRoleList.put(role);
            }
            user.put("roles", userRoleList);

            for (Resume resume : resumes) {
                userResumeList.put(resume.getResumeAsJson());
            }
            user.put("resumes", userResumeList);

        } catch (JSONException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;

    }

    public ArrayList<Address> getAddresses() {
        return this.addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    private void setAddresses(ArrayList<Address> addresses) {
        db = new DbUtilities();

        for (Address address : addresses) {
            String sql = "INSERT INTO rms.UserAddress";
            sql += "(fk_userID, fk_addressID)";
            sql += " VALUES (";
            sql += "'" + this.userID + "', ";
            sql += "'" + address.getAddressID() + "');";
            try {
                db.executeQuery(sql);
            } catch (Exception ex) {
                ErrorLogger.log("An error has occurred with the insert query inside of the setAddresses method. " + ex.getMessage());
                ErrorLogger.log(sql);
            } finally {
                db.closeMySQLConnection();
            }

            this.addresses = addresses;
        }
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET lastName = '" + lastName + "' WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setLastName method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET firstName = '" + firstName + "' WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setFirstName method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.firstName = firstName;
    }

    /**
     * @return the middleInitial
     */
    public String getMiddleInitial() {
        return middleInitial;
    }

    /**
     * @param middleInitial the middleInitial to set
     */
    public void setMiddleInitial(String middleInitial) {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET middleInitial = '" + middleInitial + "' WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setMiddleInitial method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.middleInitial = middleInitial;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET phoneNumber = '" + phoneNumber + "' WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setPhoneNumber method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET email = '" + email + "' WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setEmail method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.email = email;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET login = '" + login + "' WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setLogin method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }

        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {

        db = new DbUtilities();

        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        String sql = "UPDATE rms.User SET password = '" + encryptedPassword + "' WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setPassword method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.password = password;
    }

    /**
     * @return the role
     */
    public ArrayList<String> getRoles() {
        return this.roles;
    }

    /**
     * @param peopleSoftID
     * @param graduationYear
     *
     */
    public void setRoleStudent(String peopleSoftID, Date graduationYear) {

        db = new DbUtilities();

        if (!roles.contains("student")) {
            String role = "student";
            roles.add(role);
            this.setPeopleSoftID(peopleSoftID);
            this.setGraduationYear(graduationYear);

            db = new DbUtilities();
            String sql = "INSERT INTO rms.Student";
            sql += "(peopleSoftID, graduationYear, fk_userID)";
            sql += " VALUES (";
            sql += "'" + StringUtilities.cleanMySqlInsert(peopleSoftID) + "', ";
            sql += "'" + DATE_FORMAT.format(graduationYear) + "', ";
            sql += "'" + this.userID + "'); ";
            try {
                db.executeQuery(sql);
            } catch (Exception ex) {
                ErrorLogger.log("An error has occurred with the insert query inside of the setRoleStudent method. " + ex.getMessage());
                ErrorLogger.log(sql);

            } finally {
                db.closeMySQLConnection();

            }
        }
    }

    /**
     *
     * @param placeOfWork
     * @param industry
     */
    public void setRoleEmployer(String placeOfWork, String industry) {

        db = new DbUtilities();

        if (!roles.contains("emploer")) {
            String role = "employer";
            roles.add(role);
            this.setPlaceOfWork(placeOfWork);
            //this.setIndustry(industry);

            db = new DbUtilities();
            String sql = "INSERT INTO rms.Employer";
            sql += "(placeOfWork, industry, fk_userID)";
            sql += " VALUES (";
            sql += "'" + StringUtilities.cleanMySqlInsert(placeOfWork) + "', ";
            sql += "'" + StringUtilities.cleanMySqlInsert(industry) + "', ";
            sql += "'" + this.userID + "'); ";
            try {
                db.executeQuery(sql);
            } catch (Exception ex) {
                ErrorLogger.log("An error has occurred with the insert query inside of the setRoleEmployer method. " + ex.getMessage());
                ErrorLogger.log(sql);

            } finally {
                db.closeMySQLConnection();

            }
        }
    }

    /**
     *
     * @param employeeID
     * @param position
     */
    public void setRoleStaff(String employeeID, String position) {

        db = new DbUtilities();

        if (!roles.contains("staff")) {
            String role = "staff";
            roles.add(role);
            this.setEmployeeID(employeeID);
            this.setPosition(position);

            db = new DbUtilities();
            String sql = "INSERT INTO rms.Staff";
            sql += "(employeeID, position, fk_userID)";
            sql += " VALUES (";
            sql += "'" + StringUtilities.cleanMySqlInsert(employeeID) + "', ";
            sql += "'" + StringUtilities.cleanMySqlInsert(position) + "', ";
            sql += "'" + this.userID + "'); ";
            try {
                db.executeQuery(sql);
            } catch (Exception ex) {
                ErrorLogger.log("An error has occurred with the insert query inside of the setRoleStaff method. " + ex.getMessage());
                ErrorLogger.log(sql);

            } finally {
                db.closeMySQLConnection();

            }
        }
    }

    /**
     *
     * @param role
     */
    public void removeRole(String role) {
        db = new DbUtilities();
        String sql = "";
        if (role.equals("student")) {
            roles.remove("student");
            sql = "DELETE FROM rms.Student WHERE fk_userID = '" + this.userID + "';";
            this.setPeopleSoftID("");

            // this.setGraduationYear("");
            // this.setGraduationYear("");
        }
        if (role.equals("employer")) {
            roles.remove("employer");
            sql = "DELETE FROM rms.Employer WHERE fk_userID = '" + this.userID + "';";
            this.setPlaceOfWork("");
//            this.setIndustry("");
        }

        if (role.equals("staff")) {
            roles.remove("staff");
            sql = "DELETE FROM rms.Staff WHERE fk_userID = '" + this.userID + "';";
            this.setEmployeeID("");
            this.setPosition("");
        }

        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the Delete query inside of the removeRole method. " + ex.getMessage());
            ErrorLogger.log(sql);
        }
        db.closeMySQLConnection();
    }

    /**
     * @return the peopleSoftID
     */
    public String getPeopleSoftID() {
        return peopleSoftID;
    }

    /**
     * @param peopleSoftID the peopleSoftID to set
     */
    public void setPeopleSoftID(String peopleSoftID) {
        db = new DbUtilities();
        String sql = "UPDATE rms.Student SET peopleSoftID = '" + peopleSoftID + "' WHERE fk_userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setPeopleSoftID method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.peopleSoftID = peopleSoftID;
    }

    /**
     * @return the graduationYear
     */
    public Date getGraduationYear() {
        return graduationYear;
    }

    /**
     * @param graduationYear the graduationYear to set
     */
    public void setGraduationYear(Date graduationYear) {
        db = new DbUtilities();
        String sql = "UPDATE rms.Student SET graduationYear= '" + DATE_FORMAT.format(graduationYear) + "' WHERE fk_userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in with the insert query inside of setGraduationYear. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.graduationYear = graduationYear;
    }

    /**
     * @return the placeOfWork
     */
    public String getPlaceOfWork() {
        return placeOfWork;
    }

    /**
     * @param placeOfWork the placeOfWork to set
     */
    public void setPlaceOfWork(String placeOfWork) {
        db = new DbUtilities();
        String sql = "UPDATE rms.Employer SET placeOfWork = '" + placeOfWork + "' WHERE fk_userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setPlaceOfWork method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.placeOfWork = placeOfWork;
    }

    /**
     * @return the industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * @param industry the industry to set
     */
    public void setIndustry(String industry) {
        db = new DbUtilities();
        String sql = "UPDATE rms.Industry SET industry = '" + industry + "' WHERE fk_userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setIndustry method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.industry = industry;
    }

    /**
     * @return the employeeID
     */
    public String getEmployeeID() {
        return employeeID;
    }

    /**
     * @param employeeID the employeeID to set
     */
    public void setEmployeeID(String employeeID) {
        db = new DbUtilities();
        String sql = "UPDATE rms.Staff SET employeeID = '" + employeeID + "' WHERE fk_userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setEmployeeID method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.employeeID = employeeID;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        db = new DbUtilities();
        String sql = "UPDATE rms.Staff SET position = '" + position + "' WHERE fk_userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setPosition method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.position = position;
    }
    /*
     * Sets user status to enabled
     */

    public void setEnabled() {

        db = new DbUtilities();

        String sql = "UPDATE rms.User SET enabled = 1  WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setPosition method. " + ex.getMessage());
            ErrorLogger.log(sql);

        } finally {
            db.closeMySQLConnection();

        }
        this.status = 1;
    }

    /*
     * Sets user status to disabled
     */
    public void setDisabled() {

        db = new DbUtilities();

        String sql = "UPDATE rms.User SET enabled = 0  WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setPosition method. " + ex.getMessage());
            ErrorLogger.log(sql);

        } finally {
            db.closeMySQLConnection();
        }
        this.status = 0;
    }
    /*
     * Sets user US status to enabled
     */

    public void setUSEligibleEnabled() {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET usEligible = 1  WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setUSEligibleEnabled method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.usEligible = 1;
    }
    /*
     * Sets user US status to disables
     */

    public void setUSEligibleDisabled() {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET usEligible = 0 WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setUSEligibleDisabled method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.usEligible = 0;
    }
    /*
     * Sets user US status to enabled
     */

    public void setUSProofEnabled() {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET usProof = 1  WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setUSProofEnabled method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.usProof = 1;
    }
    /*
     * Sets user US status to disables
     */

    public void setUSProofDisabled() {
        db = new DbUtilities();
        String sql = "UPDATE rms.User SET usProof = 0 WHERE userID = '" + this.userID + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred with the insert query inside of the setUSProofDisabled method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }
        this.usProof = 0;
        this.status = 0;
    }

    //}
    /**
     * @return the employeeID
     */
    public ArrayList getResumes() {
        return resumes;
    }

    /**
     * Adds resumes to the list of user addresses
     *
     * @param resumes
     */
    public void setResumes(ArrayList<Resume> resumes) {
        for (Resume resume : resumes) {
            resumes.add(resume);
        }

    }

    /**
     * Adds a resume to the list of user resumes
     *
     * @param resume
     */
    public void addResume(Resume resume) {
        resumes.add(resume);

    }

    /**
     * Removes a resume from the list of user resumes
     *
     * @param resume
     */
    public void removeResume(Resume resume) {
        db = new DbUtilities();
        String sql = "DELETE FROM rms.Resume WHERE resumeID = '" + resume.getResumeID() + "';";
        try {
            db.executeQuery(sql);
        } catch (Exception ex) {
            ErrorLogger.log("An error has occurred in the insert query inside of removeResume method. " + ex.getMessage());
            ErrorLogger.log(sql);
        } finally {
            db.closeMySQLConnection();
        }

        resumes.remove(resume);
    }

}
