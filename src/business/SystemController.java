package business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	private static Auth currentAuth = null;
	private static User loggedInUser = null;

	public static boolean isLoggedIn(){
		return loggedInUser != null;
	}

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		loggedInUser = map.get(id);
		currentAuth = map.get(id).getAuthorization();
	}

	public void logout(){
		loggedInUser = null;
		currentAuth = null;
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	public void addNewMember(String memberID, String firstName, String lastName, String street, String city, String state, String zip, String cell) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.readMemberMap().get(memberID);
		if(member != null){
			throw new LibrarySystemException("There already is a member with the same ID!");
		}
		member = new LibraryMember(memberID, firstName, lastName, cell, new Address(street, city, state, zip));
		da.saveNewMember(member);
	}

	public void addBookCopy(String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		Book b = books.get(isbn);
		if(b == null){
			throw new LibrarySystemException("There is no such book with isbn: "+isbn);
		}
		b.addCopy();
		da.saveBookMap(books);
	}

	public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		Book b = books.get(isbn);
		if(b != null){
			throw new LibrarySystemException("There is already another book with same ISBN: "+isbn);
		}
		b = new Book(isbn, title, maxCheckoutLength, authors);
		da.saveNewBook(b);
	}

	@Override
	public void checkoutBook(String memberID, String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		Book b = books.get(isbn);
		HashMap<String, LibraryMember> members = da.readMemberMap();
		LibraryMember m = members.get(memberID);
		if(b == null){
			throw new LibrarySystemException("There is no such book with isbn: "+isbn);
		}
		if(m == null){
			throw new LibrarySystemException("There is no such member with memberID: "+memberID);
		}
		CheckoutFactory.checkOutBook(b, m);
		da.saveMemberMap(members);
		da.saveBookMap(books);
	}

	@Override
	public List<String[]> getCheckoutEntries(String memberID) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		LibraryMember m = da.readMemberMap().get(memberID);
		if(m == null){
			throw new LibrarySystemException("There is no such member with memberID: "+memberID);
		}
		CheckoutRecord checkoutRecord = m.getCheckoutRecord();
		List<CheckoutEntry> checkoutEntries = checkoutRecord.getEntries();
		List<String[]> columns = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		for (CheckoutEntry ce: checkoutEntries){
			String[] row = new String[]{
					memberID,
					m.getFirstName() + " " + m.getLastName(),
					ce.getBookCopy().getBook().getIsbn(),
					ce.getBookCopy().getBook().getTitle(),
					ce.getBookCopy().getCopyNum()+"",
					ce.getCheckoutDate().toString(),
					ce.getDueDate().toString()
			};
			columns.add(row);
		}
		return columns;
	}

	@Override
	public Auth getAuth() {
		return currentAuth;
	}


}
