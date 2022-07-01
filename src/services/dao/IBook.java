package services.dao;

import exceptions.LibrarySystemException;
import model.Author;
import model.Book;

import java.util.List;

public interface IBook {
    public void addBookCopy(String isbn) throws LibrarySystemException;
    public void addBook(Book book) throws LibrarySystemException;
    public List<String[]> getAllBookList();
    public void checkoutBook(String memberID, String isbn) throws LibrarySystemException;
    public List<String> allBookIds();
}
