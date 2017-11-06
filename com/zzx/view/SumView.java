package com.zzx.view;

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


public class SumView extends JFrame {
	private static final long serialVersionUID = -2984182788841566838L;
	
	private JPanel jPanelCenter, jPanelSouth;
	private JButton sumButton, exitButton;
	private JTextField month, sum; // 根据月份查找统计
	
	public SumView()
	{
		init();
	}
	private void init()
	{
		setTitle(AppConstants.SUMVIEW_TITLE);
		// center panel
		jPanelCenter = new JPanel();
		jPanelCenter.setLayout(new GridLayout(3, 2));
		jPanelCenter.add(new JLabel(AppConstants.SUMVIEW_MONTH));
		month = new JTextField();
		jPanelCenter.add(month);
		jPanelCenter.add(new JLabel(AppConstants.PARAM_SUM));
		sum = new JTextField();
		jPanelCenter.add(sum);
		jPanelCenter.add(new JLabel("-------------------------------------------------"));
		jPanelCenter.add(new JLabel("-------------------------------------------------"));

		// south panel
		jPanelSouth = new JPanel();
		jPanelSouth.setLayout(new GridLayout(1, 2));
		sumButton = new JButton(AppConstants.SUMVIEW_SUMBUTTON);
		sumButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (check()) {
					sumBill();
				}
			}
		});
		jPanelSouth.add(sumButton);
		
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
		setBounds(470, 250, 400, 130);
		setResizable(false);
		setVisible(true);
	}
	
	private boolean check() {
		boolean result = false;
		if ("".equals(month.getText())) {
			return result;
		} else {
			result = true;
		}
		return result;
	}
	
	private void sumBill()
	{
		/*1、获得所需统计的月份*/
		String queryMonth = month.getText();
		
		/*2.获得该月份所有的账单*/
		float total= ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).queryByMonth(queryMonth);
		
		sum.setText(String.valueOf(total));
	}
}
