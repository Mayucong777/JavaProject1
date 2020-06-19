package service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import vo.User;

public class Global {

	public static User currentUser;
	
	public static String classPath = "D:\\JAVA_projects\\BookManageByMybatis\\src\\data";
	
	public static String jdbcPropertity = "D:\\JAVA_projects\\BookManageByMybatis\\config\\jdbc.properties";
	
	public static String NAME_FILE = "";//导出文件名称
	
	//导入文件的存放目录
	public static String XLS_FILE = "D:\\"+NAME_FILE+".xls";
	public static String TXT_FILE = "D:\\"+NAME_FILE+".txt";
	public static String XML_FILE = "D:\\"+NAME_FILE+".xml";
	
	public static String resource = "mybatis-config.xml";	
	public static InputStream is;	
	public static SqlSessionFactory sqlsf;
	public static SqlSession sql;
	
	public static SqlSession getSQLSession() throws Exception {
		
		is = Resources.getResourceAsStream(resource);
		
		sqlsf = new SqlSessionFactoryBuilder().build(is);
		
		sql = sqlsf.openSession();
		
		return sql;
	}
	
	public static String getTodayDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String todayStr = df.format(today);
		return todayStr;
	}
	
	public static void setFileName(String name) {
		XLS_FILE = "D:\\"+name+".xls";
		TXT_FILE = "D:\\"+name+".txt";
		XML_FILE = "D:\\"+name+".xml";
	}
}
