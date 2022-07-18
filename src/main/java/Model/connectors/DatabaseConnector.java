package Model.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

        private static String DATABASE_URL = "jdbc:mysql://localhost:3306/BookStore?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
        private static String DATABASE_USERNAME = "Connector";
        private static String DATABASE_PASSWORD = "m123456K";

        private static Connection instance = null;

        public static Connection getConnection() throws ClassNotFoundException, SQLException {
            //Class.forName("com.mysql.jdbc.Driver");
            instance = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            return instance;
        }
}

