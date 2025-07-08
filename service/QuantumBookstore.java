package service;

import model.*;
import java.time.LocalDate;
import java.util.*;

public class QuantumBookstore {
    private final Map<String, Book> inventory;
    private final ShippingService shippingService;
    private final MailService mailService;

    public QuantumBookstore(ShippingService shippingService, MailService mailService) {
        this.inventory = new HashMap<>();
        this.shippingService = shippingService;
        this.mailService = mailService;
    }

    public void addBook(Book book) {
        System.out.println("Quantum Book Store: Adding book - " + book.getTitle());
        inventory.put(book.getIsbn(), book);
    }

    public List<Book> removeOutdatedBooks(int yearsThreshold) {
        System.out.println("Quantum Book Store: Removing outdated books");
        int currentYear = LocalDate.now().getYear();
        List<Book> outdatedBooks = new ArrayList<>();

        inventory.entrySet().removeIf(entry -> {
            Book book = entry.getValue();
            if ((currentYear - book.getPublicationYear()) > yearsThreshold) {
                outdatedBooks.add(book);
                return true;
            }
            return false;
        });

        return outdatedBooks;
    }

    public double buyBook(String isbn, int quantity, String email, String address) {
        System.out.println("Quantum Book Store: Processing purchase for ISBN: " + isbn);

        Book book = inventory.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        if (!book.isForSale()) {
            throw new IllegalArgumentException("Book is not for sale");
        }

        double totalPrice = book.getPrice() * quantity;

        if (book instanceof PaperBook) {
            PaperBook paperBook = (PaperBook) book;
            paperBook.reduceStock(quantity);
            shippingService.shipBook(paperBook, address);
        } else if (book instanceof EBook) {
            mailService.sendEBook((EBook) book, email);
        }

        return totalPrice;
    }
}