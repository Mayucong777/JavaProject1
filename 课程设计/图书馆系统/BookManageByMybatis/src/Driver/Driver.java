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
				System.out.println("你已经尝试了三次，程序退出！欢迎下次访问！");
				System.exit(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("登录异常！");
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
				System.out.println("退出成功!");
				System.exit(0);
				break;
			default:
				System.out.println("输入无效，只能输入1-8！");
				break;
			}
		}
	}

	// 借阅
	private static boolean BookBorrow(BookService bookService, BorrowdetailService bdService) {
		// TODO Auto-generated method stub
		System.out.println("请输入图书编号(6位数字字符):");
		Scanner sc = new Scanner(System.in);
		String bookID = sc.next();
		try {
			Map<String, Object> map3 = bookService.Check(bookID);
			while (map3.get("code").equals(1) || map3.get("code").equals(2)) {
				System.out.println(map3.get("msg"));
				bookID = sc.next();
				if (bookID.equals("返回")) {
					return false;
				}
				map3 = bookService.Check(bookID);
			}

			ArrayList<Book> booklist = new ArrayList<Book>();
			if (map3.get("code").equals(0)) {
				booklist = (ArrayList<Book>) map3.get("msg");
			} else {
				System.out.println("查找出现异常,请稍后重试！");
				return false;
			}
			if (booklist.get(0).getInventory() == 0) {
				System.out.println("该图书库存量为0，无法借阅！");
				return false;
			}
			Borrowdetail bd = new Borrowdetail();
			bd.setBookID(booklist.get(0).getBookID());

			Map<String, Object> map4 = bdService.CheckBD(bd);
			if (map4.get("code").equals(1)) {
				System.out.println(map4.get("msg"));
				return false;
			} else {
				// 显示图书
				bookService.showBook(booklist);

				// 借阅时长
				System.out.println("请输入借阅时间（天）：");
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

				// 更新book数据库，库存量减一
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
			System.out.println("操作异常!");
		}
		return true;
	}

	// 查询
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
				System.out.println("请输入要查找的图书名称:");
				msg = sc.next();
				book.setBookname(msg);
				break;
			case 2:
				System.out.println("请输入要查找的图书编号:");
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
				System.out.println("请输入要查找的图书类型:");
				msg = sc.next();
				book.setBooktype(msg);
				break;
			case 4:
				System.out.println("请输入要查找的作者名:");
				msg = sc.next();
				book.setAuthor(msg);
				break;
			case 5:
				System.out.println("请输入要查找的出版社名:");
				msg = sc.next();
				book.setSupply(msg);
				break;
			case 6:
				break;
			case 7:
				result = false;
				break;
			default:
				System.out.println("输入无效!请重新输入：");
				c = 0;
			}
			if (c != 0) {
				try {
					booklist = bookService.Query(book);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("查询发生了异常，请稍后重试!");
				}
				if (!booklist.isEmpty()) {
					bookService.showBook(booklist);
				}
			}
		}

	}

	// 归还
	private static boolean BookReback(BookService bookService, BorrowdetailService bdService) {
		// TODO Auto-generated method stub
		System.out.println("请输入要还的图书编号(6位数字字符):");
		Scanner sc = new Scanner(System.in);
		String bookID = sc.next();
		try {
			Map<String, Object> map3 = bookService.Check(bookID);
			while (map3.get("code").equals(1) || map3.get("code").equals(2)) {
				System.out.println(map3.get("msg"));
				bookID = sc.next();
				if (bookID.equals("返回")) {
					return false;
				}
				map3 = bookService.Check(bookID);
			}

			ArrayList<Book> booklist = new ArrayList<Book>();
			if (map3.get("code").equals(0)) {
				booklist = (ArrayList<Book>) map3.get("msg");
			} else {
				System.out.println("查找出现异常,请稍后重试！");
				return false;
			}

			// 显示图书
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

				// 更新book数据库，库存量加一
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
			System.out.println("操作异常!");
		}
		return true;
	}

	// 图书维护
	private static boolean BookMaintain(BookService bookService) {
		// TODO Auto-generated method stub
		if (!Global.currentUser.getRole().equals("管理员")) {
			System.out.println("当前用户没有执行该项功能的权限!");
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
					System.out.println("图书删除发生了异常，请稍后重试!");
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
				System.out.println("输入无效!请重新输入：");
			}
		}
		return true;
	}

	// 增加图书
	private static void BookAdd(BookService bookService) {
		// TODO Auto-generated method stub
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		// 指定导入文件名称
		Global.setFileName("Book");
		while (result) {
			switch (Menu.Addbook_Menu()) {
			case 1:
				try {
					duDAO.arraytoTable(duDAO.getXLSAsArray(Global.XLS_FILE, "vo.Book"), "vo.Book", "book");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("导入失败，请仔细检查您的文件格式!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTable(duDAO.getTXTAsArray(Global.TXT_FILE, "vo.Book"), "vo.Book", "book");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("导入失败，请仔细检查您的文件格式!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoTable(duDAO.getXMLAsArray(Global.XML_FILE, "vo.Book"), "vo.Book", "book");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("导入失败，请仔细检查您的文件格式!");
				}
				break;
			case 4:
				System.out.println("请输入要导入的图书信息(图书编号，图书名称，出版社，作者，图书类型，库存量):");
				String msg = sc.nextLine();
				String str[] = msg.split(",|，");
				Book book = new Book(str[0], str[1], str[2], str[3], str[4], Integer.parseInt(str[5]));
				ArrayList<Object> list = new ArrayList<Object>();
				list.add(book);
				try {
					duDAO.arraytoTable(list, "vo.Book", "book");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("导入失败，请仔细检查您的输入格式!");
				}
				break;
			case 5:
				result = false;
				break;
			default:
				System.out.println("输入无效!请重新输入：");
			}
		}
	}

	// 删除图书
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
				System.out.println("请输入要删除的图书名称:");
				msg = sc.next();
				book.setBookname(msg);
				break;
			case 2:
				System.out.println("请输入要删除的图书编号:");
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
				System.out.println("请输入要删除的图书类型:");
				msg = sc.next();
				book.setBooktype(msg);
				break;
			case 4:
				System.out.println("请输入要删除的作者名:");
				msg = sc.next();
				book.setAuthor(msg);
				break;
			case 5:
				System.out.println("请输入要删除的出版社名:");
				msg = sc.next();
				book.setSupply(msg);
				break;
			case 6:
				System.out.println("您是否将清空数据库中所有图书（谨慎操作）？");
				String choice = sc.next();
				if (choice.equals("否")) {
					c = 1;
				}
				break;
			case 7:
				result = false;
				c = 1;
				break;
			default:
				System.out.println("输入无效!请重新输入：");
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

	// 修改图书
	private static boolean BookAlter(BookService bookService) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String msg = "";
		if (true) {
			Book book = new Book();
			System.out.println("请输入要修改的图书编号:");
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

				System.out.println("请输入修改后的各项信息(图书名称，出版社，作者，图书类型，库存量)(输入返回返回上一级):");
				String info = sc.next();
				if (info.equals("返回")) {
					return false;
				}
				String str[] = info.split("，|,");
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
				System.out.println("图书修改发生了异常，请稍后重试!");
			}
		}
		return true;
	}

	// 导出图书
	private static void BookExport(BookService bookService) {
		// TODO Auto-generated method stub
		boolean result = true;
		// 导出文件名，类名加日期
		Global.setFileName( "Book" + Global.getTodayDate());
		while (result) {
			switch (Menu.Exportbook_Menu()) {
			case 1:
				try {
					duDAO.arraytoXLS(duDAO.getTableAsArray("book", "vo.Book"), "vo.Book", Global.XLS_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("图书信息导出失败，请检查是否存在错误!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTXT(duDAO.getTableAsArray("book", "vo.Book"), "vo.Book", Global.TXT_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("图书信息导出失败，请检查是否存在错误!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoXML(duDAO.getTableAsArray("book", "vo.Book"), "vo.Book", Global.XML_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("图书信息导出失败，请检查是否存在错误!");
				}
				break;
			case 4:
				result = false;
				break;
			}
		}
	}

	// 用户维护
	private static boolean UserMaintain(UserService userService) {
		// TODO Auto-generated method stub
		if (!Global.currentUser.getRole().equals("管理员")) {
			System.out.println("当前用户没有执行该项功能的权限!");
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
					System.out.println("用户删除出现了异常，请稍后重试!");
				}
				break;
			case 3:
				try {
					UserAlter(userService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("用户修改出现了异常，请稍后重试!");
				}
				break;
			case 4:
				try {
					UserQuery(userService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("用户查询出现了异常，请稍后重试!");
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
		// 指定导入文件名称
		Global.setFileName("User");
		while (result) {
			switch (Menu.AddUser_Menu()) {
			case 1:
				try {
					duDAO.arraytoTable(duDAO.getXLSAsArray(Global.XLS_FILE, "vo.User"), "vo.User", "user");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("导入失败，请仔细检查您的文件格式!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTable(duDAO.getTXTAsArray(Global.TXT_FILE, "vo.User"), "vo.User", "user");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("导入失败，请仔细检查您的文件格式!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoTable(duDAO.getXMLAsArray(Global.XML_FILE, "vo.User"), "vo.User", "user");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("导入失败，请仔细检查您的文件格式!");
				}
				break;
			case 4:
				System.out.println("请输入要导入的用户信息(拥护账号，密码，用户姓名，所属学院，角色，学号/工号):");
				String msg = sc.nextLine();
				String str[] = msg.split(",|，");
				User user = new User(str[0], str[1], str[2], str[3], str[4], str[5]);
				ArrayList<Object> list = new ArrayList<Object>();
				list.add(user);
				try {
					duDAO.arraytoTable(list, "vo.User", "user");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("导入失败，请仔细检查您的输入格式!");
				}
				break;
			case 5:
				result = false;
				break;
			default:
				System.out.println("输入无效!请重新输入：");
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
				System.out.println("请输入要删除的用户账号:");
				msg = sc.next();
				Map<String, Object> map3 = userService.CheckUserID(msg);
				while (!map3.get("code").equals(0)) {
					System.out.println(map3.get("msg"));
					msg = sc.next();
					if (msg.equals("返回")) {
						return false;
					}
					map3 = userService.CheckUserID(msg);
				}
				user.setUserID(msg);
				break;
			case 2:
				System.out.println("请输入要删除的用户姓名:");
				msg = sc.next();
				user.setUsername(msg);
				break;
			case 3:
				System.out.println("请输入要删除的学号:");
				msg = sc.next();
				user.setId(msg);
				break;
			case 4:
				System.out.println("请输入要删除的学院:");
				msg = sc.next();
				user.setAcademy(msg);
				break;
			case 5:
				System.out.println("请输入要删除的用户角色:");
				msg = sc.next();
				user.setRole(msg);
				break;
			case 6:
				System.out.println("您是否将清空数据库中所有用户（谨慎操作）？");
				String choice = sc.next();
				if (choice.equals("否")) {
					c = 1;
				}
				break;
			case 7:
				result = false;
				c = 1;
				break;
			default:
				System.out.println("输入无效!请重新输入：");
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
			System.out.println("请输入您要修改的用户账号:");
			msg = sc.next();
			Map<String, Object> map3 = userService.CheckUserID(msg);
			while (map3.get("code").equals(1) || map3.get("code").equals(2)) {
				System.out.println(map3.get("msg"));
				msg = sc.next();
				map3 = userService.CheckUserID(msg);
			}
			ArrayList<User> userlist = (ArrayList<User>) map3.get("msg");
			userService.showUser(userlist);

			System.out.println("请输入修改后的各项信息(密码，用户名，所属学院，角色，学号/工号)(输入返回返回上一级):");
			String info = sc.next();
			if (info.equals("返回")) {
				return false;
			}
			String str[] = info.split("，|,");
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
				System.out.println("请输入要查找的用户账号:");
				msg = sc.next();
				Map<String, Object> map3 = userService.CheckUserID(msg);
				while (!map3.get("code").equals(0)) {
					System.out.println(map3.get("msg"));
					msg = sc.next();
					if (msg.equals("返回")) {
						return false;
					}
					map3 = userService.CheckUserID(msg);
				}
				user.setUserID(msg);
				break;
			case 2:
				System.out.println("请输入要查找的用户姓名:");
				msg = sc.next();
				user.setUsername(msg);
				break;
			case 3:
				System.out.println("请输入要查找的学号:");
				msg = sc.next();
				user.setId(msg);
				break;
			case 4:
				System.out.println("请输入要查找的学院:");
				msg = sc.next();
				user.setAcademy(msg);
				break;
			case 5:
				System.out.println("请输入要查找的用户角色:");
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
				System.out.println("输入无效!请重新输入：");
				c = 1;
			}
			if (c != 0) {
				try {
					userlist = userService.Query(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("查询发生了异常，请稍后重试!");
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
		// 导出文件名，类名加日期
		Global.setFileName("User" + Global.getTodayDate());
		while (result) {
			switch (Menu.Exportbook_Menu()) {
			case 1:
				try {
					duDAO.arraytoXLS(duDAO.getTableAsArray("user", "vo.User"), "vo.User", Global.XLS_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("用户信息导出失败，请检查是否存在错误!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTXT(duDAO.getTableAsArray("user", "vo.User"), "vo.User", Global.TXT_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("用户信息导出失败，请检查是否存在错误!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoXML(duDAO.getTableAsArray("user", "vo.User"), "vo.User", Global.XML_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("用户信息导出失败，请检查是否存在错误!");
				}
				break;
			case 4:
				result = false;
				break;
			}
		}
	}

	// 个人中心
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
					System.out.println("查询借阅记录发生了异常，请稍后重试!");
				}
				break;
			case 2:
				try {
					lengthenDuration(bdService);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("借阅时间延长发生了异常，请稍后重试!");
				}
				break;
			case 3:
				try {
					changePassword(userService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("用户密码更新发生了异常,请稍后重试!");
				}
				break;
			case 4:
				try {
					changeInformation(userService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("用户信息更新发生了异常,请稍后重试!");
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
		System.out.println("请输入要延长的图书编号:");
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
			System.out.println("请输入要延长的天数:");
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
		System.out.println("请输入该账号已绑定的真实姓名:");
		String username = sc.next();
		System.out.println("请输入该账户已绑定的学号/工号");
		String id = sc.next();
		while (!(Global.currentUser.getUsername().equals(username) | Global.currentUser.getId().equals(id))) {
			System.out.println("输入信息与预先绑定信息不符!");
			System.out.println("请输入该账号已绑定的真实姓名:");
			username = sc.next();
			System.out.println("请输入该账户已绑定的学号/工号");
			id = sc.next();
			c++;
			if (c > 3) {
				System.out.println("系统核查到您的身份信息不符，账号已冻结！");
				System.exit(0);
			}
		}
		System.out.println("请输入修改后的信息(姓名，学院，学号/工号)(输入返回返回上一级):");
		String info = sc.nextLine();
		if (info.equals("返回")) {
			return false;
		}
		String str[] = info.split("，|,");
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

	// 修改密码
	private static void changePassword(UserService userService) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("请输入修改后的密码:");
		Scanner sc = new Scanner(System.in);
		String password = sc.next();

		Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");
		Matcher m = p.matcher(password);
		while (!m.find()) {

			System.out.println("您的密码不符合复杂性要求（密码长度不少于6个字符，至少有一个小写字母，至少有一个大写字母，至少一个数字），请重新输入：");
			password = sc.next();
			m = p.matcher(password);

		}
		System.out.println("请输入确认密码:");
		String password2 = sc.next();
		while (!password2.equals(password)) {
			System.out.println("两次输入的密码必须一致,请重新输入确认密码:");
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

	// 借阅信息管理
	private static boolean BorrowdetailManage(BorrowdetailService bdService) {
		// TODO Auto-generated method stub
		if (!Global.currentUser.getRole().equals("管理员")) {
			System.out.println("当前用户没有执行该项功能的权限!");
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
					System.out.println("借阅记录查询出现了异常，请稍后重试!");
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
				System.out.println("请输入要查找的图书名称:");
				msg = sc.next();
				bd.setBookname(msg);
				break;
			case 2:
				System.out.println("请输入要查找的图书编号:");
				msg = sc.next();
				Map<String, Object> map3 = bookService.Check(msg);
				while (! map3.get("code").equals(0)) {
					System.out.println(map3.get("msg"));
					msg = sc.next();
					if(msg.equals("返回")) {
						return false;
					}
					map3 = bookService.Check(msg);
				}
				bd.setBookID(msg);
				break;
			case 3:
				System.out.println("请输入要查找的用户账号:");
				msg = sc.next();
				Map<String, Object> map4 = userService .CheckUserID(msg);
				while (!map4.get("code").equals(0)) {
					System.out.println(map4.get("msg"));
					msg = sc.next();
					if (msg.equals("返回")) {
						return false;
					}
					map4 = userService.CheckUserID(msg);
				}
				bd.setUserID(msg);
				break;
			case 4:
				System.out.println("请输入要查找的用户姓名:");
				msg = sc.next();
				bd.setUsername(msg);
				break;
			case 5:
				System.out.println("请输入要查找的借阅号:");
				msg = sc.next();
				bd.setJyh(msg);
				break;
			case 6:
				System.out.println("请输入要查找的借阅日期(yyyy-MM-dd):");
				msg = sc.next();
				Map<String, Object> map = bdService.searchByDate(msg,"借阅");
				if(!map.get("code").equals(0)) {
					System.out.println(map.get("msg"));
				}else {
					bdService.showBorrowdetail((ArrayList<Borrowdetail>) map.get("msg"));
				}
				c=0;
				break;
			case 7:
				System.out.println("请输入要查找的还书日期(yyyy-MM-dd:");
				msg = sc.next();
				Map<String, Object> map2 = bdService.searchByDate(msg,"归还");
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
				System.out.println("输入无效!请重新输入：");
				c = 0;
			}
			if (c != 0) {
				try {
					bdlist = bdService.Query(bd);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("查询发生了异常，请稍后重试!");
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
		// 导出文件名，类名加日期
		Global.setFileName("Borrowdetail" + Global.getTodayDate());
		while (result) {
			switch (Menu.Exportbook_Menu()) {
			case 1:
				try {
					duDAO.arraytoXLS(duDAO.getTableAsArray("borrowdetail", "vo.Borrowdetail"), "vo.Borrowdetail", Global.XLS_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("借阅信息导出失败，请检查是否存在错误!");
				}
				break;
			case 2:
				try {
					duDAO.arraytoTXT(duDAO.getTableAsArray("borrowdetail", "vo.Borrowdetail"), "vo.Borrowdetail", Global.TXT_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("借阅信息导出失败，请检查是否存在错误!");
				}
				break;
			case 3:
				try {
					duDAO.arraytoXML(duDAO.getTableAsArray("borrowdetail", "vo.Borrowdetail"), "vo.Borrowdetail", Global.XML_FILE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("借阅信息导出失败，请检查是否存在错误!");
				}
				break;
			case 4:
				result = false;
				break;
			}
		}
	}

	// 登录验证
	private static boolean login(UserService userService) throws Exception {
		// TODO Auto-generated method stub

		User user = new User();
		boolean result = false;
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 3; i++) {
			System.out.println("请输入账号:");
			String userID = sc.next();
			System.out.println("请输入密码:");
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
