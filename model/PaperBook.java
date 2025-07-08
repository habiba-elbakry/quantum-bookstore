package model;

public class PaperBook extends Book {
    private int stock;

    public PaperBook(String isbn, String title, String author,
            int publicationYear, double price, int stock) {
        super(isbn, title, author, publicationYear, price);
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        stock -= quantity;
    }

    @Override
    public boolean isForSale() {
        return stock > 0;
    }

    @Override
    public String getType() {
        return "Paper Book";
    }
}