package util;

import controller.AuthController;

public class LoginUtils {

    private static AuthController authController ;
    private  LoginUtils loginUtils = new LoginUtils();
    private LoginUtils(){};

    public static boolean isLoggedIn() {
        if(authController == null) authController = new AuthController();
       return authController.isLoggedIn();
    }

}
