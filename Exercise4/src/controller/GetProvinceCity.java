package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ProvinceCityDao;
import tools.databaseConnection;
import vo.City;
import vo.Province;

@WebServlet(urlPatterns = "/getProvinceCity.do")
public class GetProvinceCity extends HttpServlet {

	private databaseConnection dbc;
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String provinceCode = request.getParameter("provinceCode");
		String jsonStr = "";
		ProvinceCityDao dao = new ProvinceCityDao(this.dbc.getConnection());
		if (provinceCode == null) {
			ArrayList<Province> list = dao.getAllProvince();
			jsonStr = new Gson().toJson(list);
		} else {
			ArrayList<City> list = dao.getCity(provinceCode);
			jsonStr = new Gson().toJson(list);
		}
		PrintWriter out = response.getWriter();
		System.out.println(jsonStr);
		out.print(jsonStr);
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
