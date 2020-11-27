package tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class databaseConnection  {
	//���ݿ���������ļ���
	private static final String JDBCPROPERTY="jdbc.properties";
	//׼�����ݿ���Ĵ����
	private static String DBDRIVER="";
	private static String DBURL="";
	private static String DBUSER="";
	private static String PASSWORD="";
	
	//׼�����ݿ����Ӷ���
	private Connection conn;
	
	static {
		Properties property=new Properties();
		InputStream is = databaseConnection.class.getClassLoader().getResourceAsStream("resource/"+JDBCPROPERTY);
		try {
			//������Դ
			property.load(new InputStreamReader(is, "utf-8"));
			is.close();
			DBDRIVER=property.getProperty("DBDRIVER");
			DBURL=property.getProperty("DBURL");
			DBUSER=property.getProperty("DBUSER");
			PASSWORD=property.getProperty("PASSWORD");
			
			//��������
			Class.forName(DBDRIVER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//���췽����ʵ��������ʱ�������Ӷ���
	public databaseConnection() throws Exception {
		this.conn=DriverManager.getConnection(DBURL,DBUSER,PASSWORD);
		
	}
	
	//ֱ�ӷ���ʵ��������ʱ���������Ӷ���
	public  Connection getConnection() {
		return this.conn;
	}
	
	//�ر����Ӷ���
	public void close() throws Exception {
		if(conn!=null) {
			this.conn.close();
		}
	}
	
	
}
