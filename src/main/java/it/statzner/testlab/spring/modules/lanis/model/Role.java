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

public enum Role {

    STUDENT("schüler"),
    TEACHER("lehrer"),
    OTHER;

    private String[] aliases;
    Role(String...aliases) {
        this.aliases = aliases;
    }

    private String customValue;

    public String getCustomRole() {
        return customValue;
    }

    public void setCustomRole(String customValue) {
        this.customValue = customValue;
    }

    public static Role getRole(String role) {
        role = role.toLowerCase().trim();

        for (Role value : values()) {
            if(value.name().toLowerCase().equalsIgnoreCase(role)) {
                return value;
            }

            // Check aliases
            for (String alias : value.aliases) {
                if(alias.toLowerCase().equalsIgnoreCase(role)) {
                    return value;
                }
            }
        }

        Role res = Role.OTHER;
        res.setCustomRole(role);
        return res;
    }

}
