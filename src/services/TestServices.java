package services;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import model.Book;
import model.LibraryMember;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestServices {
    public static void main(String[] args) {
        System.out.println(allWhoseZipContains3());
        System.out.println(allHavingOverdueBook());
        System.out.println(allHavingMultipleAuthors());

    }
    //Returns a list of all ids of LibraryMembers whose zipcode contains the digit 3
    public static List<String> allWhoseZipContains3() {
        DataAccess da = new DataAccessFacade();
        Collection<LibraryMember> members = da.readMemberMap().values();
        List<LibraryMember> mems = new ArrayList<>();
        mems.addAll(members.stream().filter(m -> m.getAddress().getZip().contains("3")).toList());
        //implement
        return mems.stream().map(LibraryMember::getMemberId).toList();

    }
    //Returns a list of all ids of  LibraryMembers that have an overdue book
    public static List<String> allHavingOverdueBook() {
        DataAccess da = new DataAccessFacade();
        Collection<LibraryMember> members = da.readMemberMap().values();
        List<LibraryMember> mems = new ArrayList<>();
        mems.addAll(members.stream().filter(m -> m.getCheckoutRecord().getEntries().stream().anyMatch(e -> e.getCheckoutDate().compareTo(e.getDueDate()) > 0)).toList());
        //implement
        return mems.stream().map(LibraryMember::getMemberId).toList();

    }

    //Returns a list of all isbns of  Books that have multiple authors
    public static List<String> allHavingMultipleAuthors() {
        DataAccess da = new DataAccessFacade();
        Collection<Book> books = da.readBooksMap().values();
        List<Book> bs = new ArrayList<>();
        bs.addAll(books.stream().filter(b -> b.getAuthors().size() > 1).toList());
        //implement
        return bs.stream().map(Book::getIsbn).toList();

    }

}
