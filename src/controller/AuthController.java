package controller;

import exceptions.LoginException;
import dao.IAuth;
import dataaccess.Auth;
import services.AuthService;

public class AuthController implements IAuth {
    IAuth auth = new AuthService();
    @Override
    public void login(String id, String password) throws LoginException {
        auth.login(id,password);
    }

    @Override
    public void logout() {
        auth.logout();
    }

    @Override
    public Auth getAuth() {
        return auth.getAuth();
    }


    @Override
    public boolean isLoggedIn() {
        return auth.isLoggedIn();
    }



}
