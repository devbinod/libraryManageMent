package services;

import model.LibraryMember;
import exceptions.LibrarySystemException;
import services.dao.IBook;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import model.Author;
import model.Book;
import model.CheckoutFactory;

import java.util.*;

public class BookService implements IBook {
    public void addBookCopy(String isbn) throws LibrarySystemException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> books = da.readBooksMap();
        Book b = books.get(isbn);
        if (b == null) {
            throw new LibrarySystemException("There is no such book with isbn: " + isbn);
        }
        b.addCopy();
        da.saveBookMap(books);
    }
//
//    @Override
//    public void addNewMember(String memberID, String firstName, String lastName, String street, String city, String state, String zip, String cell) throws LibrarySystemException {
//
//    }

    public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) throws LibrarySystemException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> books = da.readBooksMap();
        Book b = books.get(isbn);
        if (b != null) {
            throw new LibrarySystemException("There is already another book with same ISBN: " + isbn);
        }
        b = new Book(isbn, title, maxCheckoutLength, authors);
        da.saveNewBook(b);
    }

    @Override
    public List<String[]> getAllBookList() {

        DataAccess da = new DataAccessFacade();
        List<String[]> columns = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, Book> entry : da.readBooksMap().entrySet()) {

            String[] row = new String[]{
                    String.valueOf(++count),
                    entry.getValue().getIsbn(),
                    entry.getValue().getTitle(),
                    String.valueOf(entry.getValue().getNumCopies()),
                    String.valueOf(Arrays.stream(entry.getValue().getCopies()).filter(bookCopy -> bookCopy.isAvailable()).count())

            };
            columns.add(row);

        }
        return columns;

    }

    @Override
    public void checkoutBook(String memberID, String isbn) throws LibrarySystemException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> books = da.readBooksMap();
        Book b = books.get(isbn);
        HashMap<String, LibraryMember> members = da.readMemberMap();
        LibraryMember m = members.get(memberID);
        if (b == null) {
            throw new LibrarySystemException("There is no such book with isbn: " + isbn);
        }
        if (m == null) {
            throw new LibrarySystemException("There is no such member with memberID: " + memberID);
        }
        CheckoutFactory.checkOutBook(b, m);
        da.saveMemberMap(members);
        da.saveBookMap(books);
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }


}
