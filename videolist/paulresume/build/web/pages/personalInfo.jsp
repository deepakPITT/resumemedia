<%--
    Document   : personalInfo
    Created on : Apr 15, 2014, 9:02:43 PM
    Author     : Mandy
--%>

<%@page import="edu.pitt.utilities.Security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>

<div id="mainContainer" class="container theme-showcase" style="width:900px">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><strong>Create Your Resume</strong></h3>
        </div>
        <div class="panel-body">
            <div class="page-header">
                <form class="frmPersonalInfo" method="post" action="../process/processPersonalInfo">
                    <h2 class="form-signin-heading">Personal Information</h2><br>
                    <input type="text" class="form-control" id="txtFirstName" name="txtFirstName" placeholder="First Name" autofocus="" required><br>
                    <input type="text" class="form-control" id="txtLastName" name="txtLastName" placeholder="Last Name" required><br>
                    <input type="text" class="form-control" id="txtAddressLine1" name="txtAddressLine1" placeholder="Street Address" required><br>
                    <input type="text" class="form-control" id="txtAddressLine2" name="txtAddressLine2" placeholder="Address"><br>
                    <input type="text" class="form-control" id="txtCity" name="txtCity" placeholder="City" required style="width:50%; display:inline">

                    <select class="form-control" id="txtState" name="txtState" required style="width:49%; display:inline">
                        <option value="">State</option>
                        <option value="AL">Alabama</option>
                        <option value="AK">Alaska</option>
                        <option value="AZ">Arizona</option>
                        <option value="AR">Arkansas</option>
                        <option value="CA">California</option>
                        <option value="CO">Colorado</option>
                        <option value="CT">Connecticut</option>
                        <option value="DE">Delaware</option>
                        <option value="DC">District Of Columbia</option>
                        <option value="FL">Florida</option>
                        <option value="GA">Georgia</option>
                        <option value="HI">Hawaii</option>
                        <option value="ID">Idaho</option>
                        <option value="IL">Illinois</option>
                        <option value="IN">Indiana</option>
                        <option value="IA">Iowa</option>
                        <option value="KS">Kansas</option>
                        <option value="KY">Kentucky</option>
                        <option value="LA">Louisiana</option>
                        <option value="ME">Maine</option>
                        <option value="MD">Maryland</option>
                        <option value="MA">Massachusetts</option>
                        <option value="MI">Michigan</option>
                        <option value="MN">Minnesota</option>
                        <option value="MS">Mississippi</option>
                        <option value="MO">Missouri</option>
                        <option value="MT">Montana</option>
                        <option value="NE">Nebraska</option>
                        <option value="NV">Nevada</option>
                        <option value="NH">New Hampshire</option>
                        <option value="NJ">New Jersey</option>
                        <option value="NM">New Mexico</option>
                        <option value="NY">New York</option>
                        <option value="NC">North Carolina</option>
                        <option value="ND">North Dakota</option>
                        <option value="OH">Ohio</option>
                        <option value="OK">Oklahoma</option>
                        <option value="OR">Oregon</option>
                        <option value="PA">Pennsylvania</option>
                        <option value="RI">Rhode Island</option>
                        <option value="SC">South Carolina</option>
                        <option value="SD">South Dakota</option>
                        <option value="TN">Tennessee</option>
                        <option value="TX">Texas</option>
                        <option value="UT">Utah</option>
                        <option value="VT">Vermont</option>
                        <option value="VA">Virginia</option>
                        <option value="WA">Washington</option>
                        <option value="WV">West Virginia</option>
                        <option value="WI">Wisconsin</option>
                        <option value="WY">Wyoming</option>
                        <option value="AS">American Samoa</option>
                        <option value="GU">Guam</option>
                        <option value="MP">Northern Mariana Islands</option>
                        <option value="PR">Puerto Rico</option>
                        <option value="UM">United States Minor Outlying Islands</option>
                        <option value="VI">Virgin Islands</option>
                    </select><br><br>
                    
                    <input type="text" class="form-control" id="txtProvince" name="txtProvince" placeholder="Province" style="width:40%; display:inline">
                    <input type="text" class="form-control" id="txtCountry" name="txtCountry" placeholder="Country" style="width:38%; display:inline">
                    <input type="text" class="form-control" id="txtPostalCode" name="txtPostalCode" placeholder="Postal Code" required style="width:20%; display:inline"><br><br>
                    <input type="tel" class="form-control" id="txtPhone" name="txtPhone" placeholder="Phone Number: xxx-xxx-xxxx" required pattern="\d{3}-?\d{3}-?\d{4}"style="width:50%; display:inline">
                    <input type="email" class="form-control" id="txtEmail" name="txtEmail" placeholder="Email" required style="width:49%; display:inline"><br><br>

                    <label> Are You Legally Eligible to Work in the US?</label>
                    <input type="radio" id="radUSEligibleTrue" name="radUSEligible" value="true" required> Yes
                    <input type="radio" id="radUSEligibleFalse" name="radUSEligible" value="false" required> No<br><br>

                    <label> Can you provide evidence of your legal eligibility to work in the US? </label>
                    <input type="radio" id="radWkEvidenceTrue" name="radWkEvidence" value="true" required> Yes
                    <input type="radio" id="radWkEvidenceFalse" name="radWkEvidence" value="false" required> No<br><br>

                    <button class="btn btn-lg btn-default" type="submit" name="btnSubmitPersonalInfo" id="btnSubmitPersonalInfo">Next</button>
                </form><br>

            </div>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
