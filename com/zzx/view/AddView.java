package com.zzx.view;

import java.util.Date;
import java.text.SimpleDateFormat;


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
	private JTextField breakfast, lunch, dinner, others, remarks, Date;
	
	public AddView() {
		init();
	}
	
	private void init() {
		setTitle(AppConstants.ADDVIEW_TITLE);
		// center panel
		jPanelCenter = new JPanel();
		jPanelCenter.setLayout(new GridLayout(7, 2));
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
		
		jPanelCenter.add(new JLabel(AppConstants.DATE));
		Date = new JTextField();
		jPanelCenter.add(Date);
		/*自动获得日期*/
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		Date.setText(date);
		
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
					boolean isSuccess = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).add(bill);
					if (isSuccess) {
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
				|| "".equals(others.getText()) || "".equals(remarks.getText()) || "".equals(Date.getText()))
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
		bill.setDate(Date.getText());
	}
	
	private void setEmpty()
	{
		breakfast.setText("");
		lunch.setText("");
		dinner.setText("");
		others.setText("");
		remarks.setText("");
	}
	
}
