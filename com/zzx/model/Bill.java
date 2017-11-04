package com.zzx.model;


public class Bill {
	private int id;
	private String date;			//日期
	private String breakfast;	//早餐费用
	private String lunch;		//午餐费用
	private String dinner;		//晚餐费用
	private String others;		//其他费用
	private String remarks;		//其他费用的备注
	private String total;		//总计费用 
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int ID)
	{
		id = ID;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String setdate)
	{
		date = setdate;
	}
	
	public String getBreakfast()
	{
		return breakfast;
	}
	
	public void setBreakfast(String setbreakfast)
	{
		breakfast = setbreakfast;
	}
	
	public String getLunch()
	{
		return lunch;
	}
	
	public void setLunch(String setlunch)
	{
		lunch = setlunch;
	}
	
	public String getDinner()
	{
		return dinner;
	}
	
	public void setDinner(String setdinner)
	{
		dinner = setdinner;
	}
	
	public String getOthers()
	{
		return others;
	}
	
	public void setOthers(String setothers)
	{
		others = setothers;
	}
	
	public String getRemarks()
	{
		return remarks;
	}
	
	public void setRemarks(String setremarks)
	{
		remarks = setremarks;
	}
	
	public String getTotal()
	{
		return total;
	}
	
	public void setTotal(String settotal)
	{
		total = settotal;
	}
}
