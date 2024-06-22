package com.alexkekiy.server.data.dao;

import com.alexkekiy.server.data.entities.AccountEntity;
import com.alexkekiy.server.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class JBDCServerAccountDao implements Dao<AccountEntity> {

    Connection con;

    public JBDCServerAccountDao(DBConnection con) {
        this.con = con.getConnection();
        try {
            this.con.setAutoCommit(true);
            this.con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException ignored) {

        }
    }

    public Optional<AccountEntity> get(String login) {
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
    public void save(AccountEntity serverAccount) {
        try (PreparedStatement stmt = con.prepareStatement("INSERT INTO users (password, id, login) VALUES (?, ?, ?)")) {
            stmt.setString(1, serverAccount.getPassword());
            stmt.setLong(2, serverAccount.getId());
            stmt.setString(3, serverAccount.getLogin());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountEntity> get(long id) {
        throw new AssertionError();
    }

    @Override
    public void delete(AccountEntity serverAccount) {

        try (PreparedStatement stmt = con.prepareStatement("DELETE FROM users  where (password, id, login) = (?, ?, ?)")) {

            stmt.setString(1, serverAccount.getPassword());

            stmt.setLong(2, serverAccount.getId());
            stmt.setString(3, serverAccount.getLogin());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }

    @Override
    public void update(AccountEntity serverAccount) {
        this.save(serverAccount);
    }


}