package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS user (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NULL, lastName VARCHAR(45) NULL, age TINYINT NULL, UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE, PRIMARY KEY (id))";
    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS user";
    private static final String CLEAR_USER_TABLES_CONTENTS = "TRUNCATE user";
    private static final String ADD_USER_TO_TABLE = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM user";

    Connection connection = Util.getConnection();

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_USER_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_TO_TABLE)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAR_USER_TABLES_CONTENTS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
