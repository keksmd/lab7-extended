package com.alexkekiy.server.data.repositories;

import com.alexkekiy.server.data.dao.Dao;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * класс-шаблон для создания классов repository-слоя,инкапсулирующего dao методы и работу с транзакциями
 *
 * @param <T> - entity класс
 */

@Getter

public class DBRepository<T> {

    private final Dao<T> dao;


    public DBRepository(Dao<T> dao) {
        this.dao = dao;

    }

    @Transactional
    public void update(T entity) {
        dao.beginTransaction();
        try {
            dao.update(entity);
            dao.commit();
        } catch (Exception e) {
            e.printStackTrace();
            dao.rollback();
        }
    }

    @Transactional
    public void remove(T entity) {
        dao.beginTransaction();
        try {
            dao.delete(entity);
            dao.commit();
        } catch (Exception e) {
            e.printStackTrace();
            dao.rollback();
        }
    }

    @Transactional

    public void add(T entity) {
        dao.beginTransaction();
        try {

            dao.save(entity);
            dao.commit();
        } catch (Exception e) {
            e.printStackTrace();
            dao.rollback();
        }
    }

    @Transactional
    public Optional<T> get(long id) {
        dao.beginTransaction();
        Optional<T> optional;
        try {
            optional = dao.get(id);
            dao.commit();
        } catch (Exception e) {
            e.printStackTrace();
            dao.rollback();
            return Optional.empty();

        }

        return optional;

    }

}
