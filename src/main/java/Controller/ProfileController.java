package Controller;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileController {
    public void setUser(User user) {
        this.user = user;
    }
    public void SetCartBooks(ArrayList<Book> cartBooks) {
        this.CartBooks=  (ArrayList<Book>) cartBooks.clone();
    }
    private ArrayList<Book> CartBooks=new ArrayList<>();
    User user;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    //labels for profile
    @FXML
    private Label profile_username;
    @FXML
    private Label profile_firstname;
    @FXML
    private Label profile_lastname;
    @FXML
    private Label profile_mail;
    @FXML
    private Label profile_address;
    @FXML
    private Label profile_phone;

    FXMLLoader fxmlLoader;


    private void switch_scene(String sceneName, ActionEvent e) throws IOException {
        fxmlLoader = new FXMLLoader(Main_Application.class.getResource(sceneName));
        root=fxmlLoader.load();
        stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setProfile() throws SQLException, EmptyResultSetException, ClassNotFoundException {
        System.out.println(user.getUsername());
        profile_username.setText(user.getUsername());
        profile_firstname.setText(user.getFirstName());
        profile_lastname.setText(user.getLastName());
        profile_address.setText(user.getShippingAddress());
        profile_mail.setText(user.getEmail());
        profile_phone.setText(user.getPhone());
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
    public void logout(ActionEvent e) throws IOException {
        switch_scene("SignIn.fxml",e);
    }
    public void goToEdit(ActionEvent e) throws IOException {
        if(user.getRole()== UserRole.USER){
            switch_scene("EditProfile-User.fxml",e);
        }
        else{
            switch_scene("EditProfile-Manager.fxml",e);
        }
        UpdateProfileController updateProfileController=fxmlLoader.getController();
        updateProfileController.setUser(user);
        updateProfileController.SetCartBooks(this.CartBooks);
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
