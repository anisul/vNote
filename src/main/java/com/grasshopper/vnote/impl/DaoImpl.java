package com.grasshopper.vnote.impl;

import com.grasshopper.vnote.dao.Dao;
import com.grasshopper.vnote.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Anisul on 8/15/2015.
 */

@Repository
public class DaoImpl implements Dao{
    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    public void addUser(User user) {
        entityManager.persist(user);
    }
}
