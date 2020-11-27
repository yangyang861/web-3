package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DownloadDao;
import vo.Download;

@WebServlet(urlPatterns="/download.do")
public class DownloadController extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id=request.getParameter("����");
		
		Download download=new Download();
		DownloadDao downloadDao=new DownloadDao();
		download=downloadDao.findById(Integer.parseInt(id));
		
		String pa=download.getPath();

		//1.��ȡҪ���ص��ļ��ľ���·��
		String path=request.getServletContext().getRealPath(pa);
		//2.��ȡҪ���ص��ļ���
		String fileName=path.substring(path.lastIndexOf("//"+1));
		//3.���� context-disposition��Ӧͷ����������������ص���ʽ��
		response.setHeader("context-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
		//4.��ȡ�����ļ���������
		InputStream in=new FileInputStream(path);
		int len=0;
		//5.�������ݻ�����
		byte[] buffer=new byte[1024];
		//6.��ȡOutputStream��
		ServletOutputStream out=response.getOutputStream();
		//7.��FileInputStream��д�뵽buffer������
		while((len=in.read(buffer))!=-1){
			//8.ʹ��OutputStream��������������������ͻ������
			out.write(buffer,0,len);
		}
		in.close();
		out.close();
		
	
	}

}
