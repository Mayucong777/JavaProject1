package dao;

import java.util.ArrayList;

import vo.Book;

public interface IBookDAO {

		// 创建
		public boolean Create(Book book) throws Exception;

		// 修改
		public boolean Update(Book book) throws Exception;

		// 删除
		public boolean Remove(Book book) throws Exception;


		// 查找，所有条件均可查询，不再单为某一属性设立
		public ArrayList<Book> Query(Book book) throws Exception;
}
