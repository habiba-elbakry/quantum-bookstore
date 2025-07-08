package service;

import model.PaperBook;

public interface ShippingService {
    void shipBook(PaperBook book, String address);
}