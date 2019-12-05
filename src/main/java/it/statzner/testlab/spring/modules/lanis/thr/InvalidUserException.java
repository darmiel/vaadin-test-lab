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

package it.statzner.testlab.spring.modules.lanis.thr;

public class InvalidUserException extends Exception {

    private String data;

    public InvalidUserException(String data) {
        super("'" + data + "' is not a valid user");
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
