package vo;

public class Book {

	private String bookID;
	private String bookname;
	private String supply;
	private String author;
	private String booktype;
	private int inventory;

	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(String bookID, String bookname, String supply, String author, String booktype, int inventory) {
		this.bookID = bookID;
		this.bookname = bookname;
		this.supply = supply;
		this.author = author;
		this.booktype = booktype;
		this.inventory = inventory;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getSupply() {
		return supply;
	}

	public void setSupply(String supply) {
		this.supply = supply;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBooktype() {
		return booktype;
	}

	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public String toString() {
		return  bookID + "\t" + bookname + "\t" + supply + "\t" + author
				+ "\t" + booktype + "\t" + inventory ;
	}

}
