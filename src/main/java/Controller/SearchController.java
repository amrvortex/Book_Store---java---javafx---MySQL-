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

public class SearchController {
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
    private TextField isbn;
    @FXML
    private TextField title;
    @FXML
    private TextField priceField;
    @FXML
    private TextField category;
    @FXML
    private TextField author;
    @FXML
    private TextField publisher;
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Number> ISBNCol;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> categoryCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, Number> price;
    private ObservableList<Book> bookObservableList;
    @FXML
    private TextField item_isbn;

    boolean catFlag =false;
    boolean publisherFlag = false;
    boolean authorFlag = false;
    boolean ISBNFlag = false;
    boolean priceFlag = false;
    boolean titleFlag = false;


    int pageNumber = 1;
    public void setBookObservableList(List<Book> list) {
        this.bookObservableList = FXCollections.observableArrayList();
            bookObservableList.addAll(list);
            bookTable.setItems(bookObservableList);
    }

    public void getNextPage() {
        pageNumber++;
        List<Book> books = new ArrayList<>();
        if (catFlag) {
            BookDao bookDao=new BookDao();
            List<Book> list = null;
            try {
                list = bookDao.getBooksByCategory(category.getText(), pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                books = list;
            } else {
                pageNumber--;
                System.out.println(pageNumber);
            }
        } else if (priceFlag) {
            BookDao bookDao=new BookDao();
            List<Book> list = null;
            try {
                list = bookDao.getBookByPrice(Integer.parseInt(priceField.getText()), pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                books = list;
            } else {
                pageNumber--;
            }

        } else if (publisherFlag) {
            BookDao bookDao=new BookDao();
            List<Book> list = null;
            try {
                list = bookDao.getBooksByPublisher(publisher.getText(), pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                books = list;
            } else {
                pageNumber--;
            }
        } else if (ISBNFlag) {
            BookDao bookDao=new BookDao();
            Book book= null;
            try {
                book = bookDao.getBookByISBN(Integer.parseInt(isbn.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            books.add(book);
        } else if (titleFlag) {
            BookDao bookDao=new BookDao();
            Book book= null;
            try {
                book = bookDao.getBookByTitle(title.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            books.add(book);
        } else if (authorFlag) {
            BookDao bookDao=new BookDao();
            List<Book> list = null;
            try {
                list = bookDao.getBooksByAuthor(author.getText(), pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                books = list;
            } else {
                pageNumber--;
            }
        }
        if (books.size() != 0) {
            setBookObservableList(books);
            ISBNCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
            titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
            price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
            publisherCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        }
    }

    public void getPrevPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        List<Book> books = new ArrayList<>();
        if (catFlag) {
            BookDao bookDao=new BookDao();
            List<Book> list = null;
            try {
                list = bookDao.getBooksByCategory(category.getText(), pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                books = list;
            }
        } else if (priceFlag) {
            BookDao bookDao=new BookDao();
            List<Book> list = null;
            try {
                list = bookDao.getBookByPrice(Integer.parseInt(priceField.getText()), pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                books = list;
            }
        } else if (publisherFlag) {
            BookDao bookDao=new BookDao();
            List<Book> list = null;
            try {
                list = bookDao.getBooksByPublisher(publisher.getText(), pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                books = list;
            } else {
                pageNumber--;
            }
        } else if (ISBNFlag) {
            BookDao bookDao=new BookDao();
            Book book= null;
            try {
                book = bookDao.getBookByISBN(Integer.parseInt(isbn.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            books.add(book);
        } else if (titleFlag) {
            BookDao bookDao=new BookDao();
            Book book= null;
            try {
                book = bookDao.getBookByTitle(title.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            books.add(book);
        } else if (authorFlag) {
            BookDao bookDao=new BookDao();
            List<Book> list = null;
            try {
                list = bookDao.getBooksByAuthor(author.getText(), pageNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
            }
            if (list.size() != 0) {
                books = list;
            }
        }
        setBookObservableList(books);
        ISBNCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisherCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
    }
    private void switch_scene(String sceneName, ActionEvent e) throws IOException {
        fxmlLoader = new FXMLLoader(Main_Application.class.getResource(sceneName));
        root=fxmlLoader.load();
        stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    public void Search_Title(ActionEvent e) throws SQLException, EmptyResultSetException, ClassNotFoundException {
        catFlag =false;
        publisherFlag = false;
        authorFlag = false;
        ISBNFlag = false;
        priceFlag = false;
        titleFlag = true;
        pageNumber = 1;
        BookDao bookDao=new BookDao();
        Book book= bookDao.getBookByTitle(title.getText());
        List<Book> list = new ArrayList<>();
        list.add(book);
        setBookObservableList(list);
        ISBNCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisherCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        authorCol.setCellValueFactory(cellData -> new SimpleStringProperty(""));

    }

    public void Search_Price(ActionEvent e) throws SQLException, EmptyResultSetException, ClassNotFoundException {
        catFlag =false;
        publisherFlag = false;
        authorFlag = false;
        ISBNFlag = false;
        priceFlag = true;
        titleFlag = false;
        pageNumber = 1;
        BookDao bookDao=new BookDao();
        List<Book> book= bookDao.getBookByPrice(Integer.parseInt(priceField.getText()), pageNumber);
        setBookObservableList(book);
        ISBNCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisherCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        authorCol.setCellValueFactory(cellData -> new SimpleStringProperty(""));


    }
    public void Search_ISBN(ActionEvent e) throws SQLException, EmptyResultSetException, ClassNotFoundException {
        catFlag =false;
        publisherFlag = false;
        authorFlag = false;
        ISBNFlag = true;
        priceFlag = false;
        titleFlag = false;
        pageNumber = 1;
        BookDao bookDao=new BookDao();
        Book book= bookDao.getBookByISBN(Integer.parseInt(isbn.getText()));
        List<Book> list = new ArrayList<>();
        list.add(book);
        setBookObservableList(list);
        ISBNCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisherCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        authorCol.setCellValueFactory(cellData -> new SimpleStringProperty(""));
    }
    public void Search_Author(ActionEvent e) throws SQLException, EmptyResultSetException, ClassNotFoundException {
        catFlag =false;
        publisherFlag = false;
        authorFlag = true;
        ISBNFlag = false;
        priceFlag = false;
        titleFlag = false;
        pageNumber = 1;
        BookDao bookDao=new BookDao();
        List<Book> book= bookDao.getBooksByAuthor(author.getText(), pageNumber);
        setBookObservableList(book);
        ISBNCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisherCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        authorCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthors().get(0).getName()));
    }
    public void Search_Publisher(ActionEvent e) throws SQLException, EmptyResultSetException, ClassNotFoundException {
        catFlag =false;
        publisherFlag = true;
        authorFlag = false;
        ISBNFlag = false;
        priceFlag = false;
        titleFlag = false;
        pageNumber = 1;
        BookDao bookDao=new BookDao();
        List<Book> book= bookDao.getBooksByPublisher(publisher.getText(), pageNumber);
        setBookObservableList(book);
        ISBNCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisherCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        authorCol.setCellValueFactory(cellData -> new SimpleStringProperty(""));

        System.out.println(book.get(0).getPublisher());
    }
    public void Search_Category(ActionEvent e) throws SQLException, EmptyResultSetException, ClassNotFoundException {
        catFlag =true;
        publisherFlag = false;
        authorFlag = false;
        ISBNFlag = false;
        priceFlag = false;
        titleFlag = false;
        pageNumber = 1;
        BookDao bookDao=new BookDao();
        List<Book> book= bookDao.getBooksByCategory(category.getText(), pageNumber);
        setBookObservableList(book);
        ISBNCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getISBN()));
        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().name()));
        price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
        publisherCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        authorCol.setCellValueFactory(cellData -> new SimpleStringProperty(""));


    }
}
