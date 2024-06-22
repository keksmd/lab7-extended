package com.alexkekiy.server.data.dao;

import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Component
public class SpaceMarineDao implements Dao<SpaceMarineEntity> {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<SpaceMarineEntity> query;
    private final Root<SpaceMarineEntity> root;

    @Autowired
    public SpaceMarineDao(EntityManager entityManager) {
        this.entityManager = entityManager;

        criteriaBuilder = entityManager.getCriteriaBuilder();
        query = criteriaBuilder.createQuery(SpaceMarineEntity.class);
        root = query.from(SpaceMarineEntity.class);

    }

    public List<SpaceMarineEntity> getAllByUserId(long userId) {
        try {
            return entityManager.createQuery(query.where(criteriaBuilder.equal(root.get("user_id"), userId))).getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<SpaceMarineEntity> getAll() {
        try {
            query.select(root);
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void save(SpaceMarineEntity spm) {
        entityManager.persist(spm);
    }

    @Override
    public Optional<SpaceMarineEntity> get(long id) {
        Optional<SpaceMarineEntity> spm;
        try {
            spm = Optional.ofNullable(entityManager.find(SpaceMarineEntity.class, id));
        } catch (Exception e) {
            spm = Optional.empty();
        }
        return spm;
    }

    @Override
    public void commit() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void rollback() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void beginTransaction() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    @Override
    public void delete(SpaceMarineEntity spm) {
        entityManager.detach(spm);
    }

    @Override
    public void update(SpaceMarineEntity spm) {
        entityManager.merge(spm);
    }
}