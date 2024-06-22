package com.alexkekiy.server.util;


import lombok.Getter;

import java.io.Closeable;
import java.sql.*;

public class DBConnection implements Closeable {
    @Getter
    private Connection connection;
    static DBConnection inst = null;
    private static String URL ="";
    private static String PSWD ="";
    private static String LOGIN ="";
    static {
        if(System.getenv("PERSIST").equals("local")){
            URL = "jdbc:postgresql://localhost:5432/postgres";
            LOGIN = "postgres";
            PSWD = "";
        }else{
            URL = "jdbc:postgresql://pg:5432/studs";
            LOGIN = "s374052";
            PSWD = "G0gTu9RURbnLGFZN";
        }
    }

    public static DBConnection getDBConnection() {
        if (inst == null) {
            try {
                inst = new DBConnection();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return inst;
    }


    private DBConnection() throws ClassNotFoundException, SQLException {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,LOGIN, PSWD);
            try (Statement statement = connection.createStatement();
                 PreparedStatement ps = connection.prepareStatement("CREATE SEQUENCE my_sequence_name")
            ) { //ps.execute();



                ResultSet resultSet = statement.executeQuery("SELECT nextval('my_sequence_name')");
                if (resultSet.next()) {
                    int newId = resultSet.getInt(1);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public long newId () {
            try (Statement statement = connection.createStatement();) {

                ResultSet resultSet = statement.executeQuery("SELECT nextval('my_sequence_name')");
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
            return Long.parseLong(String.valueOf(Math.random()));
        }

        @Override
        public void close () {
            try {
                connection.close();
                inst = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
