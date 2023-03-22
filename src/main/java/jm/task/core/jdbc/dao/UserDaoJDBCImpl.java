package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        Connection connection1 = null;
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age TINYINT)");
            connection.commit();
            connection1 = connection;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void dropUsersTable() {
        Connection connection1 = null;
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();
            connection1 = connection;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection1 = null;
        try (Connection connection = Util.getConnection(); PreparedStatement prstatement = connection.prepareStatement(
                "insert into users (name, last_name, age) values( ?, ?, ?)");) {
            connection.setAutoCommit(false);
            prstatement.setString(1, name);
            prstatement.setString(2, lastName);
            prstatement.setByte(3, age);
            prstatement.executeUpdate();
            connection.commit();
            connection1 = connection;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection1 = null;
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE FROM users WHERE id = " + id);
            connection.commit();
            connection1 = connection;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Connection connection1 = null;
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            connection.commit();
            connection1 = connection;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        Connection connection1 = null;
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE FROM users");
            connection.commit();
            connection1 = connection;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection1.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}