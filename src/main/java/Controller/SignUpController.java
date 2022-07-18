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

public class SignUpController {
    //sign up fields
    @FXML
    private TextField username_signup;
    @FXML
    private PasswordField password_signup;
    @FXML
    private TextField lastname;
    @FXML
    private TextField firstname;
    @FXML
    private TextField mail;
    @FXML
    private TextField phone;
    @FXML
    private TextField shipping_address;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    FXMLLoader fxmlLoader;
    private ArrayList<Book> CartBooks=new ArrayList<>();

    public void signup(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
/////////////Checking signup ////////////////////
        String user_name= username_signup.getText();
        String pass=password_signup.getText();
        String lastN=lastname.getText();
        String firstN=firstname.getText();
        String email=mail.getText();
        String telephone=phone.getText();
        String address=shipping_address.getText();
        //SignUp Model
        UserRole role;
        role = UserRole.USER;

        User user = new User(1, user_name, pass, email, firstN, lastN, telephone, address, role);
        UserDao dao = new UserDao();
        try {
            boolean response =  dao.addUser(user);
            if (response) {
                stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setUserData(dao.getUser(user_name, pass));
                switch_scene("Home-User.fxml",e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Data entered is invalid");
            errorAlert.setContentText("Please enter valid data");
            errorAlert.showAndWait();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Data entered is invalid");
            errorAlert.setContentText("Please enter valid data");
            errorAlert.showAndWait();
        } catch (EmptyResultSetException ex) {
            ex.printStackTrace();
        }

        //passing user to home
        HomeController homeController=fxmlLoader.getController();
        homeController.setUser(user);
        homeController.SetCartBooks(this.CartBooks);
    }
    public void backToSignIn(ActionEvent e) throws IOException {
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
}
