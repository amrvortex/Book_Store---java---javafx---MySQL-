package Controller;

import Model.daos.BookDao;
import Model.daos.OrderDao;
import Model.exceptions.EmptyResultSetException;
import Model.models.Book;
import Model.models.User;
import Model.models.UserRole;
import View.Main_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckoutController {
    public void setUser(User user) {
        this.user = user;
    }
    public void SetCartBooks(ArrayList<Book> cartBooks) {
        this.CartBooks=  (ArrayList<Book>) cartBooks.clone();
        initialize();
    }
    private ArrayList<Book> CartBooks=new ArrayList<>();
    User user;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    FXMLLoader fxmlLoader;

    @FXML
    private TextField creditcard;
    @FXML
    private TextField date;
    @FXML
    private Label total_price;

    private void initialize() {
        double total=0;
        for(int i=0;i<this.CartBooks.size();i++){
            total+=this.CartBooks.get(i).getPrice();
        }
        total_price.setText(Double.toString(total));
    }

    private void switch_scene(String sceneName, ActionEvent e) throws IOException {
        fxmlLoader = new FXMLLoader(Main_Application.class.getResource(sceneName));
        root=fxmlLoader.load();
        stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void logout(ActionEvent e) throws IOException {
        switch_scene("SignIn.fxml",e);
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
    public void Confirm(ActionEvent e) throws SQLException, ClassNotFoundException {
        if(creditcard.getText().length()==12){
            OrderDao orderDao=new OrderDao();
            orderDao.makeOrder(this.CartBooks,this.user.getUserID());
            Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
            errorAlert.setHeaderText("Order Confirmed");
            errorAlert.setContentText("Your order is on it's way");
            errorAlert.showAndWait();
        }
        else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Credit Card INVALID");
            errorAlert.setContentText("Please enter Valid 12 Digits");
            errorAlert.showAndWait();
        }
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

    public void goToManage(ActionEvent e) throws IOException {
        switch_scene("ManageBooks.fxml", e);
        ManageController manageController=fxmlLoader.getController();
        manageController.setUser(user);
        manageController.SetCartBooks(this.CartBooks);
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

}
