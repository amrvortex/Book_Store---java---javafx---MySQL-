package Model.models;

public class Author {
    private int ISBN;
    private String name;

    public Author(int ISBN, String name) {
        this.ISBN = ISBN;
        this.name = name;
    }

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
