/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.config;

import de.htw.ai.kbe.songsrx.restserver.storage.DBSongListDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.DBSongsDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.DBUsersDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.SongListDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.SongsDAO;
import de.htw.ai.kbe.songsrx.restserver.storage.UsersDAO;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 *
 * @author smadi
 */
public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(Persistence
                .createEntityManagerFactory("songsRX-PU"))
                .to(EntityManagerFactory.class);
        bind(DBSongsDAO.class)
                .to(SongsDAO.class)
                .in(Singleton.class);
        bind(DBSongListDAO.class)
                .to(SongListDAO.class)
                .in(Singleton.class);
        bind(DBUsersDAO.class)
                .to(UsersDAO.class)
                .in(Singleton.class);
    }

}
