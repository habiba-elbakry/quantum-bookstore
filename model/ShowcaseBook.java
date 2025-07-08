package model;

public class ShowcaseBook extends Book {
    public ShowcaseBook(String isbn, String title, String author,
            int publicationYear, double price) {
        super(isbn, title, author, publicationYear, price);
    }

    @Override
    public boolean isForSale() {
        return false;
    }

    @Override
    public String getType() {
        return "Showcase Book";
    }
}