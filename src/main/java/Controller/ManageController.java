package Controller;

import Model.daos.BookDao;
import Model.daos.BookOrderDao;
import Model.daos.UserDao;
import Model.exceptions.EmptyResultSetException;
import Model.models.Book;
import Model.models.Category;
import Model.models.User;
import Model.models.UserRole;
import View.Main_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageController {
    public void setUser(User user) {
        this.user = user;
    }
    public void SetCartBooks(ArrayList<Book> cartBooks) {
        this.CartBooks=  (ArrayList<Book>) cartBooks.clone();
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
    private TextField remove_isbn;
    @FXML
    private TextField isbn;
    @FXML
    private TextField title;
    @FXML
    private TextField price;
    @FXML
    private TextField quantity;
    @FXML
    private TextField threshold;
    @FXML
    private TextField category;
    @FXML
    private TextField publisher;
    @FXML
    private TextField year;

    @FXML
    private TextField username;

    @FXML
    private TextField edit_isbn_old;
    @FXML
    private TextField edit_isbn_new;
    @FXML
    private TextField edit_title;
    @FXML
    private TextField edit_price;
    @FXML
    private TextField edit_quantity;
    @FXML
    private TextField edit_threshold;
    @FXML
    private TextField edit_category;
    @FXML
    private TextField edit_publisher;
    @FXML
    private TextField edit_year;


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
    public void ConfirmOrders(ActionEvent e) throws IOException {
        switch_scene("Book_Order.fxml",e);
        BookOrderController bookOrderController =fxmlLoader.getController();
        bookOrderController.setUser(user);
        bookOrderController.SetCartBooks(this.CartBooks);
    }
    private void switch_scene(String sceneName,ActionEvent e) throws IOException {
        fxmlLoader = new FXMLLoader(Main_Application.class.getResource(sceneName));
        root=fxmlLoader.load();
        stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void Add_Book(ActionEvent e) throws SQLException, ClassNotFoundException {
        Book book = new Book();
        book.setISBN((Integer.parseInt(isbn.getText())));
        book.setTitle(title.getText());
        book.setPublisher(publisher.getText());
        book.setPublication_year(year.getText());
        book.setPrice(Double.parseDouble(price.getText()));
        book.setCategory(Category.valueOf(category.getText()));
        book.setQuantity(Integer.parseInt(quantity.getText()));
        book.setThreshold(Integer.parseInt(threshold.getText()));
        BookDao bookDao=new BookDao();
        bookDao.addBook(book);
    }
    public void Promote(ActionEvent e) throws SQLException, ClassNotFoundException {
        UserDao userDao=new UserDao();
        userDao.promoteUser(username.getText());
    }
    public void ConfirmEdit(ActionEvent e) throws SQLException, ClassNotFoundException {
        Book book = new Book();
        book.setISBN((Integer.parseInt(edit_isbn_new.getText())));
        book.setTitle(edit_title.getText());
        book.setPublisher(edit_publisher.getText());
        book.setPublication_year(edit_year.getText());
        book.setPrice(Double.parseDouble(edit_price.getText()));
        book.setCategory(Category.valueOf(edit_category.getText()));
        book.setQuantity(Integer.parseInt(edit_quantity.getText()));
        book.setThreshold(Integer.parseInt(edit_threshold.getText()));
        BookDao bookDao=new BookDao();
        bookDao.editBook(Integer.parseInt(edit_isbn_old.getText()),book);
    }
    public void Remove_Book(ActionEvent e) throws SQLException, ClassNotFoundException {
    BookDao bookDao=new BookDao();
    bookDao.deleteBook(Integer.parseInt(remove_isbn.getText()));
    }
    public void logout(ActionEvent e) throws IOException {
        switch_scene("SignIn.fxml",e);
    }
}
