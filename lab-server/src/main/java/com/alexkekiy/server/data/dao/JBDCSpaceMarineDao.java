package com.alexkekiy.server.data.dao;

import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import com.alexkekiy.server.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

public class JBDCSpaceMarineDao implements Dao<SpaceMarineEntity> {

    Connection con;

    public JBDCSpaceMarineDao(DBConnection con) {
        this.con = con.getConnection();
        try {
            this.con.setAutoCommit(true);
            this.con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException ignored) {

        }
    }

    public Optional<SpaceMarineEntity> get(String login) {
        throw new AssertionError();
    }

    @Override


    public void commit() {
        try {
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void beginTransaction() {
        try {
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(SpaceMarineEntity spaceMarine) {

        try (PreparedStatement stmt = con.prepareStatement("INSERT INTO spacemarines ( id, name, health, loyal, height, weapontype,chapter_id, coordinate_id,creationdate,user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setLong(1, spaceMarine.getId());
            stmt.setString(2, spaceMarine.getName());
            stmt.setLong(3, spaceMarine.getHealth());
            stmt.setBoolean(4, spaceMarine.getLoyal());
            stmt.setFloat(5, spaceMarine.getHeight());
            stmt.setObject(6, String.valueOf(spaceMarine.getWeaponType().ordinal()), Types.VARCHAR);
            stmt.setLong(7, spaceMarine.getChapterEntity().getId());
            stmt.setLong(8, spaceMarine.getCoordinatesEntity().getId());
            stmt.setObject(9, spaceMarine.getCreationDate(), Types.TIMESTAMP);
            stmt.setLong(10, spaceMarine.getOwner().getId());

            PreparedStatement coordinatesStatement = con.prepareStatement("INSERT INTO coordinates ( id, x, y) VALUES (?, ?, ?)");
            coordinatesStatement.setLong(1, spaceMarine.getCoordinatesEntity().getId());
            coordinatesStatement.setLong(2, spaceMarine.getCoordinatesEntity().getX());
            coordinatesStatement.setFloat(3, spaceMarine.getCoordinatesEntity().getY());
            coordinatesStatement.execute();

            PreparedStatement chapterStatement = con.prepareStatement("INSERT INTO chapters ( id, name, world) VALUES (?, ?, ?)");
            chapterStatement.setLong(1, spaceMarine.getChapterEntity().getId());
            chapterStatement.setString(2, spaceMarine.getChapterEntity().getName());
            chapterStatement.setString(3, spaceMarine.getChapterEntity().getWorld());
            chapterStatement.execute();

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SpaceMarineEntity> get(long id) {
        throw new AssertionError();
    }

    @Override
    public void delete(SpaceMarineEntity spaceMarine) {

        try (PreparedStatement stmt = con.prepareStatement("DELETE FROM spacemarines  where (id) = (?)")) {
            stmt.setLong(1, spaceMarine.getId());
            PreparedStatement coordinatesStmt = con.prepareStatement("DELETE FROM coordinates  where (id) = (?)");
            coordinatesStmt.setLong(1, spaceMarine.getCoordinatesEntity().getId());
            coordinatesStmt.addBatch();
            PreparedStatement chapterStmt = con.prepareStatement("DELETE FROM chapters  where (id) = (?)");
            chapterStmt.setLong(1, spaceMarine.getChapterEntity().getId());
            chapterStmt.addBatch();
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void update(SpaceMarineEntity spaceMarine) {
        this.save(spaceMarine);
    }


}