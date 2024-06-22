package com.alexkekiy.server.data.dao;

import com.alexkekiy.server.data.entities.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Component
public class ServerAccountDao implements Dao<AccountEntity> {
    private final EntityManager em;
    private final CriteriaBuilder cb;
    private final CriteriaQuery<AccountEntity> query;
    private final Root<AccountEntity> root;
@Autowired
    public ServerAccountDao(EntityManager em) {
        this.em = em;
        cb = em.getCriteriaBuilder();
        query = cb.createQuery(AccountEntity.class);
        root = query.from(AccountEntity.class);
    }

    public Optional<AccountEntity> get(String login) {
        AccountEntity serverAccount = em.createQuery(query.where(cb.equal(root.get("login"), login))).getSingleResult();
        return Optional.ofNullable(serverAccount);
    }

    @Override
    public void commit() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }

    }

    @Override
    public void rollback() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
    @Override
    public void beginTransaction() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }
    @Override
    public void save(AccountEntity serverAccount) {
        em.persist(serverAccount);
    }
    @Override
    public Optional<AccountEntity> get(long id) {
        Optional<AccountEntity> acc;
        try {
            acc = Optional.ofNullable(em.find(AccountEntity.class, id));
        } catch (Exception e) {
            acc = Optional.empty();
        }
        return acc;
    }
    @Override
    public void delete(AccountEntity serverAccount) {
        em.detach(serverAccount);
    }
    @Override
    public void update(AccountEntity serverAccount) {
        em.merge(serverAccount);
    }

}