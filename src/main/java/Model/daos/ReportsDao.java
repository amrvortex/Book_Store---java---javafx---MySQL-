package Model.daos;

import Model.connectors.DatabaseConnector;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;

public class ReportsDao {

    public void salesReport() throws JRException, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport("src/main/resources/report1.jasper", null, connection);
        } catch (JRException e) {
            e.printStackTrace();
        }
        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        jasperViewer.setVisible(true);

    }
    public void top5CustomersReport() throws JRException, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport("src/main/resources/report2.jasper", null, connection);
        } catch (JRException e) {
            e.printStackTrace();
        }
        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        jasperViewer.setVisible(true);

    }
    public void topSellingReport() throws JRException, SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport("src/main/resources/report3.jasper", null, connection);
        } catch (JRException e) {
            e.printStackTrace();
        }
        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        jasperViewer.setVisible(true);

    }
}
