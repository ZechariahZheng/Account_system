package com.zzx.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.zzx.bill.AppConstants;
import com.zzx.bill.DAO;
import com.zzx.dao.BillDAO;
import com.zzx.base.BaseDAO;

/**
 * 模块说明:首页界面
 * */

public class MainView extends JFrame {
	private static final long serialVersionUID = 5870864087464173884L;
	
	private final int maxPageNum = 99;
	
	private JPanel jPanelNorth, jPanelSouth, jPanelCenter;
	private JButton jButtonFirst, jButtonLast, jButtonNext, jButtonPre, jButtonAdd, jButtonDelete, jButtonUpdate,
	jButtonFind, jButtonSum;
	private JLabel currPageNumJLabel;
	private JTextField condition;
	public static JTable jTable;
	private JScrollPane jScrollPane;
	private DefaultTableModel myTableModel;
	
	public static String[] column = { "id", AppConstants.BREAKFAST, AppConstants.LUNCH, AppConstants.DINNER, AppConstants.OTHERS,
			AppConstants.REMARKS, AppConstants.TOTAL, AppConstants.DATE };
	public static int currPageNum = 1;
	
	public MainView() {
		init();
	}
	
	private void init() {
		setTitle(AppConstants.MAINVIEW_TITLE);

		// north panel
		jPanelNorth = new JPanel();
		jPanelNorth.setLayout(new GridLayout(1, 6));
		condition = new JTextField(AppConstants.PARAM_FIND_CONDITION);
		condition.addKeyListener(new FindListener());
		jPanelNorth.add(condition);
		
		//按日期查找
		jButtonFind = new JButton(AppConstants.PARAM_FIND);
		jButtonFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				find();
			}
		});
		jButtonFind.addKeyListener(new FindListener());
		jPanelNorth.add(jButtonFind);
		
		//添加
		jButtonAdd = new JButton(AppConstants.PARAM_ADD);
		jButtonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddView();
			}
		});
		jPanelNorth.add(jButtonAdd);
		
		//删除
		jButtonDelete = new JButton(AppConstants.PARAM_DELETE);
		jButtonDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DeleteView();
			}
		});
		jPanelNorth.add(jButtonDelete);
		
		//更新
		jButtonUpdate = new JButton(AppConstants.PARAM_UPDATE);
		jButtonUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UpdateView();
			}
		});
		jPanelNorth.add(jButtonUpdate);
		
		//月和
		jButtonSum = new JButton(AppConstants.PARAM_SUM);
		jButtonSum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SumView();
			}
		});
		jPanelNorth.add(jButtonSum);

		// center panel
		jPanelCenter = new JPanel();
		jPanelCenter.setLayout(new GridLayout(1, 1));

		// init jTable
		String[][] result = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).list(currPageNum);
		myTableModel = new DefaultTableModel(result, column);
		jTable = new JTable(myTableModel);
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.CENTER);
		jTable.setDefaultRenderer(Object.class, cr);
		initJTable(jTable, result);

		jScrollPane = new JScrollPane(jTable);
		jPanelCenter.add(jScrollPane);

		// south panel
		jPanelSouth = new JPanel();
		jPanelSouth.setLayout(new GridLayout(1, 5));

		//首页
		jButtonFirst = new JButton(AppConstants.MAINVIEW_FIRST);
		jButtonFirst.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currPageNum = 1;
				String[][] result = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).list(currPageNum);
				initJTable(jTable, result);
				currPageNumJLabel.setText(AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
						+ AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
			}
		});
		
		//上一页
		jButtonPre = new JButton(AppConstants.MAINVIEW_PRE);
		jButtonPre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currPageNum--;
				if (currPageNum <= 0) {
					currPageNum = 1;
				}
				String[][] result = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).list(currPageNum);
				initJTable(jTable, result);
				currPageNumJLabel.setText(AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
						+ AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
			}
		});
		
		//下一页
		jButtonNext = new JButton(AppConstants.MAINVIEW_NEXT);
		jButtonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currPageNum++;
				if (currPageNum > maxPageNum) {
					currPageNum = maxPageNum;
				}
				String[][] result = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).list(currPageNum);
				initJTable(jTable, result);
				currPageNumJLabel.setText(AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
						+ AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
			}
		});
		
		//末页
		jButtonLast = new JButton(AppConstants.MAINVIEW_LAST);
		jButtonLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currPageNum = maxPageNum;
				String[][] result = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).list(currPageNum);
				initJTable(jTable, result);
				currPageNumJLabel.setText(AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
						+ AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
			}
		});
		
		//页数显示
		currPageNumJLabel = new JLabel(
				AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum + AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
		currPageNumJLabel.setHorizontalAlignment(JLabel.CENTER);

		jPanelSouth.add(jButtonFirst);
		jPanelSouth.add(jButtonPre);
		jPanelSouth.add(currPageNumJLabel);
		jPanelSouth.add(jButtonNext);
		jPanelSouth.add(jButtonLast);

		this.add(jPanelNorth, BorderLayout.NORTH);
		this.add(jPanelCenter, BorderLayout.CENTER);
		this.add(jPanelSouth, BorderLayout.SOUTH);

		setBounds(400, 200, 750, 340);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void initJTable(JTable jTable, String[][] result) {
		((DefaultTableModel) jTable.getModel()).setDataVector(result, column);
		jTable.setRowHeight(20);
		TableColumn firsetColumn = jTable.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(30);
		firsetColumn.setMaxWidth(30);
		firsetColumn.setMinWidth(30);
		TableColumn secondColumn = jTable.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(100);
		secondColumn.setMaxWidth(100);
		secondColumn.setMinWidth(100);
		TableColumn thirdColumn = jTable.getColumnModel().getColumn(2);
		thirdColumn.setPreferredWidth(100);
		thirdColumn.setMaxWidth(100);
		thirdColumn.setMinWidth(100);
		TableColumn fourthColumn = jTable.getColumnModel().getColumn(3);
		fourthColumn.setPreferredWidth(100);
		fourthColumn.setMaxWidth(100);
		fourthColumn.setMinWidth(100);
		TableColumn seventhColumn = jTable.getColumnModel().getColumn(6);
		seventhColumn.setPreferredWidth(100);
		seventhColumn.setMaxWidth(100);
		seventhColumn.setMinWidth(100);
		TableColumn ninthColumn = jTable.getColumnModel().getColumn(7);
		ninthColumn.setPreferredWidth(100);
		ninthColumn.setMaxWidth(100);
		ninthColumn.setMinWidth(100);
	}
	
	private class FindListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				find();
			}
		}
	}
	
	private void find() {
		currPageNum = 0;
		String param = condition.getText();
		if ("".equals(param) || param == null) {
			initJTable(MainView.jTable, null);
			currPageNumJLabel.setText(AppConstants.MAINVIEW_FIND_JLABEL);
			return;
		}
		String[][] result = ((BillDAO) BaseDAO.getAbilityDAO(DAO.BillDAO)).queryByDate(param);
		condition.setText("");
		initJTable(MainView.jTable, result);
		currPageNumJLabel.setText(AppConstants.MAINVIEW_FIND_JLABEL);
	}
}
