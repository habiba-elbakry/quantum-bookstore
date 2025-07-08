import model.*;
import service.*;

public class QuantumBookstoreFullTest {
    public static void main(String[] args) {
        // Create mock services
        ShippingService shippingService = (book, address) -> System.out.println("Status: Ready for shipping");

        MailService mailService = (book, email) -> System.out.println("Status: Ready for download");

        // Create bookstore
        QuantumBookstore store = new QuantumBookstore(shippingService, mailService);

        // Add books to inventory
        store.addBook(new PaperBook("P001", "Java Programming", "habiba",
                2020, 99.99, 3));
        store.addBook(new EBook("E001", "Python Basics", "alice",
                2021, 49.99, "PDF"));
        store.addBook(new ShowcaseBook("S001", "Upcoming Title", "bakry",
                2024, 0.0));

        System.out.println("\n=== Test 1: Paper Book Purchase ===");
        try {
            store.buyBook("P001", 2, "customer@email.com", "123 Main St");
        } catch (IllegalArgumentException e) {
            System.out.println("Transaction Failed!");
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Test 2: E-Book Purchase ===");
        try {
            store.buyBook("E001", 1, "customer@email.com", null);
        } catch (IllegalArgumentException e) {
            System.out.println("Transaction Failed!");
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Test 3: Showcase Book (Not for Sale) ===");
        try {
            store.buyBook("S001", 1, "customer@email.com", null);
        } catch (IllegalArgumentException e) {
            System.out.println("Transaction Failed!");
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Test 4: Insufficient Stock ===");
        try {
            store.buyBook("P001", 20, "customer@email.com", "123 Main St");
        } catch (IllegalArgumentException e) {
            System.out.println("Transaction Failed!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}