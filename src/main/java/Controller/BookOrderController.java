package Controller;

import Model.daos.BookDao;
import Model.daos.BookOrderDao;
import Model.exceptions.EmptyResultSetException;
import Model.models.Book;
import Model.models.BookOrder;
import Model.models.User;
import Model.models.UserRole;
import View.Main_Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookOrderController {

    public void setUser(User user) {
        this.user = user;
    }
    public void SetCartBooks(ArrayList<Book> cartBooks) {
        this.CartBooks=  (ArrayList<Book>) cartBooks.clone();
        initialize();
    }
    private ArrayList<Book> CartBooks=new ArrayList<>();
    private User user;

    FXMLLoader fxmlLoader;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    private TableView<BookOrder> table;
    @FXML
    private TableColumn<BookOrder, Number> isbn;
    @FXML
    private TableColumn<BookOrder, Number> id;
    @FXML
    private TableColumn<BookOrder, Number> quantity;
    @FXML
    private TextField confirm_isbn;

    private ObservableList<BookOrder> bookObservableList;
    public void setBookObservableList() {
        this.bookObservableList = FXCollections.observableArrayList();
        BookOrderDao dao = new BookOrderDao();
        try {
            bookObservableList.addAll(dao.getOrders());
            table.setItems(bookObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        setBookObservableList();
        isbn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        quantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()));
    }

    public void goToSearch(ActionEvent e) throws IOException {
        if(user.getRole()== UserRole.USER){
            switch_scene("Search-User.fxml",e);
        }
        else{
            switch_scene("Search-Manager.fxml",e);
        }
        SearchController searchController=fxmlLoader.getController();
        searchController.setUser(user);
        searchController.SetCartBooks(this.CartBooks);
    }
    public void goToManage(ActionEvent e) throws IOException {
        switch_scene("ManageBooks.fxml", e);
        ManageController manageController=fxmlLoader.getController();
        manageController.setUser(user);
        manageController.SetCartBooks(this.CartBooks);
    }
    public void goToProfile(ActionEvent e) throws IOException, SQLException, EmptyResultSetException, ClassNotFoundException {
        //passing user to profile
        if(user.getRole()== UserRole.USER){
            switch_scene("Profile-User.fxml",e);
        }
        else{
            switch_scene("Profile-Manager.fxml",e);
        }
        ProfileController profileController=fxmlLoader.getController();
        profileController.setUser(user);
        profileController.setProfile();
        profileController.SetCartBooks(this.CartBooks);
    }
    public void goToHome(ActionEvent e) throws IOException, SQLException, EmptyResultSetException, ClassNotFoundException {
        //passing user to profile
        if(user.getRole()== UserRole.USER){
            switch_scene("Home-User.fxml",e);
        }
        else{
            switch_scene("Home-Manager.fxml",e);
        }
        HomeController homeController=fxmlLoader.getController();
        homeController.setUser(user);
        homeController.SetCartBooks(this.CartBooks);
    }
    public void goToCart(ActionEvent e) throws IOException{
        if(user.getRole()== UserRole.USER){
            switch_scene("Cart-User.fxml",e);
        }
        else{
            switch_scene("Cart-Manager.fxml",e);
        }
        CartController cartController =fxmlLoader.getController();
        cartController.setUser(user);
        cartController.SetCartBooks(this.CartBooks);
    }
    public void logout(ActionEvent e) throws IOException {
        switch_scene("SignIn.fxml",e);
    }
    private void switch_scene(String sceneName,ActionEvent e) throws IOException {
        fxmlLoader = new FXMLLoader(Main_Application.class.getResource(sceneName));
        root=fxmlLoader.load();
        stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void Confirm(ActionEvent e) throws SQLException, ClassNotFoundException {
            BookOrderDao bookOrderDao=new BookOrderDao();
            bookOrderDao.ConfirmOrder(Integer.parseInt(confirm_isbn.getText()));
            initialize();
            Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
            errorAlert.setHeaderText("Order Confirmed");
            errorAlert.setContentText("Your order has been added to stock");
            errorAlert.showAndWait();
            confirm_isbn.setText("");
    }
}
