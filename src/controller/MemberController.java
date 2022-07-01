package controller;

import exceptions.LibrarySystemException;
import dao.IMember;
import services.MemberService;

import java.util.List;

public class MemberController implements IMember {
    private IMember iMember = new MemberService();
    @Override
    public void addNewMember(String memberID, String firstName, String lastName, String street, String city, String state, String zip, String cell) throws LibrarySystemException {
        iMember.addNewMember(memberID,firstName,lastName,street,city,state,zip,cell);
    }

    @Override
    public List<String> allMemberIds() {
        return iMember.allMemberIds();
    }

    @Override
    public List<String[]> getCheckoutEntries(String memberID) throws LibrarySystemException {
        return iMember.getCheckoutEntries(memberID);
    }
}
