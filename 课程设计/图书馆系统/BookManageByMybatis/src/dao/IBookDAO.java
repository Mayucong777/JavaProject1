package dao;

import java.util.ArrayList;

import vo.Book;

public interface IBookDAO {

		// ����
		public boolean Create(Book book) throws Exception;

		// �޸�
		public boolean Update(Book book) throws Exception;

		// ɾ��
		public boolean Remove(Book book) throws Exception;


		// ���ң������������ɲ�ѯ�����ٵ�Ϊĳһ��������
		public ArrayList<Book> Query(Book book) throws Exception;
}
