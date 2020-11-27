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

	private String notCheckPath;//������˵������ַ����web.xml�л�ȡ
	//���ݿ�������
	private databaseConnection dbc;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//�������ݿ�
		try {
			this.dbc=new databaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		HttpServletRequest request=(HttpServletRequest) req;
		HttpSession session=request.getSession();
		String path=request.getServletPath();
//		System.out.println(path+"&&&&&&&&&&&&&&&&&&");
		
		User currentUser=(User) session.getAttribute("currentUser");
		if(currentUser==null){ //˵��û��½
			if(notCheckPath.indexOf(path) != -1)//˵��Ҫ���ʵ�ҳ���Ƿ��е�
				chain.doFilter(request,resp);//����
			else{
				request.setAttribute("info", "���ȵ�½��");
				request.setAttribute("path", "login.html");
				request.getRequestDispatcher("/error.jsp").forward(request, resp);
			}
		}else{//��½��
//			chain.doFilter(request,resp);
			//ʵ������ʵ������
			ResourceDao dao=new ResourceDao(this.dbc.getConnection());
			List<String> list=dao.getUrlByUserName(currentUser.getUserName());
			if(list.contains(path)){
				chain.doFilter(request,resp);
			}else{
				request.setAttribute("info", "������ͨ�û���û��Ȩ�޷���");
				request.setAttribute("path", "login.html");
				request.getRequestDispatcher("/block.jsp").forward(request, resp);
			}
		}
//		if(notCheckPath.indexOf(path)==-1){
//			
//			if(session.getAttribute("currentUser")==null){//û�е�½
//				request.setAttribute("info","û��Ȩ�޷���");
//				request.getRequestDispatcher("/error.jsp").forward(request,resp);
//				
//			}else{//�ѵ�¼
//				chain.doFilter(req,resp);
//			}
//		}else{//����Ҫ����
//			chain.doFilter(req,resp);
//		}
//		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		notCheckPath=config.getInitParameter("notCheckPath");
	}

}
