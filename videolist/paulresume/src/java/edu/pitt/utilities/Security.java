/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jasypt.util.password.*;

/**
 *
 * @author jordanstevenfeldman
 */
public class Security {

    public static boolean isValidSession(HttpSession session) {

        if (session != null) {
            return session.getAttribute("authenticatedUser") == null;
        } else {
            return false;
        }

    }

    public static boolean checkHijackedSession(HttpSession session, HttpServletRequest request) {

        if (isValidSession(session)) {
            if (request.getRemoteAddr() != session.getAttribute("authenticatedUserIP")) {
                if (request.getHeader("User-Agent").contains("Mobile")) {
                    return false;
                } else {
                    System.out.println("Client is not mobile and IP address has changed");
                    return true;
                }

            }
        }
        return false;
    }

    public static boolean checkPassword(String inputPassword, String encryptedUserPassword) {

        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        return passwordEncryptor.checkPassword(inputPassword, encryptedUserPassword);
    }
}
