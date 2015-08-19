package com.grasshopper.vnote.dao;

import com.grasshopper.vnote.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Anisul on 8/15/2015.
 */
public interface Dao {
    public EntityManager getEntityManager();

    public User getUser(Long id);

    public void addUser(User user);
}
