package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vo.Download;
import vo.User;

public class DownloadDao {

	public List<Download> get() {
		List<Download> DownloadList=new ArrayList<Download>();
		Download download = null;
		try {
			// 1.������
			Class.forName("com.mysql.jdbc.Driver");
			// 2.�������ݿ�����
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/excise?useunicode=true&character=utf-8", "root", "03251115..");
			// 3.�������
			String sql = "select * from t_download where 1=1";
			PreparedStatement pst = con.prepareStatement(sql);
			// 4.ִ�����
			
			ResultSet rs = pst.executeQuery();
			// 5.��Ӧ����
			if (rs.next()) {
				download = new Download(rs.getInt("id"), rs.getString("name"),
						rs.getString("path"), rs.getString("description"),rs.getLong("size"),rs.getInt("star"),rs.getString("image"));
				DownloadList.add(download);
			}
			// 6.�ر�����
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return DownloadList;
	}
	
	public Download findById(int id){
		Download download=null;
		try {
			// 1.������
			Class.forName("com.mysql.jdbc.Driver");
			// 2.�������ݿ�����
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/excise?useunicode=true&character=utf-8", "root", "03251115..");
			// 3.�������
			String sql = "select * from t_download where id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			// 4.ִ�����
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			// 5.��Ӧ����
			if (rs.next()) {
				download = new Download(rs.getInt("id"), rs.getString("name"),
						rs.getString("path"), rs.getString("description"),rs.getLong("size"),rs.getInt("star"),rs.getString("image"));
				
			}
			// 6.�ر�����
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return download;
	}
}
