/**
 * @file __FILE__
 * @brief  "检查更新"菜单的弹出界面
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * 
 * 此页面为检查更新页面，附加GitHub的链接。
 * 通过多线程读取文件和网页上的版本信息
 */
package connect6ng;

import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import javax.swing.ImageIcon;

/** @brief  "检查更新"菜单的弹出界面
 * 
 * 检查软件的更新情况，是否为最新版本
 */
@SuppressWarnings("serial")
public class UpdateDialog extends javax.swing.JDialog implements Observer  {

	UpdateModel model = null;
	
	/***@brief 构造函数
	 * 
	 * @param parent 父构件
	 * @param modal 是否为模态窗体
	 */
	public UpdateDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		model = new UpdateModel();
		initComponents();
	}

	/**@brief 初始化窗体
	 * 
	 * 初始化窗体，完成窗体的布局
	 */
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
        local_version = new javax.swing.JLabel();
        latest_version = new javax.swing.JLabel();

		icon_label.setIcon(new javax.swing.ImageIcon(
				"./res/logo_100.png")); // NOI18N

		title_label.setFont(new java.awt.Font("华文行楷", 1, 22)); // NOI18N
		title_label.setText("六子棋小游戏");

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		icon_label1.setIcon(new javax.swing.ImageIcon(
				"./res/logo_100.png")); // NOI18N

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

		local_version.setFont(new java.awt.Font("华文楷体", 0, 14)); // NOI18N
		local_version.setText( model.getLocalVersion() );

        latest_version.setFont(new java.awt.Font("华文楷体", 0, 14)); // NOI18N
        latest_version.setText(model.getLatestVersion());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(latest_version)
                            .addComponent(local_version)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(icon_label1)
                        .addGap(26, 26, 26)
                        .addComponent(title_label1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(version_label1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(github_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bitbucket_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(google_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(OK_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(icon_label1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(title_label1)
                        .addGap(32, 32, 32)))
                .addGap(18, 18, 18)
                .addComponent(local_version)
                .addGap(18, 18, 18)
                .addComponent(latest_version)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(version_label1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(github_btn)
                    .addComponent(bitbucket_btn)
                    .addComponent(google_btn))
                .addGap(13, 13, 13)
                .addComponent(OK_btn)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        
		setTitle("软件升级");
		setIconImage( (new ImageIcon("./res/logo_50.png")).getImage() );
		pack();
		
		model.addObserver(this);
		updateVersion();
	}// </editor-fold>

	/** @brief 确定 按钮的事件监听函数
	 */
	private void OK_btnActionPerformed(java.awt.event.ActionEvent evt) {
		UpdateDialog.this.dispose();
	}

	/** @brief github按钮的事件监听函数
	 */
	private void github_btnActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			browse("https://github.com/HouQi/connect6-ng-dev");
		} catch (Exception e) {
			Flogger.getLogger().log(Level.WARNING, "browse github error");
		} finally {
			UpdateDialog.this.dispose();
		}
	}
	
	/** @brief bitbucket按钮的事件监听函数
	 */
	private void bitbucket_btnActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			browse("https://bitbucket.org/houqi/connect6-ng/src");
		} catch (Exception e) {
			Flogger.getLogger().log(Level.WARNING, "browse bitbucket error");
		} finally {
			UpdateDialog.this.dispose();
		}
	}

	/** @brief google code按钮的事件监听函数
	 */
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
	
	/**@brief 更新版本号
	 * 
	 * 用多线程实现版本号的读取，提高响应速度
	 */
	private void updateVersion(){
		Thread t1 = new Thread(){
			public void run(){
				String local_v = VersionManager.getLocalVersion();
				model.setLocalVersion(local_v);
			}
		};
		t1.start();
		
		Thread t2 = new Thread(){
			public void run(){				
		        String latest_v = VersionManager.getLatestVersion();
		        model.setLatestVersion(latest_v);
			}
		};
		t2.start();
	}
	
	/**@brief 更新函数
	 * 
	 * 重设标签信息，显示正确的版本号
	 */
	@Override
	public void update(Observable obs, Object obj) {
		model = (UpdateModel) obs;
		String local_v = model.getLocalVersion();
		if( local_v == null ){
			local_version.setText("获取本地版本号错误");
		}else
			local_version.setText("本地版本号：" + local_v);
		
		String latest_v = model.getLatestVersion();
		if( latest_v == null ){
			latest_version.setText("获取最新版本号错误，请检查网络连接");
		}else
			latest_version.setText("最新版本号：" + latest_v);
	}

	// 控件
	private javax.swing.JButton OK_btn;
	private javax.swing.JButton bitbucket_btn;
	private javax.swing.JButton github_btn;
	private javax.swing.JButton google_btn;
	private javax.swing.JLabel icon_label;
	private javax.swing.JLabel icon_label1;
	private javax.swing.JLabel title_label;
	private javax.swing.JLabel title_label1;
	private javax.swing.JLabel version_label1;
    private javax.swing.JLabel latest_version;
    private javax.swing.JLabel local_version;
}
