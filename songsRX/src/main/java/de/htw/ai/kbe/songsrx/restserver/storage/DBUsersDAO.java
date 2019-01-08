/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.ai.kbe.songsrx.restserver.storage;

import de.htw.ai.kbe.songsrx.restserver.bean.User;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author smadi
 */
@Singleton
public class DBUsersDAO implements UsersDAO {

    private EntityManagerFactory emf;

    @Inject
    public DBUsersDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public User getUser(String userId) {
        EntityManager em = emf.createEntityManager();
        User entity = null;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User AS u WHERE u.userId = :userId", User.class);
            query.setParameter("userId", userId);
            if (query.getResultList().size() > 0) {
                entity = query.getSingleResult();
            }
        } finally {
            em.close();
        }
        return entity;
    }

}
