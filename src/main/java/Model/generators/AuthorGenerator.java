package Model.generators;

import Model.connectors.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorGenerator {

    public void insertAuthors() throws SQLException, ClassNotFoundException {
        DatabaseConnector connector = new DatabaseConnector();
        Connection dbConnection = connector.getConnection();
        for (int i = 0; i<10;++i) {
            String sql = "INSERT INTO Author VALUES (?, ?)";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            for (int j=1; j<50000;++j) {
                ps.setString(1, Integer.toString((i*50000)+j));
                ps.setString(2, "Author"+((i*50000)+j));
                ps.addBatch();
            }
            ps.executeBatch();
        }

    }
}
