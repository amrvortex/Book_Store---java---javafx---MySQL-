package Model.generators;

import Model.connectors.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class PublisherGenerator {

    public void insertPublishers() throws SQLException, ClassNotFoundException {
        DatabaseConnector connector = new DatabaseConnector();
        Connection dbConnection = connector.getConnection();
        Random random = new Random();
        for (int i = 0; i<4;++i) {
            String sql = "INSERT INTO Publisher VALUES (?, ?, ?)";
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            for (int j=1; j<50000;++j) {
                String name = "Publisher"+((i*50000)+j);
                ps.setString(1,name);
                ps.setString(2,"Address of "+name);
                StringBuilder num = new StringBuilder();
                num.append("01");
                for (int n =0 ; n<9;++n);{
                    num.append(random.nextInt(10));
                }
                ps.setString(3,num.toString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
