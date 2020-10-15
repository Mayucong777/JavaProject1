package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



//import filter.List;
import vo.User;

public class ResourceDao {
	private Connection con;
	public ResourceDao(Connection con) {
		this.con=con;
	}
	public List<String>  getUrlByUserName(String userName) {
		List<String> list=new ArrayList<String>();
		String s="";
		try {
			// 创建语句
			String sql = "SELECT * FROM	t_resource WHERE resourceId IN (SELECT resourceId FROM	t_role_resource	WHERE roleId IN (SELECT	roleId FROM	t_user_role	WHERE userName = ?))";
			  
			PreparedStatement pst = con.prepareStatement(sql);
			// 执行语句
			pst.setString(1, userName);
			ResultSet rs = pst.executeQuery();
			// 响应处理
			while (rs.next()) {
				s = rs.getString(3);
				System.out.println(s);
				list.add(s);
			}
			// 关闭连接
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
