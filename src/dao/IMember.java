package dao;

import exceptions.LibrarySystemException;

import java.util.List;

public interface IMember {
    public void addNewMember(String memberID, String firstName, String lastName, String street, String city, String state, String zip, String cell) throws LibrarySystemException;
    public List<String> allMemberIds();
    public List<String[]> getCheckoutEntries(String memberID) throws LibrarySystemException;
}
