import model.*;
import service.*;
import java.time.LocalDate;
import java.util.*;

public class QuantumBookstore {
    private final Map<String, Book> inventory;
    private final ShippingService shippingService;
    private final MailService mailService;
    private static final double SHIPPING_RATE = 30.0;

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
        Book book = inventory.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        if (!book.isForSale()) {
            throw new IllegalArgumentException("Book is not for sale");
        }

        double subtotal = book.getPrice() * quantity;
        double shippingCost = (book instanceof PaperBook) ? SHIPPING_RATE : 0;
        double total = subtotal + shippingCost;

        // Print checkout receipt
        System.out.println("\n** Checkout Receipt **");
        System.out.printf("%dx %s %.2f%n", quantity, book.getTitle(), subtotal);
        System.out.println("----------------------");
        System.out.printf("Subtotal %.2f%n", subtotal);
        System.out.printf("Shipping %.2f%n", shippingCost);
        System.out.printf("Total %.2f%n", total);
        System.out.println("----------------------");

        // Process the order
        if (book instanceof PaperBook) {
            PaperBook paperBook = (PaperBook) book;
            try {
                paperBook.reduceStock(quantity);
                System.out.println("\n** Shipment Notice **");
                System.out.printf("%dx %s%n", quantity, book.getTitle());
                shippingService.shipBook(paperBook, address);
                System.out.println("Shipping to: " + address);
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage());
                throw e;
            }
        } else if (book instanceof EBook) {
            System.out.println("\n** Digital Delivery **");
            System.out.println("Sending to: " + email);
            mailService.sendEBook((EBook) book, email);
        }

        return total;
    }
}