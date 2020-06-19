package dao;

import java.util.ArrayList;

import vo.User;

public interface IUserDAO {
	//����
	public boolean Create(User user) throws Exception;

	//�޸�
	public boolean Update(User user) throws Exception;

	//ɾ��
	public boolean Remove(User user) throws Exception;

	//���ң������������ɲ�ѯ�����ٵ�Ϊĳһ��������
	public ArrayList<User> Query(User user) throws Exception;

}
