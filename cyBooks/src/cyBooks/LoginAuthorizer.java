package cyBooks;

public class LoginAuthorizer {
    /**
     * This class contains a static boolean parameter loginAuth.
     * It's used to ensure that a user has successfully logged in and is currently in an active session.
     * This prevents the system from loading application pages directly, bypassing the login page.
     * On login it's set to true, on logout set to false.
     * It is a value to test with each button trigger in the JavaFX application to make sure a librarian is authenticated in the session.
     * In case of a trigger with this variable set to false, engage redirection to login page.
     */
    public static boolean loginAuth = false;

    /**
     * Sets the static variable loginAuth to the provided value.
     * @param value the new value for loginAuth
     */
    public static void setLoginAuth(boolean value){
        loginAuth = value;  
    }
}
