// UserDAO.java
package Dao;

import BD.Config;
import BD.MyConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDAO {
    private Connection con;

    public ManagerDAO() {
        con = MyConnexion.getConnexion(Config.url, Config.userName, Config.pass);
    }

    public int addUser(Classes.Manager manager) {
        int rowsAffected = 0;
        try {
            String query = "INSERT INTO manager (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, manager.getUsername());
            preparedStatement.setString(2, manager.getPassword());
            rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public ResultSet searchUser(String username) {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM manager WHERE username = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
