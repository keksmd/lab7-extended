package com.alexkekiy.server.data.dao;

import java.util.Optional;

/**
 * интерфейс data-acess-object,содержащий CRUD методы и работу с транзакциями
 *
 * @param <T> - entity класс
 */

public interface Dao<T> {

    Optional<T> get(long id);

    void commit();

    void rollback();

    void beginTransaction();

    void save(T t);

    void update(T t);

    void delete(T t);

}
