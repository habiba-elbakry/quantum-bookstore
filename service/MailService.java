package service;

import model.EBook;

public interface MailService {
    void sendEBook(EBook book, String email);
}