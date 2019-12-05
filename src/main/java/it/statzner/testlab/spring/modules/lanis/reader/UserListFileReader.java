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

import it.statzner.testlab.spring.modules.lanis.model.User;
import it.statzner.testlab.spring.modules.lanis.thr.InvalidUserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class UserListFileReader {

    public List<User> readUserList(File file) throws IOException, ParseException, InvalidUserException {
        List<User> res = new LinkedList<User>();

        for (String line : Files.readAllLines(file.toPath())) {
            line = line.trim();

            String decryptedLine = LANiSDecrypt.decryptLine(line);

            User user = UserReader.readUser(decryptedLine);
            res.add(user);
        }

        return res;
    }

}
