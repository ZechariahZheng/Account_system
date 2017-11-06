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
import com.zzx.model.Bill;

/*
 * 模块说明：删除账单
 * */
public class DeleteView extends JFrame {
	private static final long serialVersionUID = -7668153283910203959L;
	
	private JPanel jPanelCenter, jPanelSouth;
	private JButton deleteButton, exitButton;
	private JTextField date; // 根据日期删除账单
	
	public DeleteView() {
		init();
	}
	
	private void init() {
		setTitle(AppConstants.DELETEVIEW_TITLE);
		// center panel
		jPanelCenter = new JPanel();
		jPanelCenter.setLayout(new GridLayout(2, 2));
		jPanelCenter.add(new JLabel(AppConstants.DATE));
		date = new JTextField();
		jPanelCenter.add(date);
		jPanelCenter.add(new JLabel("-------------------------------------------------"));
		jPanelCenter.add(new JLabel("-------------------------------------------------"));

		// south panel
		jPanelSouth = new JPanel();
		jPanelSouth.setLayout(new GridLayout(1, 2));
		deleteButton = new JButton(AppConstants.DELETEVIEW_DELETEBUTTON);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (check()) {
					Bill bill = new Bill();
					buildBill(bill);
					boolean isSuccess = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).delete(bill);
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
		jPanelSouth.add(deleteButton);
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
		if ("".equals(date.getText())) {
			return result;
		} else {
			result = true;
		}
		return result;
	}
	
	private void buildBill(Bill bill) {
		bill.setDate(date.getText());
	}
	
	private void setEmpty() {
		date.setText("");
	}
}
