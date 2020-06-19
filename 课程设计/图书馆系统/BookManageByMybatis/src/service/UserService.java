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
				mapResult.put("msg", "�˺Ų�����!");
			} else {
				User foundUser = foundUserlist.get(0);
				if (!foundUser.getPassword().equals(user.getPassword())) {
					mapResult.put("code", 1);
					mapResult.put("msg", "���벻��ȷ!");
				} else {
					mapResult.put("code", 0);
					mapResult.put("msg", "��¼�ɹ�!");
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
				map.put("msg", "�޸ĳɹ�!");
				sql.commit();
			} else {
				map.put("code", 1);
				map.put("msg", "�޸�ʧ��!");
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
			System.out.println("�Ҳ��������������û�!");
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
			map.put("msg", "����ɾ���ɹ�!");
		} else {
			map.put("code", 2);
			map.put("msg", "ɾ���������쳣�����Ժ�����!");
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
			map.put("msg", "������û��˺Ų����ڣ���ȷ�Ϻ���������(���뷵�ػص����˵�)��");
		}else {
			map.put("code", 0);
			map.put("msg", userlist);
		}
		sql.close();
		return map;
	}


	public void showUser(ArrayList<User> userlist) {
		// TODO Auto-generated method stub
		System.out.println("�û��˺�\t�û�����\t�û�����\t\t����ѧԺ\t��ɫ\tѧ��/����");
		for (User user : userlist) {
			System.out.println(user.toString());
		}
	}
}
