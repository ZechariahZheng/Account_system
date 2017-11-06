package com.zzx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zzx.base.BaseDAO;
import com.zzx.model.Bill;


public class BillDAO extends BaseDAO{
	private final int fieldNum = 8;
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
			if (queryBydate(bill.getDate()) == false)
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
	public boolean add(Bill bill)
	{
		boolean res = false;
		if (null == bill)
			return res;
		try{
			//查询
			if (queryBydate(bill.getDate()) == true)
				return res;
			//插入
			String sql = "insert into bill(breakfast,lunch,dinner,others,remarks,total,date) values(?,?,?,?,?,?,?)";
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
		
	
	//查询页面
	public String[][] list(int pageNum)
	{
		String[][] res = null;
		if (pageNum < 1)
			return res;
		List<Bill> bill = new ArrayList<Bill>();
		int i = 0;
		int beginNum = (pageNum-1)*showNum;
		String sql = "select * from bill limit ?,?";
		Integer[] param = { beginNum, showNum};
		rs = db.executeQuery(sql, param);
		try{
			while(rs.next())
			{
				buildList(rs, bill, i);
				i++;
			}
			if (bill.size() > 0)
			{
				res = new String[bill.size()][fieldNum];
				for (int j = 0; j < bill.size(); j++)
					buildResult(res, bill, j);				
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			destroy();
		}
		return res;
	}
	
	//按日期查询，返回字符串数组
	public String[][] queryByDate(String date)
	{
		String[][] res = null;
		if (date.length() < 0)
			return res;
		List<Bill> bill = new ArrayList<Bill>();
		int i = 0;
		String sql = "select * from bill where date=?";
		String[] param = { date };
		rs = db.executeQuery(sql, param);
		try{
			while(rs.next())
			{
				buildList(rs, bill, i);
				i++;
			}
			if (bill.size() > 0)
			{
				res = new String[bill.size()][fieldNum];
				for (int j = 0; j < bill.size(); j++)
					buildResult(res, bill, j);				
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			destroy();
		}
		return res;
	}
	
	//月份统计
	public float queryByMonth(String Date)
	{
		float Total = 0.0f;
		
		/*1、获得数据库中的日期，并将其转换为YY-MM,与传递过来的
		 * 日期进行比较是否为同一月份*/
		String sql = "select * from bill where date REGEXP ?";
		String date = Date + "-.";
		String[] param = { date };
		rs = db.executeQuery(sql, param);
		try{
			while(rs.next())
			{
				/*将总计添加到List中*/
				Total += Float.parseFloat(rs.getString("total"));
			}
			
		}catch(Exception e)
		{
			System.out.println("sumByMonth failed!");
		}finally {
			destroy();
		}
		return Total;
	}
	
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
	private boolean queryBydate(String date) throws SQLException
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
