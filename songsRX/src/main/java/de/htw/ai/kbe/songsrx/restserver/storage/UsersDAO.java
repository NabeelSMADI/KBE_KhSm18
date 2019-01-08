/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.storage;

import de.htw.ai.kbe.songsrx.restserver.bean.User;

/**
 *
 * @author smadi
 */
public interface UsersDAO {
    
     public User getUser(String userId) ;
    
}
