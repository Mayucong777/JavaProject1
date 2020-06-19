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
			map.put("msg", "数据库更新成功!");
		}else {
			map.put("code", 2);
			map.put("msg", "更新发生了异常，请稍后重试!");
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
			map.put("msg", "图书编号输入格式不正确，请重新输入(输入返回回到主菜单)：");
		}else {
			
			book.setBookID(bookID);
			ArrayList<Book> booklist = new ArrayList<Book>();
			booklist = this.bookDAO.Query(book);
			if(booklist.isEmpty()) {
				map.put("code", 2);
				map.put("msg", "输入的图书编号不存在，请确认后重新输入(输入返回回到主菜单)：");
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
		System.out.println("图书编号\t图书名称\t出版社\t\t作者\t图书类型\t库存");
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
			System.out.println("找不到满足条件的图书!");
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
			map.put("msg", "数据删除成功!");
		} else {
			map.put("code", 2);
			map.put("msg", "删除发生了异常，请稍后重试!");
		}
		
		sql.close();
		return map;
	}
}
