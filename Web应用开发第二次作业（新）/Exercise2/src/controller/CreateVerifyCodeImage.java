package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CreateVerificationCodeImage;

public class CreateVerifyCodeImage extends HttpServlet {

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	//@WebServlet(urlPattern = "/servlet/CreateVerifyCodeImage")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//封装验证码生成的过程在tools的对应包中
		CreateVerificationCodeImage createVCodeImage = new CreateVerificationCodeImage();
		//产生四位随机字符串
		String VCode = createVCodeImage.createCode();
		//获取httpSession对象
		HttpSession session = request.getSession();
		//将产生的四位字符串存放于session中，以便验证
		session.setAttribute("verifyCode", VCode);
		
		//设置返回的内容
		response.setContentType("img/jpeg");
		//浏览器不缓存响应内容--验证码图片，点一次刷新一次，故不能出现缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		//设置验证码失效时间
		response.setDateHeader("Expires", 0);
		//以字节发过去，交给img的src属性解析
		BufferedImage image = createVCodeImage.CreateImages(VCode);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		out.flush();
		out.close();
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void init() throws ServletException {
		// Put your code here
	}

	
}
