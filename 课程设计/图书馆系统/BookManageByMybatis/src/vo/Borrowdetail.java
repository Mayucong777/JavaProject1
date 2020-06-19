package vo;

public class Borrowdetail {

	private String jyh;
	private String username;
	private String userID;
	private String bookname;
	private String bookID;
	private String time;
	private int duration;
	private String state;

	
	public Borrowdetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Borrowdetail(String jyh, String username, String userID, String bookname, String bookID, String time,
			int duration, String state) {
		this.jyh = jyh;
		this.username = username;
		this.userID = userID;
		this.bookname = bookname;
		this.bookID = bookID;
		this.time = time;
		this.duration = duration;
		this.state = state;

	}

	public String getJyh() {
		return jyh;
	}

	public void setJyh(String jyh) {
		this.jyh = jyh;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String toString() {
		return  jyh + "\t" + username + "\t" + userID + "\t" + bookname
				+ "\t" + bookID + "\t" + time + "\t" + duration + "\t" + state;
	}

}