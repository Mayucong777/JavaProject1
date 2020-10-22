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

	// ���ݿ�������
	private databaseConnection dbc;
	
	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//�����ַ�����
		request.setCharacterEncoding("utf-8");
		
		//�õ�connection����
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//�õ�����
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String code = request.getParameter("userCode");
		String checkBox = request.getParameter("autologin");
		
		//�õ�session����
		HttpSession session = request.getSession();
		//�õ�session�л������֤��
		String saveCode = (String) session.getAttribute("verifyCode");
		
		//ת�������url-pattern
		String forwardPath = "";
		
		if(!code.equalsIgnoreCase(saveCode)){//����
			request.setAttribute("errorInfo", "��Ǹ�����������֤�벻��ȷ!");
			forwardPath = "/error.jsp" ;
			
		}else{
			UserDao userDao = new UserDao(this.dbc.getConnection());
			User user = userDao.getByuserName(userName);
			//����
			if(user==null){//�û���������
				request.setAttribute("errorInfo", "��Ǹ����������û���������!");
				forwardPath = "/error.jsp";
			}else{
				//�û������� ���벻ƥ��
				if(!user.getPassword().equals(password)){
					request.setAttribute("errorInfo", "��Ǹ,����������벻��ȷ!");
					forwardPath = "/error.jsp";
				}else{
					//��֤�� �û��� ���붼��ȷ
					Cookie cookieUser = new Cookie("userName",user.getUserName());
					Cookie cookiePass = new Cookie("password",user.getPassword());
					if ("on".equals(checkBox)) {
						cookieUser.setMaxAge(60 * 60 * 24 * 7);
						cookiePass.setMaxAge(60 * 60 * 24 * 7);
					} else {// ����
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
		//��ȡת������
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		//������ת����Ŀ�����
		rd.forward(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
