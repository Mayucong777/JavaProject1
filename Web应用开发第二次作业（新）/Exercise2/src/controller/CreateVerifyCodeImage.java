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
		//��װ��֤�����ɵĹ�����tools�Ķ�Ӧ����
		CreateVerificationCodeImage createVCodeImage = new CreateVerificationCodeImage();
		//������λ����ַ���
		String VCode = createVCodeImage.createCode();
		//��ȡhttpSession����
		HttpSession session = request.getSession();
		//����������λ�ַ��������session�У��Ա���֤
		session.setAttribute("verifyCode", VCode);
		
		//���÷��ص�����
		response.setContentType("img/jpeg");
		//�������������Ӧ����--��֤��ͼƬ����һ��ˢ��һ�Σ��ʲ��ܳ��ֻ���
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		//������֤��ʧЧʱ��
		response.setDateHeader("Expires", 0);
		//���ֽڷ���ȥ������img��src���Խ���
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
