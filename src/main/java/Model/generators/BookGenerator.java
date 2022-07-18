package Model.generators;

import Model.connectors.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BookGenerator {

    public void insertBooks() throws SQLException, ClassNotFoundException {
        DatabaseConnector connector = new DatabaseConnector();
        Connection dbConnection = connector.getConnection();
        Random random = new Random();
        String [] categories = {"Science", "Art", "Religion", "History","Geography"};
        for (int i = 0; i<20;++i) {
            String sql = "INSERT INTO book (ISBN, title, publisher, publication_year, price, category, quantity, threshold) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            for (int j=1; j<50000;++j) {
                ps.setString(1, Integer.toString((i*50000)+j));
                ps.setString(2, "Book"+((i*50000)+j));
                int pubNum = random.nextInt(200000);
                ps.setString(3,"Publisher"+pubNum);
                int year = 1800+random.nextInt(220);
                ps.setString(4,Integer.toString(year));
                int price = 10+ random.nextInt(200);
                ps.setString(5,Integer.toString(price));
                int cate = random.nextInt(5);
                ps.setString(6,categories[cate]);
                int quantity = random.nextInt(200);
                ps.setString(7,Integer.toString(quantity));
                int threshold = 1+ random.nextInt(15);
                ps.setString(8,Integer.toString(threshold));
                ps.addBatch();
            }
            ps.executeBatch();
        }

    }
}
