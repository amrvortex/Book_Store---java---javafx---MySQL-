package Model.daos;

import Model.connectors.DatabaseConnector;
import Model.exceptions.EmptyResultSetException;
import Model.models.Author;
import Model.models.Book;
import Model.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    public boolean addBook(Book book) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "{call Add_new_book(?,?,?,?,?,?,?,?)}";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, Integer.toString(book.getISBN()));
        ps.setString(2, book.getTitle());
        ps.setString(3, book.getPublisher());
        ps.setString(4, book.getPublication_year());
        ps.setString(5, Double.toString(book.getPrice()));
        ps.setString(6, book.getCategory().toString());
        ps.setString(7, Integer.toString(book.getQuantity()));
        ps.setString(8, Integer.toString(book.getThreshold()));
        int status = ps.executeUpdate();
        ps.close();
        return status == 1;
    }
    public boolean editBook(int ISBN, Book newBook) throws SQLException, ClassNotFoundException {
        deleteBook(ISBN);
        return addBook(newBook);
    }
    public void deleteBook(int ISBN) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "DELETE FROM Book WHERE ISBN = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, (ISBN));
        ps.execute();
        ps.close();
        connection.close();
    }

    public List<Book> getBooksPage(int n) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM Book LIMIT 20 OFFSET ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, 20*(n-1));
        ResultSet resultSet = ps.executeQuery();
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setISBN(resultSet.getInt(1));
            book.setTitle(resultSet.getString(2));
            book.setPublisher(resultSet.getString(3));
            book.setPublication_year(resultSet.getString(4));
            book.setPrice(resultSet.getDouble(5));
            book.setCategory(Category.valueOf(resultSet.getString(6)));
            book.setQuantity(resultSet.getInt(7));
            book.setThreshold(resultSet.getInt(8));
            books.add(book);
        }
        ps.close();
        connection.close();
        return books;
    }

    public Book getBookByTitle(String title) throws SQLException, ClassNotFoundException, EmptyResultSetException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "{call search_by_title(?)}";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, title);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            Book book = new Book();
            book.setISBN(resultSet.getInt(1));
            book.setTitle(resultSet.getString(2));
            book.setPublisher(resultSet.getString(3));
            book.setPublication_year(resultSet.getString(4));
            book.setPrice(resultSet.getDouble(5));
            book.setCategory(Category.valueOf(resultSet.getString(6)));
            book.setQuantity(resultSet.getInt(7));
            book.setThreshold(resultSet.getInt(8));
            ps.close();
            connection.close();
            return book;
        }
        ps.close();
        connection.close();
        throw new EmptyResultSetException("No books with this title");
    }

    public List<Book> getBookByPrice(int price, int n) throws SQLException, ClassNotFoundException, EmptyResultSetException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "{call search_by_price(?, ?)}";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, price);
        ps.setInt(2, 20*(n-1));
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Book book = new Book();
            book.setISBN(resultSet.getInt(1));
            book.setTitle(resultSet.getString(2));
            book.setPublisher(resultSet.getString(3));
            book.setPublication_year(resultSet.getString(4));
            book.setPrice(resultSet.getDouble(5));
            book.setCategory(Category.valueOf(resultSet.getString(6)));
            book.setQuantity(resultSet.getInt(7));
            book.setThreshold(resultSet.getInt(8));
            books.add(book);
        }
        ps.close();
        connection.close();
        return books;
    }

    public List<Book> getBooksByCategory(String category, int n) throws SQLException, ClassNotFoundException, EmptyResultSetException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "{call search_by_category(?, ?)}";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, category);
        ps.setInt(2, 20*(n-1));
        ResultSet resultSet = ps.executeQuery();
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setISBN(resultSet.getInt(1));
            book.setTitle(resultSet.getString(2));
            book.setPublisher(resultSet.getString(3));
            book.setPublication_year(resultSet.getString(4));
            book.setPrice(resultSet.getDouble(5));
            book.setCategory(Category.valueOf(resultSet.getString(6)));
            book.setQuantity(resultSet.getInt(7));
            book.setThreshold(resultSet.getInt(8));
            books.add(book);
        }
        ps.close();
        connection.close();
        return books;
    }

    public Book getBookByISBN(int ISBN) throws SQLException, ClassNotFoundException, EmptyResultSetException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "{call search_by_ISBN(?)}";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, ISBN);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            Book book = new Book();
            book.setISBN(resultSet.getInt(1));
            book.setTitle(resultSet.getString(2));
            book.setPublisher(resultSet.getString(3));
            book.setPublication_year(resultSet.getString(4));
            book.setPrice(resultSet.getDouble(5));
            book.setCategory(Category.valueOf(resultSet.getString(6)));
            book.setQuantity(resultSet.getInt(7));
            book.setThreshold(resultSet.getInt(8));
            ps.close();
            connection.close();
            return book;
        }
        ps.close();
        connection.close();
        throw new EmptyResultSetException("No book with this ISBN");
    }


    public List<Book> getBooksByPublisher(String publisher, int n) throws SQLException, ClassNotFoundException, EmptyResultSetException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "{call search_by_publisher(?, ?)}";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, publisher);
        ps.setInt(2, 20*(n-1));
        ResultSet resultSet = ps.executeQuery();
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setISBN(resultSet.getInt(1));
            book.setTitle(resultSet.getString(2));
            book.setPublisher(resultSet.getString(3));
            book.setPublication_year(resultSet.getString(4));
            book.setPrice(resultSet.getDouble(5));
            book.setCategory(Category.valueOf(resultSet.getString(6)));
            book.setQuantity(resultSet.getInt(7));
            book.setThreshold(resultSet.getInt(8));
            books.add(book);
        }
        ps.close();
        connection.close();
        return books;
    }


    public List<Book> getBooksByAuthor(String author, int n) throws SQLException, ClassNotFoundException, EmptyResultSetException {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "{call search_by_author(?, ?)}";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, author);
        ps.setInt(2, 20*(n-1));
        ResultSet resultSet = ps.executeQuery();
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setISBN(resultSet.getInt(1));
            book.setTitle(resultSet.getString(2));
            book.setPublisher(resultSet.getString(3));
            book.setPublication_year(resultSet.getString(4));
            book.setPrice(resultSet.getDouble(5));
            book.setCategory(Category.valueOf(resultSet.getString(6)));
            book.setQuantity(resultSet.getInt(7));
            book.setThreshold(resultSet.getInt(8));
            ArrayList<Author> authors = new ArrayList<>();
            authors.add(new Author(resultSet.getString(9)));
            book.setAuthors(authors);
            books.add(book);
        }
        ps.close();
        connection.close();
        return books;
    }
}