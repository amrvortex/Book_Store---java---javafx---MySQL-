package Model.daos;

import Model.connectors.DatabaseConnector;
import Model.models.BookOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookOrderDao {

    public List<BookOrder> getOrders() throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();
        Statement statement=connection.createStatement();
        String sql = "SELECT * FROM Book_order";
        ResultSet resultSet=statement.executeQuery(sql);
        List<BookOrder> bookOrders = new ArrayList<>();
        while (resultSet.next()) {
            BookOrder book = new BookOrder();
            book.setId(resultSet.getInt(1));
            book.setISBN(resultSet.getInt(2));
            book.setQuantity(resultSet.getInt(3));
            bookOrders.add(book);
        }
        statement.close();
        connection.close();

        return bookOrders;
    }
    public void ConfirmOrder(int ISBN) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "DELETE FROM Book_order WHERE ISBN = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, (ISBN));
        ps.execute();
        ps.close();
        connection.close();
    }

}
