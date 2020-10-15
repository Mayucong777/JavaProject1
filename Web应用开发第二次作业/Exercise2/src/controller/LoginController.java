package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tools.databaseConnection;
import vo.User;
import dao.UserDao;

public class LoginController extends HttpServlet {

	// 数据库连接类
	private databaseConnection dbc;
	
	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//设置字符编码
		request.setCharacterEncoding("utf-8");
		
		//得到connection对象
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//得到参数
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String code = request.getParameter("userCode");
		String checkBox = request.getParameter("autologin");
		
		//得到session对象
		HttpSession session = request.getSession();
		//得到session中缓存的验证码
		String saveCode = (String) session.getAttribute("verifyCode");
		
		//转发程序的url-pattern
		String forwardPath = "";
		
		if(!code.equalsIgnoreCase(saveCode)){//不符
			request.setAttribute("errorInfo", "抱歉，您输入的验证码不正确!");
			forwardPath = "/error.jsp" ;
			
		}else{
			UserDao userDao = new UserDao(this.dbc.getConnection());
			User user = userDao.getByuserName(userName);
			//符合
			if(user==null){//用户名不存在
				request.setAttribute("errorInfo", "抱歉，您输入的用户名不存在!");
				forwardPath = "/error.jsp";
			}else{
				//用户名存在 密码不匹配
				if(!user.getPassword().equals(password)){
					request.setAttribute("errorInfo", "抱歉,您输入的密码不正确!");
					forwardPath = "/error.jsp";
				}else{
					//验证码 用户名 密码都正确
					Cookie cookieUser = new Cookie("userName",user.getUserName());
					Cookie cookiePass = new Cookie("password",user.getPassword());
					if ("on".equals(checkBox)) {
						cookieUser.setMaxAge(60 * 60 * 24 * 7);
						cookiePass.setMaxAge(60 * 60 * 24 * 7);
					} else {// 销毁
						cookieUser.setMaxAge(0);
						cookiePass.setMaxAge(0);
					}
					response.addCookie(cookieUser);
					response.addCookie(cookiePass);

					session.setAttribute("currentUser", user);
					forwardPath = "/main.jsp";
				}
			}
		}
		//获取转发对象
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		//将请求转发到目标程序
		rd.forward(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
