package com.alexkekiy.server.data.repositories;

import com.alexkekiy.server.data.dao.SpaceMarineDao;
import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public class SpaceMarineRepository extends DBRepository<SpaceMarineEntity> {
    private final SpaceMarineDao spmDao;

    @Autowired

    SpaceMarineRepository(SpaceMarineDao spmDao) {
        super(spmDao);
        this.spmDao = spmDao;

    }

    @Transactional

    public List<SpaceMarineEntity> getAll() {
        try {
            return spmDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Transactional
    public List<SpaceMarineEntity> getAllByUserId(long userId) {

        try {
            return spmDao.getAllByUserId(userId)/*.stream().peek(w->spmDao.update(w)).toList()*/;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();

    }

    @Transactional

    public void removeAll(Collection<SpaceMarineEntity> collection) {
        try {
            collection.forEach(spmDao::delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional

    public void addAll(Collection<SpaceMarineEntity> collection) {
        try {
            collection.forEach(spmDao::save);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Transactional
    @Override
    public void add(SpaceMarineEntity spaceMarine) {
        jbdcSpaceMarineDao.save(spaceMarine);

    }

    @Override
    @Transactional
    public void remove(SpaceMarineEntity spaceMarine) {
        jbdcSpaceMarineDao.delete(spaceMarine);
    }

     */


}
