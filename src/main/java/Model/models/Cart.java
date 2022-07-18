package Model.models;

import java.util.ArrayList;

public class Cart {

    private static Cart cart = null;

    private ArrayList<Book> books;
    private double totalPrice;


    public static Cart getInstance() {
        if (cart == null) return new Cart();
        return cart;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private Cart() {
        books = new ArrayList<>();
        totalPrice = 0;
    }


    public void addToCart(Book book) {
        cart.books.add(book);
        cart.totalPrice += book.getPrice();
    }

    public void removeFromCart(Book book) {
        cart.books.remove(book);
        totalPrice -= book.getPrice();
    }

    public void clearCart() {
        this.cart.books = new ArrayList<>();
        this.cart.totalPrice = 0;
    }
}
