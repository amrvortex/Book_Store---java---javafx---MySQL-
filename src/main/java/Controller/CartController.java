package Controller;

import Model.daos.BookDao;
import Model.exceptions.EmptyResultSetException;
import Model.models.Book;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartController {
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
    private TextField item_isbn;
    @FXML
    private Label total_price;
    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book, Number> isbn;
    @FXML
    private TableColumn<Book, String> title;
    @FXML
    private TableColumn<Book, String> category;
    @FXML
    private TableColumn<Book, String> publisher;
    @FXML
    private TableColumn<Book, Number> price;

    private ObservableList<Book> bookObservableList;

    public void setBookObservableList() {
        this.bookObservableList = FXCollections.observableArrayList();
        bookObservableList.addAll(this.CartBooks);
        table.setItems(bookObservableList);
    }

    private void initialize() {
        double total=0;
        for(int i=0;i<this.CartBooks.size();i++){
            total+=this.CartBooks.get(i).getPrice();
        }
        total_price.setText(Double.toString(total));
        setBookObservableList();
        isbn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));


    }
    private void switch_scene(String sceneName,ActionEvent e) throws IOException {
        fxmlLoader = new FXMLLoader(Main_Application.class.getResource(sceneName));
        root=fxmlLoader.load();
        stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    public void logout(ActionEvent e) throws IOException {
        switch_scene("SignIn.fxml",e);
    }
    public void Checkout(ActionEvent e) throws IOException, SQLException, EmptyResultSetException, ClassNotFoundException {
        //passing user to profile
        if(user.getRole()== UserRole.USER){
            switch_scene("CheckOut-User.fxml",e);
        }
        else{
            switch_scene("CheckOut-Manager.fxml",e);
        }
        CheckoutController checkoutController=fxmlLoader.getController();
        checkoutController.setUser(user);
        checkoutController.SetCartBooks(this.CartBooks);
    }
    public void Clear(ActionEvent e){
        this.CartBooks.clear();
        initialize();
    }
    public void RemoveFromCart(ActionEvent e ){
        this.CartBooks.remove(Integer.parseInt(item_isbn.getText())-1);
        initialize();
        item_isbn.setText("");
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
}
