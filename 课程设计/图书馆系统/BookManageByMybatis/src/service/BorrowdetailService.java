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

		// ��ý��ĺ�����
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String todayStr = df.format(today);

		int Count;
		// ��õ��������ĺŵĺ���λ
		if (this.bdDAO.GetMaxJyh(todayStr) == null) {
			Count = 0;
		} else {
			Count = Integer.parseInt(this.bdDAO.GetMaxJyh(todayStr));
		}
		Count = Count + 1;
		String si = String.valueOf(Count);
		String s = "0000";
		si = s.substring(0, 4 - si.length()) + si;

		// ����ʱ��
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		Timestamp time = new Timestamp(date.getTime());
		String Time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);

		if (bdlist.isEmpty()) {
			// ���ĺ�
			bd.setJyh(todayStr + "B" + si);
			// ״̬
			bd.setState("����");
		} else {
			bd = bdlist.get(bdlist.size()-1);
			bd.setDuration(duration);
			if (bd.getState().equals("����")) {
				bd.setJyh(todayStr + "R" + si);
				bd.setState("�黹");
			} else {
				// ���ĺ�
				bd.setJyh(todayStr + "B" + si);
				// ״̬
				bd.setState("����");
			}
		}

		bd.setUserID(Global.currentUser.getUserID());
		bd.setUsername(Global.currentUser.getUsername());
		bd.setTime(Time);
		
		if (this.bdDAO.Create(bd)) {
			sql.commit();
			map.put("code", 0);
			map.put("msg", "�ɹ�����һ�����ļ�¼��");
		} else {
			map.put("code", 2);
			map.put("msg", "�����������쳣����������һ�£�");
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
			if (b.getState().equals("����")) {
				c++;
			}
			if (b.getState().equals("�黹")) {
				c++;
			}
		}
		if (c % 2 == 0) {
			// ż����
			map.put("code", 0);
			map.put("msg", "��δ���ĸ�ͼ��!");
		} else {
			// ������
			map.put("code", 1);
			map.put("msg", "��δ�黹��ͼ��!");
		}
		sql.close();
		return map;
	}

	public Map<String, Object> UpdateDuration(Borrowdetail bd) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		SqlSession sql = Global.getSQLSession();
		this.bdDAO = sql.getMapper(IBorrowdetailDAO.class);

		// ���²���ʱ��
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		String Time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
		bd.setTime(Time);
		
		if(this.bdDAO.Update(bd)) {
			sql.commit();
			map.put("code", 0);
			map.put("msg", "�ɹ�����һ�����ļ�¼��");
		} else {
			map.put("code", 2);
			map.put("msg", "���·������쳣����������һ�£�");
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
			map.put("msg", "ͼ���Ÿ�ʽ����ȷ,�����Ƿ��������!");
		}else {
			if (this.bdDAO.Query(bd).isEmpty()) {
				map.put("code", 1);
				map.put("msg", "δ�ҵ�ͼ����Ϊ:" + bookID + "�Ľ��ļ�¼,�����Ƿ��������!");
			} else {
				if(this.bdDAO.Query(bd).get(this.bdDAO.Query(bd).size()-1).getState().equals("����")) {
					map.put("code", 0);
					map.put("msg", this.bdDAO.Query(bd).get(this.bdDAO.Query(bd).size()-1));
				}else {
					map.put("code", 3);
					map.put("msg", "ͼ���ѹ黹,��Լʧ��!");
				}
			}
		}
		sql.close();
		return map;
	}

	public void showBorrowdetail(ArrayList<Borrowdetail> bdlist) {
		// TODO Auto-generated method stub
		System.out.println("���ĺ�\t\t�û�����\t�û��˺�\tͼ������\tͼ����\t����ʱ��\t\t\t��������\t����״̬");
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
			System.out.println("�Ҳ������������Ľ��ļ�¼!");
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
			map.put("msg", "�������ڸ�ʽ����,���鲢��������!");
		}else {
			if(this.bdDAO.Query(bd).isEmpty()) {
				map.put("code", 2);
				map.put("msg", "δ�ҵ�����Ϊ:"+date+"����Ľ��ļ�¼!");
			}else {
				for(Borrowdetail b : this.bdDAO.Query(bd)) {
					if(b.getState().equals(state)) {
						bdlist.add(b);
					}
				}
				if(state.equals("����")&&bdlist.isEmpty()) {
					map.put("code", 2);
					map.put("msg", "δ�ҵ�����Ϊ"+date+"����Ľ��ļ�¼!");
				}else if(state.equals("�黹")&&bdlist.isEmpty()) {
					map.put("code", 2);
					map.put("msg", "δ�ҵ�����Ϊ"+date+"����Ĺ黹��¼!");
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
