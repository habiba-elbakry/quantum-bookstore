package model;

public class EBook extends Book {
    private String fileType;

    public EBook(String isbn, String title, String author,
            int publicationYear, double price, String fileType) {
        super(isbn, title, author, publicationYear, price);
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public boolean isForSale() {
        return true;
    }

    @Override
    public String getType() {
        return "E-Book";
    }
}