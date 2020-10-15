package filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.ResourceDao;
import tools.databaseConnection;
import vo.User;

public class PermissionFilter implements Filter {

	private String notCheckPath;//不需过滤的请求地址，从web.xml中获取
	
	private databaseConnection dbc;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		try {
			this.dbc=new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		HttpServletRequest request=(HttpServletRequest) req;
		HttpSession session=request.getSession();
		String path=request.getServletPath();
		
		User currentUser=(User) session.getAttribute("currentUser");
		if(currentUser==null){ //没登陆
			if(notCheckPath.indexOf(path) != -1)//要访问的页面是有效的
				chain.doFilter(request,resp);
			else{
				request.setAttribute("errorInfo", "还未登录，无法访问！");
				request.setAttribute("path", "login.html");
				request.getRequestDispatcher("/error.jsp").forward(request, resp);
			}
		}else{
			//检验权限
			ResourceDao dao=new ResourceDao(this.dbc.getConnection());
			List<String> list=dao.getUrlByUserName(currentUser.getUserName());
			if(list.contains(path)){
				chain.doFilter(request,resp);
			}else{
				request.setAttribute("errorInfo", "普通用户没有权限访问");
				request.setAttribute("path", "login.html");
				request.getRequestDispatcher("/error.jsp").forward(request, resp);
			}
		}
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		notCheckPath=config.getInitParameter("notCheckPath");
	}

}
