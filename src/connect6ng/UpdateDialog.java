/**
 * @file __FILE__
 * @brief  "关于六子棋"菜单的弹出界面
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * @version v 1.0.0
 * 
 * 此页面为检查更新页面，挂载GitHub的链接。
 */
package connect6ng;

import java.lang.reflect.Method;
import java.util.logging.Level;

import javax.swing.ImageIcon;

/**
 * 
 * @author houqi1993
 */
@SuppressWarnings("serial")
public class UpdateDialog extends javax.swing.JDialog {

	final int x = 1;

	/**
	 * Creates new form UpdateDialog
	 */
	public UpdateDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	private void initComponents() {

		icon_label = new javax.swing.JLabel();
		title_label = new javax.swing.JLabel();
		icon_label1 = new javax.swing.JLabel();
		title_label1 = new javax.swing.JLabel();
		OK_btn = new javax.swing.JButton();
		version_label1 = new javax.swing.JLabel();
		github_btn = new javax.swing.JButton();
		bitbucket_btn = new javax.swing.JButton();
		google_btn = new javax.swing.JButton();

		icon_label.setIcon(new javax.swing.ImageIcon(
				"D:\\Study\\WorkSpace\\connect6-ng-dev\\res\\logo_100.png")); // NOI18N

		title_label.setFont(new java.awt.Font("华文行楷", 1, 22)); // NOI18N
		title_label.setText("六子棋小游戏");

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		icon_label1.setIcon(new javax.swing.ImageIcon(
				"D:\\Study\\WorkSpace\\connect6-ng-dev\\res\\logo_100.png")); // NOI18N

		title_label1.setFont(new java.awt.Font("华文行楷", 1, 22)); // NOI18N
		title_label1.setText("六子棋小游戏");

		OK_btn.setText("确定");
		OK_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OK_btnActionPerformed(evt);
			}
		});

		version_label1.setFont(new java.awt.Font("华文楷体", 1, 14)); // NOI18N
		version_label1.setText("获取源代码：");

		github_btn.setIcon(new javax.swing.ImageIcon(
				"./res/github.png")); // NOI18N
		github_btn.setToolTipText("Github");
		github_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				github_btnActionPerformed(evt);
			}
		});

		bitbucket_btn.setIcon(new javax.swing.ImageIcon(
				"./res/bitbucket.png")); // NOI18N
		bitbucket_btn.setToolTipText("BitBucket");
		bitbucket_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bitbucket_btnActionPerformed(evt);
			}
		});

		google_btn.setIcon(new javax.swing.ImageIcon(
				"./res/Google Code.png")); // NOI18N
		google_btn.setToolTipText("Google Code");
		google_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				google_btnActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(58, 58,
																		58)
																.addComponent(
																		icon_label1)
																.addGap(26, 26,
																		26)
																.addComponent(
																		title_label1))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(35, 35,
																		35)
																.addComponent(
																		version_label1)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						OK_btn,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						98,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										github_btn,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										48,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										bitbucket_btn,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										48,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										google_btn,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										48,
																										javax.swing.GroupLayout.PREFERRED_SIZE)))))
								.addContainerGap(58, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(31, 31, 31)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														icon_label1,
														javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		title_label1)
																.addGap(32, 32,
																		32)))
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(57, 57,
																		57)
																.addComponent(
																		version_label1)
																.addContainerGap(
																		92,
																		Short.MAX_VALUE))
												.addGroup(
														layout.createSequentialGroup()
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						github_btn,
																						javax.swing.GroupLayout.Alignment.TRAILING)
																				.addComponent(
																						bitbucket_btn,
																						javax.swing.GroupLayout.Alignment.TRAILING)
																				.addComponent(
																						google_btn,
																						javax.swing.GroupLayout.Alignment.TRAILING))
																.addGap(28, 28,
																		28)
																.addComponent(
																		OK_btn)
																.addGap(28, 28,
																		28)))));
		setTitle("软件升级");
		setIconImage( (new ImageIcon("./res/logo_50.png")).getImage() );
		pack();
	}// </editor-fold>

	private void OK_btnActionPerformed(java.awt.event.ActionEvent evt) {
		UpdateDialog.this.dispose();
	}

	private void github_btnActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			browse("https://github.com/HouQi/connect6-ng-dev");
		} catch (Exception e) {
			Flogger.getLogger().log(Level.WARNING, "browse github error");
		} finally {
			UpdateDialog.this.dispose();
		}
	}

	private void bitbucket_btnActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			browse("https://bitbucket.org/houqi/connect6-ng/src");
		} catch (Exception e) {
			Flogger.getLogger().log(Level.WARNING, "browse bitbucket error");
		} finally {
			UpdateDialog.this.dispose();
		}
	}

	private void google_btnActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			browse("https://code.google.com/p/connect6-ng-dev/source/browse/");
		} catch (Exception e) {
			Flogger.getLogger().log(Level.WARNING, "browse google code error");
		} finally {
			UpdateDialog.this.dispose();
		}
	}

	/**
	 * 发布消息
	 * 
	 * @param url
	 *            待发布的消息
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void browse(String url) throws Exception {
		// 获取操作系统的名字
		String osName = System.getProperty("os.name", "");
		if (osName.startsWith("Mac OS")) {
			// 苹果的打开方式
			Class fileMgr = Class.forName("com.apple.eio.FileManager");
			Method openURL = fileMgr.getDeclaredMethod("openURL",
					new Class[] { String.class });
			openURL.invoke(null, new Object[] { url });
		} else if (osName.startsWith("Windows")) {
			// windows的打开方式。
			Runtime.getRuntime().exec(
					"rundll32 url.dll,FileProtocolHandler " + url);
		} else {
			// Unix or Linux的打开方式
			String[] browsers = { "firefox", "opera", "konqueror", "epiphany",
					"mozilla", "netscape" };
			String browser = null;
			for (int count = 0; count < browsers.length && browser == null; count++)
				// 执行代码，在brower有值后跳出，
				// 这里是如果进程创建成功了，==0是表示正常结束。
				if (Runtime.getRuntime()
						.exec(new String[] { "which", browsers[count] })
						.waitFor() == 0)
					browser = browsers[count];
			if (browser == null)
				throw new Exception("Could not find web browser");
			else
				// 这个值在上面已经成功的得到了一个进程。
				Runtime.getRuntime().exec(new String[] { browser, url });
		}
	}

	// Variables declaration - do not modify
	private javax.swing.JButton OK_btn;
	private javax.swing.JButton bitbucket_btn;
	private javax.swing.JButton github_btn;
	private javax.swing.JButton google_btn;
	private javax.swing.JLabel icon_label;
	private javax.swing.JLabel icon_label1;
	private javax.swing.JLabel title_label;
	private javax.swing.JLabel title_label1;
	private javax.swing.JLabel version_label1;
	// End of variables declaration
}
