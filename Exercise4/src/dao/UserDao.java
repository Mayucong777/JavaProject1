package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import tools.databaseConnection;
import vo.Page;
import vo.User;



public class UserDao {
	private Connection con;
	private databaseConnection dbc;
	public UserDao() {
		
	}
	
	public User getByuserName(String userName) {
		User user = null;
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con=this.dbc.getConnection();
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
			rs.close();
			pst.close();
			// 6.关闭连接
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
	//增加用户
	public boolean insert(User user) {
		boolean success = false;
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con=this.dbc.getConnection();
		try {

			// 3.创建语句
			String sql = "insert into t_user(userName,password,chrName,mail,provinceCode,cityCode)";
			sql += " values(?,?,?,?,?,?)";
			PreparedStatement pst = this.con.prepareStatement(sql);

			pst.setString(1, user.getUserName());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getChrName());
			pst.setString(4, user.getMail());
			pst.setString(5, user.getProvinceCode());
			pst.setString(6, user.getCityCode());
			// 4.执行语句
			int i = pst.executeUpdate();
			// 5.结果处理
			if (i > 0) {
				success = true;
			}

			// 6.关闭连接
			this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return success;

	}

	//修改用户
	public boolean update(User user) {
		boolean success = false;
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con=this.dbc.getConnection();
		try {

			// 3.创建语句
			String sql = "update t_user set password=?,chrName = ?,mail=?,provinceCode=?,cityCode = ?";
			sql += " where userName =? ";
			PreparedStatement pst = this.con.prepareStatement(sql);
			pst.setString(1, user.getPassword());
			pst.setString(2, user.getChrName());
			pst.setString(3, user.getMail());
			pst.setString(4, user.getProvinceCode());
			pst.setString(5, user.getCityCode());
			pst.setString(6, user.getUserName());
			// 4.执行语句
			int i = pst.executeUpdate();
			// 5.结果处理
			if (i > 0) {
				success = true;
			}

			// 6.关闭连接
			this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return success;

	}

	//模糊查询用户
	public User getByField(String field, String param) {
		User user = null;
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con=this.dbc.getConnection();
		try {
			// 3.创建语句
			String sql = "select * from t_user where " + field + "=?";
			PreparedStatement pst = this.con.prepareStatement(sql);

			pst.setString(1, param);
			// 4.执行语句
			ResultSet rs = pst.executeQuery();
			// 5.结果处理
			if (rs.next()) {
				user = new User(rs.getString("userName"),
						rs.getString("password"), rs.getString("chrName"));
			}

			// 6.关闭连接
						this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return user;
	}

	//输出查询的用户连接查询结果
	public ArrayList<User> query(User user, Page page) {
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con=this.dbc.getConnection();
		ArrayList<User> list = new ArrayList<User>(); // 存放查询结果的集合
		StringBuffer condition = new StringBuffer();// 查询条件
		if (user.getUserName() != null && !"".equals(user.getUserName())) { // 判断是否有该查询条件
			condition.append(" and userName like '%")
					.append(user.getUserName()).append("%'");
		}
		if (user.getChrName() != null && !"".equals(user.getChrName())) { //
			condition.append(" and chrName like '%").append(user.getChrName())
					.append("%'");
		}
		if (user.getMail() != null && !"".equals(user.getMail())) { //
			condition.append(" and mail like '%").append(user.getMail())
					.append("%'");
		}
		if (user.getProvinceName() != null
				&& !"".equals(user.getProvinceName())) { //
			condition.append(" and provinceName like '%")
					.append(user.getProvinceName()).append("%'");
		}
		if (user.getCityName() != null && !"".equals(user.getCityName())) { //
			condition.append(" and cityName like '%")
					.append(user.getCityName()).append("%'");
		}

		int begin = page.getPageSize() * (page.getPageNumber() - 1);
		String sql = "select userName,password,chrName,mail,A.provinceCode provinceCode,";
		sql = sql
				+ " B.provinceName provinceName,A.cityCode cityCode,C.cityName cityName ";
		sql = sql + " from t_user A left join t_province B ";
		sql = sql
				+ " on A.provinceCode = B.provinceCode left join t_city C on A.cityCode = C.cityCode ";
		sql = sql + " where  1=1 ";
		sql = sql + condition + " order by " + page.getSort() + " "
				+ page.getSortOrder() + " limit " + begin + ","
				+ page.getPageSize();

		System.out.println(sql);
		// DatabaseConnection类封装了数据库驱动加载和连接
		
		try {
			Statement stm = this.con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				User userResult = new User();
				userResult.setUserName(rs.getString("userName"));
				userResult.setPassword(rs.getString("password"));
				userResult.setChrName(rs.getString("chrName"));
				userResult.setMail(rs.getString("mail"));
				userResult.setProvinceCode(rs.getString("provinceCode"));
				userResult.setProvinceName(rs.getString("provinceName"));
				userResult.setCityCode(rs.getString("cityCode"));
				userResult.setCityName(rs.getString("cityName"));
				list.add(userResult);
			}
			
			// 6.关闭连接
						this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return list;
	}

	//统计用户总数来计算分页数
	public int count(User user, Page page) {
		int total = 0;
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con=this.dbc.getConnection();
		StringBuffer condition = new StringBuffer();// 查询条件
		if (user.getUserName() != null && !"".equals(user.getUserName())) { //
			condition.append(" and userName like '%")
					.append(user.getUserName()).append("%'");
		}
		if (user.getChrName() != null && !"".equals(user.getChrName())) { //
			condition.append(" and chrName like '%").append(user.getChrName())
					.append("%'");
		}
		if (user.getMail() != null && !"".equals(user.getMail())) { //
			condition.append(" and mail like '%").append(user.getMail())
					.append("%'");
		}
		if (user.getProvinceName() != null
				&& !"".equals(user.getProvinceName())) { //
			condition.append(" and provinceName like '%")
					.append(user.getProvinceName()).append("%'");
		}
		if (user.getCityName() != null && !"".equals(user.getCityName())) { //
			condition.append(" and cityName like '%")
					.append(user.getCityName()).append("%'");
		}

		String sql = "select count(*) from t_user A left join t_province B";
		sql = sql
				+ " on A.provinceCode = B.provinceCode left join t_city C on A.cityCode = C.cityCode ";
		sql = sql + " where  1=1 ";
		sql = sql + condition;
		System.out.println(sql);
		
		try {
			Statement stm = this.con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				total = rs.getInt(1);
			}
			
			// 6.关闭连接
						this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return total;
	}

	//删除用户
	public boolean delete(String ids) {
		boolean success = false;
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con=this.dbc.getConnection();
		String[] array = ids.split(",");
		try {

			// 3.创建语句
			String sql = "delete from t_user where userName in(";
			StringBuffer sb = new StringBuffer("?");
			for (int i = 0; i < array.length - 1; i++) {
				sb.append(",?");
			}
			sql = sql + sb.toString() + ")";
			System.out.println(sql);
			PreparedStatement pst = this.con.prepareStatement(sql);
			for (int i = 0; i < array.length; i++) {
				pst.setString(i + 1, array[i]);
			}

			// 4.执行语句
			int i = pst.executeUpdate();
			// 5.结果处理
			if (i > 0) {
				success = true;
			}

			// 6.关闭连接
						this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return success;

	}
}

