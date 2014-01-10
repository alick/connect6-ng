package connect6ng;

import javax.swing.ImageIcon;

/**
 * @file __FILE__
 * @brief 历史记录模块
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2013-12-30
 * @version 1.0.0
 * 
 */
@SuppressWarnings("serial")
/**
 * @brief 历史记录类
 * 
 *	用来显示历史记录
 */
public class HistoryView extends javax.swing.JDialog {

	/**
	 * @brief 构造函数，调用初始化组件函数
	 * @param parent
	 * @param modal
	 */
	public HistoryView(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	/**
	 * @brief 初始化组件
	 */
	private void initComponents() {

		icon_label = new javax.swing.JLabel();
		title_label = new javax.swing.JLabel();
		all_label_text = new javax.swing.JLabel();
		win_label_text = new javax.swing.JLabel();
		ratio_label_text = new javax.swing.JLabel();
		OK_btn = new javax.swing.JButton();
		Clear_btn = new javax.swing.JButton();
		all_label = new javax.swing.JLabel();
		win_label = new javax.swing.JLabel();
		ratio_label = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		icon_label.setIcon(new javax.swing.ImageIcon("./res/logo_100.png")); // NOI18N

		title_label.setFont(new java.awt.Font("华文行楷", 1, 22)); // NOI18N
		title_label.setText("六子棋小游戏");

		all_label_text.setText("已玩游戏局数");

		win_label_text.setText("游戏胜利次数");

		ratio_label_text.setText(" 游戏胜利率 ");

		OK_btn.setText("确定");
		OK_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OK_btnActionPerformed(evt);
			}
		});

		Clear_btn.setText("清空历史记录");
		Clear_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Clear_btnActionPerformed(evt);
			}
		});

		HistoryStruct h = (new HistoryModel()).DataStatistics();
		all_label.setText(String.valueOf(h.getAll()));

		win_label.setText(String.valueOf(h.getWin()));

		if (h.getAll() == 0) {
			ratio_label.setText("0.00%");
		} else {
			ratio_label.setText(String.format("%2.2f%%",
					100 * (float) (h.getWin()) / h.getAll()));
		}

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(58, 58, 58)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(all_label_text)
												.addComponent(icon_label)
												.addGroup(
														layout.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(
																		ratio_label_text)
																.addComponent(
																		win_label_text)))
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(26, 26,
																		26)
																.addComponent(
																		title_label))
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						win_label)
																				.addComponent(
																						all_label)
																				.addComponent(
																						ratio_label))
																.addGap(75, 75,
																		75)))
								.addContainerGap(32, Short.MAX_VALUE))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(Clear_btn,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										98,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(40, 40, 40)
								.addComponent(OK_btn,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										98,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(45, 45, 45)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(31, 31, 31)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														icon_label,
														javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		title_label)
																.addGap(32, 32,
																		32)))
								.addGap(31, 31, 31)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(all_label_text)
												.addComponent(all_label))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(win_label_text)
												.addComponent(win_label))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(ratio_label_text)
												.addComponent(ratio_label))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										26, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(OK_btn)
												.addComponent(Clear_btn))
								.addGap(19, 19, 19)));

		setTitle("历史记录");
		setIconImage((new ImageIcon("./res/logo_50.png")).getImage());
		pack();
	}// </editor-fold>

	/**
	 * @brief 确定按钮回调函数
	 * @param evt
	 *            触发事件
	 */
	private void OK_btnActionPerformed(java.awt.event.ActionEvent evt) {
		HistoryView.this.dispose();
	}

	/**
	 * @brief 清空历史记录
	 * @param evt
	 *            触发事件
	 * 
	 *            清空历史记录，并关闭历史记录框
	 */
	private void Clear_btnActionPerformed(java.awt.event.ActionEvent evt) {
		(new HistoryModel()).clearDB();
		HistoryView.this.dispose();
	}

	private javax.swing.JButton OK_btn;
	private javax.swing.JButton Clear_btn;
	private javax.swing.JLabel all_label;
	private javax.swing.JLabel all_label_text;
	private javax.swing.JLabel icon_label;
	private javax.swing.JLabel ratio_label;
	private javax.swing.JLabel ratio_label_text;
	private javax.swing.JLabel title_label;
	private javax.swing.JLabel win_label;
	private javax.swing.JLabel win_label_text;
}
