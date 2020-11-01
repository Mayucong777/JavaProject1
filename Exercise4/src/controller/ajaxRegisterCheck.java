package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tools.databaseConnection;
import vo.User;

import com.google.gson.Gson;

import dao.UserDao;

@WebServlet(urlPatterns = "/ajaxRegisterCheck.do")
public class ajaxRegisterCheck extends HttpServlet {

	// ���ݿ�������
		private databaseConnection dbc;

		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");

			// �������ݿ�
			try {
				this.dbc = new databaseConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 1.��ȡ����ֵ
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String chrName = request.getParameter("name");
			String mail = request.getParameter("E-mail");
			String provinceCode = request.getParameter("provinceCode");
			String cityCode = request.getParameter("cityCode");
			
			int flag = Integer.parseInt(request.getParameter("flag"));
			System.out.println(userName);
			// 2.��ȡHttpSession����
			HttpSession session = request.getSession();

			// Map��ŷŻص���Ϣ
			Map<String, Object> map = new HashMap<String, Object>();

			UserDao userDao = new UserDao();
			
			if(flag==0){
				
				User user = userDao.getByuserName(userName);
				if (user == null) {
					map.put("code", 0);
					map.put("info", "�û����Ϸ�");
				
				} else {// �û�������
					map.put("code", 1);
					map.put("info", "�û����Ѵ���");
				}
			}else{
				
				User user=new User(userName,password,chrName,mail,provinceCode,cityCode);
				
				if(userDao.insert(user)==true){
					map.put("register", 0);
				}
					
				else {
					map.put("register", 1);
					
				}
			}
			
			
			String jsonStr = new Gson().toJson(map);
			// �ַ�������ַ���
			PrintWriter out = response.getWriter();
			out.print(jsonStr);
			out.flush();
			out.close();
		}

}
