package Dao;

import BD.Config;
import BD.MyConnexion;
import Classes.Course;
import Classes.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDao {
    private Connection con;

    public CourseDao() {
        con = MyConnexion.getConnexion(Config.url, Config.userName, Config.pass);
    }

    public int addCourse(Course course) {
        int rowsAffected = 0;
        try {
            String query = "INSERT INTO course (name, description, id) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());

            preparedStatement.setInt(3, course.getId());
            rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public int updateCourse(Course course) {
        int rowsAffected = 0;
        try {
            String query = "UPDATE course SET name=?, description=?WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());

            preparedStatement.setInt(3, course.getId());
            rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public int deleteCourse(int id) {
        int rowsAffected = 0;
        try {
            String query = "DELETE FROM course WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public ResultSet getAllCourses() {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM course";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet searchCourse(int id) {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM course WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet searchCourseByName(String courseName) {
        return null ;
    }
}
