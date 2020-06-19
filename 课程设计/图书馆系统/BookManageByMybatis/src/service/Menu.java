package service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

	private static int choice;
	public static Scanner sc = new Scanner(System.in);
	
	public static int Main_Menu() {
		System.out.println("===****图书馆系统****===\r\n" + 
				"1、借阅图书\r\n" + 
				"2、查询图书\r\n" + 
				"3、归还图书\r\n" + 
				"4、图书维护\r\n" + 
				"5、用户维护\r\n" + 
				"6、个人中心\r\n" + 
				"7、借阅信息管理\r\n" + 
				"8、退出系统\r\n"+
				"当前用户："+Global.currentUser.getUsername()+"\r\n" + 
				"请选择（1-8）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int QueryBook_Menu() {
		System.out.println("===****图书查询****===\r\n" + 
				"1、图书名称查询\r\n" + 
				"2、图书编号查询\r\n" + 
				"3、图书类型查询\r\n" + 
				"4、作者名查询\r\n" + 
				"5、出版社名查询\r\n" + 
				"6、显示所有在库图书\r\n" + 
				"7、返回主菜单\r\n" + 
				"请选择（1-7）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int Maintainbook_Menu() {
		System.out.println("===****图书馆图书管理维护****====\r\n" + 
				"1、增加图书\r\n" + 
				"2、删除图书\r\n" + 
				"3、修改图书\r\n" + 
				"4、图书查询 \r\n" + 
				"5、图书信息导出\r\n" + 
				"6、返回主菜单\r\n" + 
				"请选择（1-6）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int Addbook_Menu() {
		System.out.println("===****增加图书****====\r\n" + 
				"1、从excel中导入数据\r\n" + 
				"2、从文本文件导入数据\r\n" + 
				"3、从xml文件中导入数据\r\n" + 
				"4、从键盘导入数据\r\n" + 
				"5、返回上一级\r\n" + 
				"请选择（1-5）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int Subbook_Menu() {
		System.out.println("===****删除图书****====\r\n" + 
				"1、按图书名称删除\r\n" + 
				"2、按图书编号删除\r\n" + 
				"3、按图书类型删除\r\n" + 
				"4、按作者名删除\r\n" + 
				"5、按出版社删除\r\n" + 
				"6、清空所有在库图书（谨慎操作）\r\n" + 
				"7、返回上一级\r\n" + 
				"请选择（1-7）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int Exportbook_Menu() {
		System.out.println("===****图书信息导出****====\r\n" + 
				"1、导出到excel文件\r\n" + 
				"2、导出到文本文件\r\n" + 
				"3、导出到xml文件\r\n" + 
				"4、返回主菜单\r\n" + 
				"请选择（1-4）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int PersonalCenter_Menu() {
		System.out.println("===****个人中心****====\r\n" + 
				"1、查询借阅记录\r\n" + 
				"2、延长借阅时间\r\n" + 
				"3、修改用户密码\r\n" + 
				"4、修改绑定信息\r\n" + 
				"5、返回主菜单\r\n" + 
				"请选择（1-5）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int MaintainUser_Menu() {
		System.out.println("===****图书馆用户管理维护****====\r\n" + 
				"1、增加用户\r\n" + 
				"2、删除用户\r\n" + 
				"3、修改用户\r\n" + 
				"4、用户查询 \r\n" + 
				"5、用户信息导出\r\n" + 
				"6、返回主菜单\r\n" + 
				"请选择（1-6）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int AddUser_Menu() {
		System.out.println("===****增加用户****====\r\n" + 
				"1、从excel中导入数据\r\n" + 
				"2、从文本文件导入数据\r\n" + 
				"3、从xml文件中导入数据\r\n" + 
				"4、从键盘导入数据\r\n" + 
				"5、返回上一级\r\n" + 
				"请选择（1-5）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int SubUser_Menu() {
		System.out.println("===****删除用户****====\r\n" + 
				"1、按账号删除\r\n" + 
				"2、按姓名删除\r\n" + 
				"3、按学号删除\r\n" + 
				"4、按所属学院删除\r\n" + 
				"5、按用户角色删除\r\n" + 
				"6、清空所有在库用户（谨慎操作）\r\n" + 
				"7、返回上一级\r\n" + 
				"请选择（1-7）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int QueryUser_Menu() {
		System.out.println("===****用户查询****===\r\n" + 
				"1、用户账号查询\r\n" + 
				"2、姓名查询\r\n" + 
				"3、学号查询\r\n" + 
				"4、所属学院查询\r\n" + 
				"5、用户角色查询\r\n" + 
				"6、显示所有在库用户\r\n" + 
				"7、返回主菜单\r\n" + 
				"请选择（1-7）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int ExportUser_Menu() {
		System.out.println("===****用户信息导出****====\r\n" + 
				"1、导出到excel文件\r\n" + 
				"2、导出到文本文件\r\n" + 
				"3、导出到xml文件\r\n" + 
				"4、返回主菜单\r\n" + 
				"请选择（1-4）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int BorrowdetailManage_Menu() {
		System.out.println("===****图书馆借阅信息管理****====\r\n" + 
				"1、借阅记录查询\r\n" + 
				"2、借阅记录导出\r\n" + 
				"3、返回主菜单\r\n" + 
				"请选择（1-3）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int QureyBorrowdetail_Menu() {
		System.out.println("===****图书借阅记录查询****===\r\n" + 
				"1.图书名称查询\r\n" + 
				"2.图书编号查询\r\n" + 
				"3.账号查询\r\n" + 
				"4.姓名查询\r\n" + 
				"5.借阅号查询\r\n" + 
				"6.借阅时间查询\r\n" + 
				"7.归还时间查询\r\n" + 
				"8.显示所有在库借阅记录\r\n" + 
				"9.返回主菜单\r\n" + 
				"请选择（1-9）：");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int ExportBorrowdetail_Menu() {
		System.out.println("===****借阅记录导出****====\r\n" + 
				"1、导出到excel文件\r\n" + 
				"2、导出到文本文件\r\n" + 
				"3、导出到xml文件\r\n" + 
				"4、返回主菜单\r\n" + 
				"请选择（1-4）：");
		choice = sc.nextInt();
		return choice;
	}
}
