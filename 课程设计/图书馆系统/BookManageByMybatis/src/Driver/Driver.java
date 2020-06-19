package Driver;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.IDataUtilDAO;
import dao.impl.DataUtilDAOImpl;
import service.BookService;
import service.BorrowdetailService;
import service.Global;
import service.Menu;
import service.UserService;
import vo.Book;
import vo.User;
import vo.Borrowdetail;

public class Driver {

	static IDataUtilDAO duDAO = new DataUtilDAOImpl();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserService userService = new UserService();

		BookService bookService = new BookService();

		BorrowdetailService bdService = new BorrowdetailService();

		try {
			if (!login(userService)) {
				System.out.println("���Ѿ����������Σ������˳�����ӭ�´η��ʣ�");
				System.exit(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��¼�쳣��");
		}
		while (true) {
			switch (Menu.Main_Menu()) {
			case 1:
				BookBorrow(bookService, bdService);
				break;
			case 2:
				BookQuery(bookService);
				break;
			case 3:
				BookReback(bookService, bdService);
				break;
			case 4:
				BookMaintain(bookService);
				break;
			case 5:
				UserMaintain(userService);
				break;
			case 6:
				PersonalCenter(userService, bdService);
				break;
			case 7:
				BorrowdetailManage(bdService);
				break;
			case 8:
				System.out.println("�˳��ɹ�!");
				System.exit(0);
				break;
			default:
				System.out.println("������Ч��ֻ������1-8��");
				break;
			}
		}
	}

	// ����
	private static boolean BookBorrow(BookService bookService, BorrowdetailService bdService) {
		// TODO Auto-generated method stub
		System.out.println("������ͼ����(6λ�����ַ�):");
		Scanner sc = new Scanner(System.in);
		String bookID = sc.next();
		try {
			Map<String, Object> map3 = bookService.Check(bookID);
			while (map3.get("code").equals(1) || map3.get("code").equals(2)) {
				System.out.println(map3.get("msg"));
				bookID = sc.next();
				if (bookID.equals("����")) {
					return false;
				}
				map3 = bookService.Check(bookID);
			}

			ArrayList<Book> booklist = new ArrayList<Book>();
			if (map3.get("code").equals(0)) {
				booklist = (ArrayList<Book>) map3.get("msg");
			} else {
				System.out.println("���ҳ����쳣,���Ժ����ԣ�");
				return false;
			}
			if (booklist.get(0).getInventory() == 0) {
				System.out.println("��ͼ������Ϊ0���޷����ģ�");
				return false;
			}
			Borrowdetail bd = new Borrowdetail();
			bd.setBookID(booklist.get(0).getBookID());

			Map<String, Object> map4 = bdService.CheckBD(bd);
			if (map4.get("code").equals(1)) {
				System.out.println(map4.get("msg"));
				return false;
			} else {
				// ��ʾͼ��
				bookService.showBook(booklist);

				// ����ʱ��
				System.out.println("���������ʱ�䣨�죩��");
				int duration = sc.nextInt();

				// Borrowdetail bd = new Borrowdetail();
				bd.setBookID(booklist.get(0).getBookID());
				bd.setBookname(booklist.get(0).getBookname());
				bd.setDuration(duration);

				Map<String, Object> map = bdService.Create(bd);
				if (map.get("code").equals(0)) {
					System.out.println(map.get("msg"));
				} else {
					System.out.println(map.get("msg"));
				}

				Book book2 = booklist.get(0);

				// ����book���ݿ⣬�������һ
				book2.setInventory(booklist.get(0).getInventory() - 1);
				Map<String, Object> map2 = bookService.Update(book2);
				if (map2.get("code").equals(0)) {
					System.out.println(map2.get("msg"));
				} else {
					System.out.println(map2.get("msg"));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�����쳣!");
		}
		return true;
	}

	// ��ѯ
	private static void BookQuery(BookService bookService) {
		// TODO Auto-generated method stub
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		String msg = "";
		while (result) {
			int c = 1;
			Book book = new Book();
			ArrayList<Book> booklist = new ArrayList<Book>();
			switch (Menu.QueryBook_Menu()) {
			case 1:
				System.out.println("������Ҫ���ҵ�ͼ������:");
				msg = sc.next();
				book.setBookname(msg);
				break;
			case 2:
				System.out.println("������Ҫ���ҵ�ͼ����:");
				msg = sc.next();
				try {
					Map<String, Object> map3 = bookService.Check(msg);
					while (map3.get("code").equals(1) || map3.get("code").equals(2)) {
						System.out.println(map3.get("msg"));
						msg = sc.next();
						map3 = bookService.Check(msg);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				book.setBookID(msg);
				break;
			case 3:
				System.out.println("������Ҫ���ҵ�ͼ������:");
				msg = sc.next();
				book.setBooktype(msg);
				break;
			case 4:
				System.out.println("������Ҫ���ҵ�������:");
				msg = sc.next();
				book.setAuthor(msg);
				break;
			case 5:
				System.out.println("������Ҫ���ҵĳ�������:");
				msg = sc.next();
				book.setSupply(msg);
				break;
			case 6:
				break;
			case 7:
				result = false;
				break;
			default:
				System.out.println("������Ч!���������룺");
				c = 0;
			}
			if (c != 0) {
				try {
					booklist = bookService.Query(book);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("��ѯ�������쳣�����Ժ�����!");
				}
				if (!booklist.isEmpty()) {
					bookService.showBook(booklist);
				}
			}
		}

	}

	// �黹
	private static boolean BookReback(BookService bookService, BorrowdetailService bdService) {
		// TODO Auto-generated method stub
		System.out.println("������Ҫ����ͼ����(6λ�����ַ�):");
		Scanner sc = new Scanner(System.in);
		String bookID = sc.next();
		try {
			Map<String, Object> map3 = bookService.Check(bookID);
			while (map3.get("code").equals(1) || map3.get("code").equals(2)) {
				System.out.println(map3.get("msg"));
				bookID = sc.next();
				if (bookID.equals("����")) {
					return false;
				}
				map3 = bookService.Check(bookID);
			}

			ArrayList<Book> booklist = new ArrayList<Book>();
			if (map3.get("code").equals(0)) {
				booklist = (ArrayList<Book>) map3.get("msg");
			} else {
				System.out.println("���ҳ����쳣,���Ժ����ԣ�");
				return false;
			}

			// ��ʾͼ��
			// bookService.showBook(booklist);

			Borrowdetail bd = new Borrowdetail();
			bd.setBookID(booklist.get(0).getBookID());

			Map<String, Object> map4 = bdService.CheckBD(bd);
			if (map4.get("code").equals(0)) {
				System.out.println(map4.get("msg"));
				return false;
			} else {
				Map<String, Object> map = bdService.Create(bd);
				if (map.get("code").equals(0)) {
					System.out.println(map.get("msg"));
				} else {
					System.out.println(map.get("msg"));
				}

				Book book2 = booklist.get(0);

				// ����book���ݿ⣬�������һ
				book2.setInventory(booklist.get(0).getInventory() + 1);
				Map<String, Object> map2 = bookService.Update(book2);
				if (map2.get("code").equals(0)) {
					System.out.println(map2.get("msg"));
				} else {
					System.out.println(map2.get("msg"));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�����쳣!");
		}
		return true;
	}

	// ͼ��ά��
	private static boolean BookMaintain(BookService bookService) {
		// TODO Auto-generated method stub
		if (!Global.currentUser.getRole().equals("����Ա")) {
			System.out.println("��ǰ�û�û��ִ�и���ܵ�Ȩ��!");
			return false;
		}
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		String msg = "";
		while (result) {
			switch (Menu.Maintainbook_Menu()) {
			case 1:
				BookAdd(bookService);
				break;
			case 2:
				try {
					BookSub(bookService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("ͼ��ɾ���������쳣�����Ժ�����!");
				}
				break;
			case 3:
				BookAlter(bookService);
				break;
			case 4:
				BookQuery(bookService);
				break;
			case 5:
				BookExport(bookService);
				break;
			case 6:
				result = false;
				break;
			default:
				System.out.println("������Ч!���������룺");
			}
		}
		return true;
	}

	// ����ͼ��
	private static void BookAdd(BookService bookService) {
		// TODO Auto-generated method stub
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		// ָ�������ļ�����
		Global.setFileName("Book");
		while (result) {
			switch (Menu.Addbook_Menu()) {
			case 1:
				try {
					duDAO.arraytoTable(duDAO.getXLSAsArray(Global.XLS_FILE, "vo.Book"), "vo.Book", "book");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("����ʧ�ܣ�����ϸ��������ļ���ʽ!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTable(duDAO.getTXTAsArray(Global.TXT_FILE, "vo.Book"), "vo.Book", "book");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("����ʧ�ܣ�����ϸ��������ļ���ʽ!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoTable(duDAO.getXMLAsArray(Global.XML_FILE, "vo.Book"), "vo.Book", "book");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("����ʧ�ܣ�����ϸ��������ļ���ʽ!");
				}
				break;
			case 4:
				System.out.println("������Ҫ�����ͼ����Ϣ(ͼ���ţ�ͼ�����ƣ������磬���ߣ�ͼ�����ͣ������):");
				String msg = sc.nextLine();
				String str[] = msg.split(",|��");
				Book book = new Book(str[0], str[1], str[2], str[3], str[4], Integer.parseInt(str[5]));
				ArrayList<Object> list = new ArrayList<Object>();
				list.add(book);
				try {
					duDAO.arraytoTable(list, "vo.Book", "book");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("����ʧ�ܣ�����ϸ������������ʽ!");
				}
				break;
			case 5:
				result = false;
				break;
			default:
				System.out.println("������Ч!���������룺");
			}
		}
	}

	// ɾ��ͼ��
	private static boolean BookSub(BookService bookService) throws Exception {
		// TODO Auto-generated method stub
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		String msg = "";
		while (result) {
			int c = 0;
			Book book = new Book();

			switch (Menu.Subbook_Menu()) {
			case 1:
				System.out.println("������Ҫɾ����ͼ������:");
				msg = sc.next();
				book.setBookname(msg);
				break;
			case 2:
				System.out.println("������Ҫɾ����ͼ����:");
				msg = sc.next();
				try {
					Map<String, Object> map3 = bookService.Check(msg);
					while (map3.get("code").equals(1) || map3.get("code").equals(2)) {
						System.out.println(map3.get("msg"));
						msg = sc.next();
						map3 = bookService.Check(msg);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				book.setBookID(msg);
				break;
			case 3:
				System.out.println("������Ҫɾ����ͼ������:");
				msg = sc.next();
				book.setBooktype(msg);
				break;
			case 4:
				System.out.println("������Ҫɾ����������:");
				msg = sc.next();
				book.setAuthor(msg);
				break;
			case 5:
				System.out.println("������Ҫɾ���ĳ�������:");
				msg = sc.next();
				book.setSupply(msg);
				break;
			case 6:
				System.out.println("���Ƿ�������ݿ�������ͼ�飨������������");
				String choice = sc.next();
				if (choice.equals("��")) {
					c = 1;
				}
				break;
			case 7:
				result = false;
				c = 1;
				break;
			default:
				System.out.println("������Ч!���������룺");
				c = 1;
			}
			if (c != 1) {
					if (bookService.Query(book).isEmpty()) {
						return false;
					}
					book.setInventory(-1);
					Map<String, Object> map = bookService.Delete(book);
					if (map.get("code").equals(0)) {
						System.out.println(map.get("msg"));
					} else {
						System.out.println(map.get("msg"));
					}
				
			}
		}
		return true;
	}

	// �޸�ͼ��
	private static boolean BookAlter(BookService bookService) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String msg = "";
		if (true) {
			Book book = new Book();
			System.out.println("������Ҫ�޸ĵ�ͼ����:");
			msg = sc.next();
			try {
				Map<String, Object> map3 = bookService.Check(msg);
				while (map3.get("code").equals(1) || map3.get("code").equals(2)) {
					System.out.println(map3.get("msg"));
					msg = sc.next();
					map3 = bookService.Check(msg);
				}
				ArrayList<Book> booklist = (ArrayList<Book>) map3.get("msg");
				bookService.showBook(booklist);

				System.out.println("�������޸ĺ�ĸ�����Ϣ(ͼ�����ƣ������磬���ߣ�ͼ�����ͣ������)(���뷵�ط�����һ��):");
				String info = sc.next();
				if (info.equals("����")) {
					return false;
				}
				String str[] = info.split("��|,");
				Book b = new Book(msg, str[0], str[1], str[2], str[3], Integer.parseInt(str[4]));
				Map<String, Object> map2 = bookService.Update(b);
				if (map2.get("code").equals(0)) {
					System.out.println(map2.get("msg"));
				} else {
					System.out.println(map2.get("msg"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ͼ���޸ķ������쳣�����Ժ�����!");
			}
		}
		return true;
	}

	// ����ͼ��
	private static void BookExport(BookService bookService) {
		// TODO Auto-generated method stub
		boolean result = true;
		// �����ļ���������������
		Global.setFileName( "Book" + Global.getTodayDate());
		while (result) {
			switch (Menu.Exportbook_Menu()) {
			case 1:
				try {
					duDAO.arraytoXLS(duDAO.getTableAsArray("book", "vo.Book"), "vo.Book", Global.XLS_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("ͼ����Ϣ����ʧ�ܣ������Ƿ���ڴ���!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTXT(duDAO.getTableAsArray("book", "vo.Book"), "vo.Book", Global.TXT_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("ͼ����Ϣ����ʧ�ܣ������Ƿ���ڴ���!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoXML(duDAO.getTableAsArray("book", "vo.Book"), "vo.Book", Global.XML_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("ͼ����Ϣ����ʧ�ܣ������Ƿ���ڴ���!");
				}
				break;
			case 4:
				result = false;
				break;
			}
		}
	}

	// �û�ά��
	private static boolean UserMaintain(UserService userService) {
		// TODO Auto-generated method stub
		if (!Global.currentUser.getRole().equals("����Ա")) {
			System.out.println("��ǰ�û�û��ִ�и���ܵ�Ȩ��!");
			return false;
		}
		boolean result = true;
		while (result) {
			switch (Menu.MaintainUser_Menu()) {
			case 1:
				UserAdd(userService);
				break;
			case 2:
				try {
					UserSub(userService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("�û�ɾ���������쳣�����Ժ�����!");
				}
				break;
			case 3:
				try {
					UserAlter(userService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("�û��޸ĳ������쳣�����Ժ�����!");
				}
				break;
			case 4:
				try {
					UserQuery(userService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("�û���ѯ�������쳣�����Ժ�����!");
				}
				break;
			case 5:
				UserExport(userService);
				break;
			case 6:
				result = false;
				break;
			}
		}
		return true;
	}

	private static void UserAdd(UserService userService) {
		// TODO Auto-generated method stub
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		// ָ�������ļ�����
		Global.setFileName("User");
		while (result) {
			switch (Menu.AddUser_Menu()) {
			case 1:
				try {
					duDAO.arraytoTable(duDAO.getXLSAsArray(Global.XLS_FILE, "vo.User"), "vo.User", "user");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("����ʧ�ܣ�����ϸ��������ļ���ʽ!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTable(duDAO.getTXTAsArray(Global.TXT_FILE, "vo.User"), "vo.User", "user");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("����ʧ�ܣ�����ϸ��������ļ���ʽ!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoTable(duDAO.getXMLAsArray(Global.XML_FILE, "vo.User"), "vo.User", "user");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("����ʧ�ܣ�����ϸ��������ļ���ʽ!");
				}
				break;
			case 4:
				System.out.println("������Ҫ������û���Ϣ(ӵ���˺ţ����룬�û�����������ѧԺ����ɫ��ѧ��/����):");
				String msg = sc.nextLine();
				String str[] = msg.split(",|��");
				User user = new User(str[0], str[1], str[2], str[3], str[4], str[5]);
				ArrayList<Object> list = new ArrayList<Object>();
				list.add(user);
				try {
					duDAO.arraytoTable(list, "vo.User", "user");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("����ʧ�ܣ�����ϸ������������ʽ!");
				}
				break;
			case 5:
				result = false;
				break;
			default:
				System.out.println("������Ч!���������룺");
			}
		}
	}

	private static boolean UserSub(UserService userService) throws Exception {
		// TODO Auto-generated method stub
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		String msg = "";
		while (result) {
			int c = 0;
			User user = new User();
			switch (Menu.SubUser_Menu()) {
			case 1:
				System.out.println("������Ҫɾ�����û��˺�:");
				msg = sc.next();
				Map<String, Object> map3 = userService.CheckUserID(msg);
				while (!map3.get("code").equals(0)) {
					System.out.println(map3.get("msg"));
					msg = sc.next();
					if (msg.equals("����")) {
						return false;
					}
					map3 = userService.CheckUserID(msg);
				}
				user.setUserID(msg);
				break;
			case 2:
				System.out.println("������Ҫɾ�����û�����:");
				msg = sc.next();
				user.setUsername(msg);
				break;
			case 3:
				System.out.println("������Ҫɾ����ѧ��:");
				msg = sc.next();
				user.setId(msg);
				break;
			case 4:
				System.out.println("������Ҫɾ����ѧԺ:");
				msg = sc.next();
				user.setAcademy(msg);
				break;
			case 5:
				System.out.println("������Ҫɾ�����û���ɫ:");
				msg = sc.next();
				user.setRole(msg);
				break;
			case 6:
				System.out.println("���Ƿ�������ݿ��������û���������������");
				String choice = sc.next();
				if (choice.equals("��")) {
					c = 1;
				}
				break;
			case 7:
				result = false;
				c = 1;
				break;
			default:
				System.out.println("������Ч!���������룺");
				c = 1;
			}
			if (c != 1) {
				if (userService.Query(user).isEmpty()) {
					return false;
				}
				Map<String, Object> map = userService.Delete(user);
				if (map.get("code").equals(0)) {
					System.out.println(map.get("msg"));
				} else {
					System.out.println(map.get("msg"));
				}
			}
		}
		return true;
	}

	private static boolean UserAlter(UserService userService) throws Exception {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String msg = "";
		if (true) {
			User user = new User();
			System.out.println("��������Ҫ�޸ĵ��û��˺�:");
			msg = sc.next();
			Map<String, Object> map3 = userService.CheckUserID(msg);
			while (map3.get("code").equals(1) || map3.get("code").equals(2)) {
				System.out.println(map3.get("msg"));
				msg = sc.next();
				map3 = userService.CheckUserID(msg);
			}
			ArrayList<User> userlist = (ArrayList<User>) map3.get("msg");
			userService.showUser(userlist);

			System.out.println("�������޸ĺ�ĸ�����Ϣ(���룬�û���������ѧԺ����ɫ��ѧ��/����)(���뷵�ط�����һ��):");
			String info = sc.next();
			if (info.equals("����")) {
				return false;
			}
			String str[] = info.split("��|,");
			User u = new User(msg, str[0], str[1], str[2], str[3], str[4]);
			Map<String, Object> map2 = userService.Update(u);
			if (map2.get("code").equals(0)) {
				System.out.println(map2.get("msg"));
			} else {
				System.out.println(map2.get("msg"));
			}
		}
		return true;
	}

	private static boolean UserQuery(UserService userService) throws Exception {
		// TODO Auto-generated method stub
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		String msg = "";
		while (result) {
			int c = 1;
			User user = new User();
			ArrayList<User> userlist = new ArrayList<User>();
			switch (Menu.QueryUser_Menu()) {
			case 1:
				System.out.println("������Ҫ���ҵ��û��˺�:");
				msg = sc.next();
				Map<String, Object> map3 = userService.CheckUserID(msg);
				while (!map3.get("code").equals(0)) {
					System.out.println(map3.get("msg"));
					msg = sc.next();
					if (msg.equals("����")) {
						return false;
					}
					map3 = userService.CheckUserID(msg);
				}
				user.setUserID(msg);
				break;
			case 2:
				System.out.println("������Ҫ���ҵ��û�����:");
				msg = sc.next();
				user.setUsername(msg);
				break;
			case 3:
				System.out.println("������Ҫ���ҵ�ѧ��:");
				msg = sc.next();
				user.setId(msg);
				break;
			case 4:
				System.out.println("������Ҫ���ҵ�ѧԺ:");
				msg = sc.next();
				user.setAcademy(msg);
				break;
			case 5:
				System.out.println("������Ҫ���ҵ��û���ɫ:");
				msg = sc.next();
				user.setRole(msg);
				break;
			case 6:
				break;
			case 7:
				result = false;
				c = 1;
				break;
			default:
				System.out.println("������Ч!���������룺");
				c = 1;
			}
			if (c != 0) {
				try {
					userlist = userService.Query(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("��ѯ�������쳣�����Ժ�����!");
				}
				if (!userlist.isEmpty()) {
					userService.showUser(userlist);
				}
			}
		}
		return true;
	}

	private static void UserExport(UserService userService) {
		// TODO Auto-generated method stub
		boolean result = true;
		// �����ļ���������������
		Global.setFileName("User" + Global.getTodayDate());
		while (result) {
			switch (Menu.Exportbook_Menu()) {
			case 1:
				try {
					duDAO.arraytoXLS(duDAO.getTableAsArray("user", "vo.User"), "vo.User", Global.XLS_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("�û���Ϣ����ʧ�ܣ������Ƿ���ڴ���!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTXT(duDAO.getTableAsArray("user", "vo.User"), "vo.User", Global.TXT_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("�û���Ϣ����ʧ�ܣ������Ƿ���ڴ���!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoXML(duDAO.getTableAsArray("user", "vo.User"), "vo.User", Global.XML_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("�û���Ϣ����ʧ�ܣ������Ƿ���ڴ���!");
				}
				break;
			case 4:
				result = false;
				break;
			}
		}
	}

	// ��������
	private static void PersonalCenter(UserService userService, BorrowdetailService bdService) {
		// TODO Auto-generated method stub
		boolean result = true;
		while (result) {
			switch (Menu.PersonalCenter_Menu()) {
			case 1:
				try {
					BorrowdetailQurey(bdService);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					System.out.println("��ѯ���ļ�¼�������쳣�����Ժ�����!");
				}
				break;
			case 2:
				try {
					lengthenDuration(bdService);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("����ʱ���ӳ��������쳣�����Ժ�����!");
				}
				break;
			case 3:
				try {
					changePassword(userService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("�û�������·������쳣,���Ժ�����!");
				}
				break;
			case 4:
				try {
					changeInformation(userService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("�û���Ϣ���·������쳣,���Ժ�����!");
				}
				break;
			case 5:
				result = false;
				break;
			}
		}
	}

	private static boolean lengthenDuration(BorrowdetailService bdService) throws Exception {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("������Ҫ�ӳ���ͼ����:");
		String bookID = sc.next();
		BookService bookService = new BookService();

		Borrowdetail bd = new Borrowdetail();
		bd.setBookID(bookID);
		Map<String, Object> map4 = bdService.getBDbyBookID(bookID);
		if (!map4.get("code").equals(0)) {
			System.out.println(map4.get("msg"));
			return false;
		} else {
			bd = (Borrowdetail) map4.get("msg");
			System.out.println("������Ҫ�ӳ�������:");
			int count = sc.nextInt();
			bd.setDuration(bd.getDuration() + count);
			Map<String, Object> map = bdService.UpdateDuration(bd);
			if (map.get("code").equals(0)) {
				System.out.println(map.get("msg"));
			} else {
				System.out.println(map.get("msg"));
			}
		}
		return true;
	}

	private static boolean changeInformation(UserService userService) throws Exception {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int c = 0;
		System.out.println("��������˺��Ѱ󶨵���ʵ����:");
		String username = sc.next();
		System.out.println("��������˻��Ѱ󶨵�ѧ��/����");
		String id = sc.next();
		while (!(Global.currentUser.getUsername().equals(username) | Global.currentUser.getId().equals(id))) {
			System.out.println("������Ϣ��Ԥ�Ȱ���Ϣ����!");
			System.out.println("��������˺��Ѱ󶨵���ʵ����:");
			username = sc.next();
			System.out.println("��������˻��Ѱ󶨵�ѧ��/����");
			id = sc.next();
			c++;
			if (c > 3) {
				System.out.println("ϵͳ�˲鵽���������Ϣ�������˺��Ѷ��ᣡ");
				System.exit(0);
			}
		}
		System.out.println("�������޸ĺ����Ϣ(������ѧԺ��ѧ��/����)(���뷵�ط�����һ��):");
		String info = sc.nextLine();
		if (info.equals("����")) {
			return false;
		}
		String str[] = info.split("��|,");
		Global.currentUser.setUsername(str[0]);
		Global.currentUser.setAcademy(str[1]);
		Global.currentUser.setId(str[2]);
		Map<String, Object> map;
		map = userService.Update(Global.currentUser);
		if (map.get("code").equals(0)) {
			System.out.println(map.get("msg"));
		} else {
			System.out.println(map.get("msg"));
		}
		return true;
	}

	// �޸�����
	private static void changePassword(UserService userService) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("�������޸ĺ������:");
		Scanner sc = new Scanner(System.in);
		String password = sc.next();

		Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");
		Matcher m = p.matcher(password);
		while (!m.find()) {

			System.out.println("�������벻���ϸ�����Ҫ�����볤�Ȳ�����6���ַ���������һ��Сд��ĸ��������һ����д��ĸ������һ�����֣������������룺");
			password = sc.next();
			m = p.matcher(password);

		}
		System.out.println("������ȷ������:");
		String password2 = sc.next();
		while (!password2.equals(password)) {
			System.out.println("����������������һ��,����������ȷ������:");
			password2 = sc.next();
		}
		Global.currentUser.setPassword(password);

		Map<String, Object> map = userService.Update(Global.currentUser);
		if (map.get("code").equals(0)) {
			System.out.println(map.get("msg"));
		} else {
			System.out.println(map.get("msg"));
		}
	}

	// ������Ϣ����
	private static boolean BorrowdetailManage(BorrowdetailService bdService) {
		// TODO Auto-generated method stub
		if (!Global.currentUser.getRole().equals("����Ա")) {
			System.out.println("��ǰ�û�û��ִ�и���ܵ�Ȩ��!");
			return false;
		}
		boolean result = true;
		while (result) {
			switch (Menu.BorrowdetailManage_Menu()) {
			case 1:
				try {
					BorrowdetailQurey(bdService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("���ļ�¼��ѯ�������쳣�����Ժ�����!");
				}
				break;
			case 2:
				BorrowdetailExport(bdService);
				break;
			case 3:
				result = false;
				break;
			}
		}
		return true;
	}

	private static boolean BorrowdetailQurey(BorrowdetailService bdService) throws Exception {
		// TODO Auto-generated method stub
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		String msg = "";
		while (result) {
			int c = 1;
			Borrowdetail bd = new Borrowdetail();
			ArrayList<Borrowdetail> bdlist = new ArrayList<Borrowdetail>();
			UserService userService = new UserService();
			BookService bookService = new BookService();
			switch (Menu.QureyBorrowdetail_Menu()) {
			case 1:
				System.out.println("������Ҫ���ҵ�ͼ������:");
				msg = sc.next();
				bd.setBookname(msg);
				break;
			case 2:
				System.out.println("������Ҫ���ҵ�ͼ����:");
				msg = sc.next();
				Map<String, Object> map3 = bookService.Check(msg);
				while (! map3.get("code").equals(0)) {
					System.out.println(map3.get("msg"));
					msg = sc.next();
					if(msg.equals("����")) {
						return false;
					}
					map3 = bookService.Check(msg);
				}
				bd.setBookID(msg);
				break;
			case 3:
				System.out.println("������Ҫ���ҵ��û��˺�:");
				msg = sc.next();
				Map<String, Object> map4 = userService .CheckUserID(msg);
				while (!map4.get("code").equals(0)) {
					System.out.println(map4.get("msg"));
					msg = sc.next();
					if (msg.equals("����")) {
						return false;
					}
					map4 = userService.CheckUserID(msg);
				}
				bd.setUserID(msg);
				break;
			case 4:
				System.out.println("������Ҫ���ҵ��û�����:");
				msg = sc.next();
				bd.setUsername(msg);
				break;
			case 5:
				System.out.println("������Ҫ���ҵĽ��ĺ�:");
				msg = sc.next();
				bd.setJyh(msg);
				break;
			case 6:
				System.out.println("������Ҫ���ҵĽ�������(yyyy-MM-dd):");
				msg = sc.next();
				Map<String, Object> map = bdService.searchByDate(msg,"����");
				if(!map.get("code").equals(0)) {
					System.out.println(map.get("msg"));
				}else {
					bdService.showBorrowdetail((ArrayList<Borrowdetail>) map.get("msg"));
				}
				c=0;
				break;
			case 7:
				System.out.println("������Ҫ���ҵĻ�������(yyyy-MM-dd:");
				msg = sc.next();
				Map<String, Object> map2 = bdService.searchByDate(msg,"�黹");
				if(!map2.get("code").equals(0)) {
					System.out.println(map2.get("msg"));
				}else {
					bdService.showBorrowdetail((ArrayList<Borrowdetail>) map2.get("msg"));
				}
				c=0;
				break;
			case 8:
				break;
			case 9:
				result = false;
				break;
			default:
				System.out.println("������Ч!���������룺");
				c = 0;
			}
			if (c != 0) {
				try {
					bdlist = bdService.Query(bd);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("��ѯ�������쳣�����Ժ�����!");
				}
				if (!bdlist.isEmpty()) {
					bdService.showBorrowdetail(bdlist);
				}
			}
		}
		return true;
	}

	private static void BorrowdetailExport(BorrowdetailService bdService) {
		// TODO Auto-generated method stub
		boolean result = true;
		// �����ļ���������������
		Global.setFileName("Borrowdetail" + Global.getTodayDate());
		while (result) {
			switch (Menu.Exportbook_Menu()) {
			case 1:
				try {
					duDAO.arraytoXLS(duDAO.getTableAsArray("borrowdetail", "vo.Borrowdetail"), "vo.Borrowdetail", Global.XLS_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("������Ϣ����ʧ�ܣ������Ƿ���ڴ���!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTXT(duDAO.getTableAsArray("borrowdetail", "vo.Borrowdetail"), "vo.Borrowdetail", Global.TXT_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("������Ϣ����ʧ�ܣ������Ƿ���ڴ���!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoXML(duDAO.getTableAsArray("borrowdetail", "vo.Borrowdetail"), "vo.Borrowdetail", Global.XML_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("������Ϣ����ʧ�ܣ������Ƿ���ڴ���!");
				}
				break;
			case 4:
				result = false;
				break;
			}
		}
	}

	// ��¼��֤
	private static boolean login(UserService userService) throws Exception {
		// TODO Auto-generated method stub

		User user = new User();
		boolean result = false;
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 3; i++) {
			System.out.println("�������˺�:");
			String userID = sc.next();
			System.out.println("����������:");
			String password = sc.next();
			user.setUserID(userID);
			user.setPassword(password);

			Map<String, Object> map = userService.checkLogin(user);
			if (map.get("code").equals(0)) {
				System.out.println(map.get("msg"));
				result = true;
				break;
			} else {
				System.out.println(map.get("msg"));
			}
		}
		return result;
	}

}
