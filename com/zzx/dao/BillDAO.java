package com.zzx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zzx.base.BaseDAO;
import com.zzx.model.Bill;


public class BillDAO extends BaseDAO{
	private final int fieldNum = 9;
	private final int showNum = 15;
	private static BillDAO sd = null;

	public static synchronized BillDAO getInstance() 
	{
		if (sd == null) 
		{
			sd = new BillDAO();
		}
		return sd;
	}
	
	//更新
	public boolean update(Bill bill)
	{
		boolean res = false;
		if (null == bill)
			return res;
		try{
			if (queryByDate(bill.getDate()) == false)
				return res;
			String sql = "update bill set breakfast=?,lunch=?,dinner=?,others=?,remarks=?,total=? where date=?";
			String[] param = { bill.getBreakfast(), bill.getLunch(), bill.getDinner(), bill.getOthers(),
					bill.getRemarks(), bill.getTotal(), bill.getDate() };
			int rowCount = db.executeUpdate(sql, param);
			if (1 == rowCount)
				res = true;
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			destroy();
		}
		return res;
	}
	
	//删除
	public boolean delete(Bill bill)
	{
		boolean res = false;
		if (null == bill)
			return res;
		String sql = "delete from bill where date=?";
		String[] param = { bill.getDate() };
		int rowCount = db.executeUpdate(sql, param);
		if (1 == rowCount)
			res = true;
		destroy();
		return res;
	}
	
	//增加
	
	
	//将rs记录到list中
	private void buildList(ResultSet rs, List<Bill> list, int i) throws SQLException 
	{
		Bill bill = new Bill();
		bill.setId(i+1);
		bill.setBreakfast(rs.getString("breakfast"));
		bill.setLunch(rs.getString("lunch"));
		bill.setDinner(rs.getString("dinner"));
		bill.setOthers(rs.getString("others"));
		bill.setRemarks(rs.getString("remarks"));
		bill.setTotal(rs.getString("total"));
		bill.setDate(rs.getString("date"));
		
		list.add(bill);
	}
	
	//将list的内容添加到二维数组中
	private void buildResult(String[][] result, List<Bill> list, int i)
	{
		Bill bill = list.get(i);
		result[i][0] = String.valueOf(bill.getId());
		result[i][1] = bill.getBreakfast();
		result[i][2] = bill.getLunch();
		result[i][3] = bill.getDinner();
		result[i][4] = bill.getOthers();
		result[i][5] = bill.getRemarks();
		result[i][6] = bill.getTotal();
		result[i][7] = bill.getDate();
	}
	
	//按日期查询
	private boolean queryByDate(String date) throws SQLException
	{
		boolean res = false;
		if (date == null)
			return res;
		String checkSql = "select * from bill where date=?";
		String[] checkParam = { date };
		rs = db.executeQuery(checkSql, checkParam);
		if (rs.next())
			res = true;
		return res;
	}
}
