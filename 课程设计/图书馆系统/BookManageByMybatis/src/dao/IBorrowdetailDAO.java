package dao;

import java.util.ArrayList;

import vo.Borrowdetail;


public interface IBorrowdetailDAO {
	
	//����
	public boolean Create(Borrowdetail bd) throws Exception;

	//�޸�
	public boolean Update(Borrowdetail bd) throws Exception;

	//ɾ��
	public boolean Remove(Borrowdetail bd) throws Exception;

	//���ң������������ɲ�ѯ�����ٵ�Ϊĳһ��������
	public ArrayList<Borrowdetail> Query(Borrowdetail bd) throws Exception;

	// �õ�����������ĺ�
	public String GetMaxJyh(String day) throws Exception;
}
