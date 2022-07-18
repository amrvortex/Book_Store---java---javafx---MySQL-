package Controller;

import Model.daos.UserDao;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SignInController {
    @FXML
    private TextField username_login;
    @FXML
    private PasswordField password_login;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    FXMLLoader fxmlLoader;
    private ArrayList<Book> CartBooks=new ArrayList<>();

    public void login(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
        /////////////Checking Login ////////////////////
        String user_name= username_login.getText();
        String pass=password_login.getText();

        //login Model
        //        if login correct
        UserDao dao = new UserDao();
        try {
            User loggingUser = dao.getUser(user_name,pass);
            if (loggingUser.getRole() == UserRole.MANAGER){
                System.out.println("Manager");
                switch_scene("Home-Manager.fxml", e);
            }
            else if (loggingUser.getRole() == UserRole.USER){
                System.out.println("User");
                switch_scene("Home-User.fxml",e);
            }
            //passing user to home
            HomeController homeController=fxmlLoader.getController();
            homeController.setUser(loggingUser);
            homeController.SetCartBooks(this.CartBooks);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Data entered is invalid");
            errorAlert.setContentText("Please enter valid data");
            errorAlert.showAndWait();
        } catch (ClassNotFoundException | EmptyResultSetException ex) {
            ex.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(ex.getMessage());
            errorAlert.setContentText("Please enter valid data");
            errorAlert.showAndWait();
        }
    }
    public void GoToSignUp(ActionEvent e) throws IOException{
        switch_scene("SignUp.fxml",e);
    }
    private void switch_scene(String sceneName,ActionEvent e) throws IOException {
        fxmlLoader = new FXMLLoader(Main_Application.class.getResource(sceneName));
        root=fxmlLoader.load();
        stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
