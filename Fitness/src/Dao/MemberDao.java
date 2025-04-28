package Dao;

import BD.Config;
import BD.MyConnexion;
import Classes.Member;

import java.sql.*;

public class MemberDao {
    private Connection con;

    public MemberDao() {
        con = MyConnexion.getConnexion(Config.url, Config.userName, Config.pass);
    }

    public int addMember(Member member) {
        int rowsAffected = 0;
        try {

            String query = "INSERT INTO member (name, prename, age, paid) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getPrename());
            preparedStatement.setInt(3, member.getAge());
            preparedStatement.setBoolean(4, member.isPaid());

            rowsAffected = preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    member.setId(generatedKeys.getInt(1)); // Set the generated ID back to the member object
                }
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }


    public int updateMember(Member member) {
        int rowsAffected = 0;
        try {
            String query = "UPDATE member SET name=?, prename=?, age=?, paid=? WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getPrename());
            preparedStatement.setInt(3, member.getAge());
            preparedStatement.setBoolean(4, member.isPaid());
            preparedStatement.setInt(5, member.getId());
            rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public int deleteMember(int id) {
        int rowsAffected = 0;
        try {
            String query = "DELETE FROM member WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public ResultSet getAllMembers() {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM member";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet searchMember(int id) {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM member WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet searchMemberByName(String memberName) {
        return null ;
    }
}
