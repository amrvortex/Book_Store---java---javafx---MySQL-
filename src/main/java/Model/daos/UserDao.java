package Model.daos;

import Model.connectors.DatabaseConnector;
import Model.exceptions.EmptyResultSetException;
import Model.models.User;
import Model.models.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public boolean addUser(User user) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into User(user_name, password, lastName, firstName, mail, phone, shipping_address, user_type) values (?, MD5(?), ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getLastName());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPhone());
        preparedStatement.setString(7, user.getShippingAddress());
        String role = user.getRole() == UserRole.USER ? "0" : "1";
        preparedStatement.setString(8, role);
        int status = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return status == 1;
    }

    public User getUser(String username, String password) throws SQLException, ClassNotFoundException, EmptyResultSetException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from User where user_name = ? and password = MD5(?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet);
        if (resultSet.next()) {
            User user = new User();
            user.setUserID(resultSet.getLong(1));
            user.setUsername(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setFirstName(resultSet.getString(5));
            user.setLastName(resultSet.getString(6));
            user.setPhone(resultSet.getString(7));
            user.setShippingAddress(resultSet.getString(8));
            UserRole role = "0".equals(resultSet.getString(9)) ? UserRole.USER : UserRole.MANAGER;
            user.setRole(role);
            preparedStatement.close();
            connection.close();
            return user;
        }
        preparedStatement.close();
        connection.close();
        throw new EmptyResultSetException("Wrong Email or Password!!");
    }
    public void promoteUser(String username) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "UPDATE User SET user_type= 1 WHERE user_name= ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,username);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    public void editProfile(User user, String userName, String email) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "UPDATE User SET password=MD5(?), lastName= ?, firstName=?, phone=?, shipping_address=?, user_type= ? WHERE user_id= ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,user.getPassword());
        ps.setString(2,user.getLastName());
        ps.setString(3,user.getFirstName());
        ps.setString(4,user.getPhone());
        ps.setString(5,user.getShippingAddress());
        String role = user.getRole() == UserRole.USER ? "0" : "1";
        ps.setString(6, role);
        ps.setLong(7,user.getUserID());
        ps.executeUpdate();
        if (!user.getUsername().equals(userName)) {
            String sql2 = "UPDATE User SET user_name= ? WHERE user_id= ?";
            ps = connection.prepareStatement(sql2);
            ps.setString(1, user.getUsername());
            ps.setLong(2, user.getUserID());
        }

        if (!user.getEmail().equals(email)) {
            String sql2 = "UPDATE User SET mail= ? WHERE user_id= ?";
            ps = connection.prepareStatement(sql2);
            ps.setString(1, user.getEmail());
            ps.setLong(2, user.getUserID());
        }
        ps.close();
        connection.close();
    }
}
