package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tools.databaseConnection;
import vo.Download;
import dao.DownloadDao;

public class GetDownloadListController extends HttpServlet {

	// 数据库连接类
	private databaseConnection dbc;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();

		// 得到connection对象
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DownloadDao dlDAO = new DownloadDao(this.dbc.getConnection());
		List<Download> dllist = dlDAO.get();

		session.setAttribute("downloadList", dllist);
		// 转发程序的url-pattern
		String forwardPath = "";
		forwardPath = "/download.jsp";
		// 获取转发对象
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		// 将请求转发到目标程序
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
