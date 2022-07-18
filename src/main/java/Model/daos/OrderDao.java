package Model.daos;

import Model.connectors.DatabaseConnector;
import Model.models.Book;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OrderDao {
    public void makeOrder(List<Book> books, long userID) throws SQLException, ClassNotFoundException {
        HashMap<Integer,Integer> quantities = new HashMap<>();
        double price = 0;
        for (Book b:books){
            price+= b.getPrice();
            int t = b.getISBN();
            if (quantities.containsKey(t)){
                int q = quantities.get(b.getISBN());
                quantities.remove(t);
                quantities.put(t,q+1);
            }
            else {
                quantities.put(t,1);
            }
        }
        Connection connection = DatabaseConnector.getConnection();
        String sql = "INSERT INTO customer_order(user_id,date,price) VALUES(?,?,?)";
        PreparedStatement ps =connection.prepareStatement(sql);
        ps.setLong(1,userID);
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(dt);
        ps.setString(2,date);
        ps.setDouble(3,price);
        ps.executeUpdate();
        ps.close();

        Statement st=connection.createStatement();
        String last_id="SELECT LAST_INSERT_ID()";
        ResultSet rs=st.executeQuery(last_id);
        long order_id = 0;
        if(rs.next()){
             order_id= rs.getLong(1);
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = quantities.entrySet().iterator();
        while (iterator.hasNext()){
            String sql2 = "INSERT INTO Items VALUES(?,?,?)";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            Map.Entry element = iterator.next();
            ps2.setLong(1,order_id);
            ps2.setInt(2, (Integer) element.getKey());
            ps2.setInt(3, (Integer) element.getValue());
            ps2.executeUpdate();
            ps2.close();
        }
        connection.close();
    }


}
