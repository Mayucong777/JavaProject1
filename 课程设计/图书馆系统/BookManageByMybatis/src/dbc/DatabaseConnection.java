package dbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import service.Global;

public class DatabaseConnection {
	// private static final String jdbcProperty = "jdbc.properties";

	private static String DBDriver = "";

	private static String DBUrl = "";

	private static String DBUser = "";

	private static String DBPassword = "";

	private Connection con;

	// 静态代码块，只执行一次
	static {

		try {
			Properties property = new Properties();

			// 使用类加载器加载资源
			InputStream is = DatabaseConnection.class.getClassLoader().getResourceAsStream("resource/jdbc.properties");

			property.load(new InputStreamReader(is, "UTF-8"));
			is.close();

			DBDriver = property.getProperty("DBDriver");
			DBUrl = property.getProperty("DBUrl");
			DBUser = property.getProperty("DBUser");
			DBPassword = property.getProperty("DBPassword");

			// 加载驱动，只加载一次
			Class.forName(DBDriver);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 构造
	public DatabaseConnection() {
		try {
			this.con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 得到返回连接
	public Connection getConnection() {
		return this.con;
	}

	// 关闭连接
	public void close() {
		try {
			if (this.con != null)
				this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
