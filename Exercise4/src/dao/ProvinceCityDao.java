package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.City;
import vo.Province;

public class ProvinceCityDao {

	private Connection con;
	public ProvinceCityDao(Connection con) {
		this.con=con;
	}
	
	public ArrayList<Province> getAllProvince(){
		ArrayList<Province> list = new ArrayList() ;
		try {
			
			String sql = "select * from t_province ";
			PreparedStatement pst = con.prepareStatement(sql);
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				Province pro = new Province(rs.getString("provinceCode"), rs.getString("provinceName"));
				System.out.println(pro.toString());
				list.add(pro);
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public ArrayList<City> getCity(String provinceCode){
		ArrayList<City> list = new ArrayList() ;
		try {
			
			String sql = "select * from t_city where cityCode in (select cityCode from t_p_c where provinceCode =?)";
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, provinceCode);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				City city = new City(rs.getString("cityCode"), rs.getString("cityName"));
				list.add(city);
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
