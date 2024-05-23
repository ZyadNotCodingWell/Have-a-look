package cyBooks;

/**
 * Represents a librarian within the library management system.
 * This class provides methods to manage librarian authentication and access their basic details.
 */
public class Librarian {
	
	private int id;
	private String surname;
	private String name;
	private String login;
	private String password;
	 
	/**
          * Constructs a Librarian with specified details.
          *
          * @param id the unique identifier for the librarian
          * @param surname the surname of the librarian
          * @param name the name of the librarian
          * @param login the login username used for librarian authentication
          * @param password the password used for librarian authentication
          */
	public Librarian(int id, String surname, String name, String login, String password) {
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.login = login;
		this.password = password;
	}
         
	/**
          * Gets the librarian's login username.
          *
          * @return the login username of the librarian
          */
	public String getLogin() {
		return this.login;
	}
        
	/**
         * Sets the librarian's login username.
         *
         * @param login the new login username for the librarian
         */
	
	public void setLogin(String login) {
		this.login = login;
	}
       
	/**
         * Gets the librarian's password.
         *
         * @return the password of the librarian
         */
	public String getPassword() {
		return this.password;
	}
        
	/**
         * Sets the librarian's password.
         *
         * @param password the new password for the librarian
         */
	public void setPassword(String password) {
		this.password = password;
	}
         
	/**
         * Gets the librarian's unique identifier.
         *
         * @return the unique identifier of the librarian
         */
	public int getId() {
		return this.id;
	}
      
       /**
        * Gets the surname of the librarian.
        *
        * @return the surname of the librarian
        */
	public String getSurname() {
		return this.surname;
	}
        
	/**
         * Gets the name of the librarian.
         *
         * @return the name of the librarian
         */
	public String getName() {
		return this.name;
	}

	/**
         * Authenticates the librarian with a given username and password.
         *
         * @param logIn the username to log in
         * @param pwd the password to authenticate
         * @return true if the username and password are correct, false otherwise
         */
	public boolean login(String logIn, String pwd) { 
	    if (this.getLogin().equals(logIn) && this.getPassword().equals(pwd)) {
	        LoginAuthorizer.setLoginAuth(true);
	        return true;
	    } else { 
	        return false;
	    }
	}

	/**
         * Logs out the librarian from the system.
	 */
	public void logout() {
		LoginAuthorizer.setLoginAuth(false);
	}
	
}
