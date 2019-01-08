/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.songsrx.restserver.bean.User;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Singleton;

/**
 *
 * @author smadi
 */
@Singleton
public class FSUsersDAO implements UsersDAO {

    private volatile TreeMap<String, User> storage = new TreeMap<String, User>();

    private final String filename = "/users.json";

    public FSUsersDAO() {
        Map<String, User> users = readUsersFromFile();
        if (users != null) {
            storage = new TreeMap<>(users);
        }
    }

    private Map<String, User> readUsersFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = FSUsersDAO.class.getResourceAsStream(filename)) {
            List<User> OurUsersList = (List<User>) objectMapper.readValue(is, new TypeReference<List<User>>() {
            });
            return OurUsersList.stream().collect(Collectors.toMap(User::getUserId, User -> User));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FSUsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FSUsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User getUser(String userId) {
        if (storage.get(userId) == null) {
            return null;
        }
        return storage.get(userId);
    }

}
