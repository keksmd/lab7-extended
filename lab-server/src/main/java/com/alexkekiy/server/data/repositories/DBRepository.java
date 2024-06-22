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
        try {
            dao.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void remove(T entity) {
        try {
            dao.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional

    public void add(T entity) {
        try {
            dao.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Optional<T> get(int id) {
        Optional<T> optional;
        try {
            optional = dao.get(id);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return optional;

    }

}
