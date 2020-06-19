package service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

	private static int choice;
	public static Scanner sc = new Scanner(System.in);
	
	public static int Main_Menu() {
		System.out.println("===****ͼ���ϵͳ****===\r\n" + 
				"1������ͼ��\r\n" + 
				"2����ѯͼ��\r\n" + 
				"3���黹ͼ��\r\n" + 
				"4��ͼ��ά��\r\n" + 
				"5���û�ά��\r\n" + 
				"6����������\r\n" + 
				"7��������Ϣ����\r\n" + 
				"8���˳�ϵͳ\r\n"+
				"��ǰ�û���"+Global.currentUser.getUsername()+"\r\n" + 
				"��ѡ��1-8����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int QueryBook_Menu() {
		System.out.println("===****ͼ���ѯ****===\r\n" + 
				"1��ͼ�����Ʋ�ѯ\r\n" + 
				"2��ͼ���Ų�ѯ\r\n" + 
				"3��ͼ�����Ͳ�ѯ\r\n" + 
				"4����������ѯ\r\n" + 
				"5������������ѯ\r\n" + 
				"6����ʾ�����ڿ�ͼ��\r\n" + 
				"7���������˵�\r\n" + 
				"��ѡ��1-7����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int Maintainbook_Menu() {
		System.out.println("===****ͼ���ͼ�����ά��****====\r\n" + 
				"1������ͼ��\r\n" + 
				"2��ɾ��ͼ��\r\n" + 
				"3���޸�ͼ��\r\n" + 
				"4��ͼ���ѯ \r\n" + 
				"5��ͼ����Ϣ����\r\n" + 
				"6���������˵�\r\n" + 
				"��ѡ��1-6����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int Addbook_Menu() {
		System.out.println("===****����ͼ��****====\r\n" + 
				"1����excel�е�������\r\n" + 
				"2�����ı��ļ���������\r\n" + 
				"3����xml�ļ��е�������\r\n" + 
				"4���Ӽ��̵�������\r\n" + 
				"5��������һ��\r\n" + 
				"��ѡ��1-5����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int Subbook_Menu() {
		System.out.println("===****ɾ��ͼ��****====\r\n" + 
				"1����ͼ������ɾ��\r\n" + 
				"2����ͼ����ɾ��\r\n" + 
				"3����ͼ������ɾ��\r\n" + 
				"4����������ɾ��\r\n" + 
				"5����������ɾ��\r\n" + 
				"6����������ڿ�ͼ�飨����������\r\n" + 
				"7��������һ��\r\n" + 
				"��ѡ��1-7����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int Exportbook_Menu() {
		System.out.println("===****ͼ����Ϣ����****====\r\n" + 
				"1��������excel�ļ�\r\n" + 
				"2���������ı��ļ�\r\n" + 
				"3��������xml�ļ�\r\n" + 
				"4���������˵�\r\n" + 
				"��ѡ��1-4����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int PersonalCenter_Menu() {
		System.out.println("===****��������****====\r\n" + 
				"1����ѯ���ļ�¼\r\n" + 
				"2���ӳ�����ʱ��\r\n" + 
				"3���޸��û�����\r\n" + 
				"4���޸İ���Ϣ\r\n" + 
				"5���������˵�\r\n" + 
				"��ѡ��1-5����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int MaintainUser_Menu() {
		System.out.println("===****ͼ����û�����ά��****====\r\n" + 
				"1�������û�\r\n" + 
				"2��ɾ���û�\r\n" + 
				"3���޸��û�\r\n" + 
				"4���û���ѯ \r\n" + 
				"5���û���Ϣ����\r\n" + 
				"6���������˵�\r\n" + 
				"��ѡ��1-6����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int AddUser_Menu() {
		System.out.println("===****�����û�****====\r\n" + 
				"1����excel�е�������\r\n" + 
				"2�����ı��ļ���������\r\n" + 
				"3����xml�ļ��е�������\r\n" + 
				"4���Ӽ��̵�������\r\n" + 
				"5��������һ��\r\n" + 
				"��ѡ��1-5����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int SubUser_Menu() {
		System.out.println("===****ɾ���û�****====\r\n" + 
				"1�����˺�ɾ��\r\n" + 
				"2��������ɾ��\r\n" + 
				"3����ѧ��ɾ��\r\n" + 
				"4��������ѧԺɾ��\r\n" + 
				"5�����û���ɫɾ��\r\n" + 
				"6����������ڿ��û�������������\r\n" + 
				"7��������һ��\r\n" + 
				"��ѡ��1-7����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int QueryUser_Menu() {
		System.out.println("===****�û���ѯ****===\r\n" + 
				"1���û��˺Ų�ѯ\r\n" + 
				"2��������ѯ\r\n" + 
				"3��ѧ�Ų�ѯ\r\n" + 
				"4������ѧԺ��ѯ\r\n" + 
				"5���û���ɫ��ѯ\r\n" + 
				"6����ʾ�����ڿ��û�\r\n" + 
				"7���������˵�\r\n" + 
				"��ѡ��1-7����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int ExportUser_Menu() {
		System.out.println("===****�û���Ϣ����****====\r\n" + 
				"1��������excel�ļ�\r\n" + 
				"2���������ı��ļ�\r\n" + 
				"3��������xml�ļ�\r\n" + 
				"4���������˵�\r\n" + 
				"��ѡ��1-4����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int BorrowdetailManage_Menu() {
		System.out.println("===****ͼ��ݽ�����Ϣ����****====\r\n" + 
				"1�����ļ�¼��ѯ\r\n" + 
				"2�����ļ�¼����\r\n" + 
				"3���������˵�\r\n" + 
				"��ѡ��1-3����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int QureyBorrowdetail_Menu() {
		System.out.println("===****ͼ����ļ�¼��ѯ****===\r\n" + 
				"1.ͼ�����Ʋ�ѯ\r\n" + 
				"2.ͼ���Ų�ѯ\r\n" + 
				"3.�˺Ų�ѯ\r\n" + 
				"4.������ѯ\r\n" + 
				"5.���ĺŲ�ѯ\r\n" + 
				"6.����ʱ���ѯ\r\n" + 
				"7.�黹ʱ���ѯ\r\n" + 
				"8.��ʾ�����ڿ���ļ�¼\r\n" + 
				"9.�������˵�\r\n" + 
				"��ѡ��1-9����");
		choice = sc.nextInt();
		return choice;
	}
	
	public static int ExportBorrowdetail_Menu() {
		System.out.println("===****���ļ�¼����****====\r\n" + 
				"1��������excel�ļ�\r\n" + 
				"2���������ı��ļ�\r\n" + 
				"3��������xml�ļ�\r\n" + 
				"4���������˵�\r\n" + 
				"��ѡ��1-4����");
		choice = sc.nextInt();
		return choice;
	}
}
