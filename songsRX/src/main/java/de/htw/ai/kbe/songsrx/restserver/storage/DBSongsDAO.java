/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.storage;

import java.util.List;
import de.htw.ai.kbe.songsrx.restserver.bean.Song;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 *
 * @author smadi
 */
@Singleton
public class DBSongsDAO implements SongsDAO {

    private EntityManagerFactory emf;

    @Inject
    public DBSongsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Song addSong(Song song) {
        if (song.getTitle() == null) {
            return null;
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            song.setId(null);
            em.persist(song);
            transaction.commit();
            return song;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding Song: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Song> getAllSongs() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Song> query = em.createQuery("SELECT s FROM Song s", Song.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Song getSong(int id) {
        EntityManager em = emf.createEntityManager();
        Song entity = null;
        try {
            entity = em.find(Song.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public boolean updateSong(Song song) {
        EntityManager em = emf.createEntityManager();
        if (em.find(Song.class, song.getId()) == null) {
            return false;
        }
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(song);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding contact: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delSong(int id) {
        return false;
    }

    @Override
    public void preDestroy() {

    }
}
