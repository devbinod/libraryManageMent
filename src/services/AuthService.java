package services;

import exceptions.LoginException;
import dao.IAuth;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.util.HashMap;

public class AuthService implements IAuth {
    private static Auth currentAuth = null;
    private static User loggedInUser = null;

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    @Override
    public void login(String id, String password) throws LoginException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        loggedInUser = map.get(id);
        currentAuth = map.get(id).getAuthorization();
    }

    @Override
    public void logout() {
        loggedInUser = null;
        currentAuth = null;
    }

    @Override
    public Auth getAuth() {
        return currentAuth;
    }
}
