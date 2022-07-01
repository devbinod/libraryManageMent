package business;

import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public void logout();
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void checkoutBook(String memberID, String isbn) throws LibrarySystemException;
	public List<String[]> getCheckoutEntries(String memberID) throws LibrarySystemException;
	public void addBookCopy(String isbn) throws LibrarySystemException;
	public void addNewMember(String memberID, String firstName, String lastName, String street, String city, String state, String zip, String cell) throws LibrarySystemException;
	public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) throws LibrarySystemException;
	public Auth getAuth();
	
}
