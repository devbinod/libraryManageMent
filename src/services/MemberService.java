package services;

import model.LibraryMember;
import exceptions.LibrarySystemException;
import services.dao.IMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import model.Address;
import model.CheckoutEntry;
import model.CheckoutRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MemberService implements IMember {
    @Override
    public void addNewMember(String memberID, String firstName, String lastName, String street, String city, String state, String zip, String cell) throws LibrarySystemException {
        DataAccess da = new DataAccessFacade();
        LibraryMember member = da.readMemberMap().get(memberID);
        if (member != null) {
            throw new LibrarySystemException("There already is a member with the same ID!");
        }
        member = new LibraryMember(memberID, firstName, lastName, cell, new Address(street, city, state, zip));
        da.saveNewMember(member);
    }


    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }


    @Override
    public List<String[]> getCheckoutEntries(String memberID) throws LibrarySystemException {
        DataAccess da = new DataAccessFacade();
        LibraryMember m = da.readMemberMap().get(memberID);
        if (m == null) {
            throw new LibrarySystemException("There is no such member with memberID: " + memberID);
        }
        CheckoutRecord checkoutRecord = m.getCheckoutRecord();
        List<CheckoutEntry> checkoutEntries = checkoutRecord.getEntries();
        List<String[]> columns = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for (CheckoutEntry ce : checkoutEntries) {
            String[] row = new String[]{
                    memberID,
                    m.getFirstName() + " " + m.getLastName(),
                    ce.getBookCopy().getBook().getIsbn(),
                    ce.getBookCopy().getBook().getTitle(),
                    ce.getBookCopy().getCopyNum() + "",
                    ce.getCheckoutDate().toString(),
                    ce.getDueDate().toString()
            };
            columns.add(row);
        }
        return columns;
    }
}
