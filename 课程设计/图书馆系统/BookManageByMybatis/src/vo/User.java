package vo;

public class User {

	private String userID;
	private String password;
	private String username;
	private String academy;
	private String role;
	private String id;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String userID, String password, String username, String academy, String role, String id) {
		this.userID = userID;
		this.password = password;
		this.username = username;
		this.academy = academy;
		this.role = role;
		this.id = id;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return userID + "\t" + password + "\t" + username + "\t" + academy + "\t" + role + "\t" + id;
	}

}