package com.zzx.dao;

import java.sql.SQLException;
import com.zzx.base.BaseDAO;

/**
 * 模块说明：登录者查询
 * 继承说明：只是为了用到超类的变量
 * */

public class AdminDAO extends BaseDAO {
	private static AdminDAO ad = null;
	
	public static synchronized AdminDAO getInstance() 
	{
		if (ad == null) 
		{
			ad = new AdminDAO();
		}
		return ad;
	}
	
	public boolean queryForLogin(String username, String password) 
	{
		boolean result = false;
		if (username.length() == 0 || password.length() == 0) 
		{
			return result;
		}
		String sql = "select * from admin where username=? and password=?";
		String[] param = { username, password };
		rs = db.executeQuery(sql, param);
		try {
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			destroy();
		}
		return result;
	}
}
