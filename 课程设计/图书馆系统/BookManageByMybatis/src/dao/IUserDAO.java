package dao;

import java.util.ArrayList;

import vo.User;

public interface IUserDAO {
	//创建
	public boolean Create(User user) throws Exception;

	//修改
	public boolean Update(User user) throws Exception;

	//删除
	public boolean Remove(User user) throws Exception;

	//查找，所有条件均可查询，不再单为某一属性设立
	public ArrayList<User> Query(User user) throws Exception;

}
