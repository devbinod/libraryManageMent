package model;

import exceptions.LibrarySystemException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CheckoutFactory {

    public static void checkOutBook(Book book, LibraryMember member) throws LibrarySystemException {
        BookCopy[] copies = book.getCopies();
        if (!book.isAvailable()) {
            throw new LibrarySystemException("There is no available copies of the book with isbn: "+ book.getIsbn()); // Throw specific exception
        }
        for ( CheckoutEntry ce :member.getCheckoutRecord().getEntries() ) {
            if(ce.getBookCopy().getBook().getIsbn().equals(book.getIsbn())){
                throw new LibrarySystemException("This person already have a copy of the same book!");
            }
        }

        Optional<BookCopy> copy = Arrays.stream(copies).filter(BookCopy::isAvailable).
                findAny();
        if(copy.isPresent()) {
            BookCopy anyCopy = copy.get();
            CheckoutRecord record = member.getCheckoutRecord();
            LocalDate checkoutDate = LocalDate.now();
            LocalDate dueDate = checkoutDate.plusDays(book.getMaxCheckoutLength());
            CheckoutEntry entry = new CheckoutEntry(anyCopy, checkoutDate, dueDate);
            anyCopy.setCheckoutEntry(entry);
            record.addCheckoutEntry(entry);
            anyCopy.changeAvailability();

        }
    }

    public void returnCopy(BookCopy copy, LibraryMember member) {
        List<CheckoutEntry> entries = member.getCheckoutRecord().getEntries();
        if(entries.removeIf(entry -> copy.getCheckoutEntry().equals(entry))) {
            copy.changeAvailability();
        }

    }

}
