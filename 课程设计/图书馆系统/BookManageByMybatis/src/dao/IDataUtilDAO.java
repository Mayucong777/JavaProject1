package dao;

import java.util.ArrayList;

public interface IDataUtilDAO {

	// 将给定的任意（未设置属性）的XML文件中的数据转换为集合
	public ArrayList<Object> getXMLAsArray(String path, String className) throws Exception;

	// 将给定的任意txt文件中的数据转换为集合
	public ArrayList<Object> getTXTAsArray(String path, String className) throws Exception;

	// 将给定的任意xls文件中的数据转换为集合
	public ArrayList<Object> getXLSAsArray(String path, String className) throws Exception;

	// 将给定的任意数据库表中的数据转换为集合
	public ArrayList<Object> getTableAsArray(String tableName, String className) throws Exception;

	// 将任意的集合写入到数据库指定的表中
	public void arraytoTable(ArrayList<Object> list, String className, String tableName) throws Exception;

	// 将任意的集合写入到指定的XML文件中
	public void arraytoXML(ArrayList<Object> list, String className, String path) throws Exception;

	// 将任意的集合写入到指定的txt文件中
	public void arraytoTXT(ArrayList<Object> list, String className, String path) throws Exception;

	// 将任意的集合写入到指定的xls文件中
	public void arraytoXLS(ArrayList<Object> list, String className, String path) throws Exception;

}
