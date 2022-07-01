package dao;

import exceptions.LoginException;
import dataaccess.Auth;

public interface IAuth {
    public void login(String id, String password) throws LoginException;
    public void logout();

    public Auth getAuth();

    public boolean isLoggedIn();
}
