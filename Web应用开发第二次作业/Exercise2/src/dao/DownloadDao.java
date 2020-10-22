package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vo.Download;
import vo.User;

public class DownloadDao {
	
	private Connection con;
	public DownloadDao(Connection con) {
		this.con=con;
	}
	
	public List<Download> get() {
		List<Download> DownloadList=new ArrayList<Download>();
		//Download download = null;
		try {
			/*
			// 1.加载类
			Class.forName("com.mysql.jdbc.Driver");
			// 2.建立数据库连接
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/exercise?useunicode=true&character=utf-8", "root", "122903");
			// 3.创建语句
			 * 
			 * download = new Download(rs.getInt("id"), rs.getString("name"),
						rs.getString("path"), rs.getString("description"),rs.getLong("size"),
						rs.getInt("star"),rs.getString("image"),rs.getDate("time"));
			 */
			String sql = "select * from t_downloadlist";
			PreparedStatement pst = con.prepareStatement(sql);
			// 4.执行语句
			
			ResultSet resultSet = pst.executeQuery();
			// 5.响应处理
			while (resultSet.next()) {
				Download download = new Download();
                download.setId(resultSet.getInt(1));
                download.setName(resultSet.getString(2));
                download.setPath(resultSet.getString(3));
                download.setDescription(resultSet.getString(4));
                download.setSize(resultSet.getInt(5));
                download.setStar(resultSet.getInt(6));
                download.setImage(resultSet.getString(7));
                download.setTime(resultSet.getString(8));
				DownloadList.add(download);
			}
			resultSet.close();
			pst.close();
			// 6.关闭连接
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return DownloadList;
    }
	/*
	public Download findById(int id){
		Download download=null;
		try {
			// 1.加载类
			Class.forName("com.mysql.jdbc.Driver");
			// 2.建立数据库连接
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/exercise?useunicode=true&character=utf-8", "root", "122903");
			// 3.创建语句
			String sql = "select * from t_downloadlist where id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			// 4.执行语句
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			// 5.响应处理
			if (rs.next()) {
				download = new Download(rs.getInt("id"), rs.getString("name"),
						rs.getString("path"), rs.getString("description"),rs.getLong("size"),rs.getInt("star"),rs.getString("image"),rs.getDate("time"));
				
			}
			// 6.关闭连接
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return download;
	}
	*/
}
