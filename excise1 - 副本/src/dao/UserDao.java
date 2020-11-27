package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vo.User;

public class UserDao {
	private Connection con;
	public UserDao(Connection con) {
		this.con=con;
	}
	public User get(String userName) {
		User user = null;
		try {
			// 3.�������
			String sql = "select * from t_user where userName=?";
			PreparedStatement pst = con.prepareStatement(sql);
			// 4.ִ�����
			pst.setString(1, userName);
			ResultSet rs = pst.executeQuery();
			// 5.��Ӧ����
			if (rs.next()) {
				user = new User(rs.getString("userName"), rs.getString("password"),
						rs.getString("chrName"));
			}
			// 6.�ر�����
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

}
