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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HomeController {


    public void setUser(User user) {
        this.user = user;
    }

    public void SetCartBooks(ArrayList<Book> cartBooks) {
        this.CartBooks=  (ArrayList<Book>) cartBooks.clone();
    }
    private ArrayList<Book> CartBooks=new ArrayList<>();
    int pageNumber = 1;
    private User user;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Number> ISBN;
    @FXML
    private TableColumn<Book, String> title;
    @FXML
    private TableColumn<Book, String> category;
    @FXML
    private TableColumn<Book, String> publisher;
    @FXML
    private TableColumn<Book, Number> price;
    private ObservableList<Book> bookObservableList;

    @FXML
    private TextField item_isbn;

    FXMLLoader fxmlLoader;
    public void setBookObservableList() {
        this.bookObservableList = FXCollections.observableArrayList();
        BookDao dao = new BookDao();
        try {
            List<Book> books = dao.getBooksPage(pageNumber);
            if (pageNumber > 1 && books.size() ==0) pageNumber--;
            if (books.size() != 0) {
                bookObservableList.addAll(books);
                bookTable.setItems(bookObservableList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        setBookObservableList();
        ISBN.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
    }
    public void AddToCart(ActionEvent e) throws SQLException, EmptyResultSetException, ClassNotFoundException {
        BookDao bookDao=new BookDao();
        Book book=bookDao.getBookByISBN(Integer.parseInt(item_isbn.getText()));
        if(book.getQuantity()>0){
            this.CartBooks.add(book);
        }
        else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Book Out Of Stock");
            errorAlert.setContentText("Sorry,this book is out of stock");
            errorAlert.showAndWait();
        }
        item_isbn.setText("");
    }


    public void getNextPage() {
        pageNumber++;
        setBookObservableList();
        ISBN.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
    }

    public void getPrevPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        setBookObservableList();
        ISBN.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
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
    private void switch_scene(String sceneName,ActionEvent e) throws IOException {
        fxmlLoader = new FXMLLoader(Main_Application.class.getResource(sceneName));
        root=fxmlLoader.load();
        stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToSearch(ActionEvent e) throws IOException{
        if(user.getRole()== UserRole.USER){
            switch_scene("Search-User.fxml",e);
        }
        else{
            System.out.println(user.getRole());
            switch_scene("Search-Manager.fxml",e);
        }
        SearchController searchController=fxmlLoader.getController();
        searchController.setUser(user);
        searchController.SetCartBooks(this.CartBooks);
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
    public void goToManage(ActionEvent e) throws IOException {
        switch_scene("ManageBooks.fxml", e);
        ManageController manageController=fxmlLoader.getController();
        manageController.setUser(user);
        manageController.SetCartBooks(this.CartBooks);
    }
}
