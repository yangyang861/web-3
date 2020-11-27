package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tools.databaseConnection;
import vo.User;
import dao.UserDao;

@WebServlet(urlPatterns = "/login.do")
public class LoginController extends HttpServlet {

	// ���ݿ�������
	private databaseConnection dbc;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		// �������ݿ�
		try {
			this.dbc = new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 1.ȡ����
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String vcode = request.getParameter("vcode");
		String checkBox = request.getParameter("autologin");

		HttpSession session = request.getSession();
		String saveVcode = (String) session.getAttribute("verifyCode");
		String forwardPath = "";

		if (!vcode.equalsIgnoreCase(saveVcode)) {// ��֤�벻��ȷ
			request.setAttribute("info", "��֤�벻��ȷ");
			forwardPath = "/error.jsp";
		} else {// ��֤����ȷ
			UserDao userDao = new UserDao(this.dbc.getConnection());
			User user = userDao.get(userName);
			if (user == null) {// �û���������
				request.setAttribute("info", "��������û���������");
				forwardPath = "/error.jsp";

			} else {// �û�������
				if (!user.getPassword().equals(password)) {// �������
					request.setAttribute("info", "���벻��ȷ");
					forwardPath = "/error.jsp";
				} else {// ������ȷ
					Cookie cookieUser = new Cookie("userName",user.getUserName());
					Cookie cookiePass = new Cookie("password",user.getPassword());
					if ("on".equals(checkBox)) {
						cookieUser.setMaxAge(60 * 60 * 24 * 7);
						cookiePass.setMaxAge(60 * 60 * 24 * 7);
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
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
		// 3.ת��
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);

	}

}
