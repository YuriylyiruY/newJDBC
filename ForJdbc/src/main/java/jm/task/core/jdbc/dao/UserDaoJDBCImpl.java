package jm.task.core.jdbc.dao;

import java.sql.*;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao  {

    private static final Connection connection = Util.getConnection();

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE users (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(50) NOT NULL,\n" +
                    "  `lastname` VARCHAR(50) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, lastname, age) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<> ();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getByte("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
