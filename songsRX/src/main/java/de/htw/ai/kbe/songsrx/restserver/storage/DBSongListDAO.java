/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.storage;

import de.htw.ai.kbe.songsrx.restserver.bean.Song;
import de.htw.ai.kbe.songsrx.restserver.bean.SongList;
import de.htw.ai.kbe.songsrx.restserver.bean.User;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 *
 * @author smadi
 */
public class DBSongListDAO implements SongListDAO {

    private EntityManagerFactory emf;

    @Inject
    public DBSongListDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<SongList> getAllSongLists() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SongList> query = em.createQuery("SELECT s FROM SongList s", SongList.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public SongList getSongListById(int id) {
        EntityManager em = emf.createEntityManager();
        SongList entity = null;
        try {
            entity = em.find(SongList.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public List<SongList> getAllSongListsByUserId(User user, boolean isOwner) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SongList> query;
            if (isOwner) {
                query = em.createQuery("SELECT sl FROM SongList AS sl WHERE sl.owner = :owner", SongList.class);
            } else {
                query = em.createQuery("SELECT sl FROM SongList AS sl WHERE sl.owner = :owner and sl.isPublic = true", SongList.class);
            }
            query.setParameter("owner", user);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public SongList addSongList(SongList songList) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            songList.setId(null);
            em.persist(songList);
            transaction.commit();
            return songList;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding SongList: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delSongList(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        SongList songList = null;
        try {
            songList = em.find(SongList.class, id);
            if (songList != null) {
                System.out.println("Deleting SongList with ID: " + songList.getId());
                transaction.begin();
                em.remove(songList);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error removing SongList: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        } finally {
            em.close();
        }
        return false;
    }

}
