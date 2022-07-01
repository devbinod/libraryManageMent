package controller;

import exceptions.LibrarySystemException;
import services.dao.IBook;
import model.Author;
import services.BookService;

import java.util.List;

public class BookController implements IBook {

    IBook iBook = new BookService();

    @Override
    public void addBookCopy(String isbn) throws LibrarySystemException {
        iBook.addBookCopy(isbn);
    }


    @Override
    public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) throws LibrarySystemException {
        iBook.addBook(isbn,title,maxCheckoutLength,authors);
    }

    @Override
    public List<String[]> getAllBookList() {
        return iBook.getAllBookList();
    }

    @Override
    public void checkoutBook(String memberID, String isbn) throws LibrarySystemException {
        iBook.checkoutBook(memberID,isbn);
    }

    @Override
    public List<String> allBookIds() {
        return iBook.allBookIds();
    }
}
