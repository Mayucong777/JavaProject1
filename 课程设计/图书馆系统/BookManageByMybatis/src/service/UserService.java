package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import dao.IBookDAO;
import dao.IUserDAO;
import vo.Book;
import vo.User;

public class UserService {

	private IUserDAO userDAO;
	public User userforQuery = new User();

	public Map<String, Object> checkLogin(User user) throws Exception {
		Map<String, Object> mapResult = new HashMap<String, Object>();

		SqlSession sql = Global.getSQLSession();
		this.userDAO = sql.getMapper(IUserDAO.class);
		try {
			userforQuery.setUserID(user.getUserID());
			ArrayList<User> foundUserlist = this.userDAO.Query(userforQuery);
			if (foundUserlist.isEmpty()) {
				mapResult.put("code", 1);
				mapResult.put("msg", "账号不存在!");
			} else {
				User foundUser = foundUserlist.get(0);
				if (!foundUser.getPassword().equals(user.getPassword())) {
					mapResult.put("code", 1);
					mapResult.put("msg", "密码不正确!");
				} else {
					mapResult.put("code", 0);
					mapResult.put("msg", "登录成功!");
					Global.currentUser = foundUser;
				}
			}
		} catch (Exception e) {
			mapResult.put("code", 2);
			mapResult.put("msg", e.getMessage());
		} finally {
			sql.close();
		}

		return mapResult;
	}


	public Map<String, Object> Update(User user) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		SqlSession sql = Global.getSQLSession();
		try {
			this.userDAO = sql.getMapper(IUserDAO.class);

			if (this.userDAO.Update(user)) {
				map.put("code", 0);
				map.put("msg", "修改成功!");
				sql.commit();
			} else {
				map.put("code", 1);
				map.put("msg", "修改失败!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql.close();
		return map;
	}


	public ArrayList<User> Query(User user) throws Exception {
		// TODO Auto-generated method stub
		SqlSession sql = Global.getSQLSession();
		this.userDAO = sql.getMapper(IUserDAO.class);
		
		ArrayList<User> userlist = this.userDAO.Query(user);
		if(userlist.isEmpty()) {
			System.out.println("找不到满足条件的用户!");
			sql.close();
			return userlist;
		}
		sql.close();
		return userlist;
	}


	public Map<String, Object> Delete(User user) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		SqlSession sql = Global.getSQLSession();
		this.userDAO = sql.getMapper(IUserDAO.class);
		
		if(this.userDAO.Remove(user)) {
			sql.commit();
			map.put("code", 0);
			map.put("msg", "数据删除成功!");
		} else {
			map.put("code", 2);
			map.put("msg", "删除发生了异常，请稍后重试!");
		}
		
		sql.close();
		return map;
	}


	public Map<String, Object> CheckUserID(String userID) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		
		SqlSession sql = Global.getSQLSession();
		this.userDAO = sql.getMapper(IUserDAO.class);
		
		user.setUserID(userID);
		ArrayList<User> userlist = new ArrayList<User>();
		userlist = this.userDAO.Query(user);
		if(userlist.isEmpty()) {
			map.put("code", 2);
			map.put("msg", "输入的用户账号不存在，请确认后重新输入(输入返回回到主菜单)：");
		}else {
			map.put("code", 0);
			map.put("msg", userlist);
		}
		sql.close();
		return map;
	}


	public void showUser(ArrayList<User> userlist) {
		// TODO Auto-generated method stub
		System.out.println("用户账号\t用户密码\t用户姓名\t\t所属学院\t角色\t学号/工号");
		for (User user : userlist) {
			System.out.println(user.toString());
		}
	}
}
