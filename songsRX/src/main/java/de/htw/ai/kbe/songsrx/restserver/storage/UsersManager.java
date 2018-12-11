/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.storage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.songsrx.restserver.RESTConfig;
import de.htw.ai.kbe.songsrx.restserver.bean.User;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author smadi
 */
public class UsersManager {

    private volatile static TreeMap<String, User> storage = new TreeMap<String, User>();

    private static UsersManager instance = null;
    private static final String filename = "/users.json";

    private UsersManager() {
        Map<String, User> users = readUsersFromFile();
        if (users != null) {
            storage = new TreeMap<>(users);
        }
    }

    public synchronized static UsersManager getInstance() {
        if (instance == null) {
            instance = new UsersManager();
        }
        return instance;
    }

    private Map<String, User> readUsersFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = UsersManager.class.getResourceAsStream(filename)) {
            List<User> OurUsersList = (List<User>) objectMapper.readValue(is, new TypeReference<List<User>>() {
            });
            return OurUsersList.stream().collect(Collectors.toMap(User::getUserId, User -> User));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UsersManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsersManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User getUser(String userId) {
        if (storage.get(userId) == null) {
            return null;
        }
        return storage.get(userId);
    }

}
