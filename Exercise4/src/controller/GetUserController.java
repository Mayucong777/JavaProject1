package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.databaseConnection;
import vo.Page;
import vo.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.UserDao;


@WebServlet(urlPatterns = "/getUser.do")
public class GetUserController extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		//从客户端接受数据
		String queryParams = request.getParameter("queryParams");
		String pageParams = request.getParameter("pageParams");
		System.out.println("查询参数"+queryParams);
		System.out.println("分页参数"+pageParams);
		
		//将GSON字符串参数值转换为java对象
		Gson gson = new GsonBuilder().serializeNulls().create();
		HashMap<String ,Object> mapPage = gson.fromJson(pageParams, HashMap.class);
		Page page = Page.getPageParams(mapPage);
		User user=  new User();
		if(queryParams!=null){
			user = gson.fromJson(queryParams, User.class);
		}
		
		//调用dao执行处理
		UserDao dao = new UserDao();
		//查询
		ArrayList<User> rows = dao.query(user, page);
		int total = dao.count(user, page);//总的用户数量
		
		//转换为json数据
		HashMap<String ,Object> mapReturn = new HashMap<String ,Object>();
		mapReturn.put("rows", rows);
		mapReturn.put("total", total);
		String jsonStr = gson.toJson(mapReturn);
		
		//字符流输出
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println(jsonStr);
		out.print(jsonStr);
		out.flush();
		out.close();
		
	}

}
