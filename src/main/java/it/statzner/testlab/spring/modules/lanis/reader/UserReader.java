/*
 * Copyright (c) 2019.
 * Created at 16.11.2019
 * ---------------------------------------------
 * @author hyWse
 * @see https://hywse.eu
 * ---------------------------------------------
 * If you have any questions, please contact
 * E-Mail: admin@hywse.eu
 * Discord: hyWse#0126
 */

package it.statzner.testlab.spring.modules.lanis.reader;

import it.statzner.testlab.spring.modules.lanis.model.Role;
import it.statzner.testlab.spring.modules.lanis.model.User;
import it.statzner.testlab.spring.modules.lanis.thr.InvalidUserException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserReader {

    public static User readUser(String data) throws InvalidUserException, ParseException {
        if (data == null) {
            throw new NullPointerException("Data was null");
        }

        String[] splitted = data.split(";");
        if (splitted.length != 11) {
            throw new InvalidUserException(data);
        }

        User user = new User();
        user.firstName = splitted[0];
        user.lastName = splitted[1];
        user.userName = splitted[2];
        user.password = splitted[3];
        user.role = Role.getRole(splitted[4]);
        user.className = splitted[5];
        user.birthDay = new SimpleDateFormat("dd.MM.yyyy").parse(splitted[7]);
        user.email = splitted[8];
        user.yearsOld = 2019 - user.birthDay.getYear() - 1900;

        // Try to guess password
        try {
            // Default
            // System.out.println(user.password.substring(user.password.lastIndexOf("=")).length());
            if(user.password.contains("=")
                && user.password.substring(user.password.lastIndexOf("=") + 1).length() == 5) {
                user.password = new SimpleDateFormat("ddMMyyyy '(default)'").format(user.birthDay);
            } else {
                user.password = LANiSDecrypt.decryptLine(user.password);
            }
        } catch (Throwable t) {
            // System.out.println("Can't guess password for " + user.firstName + " " + user.lastName);
        }


        return user;
    }

}
