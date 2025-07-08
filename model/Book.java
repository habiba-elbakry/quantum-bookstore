package model;

public abstract class Book {
    private final String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private double price;

    public Book(String isbn, String title, String author, int publicationYear, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public abstract boolean isForSale();

    public abstract String getType();
}