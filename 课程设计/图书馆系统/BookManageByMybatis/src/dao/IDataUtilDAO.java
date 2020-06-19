package dao;

import java.util.ArrayList;

public interface IDataUtilDAO {

	// �����������⣨δ�������ԣ���XML�ļ��е�����ת��Ϊ����
	public ArrayList<Object> getXMLAsArray(String path, String className) throws Exception;

	// ������������txt�ļ��е�����ת��Ϊ����
	public ArrayList<Object> getTXTAsArray(String path, String className) throws Exception;

	// ������������xls�ļ��е�����ת��Ϊ����
	public ArrayList<Object> getXLSAsArray(String path, String className) throws Exception;

	// ���������������ݿ���е�����ת��Ϊ����
	public ArrayList<Object> getTableAsArray(String tableName, String className) throws Exception;

	// ������ļ���д�뵽���ݿ�ָ���ı���
	public void arraytoTable(ArrayList<Object> list, String className, String tableName) throws Exception;

	// ������ļ���д�뵽ָ����XML�ļ���
	public void arraytoXML(ArrayList<Object> list, String className, String path) throws Exception;

	// ������ļ���д�뵽ָ����txt�ļ���
	public void arraytoTXT(ArrayList<Object> list, String className, String path) throws Exception;

	// ������ļ���д�뵽ָ����xls�ļ���
	public void arraytoXLS(ArrayList<Object> list, String className, String path) throws Exception;

}
