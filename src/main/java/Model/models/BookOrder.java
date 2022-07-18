package Model.models;

import java.util.ArrayList;

public class BookOrder {
    private int ISBN;
    private int id;
    private int quantity;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getISBN() {
        return ISBN;
    }

}
