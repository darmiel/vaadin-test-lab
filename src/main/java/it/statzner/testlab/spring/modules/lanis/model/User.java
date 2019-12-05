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

package it.statzner.testlab.spring.modules.lanis.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

    public String firstName;
    public String lastName;
    public String userName;
    public String password;
    public Role role;
    public String className;
    public Date birthDay;
    public String email;
    public int yearsOld;

    @Override
    public String toString() {
        return "User {" + firstName + " " + lastName + " (" + email + ") [" + role.name() + (role.getCustomRole() != null ? " (" + role.getCustomRole() + ")" : "") + ", " + className + " | " + new SimpleDateFormat("dd.MM.yyyy").format(birthDay) + " | " + password + "}";
    }


    public boolean isPasswordDefault() {
        return password.equals(new SimpleDateFormat("ddMMyyyy").format(birthDay));
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getClassName() {
        return className;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public String getBirthDayString() {
        return new SimpleDateFormat("dd.MM.yyyy").format(this.birthDay);
    }

    public String getEmail() {
        return email;
    }

    public int getYearsOld() {
        return yearsOld;
    }
}
