package service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import dao.IBookDAO;
import dao.IBorrowdetailDAO;
import vo.Book;
import vo.Borrowdetail;

public class BorrowdetailService {

	private IBorrowdetailDAO bdDAO;

	public Map<String, Object> Create(Borrowdetail bd) throws Exception {
		// TODO Auto-generated method stub
		int duration = bd.getDuration();
		Map<String, Object> map = new HashMap<String, Object>();

		SqlSession sql = Global.getSQLSession();
		this.bdDAO = sql.getMapper(IBorrowdetailDAO.class);

		Borrowdetail bd1 = new Borrowdetail();
		ArrayList<Borrowdetail> bdlist = new ArrayList<Borrowdetail>();
		bd1.setBookID(bd.getBookID());
		bd1.setUserID(Global.currentUser.getUserID());
		bdlist = this.bdDAO.Query(bd1);

		// 获得借阅号日期
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String todayStr = df.format(today);

		int Count;
		// 获得当天最大借阅号的后四位
		if (this.bdDAO.GetMaxJyh(todayStr) == null) {
			Count = 0;
		} else {
			Count = Integer.parseInt(this.bdDAO.GetMaxJyh(todayStr));
		}
		Count = Count + 1;
		String si = String.valueOf(Count);
		String s = "0000";
		si = s.substring(0, 4 - si.length()) + si;

		// 操作时间
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		Timestamp time = new Timestamp(date.getTime());
		String Time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);

		if (bdlist.isEmpty()) {
			// 借阅号
			bd.setJyh(todayStr + "B" + si);
			// 状态
			bd.setState("借阅");
		} else {
			bd = bdlist.get(bdlist.size()-1);
			bd.setDuration(duration);
			if (bd.getState().equals("借阅")) {
				bd.setJyh(todayStr + "R" + si);
				bd.setState("归还");
			} else {
				// 借阅号
				bd.setJyh(todayStr + "B" + si);
				// 状态
				bd.setState("借阅");
			}
		}

		bd.setUserID(Global.currentUser.getUserID());
		bd.setUsername(Global.currentUser.getUsername());
		bd.setTime(Time);
		
		if (this.bdDAO.Create(bd)) {
			sql.commit();
			map.put("code", 0);
			map.put("msg", "成功增加一条借阅记录！");
		} else {
			map.put("code", 2);
			map.put("msg", "创建发生了异常，请重新试一下！");
		}

		sql.close();
		return map;
	}

	public Map<String, Object> CheckBD(Borrowdetail bd) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		SqlSession sql = Global.getSQLSession();
		this.bdDAO = sql.getMapper(IBorrowdetailDAO.class);

		ArrayList<Borrowdetail> bdlist = new ArrayList<Borrowdetail>();
		bd.setBookID(bd.getBookID());
		bd.setUserID(Global.currentUser.getUserID());
		bdlist = this.bdDAO.Query(bd);

		int c = 0;

		for (Borrowdetail b : bdlist) {
			if (b.getState().equals("借阅")) {
				c++;
			}
			if (b.getState().equals("归还")) {
				c++;
			}
		}
		if (c % 2 == 0) {
			// 偶数借
			map.put("code", 0);
			map.put("msg", "您未借阅该图书!");
		} else {
			// 奇数还
			map.put("code", 1);
			map.put("msg", "您未归还该图书!");
		}
		sql.close();
		return map;
	}

	public Map<String, Object> UpdateDuration(Borrowdetail bd) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		SqlSession sql = Global.getSQLSession();
		this.bdDAO = sql.getMapper(IBorrowdetailDAO.class);

		// 更新操作时间
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		String Time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
		bd.setTime(Time);
		
		if(this.bdDAO.Update(bd)) {
			sql.commit();
			map.put("code", 0);
			map.put("msg", "成功更新一条借阅记录！");
		} else {
			map.put("code", 2);
			map.put("msg", "更新发生了异常，请重新试一下！");
		}
		sql.close();
		return map;
	}

	public Map<String, Object> getBDbyBookID(String bookID) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Borrowdetail bd = new Borrowdetail();
		bd.setBookID(bookID);
		SqlSession sql = Global.getSQLSession();
		this.bdDAO = sql.getMapper(IBorrowdetailDAO.class);
		
		Pattern p = Pattern.compile("(\\d){6}");
		Matcher m = p.matcher(bookID);
		if(!m.find()) {
			map.put("code", 2);
			map.put("msg", "图书编号格式不正确,请检查是否输入错误!");
		}else {
			if (this.bdDAO.Query(bd).isEmpty()) {
				map.put("code", 1);
				map.put("msg", "未找到图书编号为:" + bookID + "的借阅记录,请检查是否输入错误!");
			} else {
				if(this.bdDAO.Query(bd).get(this.bdDAO.Query(bd).size()-1).getState().equals("借阅")) {
					map.put("code", 0);
					map.put("msg", this.bdDAO.Query(bd).get(this.bdDAO.Query(bd).size()-1));
				}else {
					map.put("code", 3);
					map.put("msg", "图书已归还,续约失败!");
				}
			}
		}
		sql.close();
		return map;
	}

	public void showBorrowdetail(ArrayList<Borrowdetail> bdlist) {
		// TODO Auto-generated method stub
		System.out.println("借阅号\t\t用户姓名\t用户账号\t图书名称\t图书编号\t操作时间\t\t\t借阅天数\t借阅状态");
		for(Borrowdetail bd : bdlist) {
			System.out.println(bd.toString());
		}
	}

	public ArrayList<Borrowdetail> Query(Borrowdetail bd) throws Exception {
		// TODO Auto-generated method stub
		SqlSession sql = Global.getSQLSession();
		this.bdDAO = sql.getMapper(IBorrowdetailDAO.class);
		
		ArrayList<Borrowdetail> bdlist = this.bdDAO.Query(bd);
		if(bdlist.isEmpty()) {
			System.out.println("找不到满足条件的借阅记录!");
			sql.close();
			return bdlist;
		}
		sql.close();
		return bdlist;
	}

	public Map<String, Object> searchByDate(String date,String state) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Borrowdetail bd = new Borrowdetail();
		ArrayList<Borrowdetail> bdlist = new ArrayList<Borrowdetail>();
		SqlSession sql = Global.getSQLSession();
		this.bdDAO = sql.getMapper(IBorrowdetailDAO.class);
		
		Pattern p = Pattern.compile(
				"((((19|20)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((19|20)\\d{2})-(0?[469]|11)-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8])))");
		Matcher m = p.matcher(date);
		bd.setTime(date);
		if(!m.find()) {
			map.put("code", 1);
			map.put("msg", "输入日期格式有误,请检查并重新输入!");
		}else {
			if(this.bdDAO.Query(bd).isEmpty()) {
				map.put("code", 2);
				map.put("msg", "未找到日期为:"+date+"当天的借阅记录!");
			}else {
				for(Borrowdetail b : this.bdDAO.Query(bd)) {
					if(b.getState().equals(state)) {
						bdlist.add(b);
					}
				}
				if(state.equals("借阅")&&bdlist.isEmpty()) {
					map.put("code", 2);
					map.put("msg", "未找到日期为"+date+"当天的借阅记录!");
				}else if(state.equals("归还")&&bdlist.isEmpty()) {
					map.put("code", 2);
					map.put("msg", "未找到日期为"+date+"当天的归还记录!");
				}else {
					map.put("code", 0);
					map.put("msg", bdlist);
				}
			}
		}
		sql.close();
		return map;
	}

}
