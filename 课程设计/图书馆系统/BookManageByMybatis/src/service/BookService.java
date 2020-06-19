package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import dao.IBookDAO;
import vo.Book;

public class BookService {

	private IBookDAO bookDAO;
	
	public Map<String, Object> Update(Book book) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		SqlSession sql = Global.getSQLSession();
		this.bookDAO = sql.getMapper(IBookDAO.class);
		
		if(this.bookDAO.Update(book)) {
			sql.commit();
			map.put("code", 0);
			map.put("msg", "���ݿ���³ɹ�!");
		}else {
			map.put("code", 2);
			map.put("msg", "���·������쳣�����Ժ�����!");
		}
		sql.close();
		return map;
	}

	public Map<String, Object> Check(String bookID) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Book book = new Book();
		
		SqlSession sql = Global.getSQLSession();
		this.bookDAO = sql.getMapper(IBookDAO.class);
		
		Pattern p = Pattern.compile("^\\d{6}$");
		Matcher m = p.matcher(bookID);
		
		if (!m.find()) {
			map.put("code", 1);
			map.put("msg", "ͼ���������ʽ����ȷ������������(���뷵�ػص����˵�)��");
		}else {
			
			book.setBookID(bookID);
			ArrayList<Book> booklist = new ArrayList<Book>();
			booklist = this.bookDAO.Query(book);
			if(booklist.isEmpty()) {
				map.put("code", 2);
				map.put("msg", "�����ͼ���Ų����ڣ���ȷ�Ϻ���������(���뷵�ػص����˵�)��");
			}else {
				map.put("code", 0);
				map.put("msg", booklist);
			}
		}
		
		sql.close();
		return map;
	}

	public void showBook(ArrayList<Book> booklist) {
		// TODO Auto-generated method stub
		System.out.println("ͼ����\tͼ������\t������\t\t����\tͼ������\t���");
		for (Book book : booklist) {
			System.out.println(book.toString());
		}
	}

	public ArrayList<Book> Query(Book book) throws Exception {
		// TODO Auto-generated method stub
		SqlSession sql = Global.getSQLSession();
		this.bookDAO = sql.getMapper(IBookDAO.class);
		
		ArrayList<Book> booklist = this.bookDAO.Query(book);
		if(booklist.isEmpty()) {
			System.out.println("�Ҳ�������������ͼ��!");
			sql.close();
			return booklist;
		}
		sql.close();
		return booklist;
	}

	public Map<String, Object> Delete(Book book) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		SqlSession sql = Global.getSQLSession();
		this.bookDAO = sql.getMapper(IBookDAO.class);
		
		if(this.bookDAO.Remove(book)) {
			sql.commit();
			map.put("code", 0);
			map.put("msg", "����ɾ���ɹ�!");
		} else {
			map.put("code", 2);
			map.put("msg", "ɾ���������쳣�����Ժ�����!");
		}
		
		sql.close();
		return map;
	}
}
