package dao;

import java.util.ArrayList;

import vo.Borrowdetail;


public interface IBorrowdetailDAO {
	
	//创建
	public boolean Create(Borrowdetail bd) throws Exception;

	//修改
	public boolean Update(Borrowdetail bd) throws Exception;

	//删除
	public boolean Remove(Borrowdetail bd) throws Exception;

	//查找，所有条件均可查询，不再单为某一属性设立
	public ArrayList<Borrowdetail> Query(Borrowdetail bd) throws Exception;

	// 得到当天的最大借阅号
	public String GetMaxJyh(String day) throws Exception;
}
