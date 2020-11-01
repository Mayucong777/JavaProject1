package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vo.User;

public class UserDao {
	private Connection con;
	public UserDao(Connection con) {
		this.con=con;
	}
	public User getByuserName(String userName) {
		User user = null;
		try {
			// 3.创建语句
			String sql = "select * from t_user where userName=?";
			PreparedStatement pst = con.prepareStatement(sql);
			// 4.执行语句
			pst.setString(1, userName);
			ResultSet rs = pst.executeQuery();
			// 5.响应处理
			if (rs.next()) {
				user = new User(rs.getString("userName"), rs.getString("password"),
						rs.getString("chrName"));
			}
			// 6.关闭连接
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

}
