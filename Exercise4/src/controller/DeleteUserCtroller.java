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

import com.google.gson.Gson;

import dao.UserDao;


@WebServlet(urlPatterns = "/deleteUser.do")
public class DeleteUserCtroller extends HttpServlet {

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids = request.getParameter("ids");
		
		UserDao dao = new UserDao();
		boolean success = dao.delete(ids);
		
		// ��ŷ�����Ϣ��Map
		Map<String, Object> map = new HashMap<String, Object>();
		if (success) {
			map.put("code", 0);
			map.put("info", "ɾ���ɹ�!");
		} else {
			map.put("code", 1);
			map.put("info", "ɾ��ʧ��!");
		}

		//ת��Ϊjson�ַ�
		String jsonStr = new Gson().toJson(map);
		//����ַ���
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}
