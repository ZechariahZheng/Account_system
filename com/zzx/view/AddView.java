package com.zzx.view;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.zzx.bill.AppConstants;
import com.zzx.bill.DAO;
import com.zzx.base.BaseDAO;
import com.zzx.dao.BillDAO;
import com.zzx.model.Bill;

public class AddView extends JFrame {
	private static final long serialVersionUID = -1984182788841566838L;
	
	private JPanel jPanelCenter, jPanelSouth;
	private JButton addButton, exitButton;
	private JTextField breakfast, lunch, dinner, others, remarks;
	
	public AddView() {
		init();
	}
	
	private void init() {
		setTitle(AppConstants.ADDVIEW_TITLE);
		// center panel
		jPanelCenter = new JPanel();
		jPanelCenter.setLayout(new GridLayout(6, 2));
		jPanelCenter.add(new JLabel(AppConstants.BREAKFAST));
		breakfast = new JTextField();
		jPanelCenter.add(breakfast);
		
		jPanelCenter.add(new JLabel(AppConstants.LUNCH));
		lunch = new JTextField();
		jPanelCenter.add(lunch);
		
		jPanelCenter.add(new JLabel(AppConstants.DINNER));
		dinner = new JTextField();
		jPanelCenter.add(dinner);
		
		jPanelCenter.add(new JLabel(AppConstants.OTHERS));
		others = new JTextField();
		jPanelCenter.add(others);
		
		jPanelCenter.add(new JLabel(AppConstants.REMARKS));
		remarks = new JTextField();
		jPanelCenter.add(remarks);
		
		
		jPanelCenter.add(new JLabel("-------------------------------------------------"));
		jPanelCenter.add(new JLabel("-------------------------------------------------"));

		// south panel
		jPanelSouth = new JPanel();
		jPanelSouth.setLayout(new GridLayout(1, 2));
		
		//添加按钮
		addButton = new JButton(AppConstants.ADDVIEW_ADDBUTTON);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (check()) {
					Bill bill = new Bill();
					buildBill(bill);
					boolean isSuccess_II = false;
					boolean isSuccess = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).add(bill);
					/*如果是月末,再添加一项*/
					if (true == isMonthend(new Date()))
					{
						Bill bill_II = new Bill();
						buildSum(bill_II);
						isSuccess_II = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).add(bill_II);
					}
					if (isSuccess || isSuccess_II) {
						setEmpty();
						if (MainView.currPageNum < 0 || MainView.currPageNum > 99) {
							MainView.currPageNum = 1;
						}
						String[][] result = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO))
								.list(MainView.currPageNum);
						MainView.initJTable(MainView.jTable, result);
					}
				}
			}
		});
		jPanelSouth.add(addButton);
		
		//退出按钮
		exitButton = new JButton(AppConstants.EXITBUTTON);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		jPanelSouth.add(exitButton);

		this.add(jPanelCenter, BorderLayout.CENTER);
		this.add(jPanelSouth, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(470, 200, 400, 270);
		setResizable(false);
		setVisible(true);
	}
	
	private boolean check()
	{
		boolean res = false;
		
		if ("".equals(breakfast.getText()) || "".equals(lunch.getText()) || "".equals(dinner.getText())
				|| "".equals(others.getText()) || "".equals(remarks.getText()))
			return res;
		else
			res = true;
		return res;
	}
	
	private void buildBill(Bill bill) {
		float f_total, f_breakfast, f_lunch, f_dinner, f_others;
		String S_total = "";
		
		try{
			f_breakfast = Float.parseFloat(breakfast.getText());
			f_lunch = Float.parseFloat(lunch.getText());
			f_dinner = Float.parseFloat(dinner.getText());
			f_others = Float.parseFloat(others.getText());
			f_total = f_breakfast+f_lunch+f_dinner+f_others;
			S_total = String.valueOf(f_total);
		}catch(Exception e){
			System.out.println("Str to Float failed!");
		}
		bill.setBreakfast(breakfast.getText());
		bill.setLunch(lunch.getText());
		bill.setDinner(dinner.getText());
		bill.setOthers(others.getText());
		bill.setRemarks(remarks.getText());
		bill.setTotal(S_total);
		
		/*自动获取日期*/
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		bill.setDate(date);
	}
	
	private void buildSum(Bill bill)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		String[] list = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).queryByMonth(date);
		if (list == null)
			System.out.println("queryByMonth failed!");
		else
		{
			int i = 0;
			float Total = 0.0f;
			do{
				try{
					Total += Float.parseFloat(list[i]);
				}catch(Exception e){
					System.out.println("Get Total failed!");
				}
				i++;
			}while(list[i]=="");
			if (Total != 0.0f)
			{
				/*相加成功,记录下来*/
				bill.setBreakfast("---");
				bill.setLunch("---");
				bill.setDinner("---");
				bill.setOthers("---");
				bill.setRemarks("---");
				bill.setTotal(String.valueOf(Total));
			}
		}
	}
	
	private void setEmpty()
	{
		breakfast.setText("");
		lunch.setText("");
		dinner.setText("");
		others.setText("");
		remarks.setText("");
	}
	
	/*检查是否到月末*/
	private boolean isMonthend(Date date)
	{
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		if (time.get(Calendar.DATE)==time.getActualMaximum(Calendar.DAY_OF_MONTH))
			return true;
		else
			return false;
	}
}
