/** @file AboutDialog.java
 * 
 * @brief "关于六子棋"菜单的弹出界面
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * 
 * 用来显示About界面，介绍软件的大体情况
 */
package connect6ng;

import java.lang.reflect.Method;
import java.util.logging.Level;
import javax.swing.ImageIcon;

/**@brief "关于六子棋"菜单的弹出界面
 */
@SuppressWarnings("serial")
public class AboutDialog extends javax.swing.JDialog {

    /** 初始化函数
     * 
     * @param parent 父部件
     * @param modal 是否为模态窗口
     */
    public AboutDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** @brief 初始化部件
     * 
     * 初始化一些部件
     */
    private void initComponents() {

        new javax.swing.JScrollPane();
        OK_btn = new javax.swing.JButton();
        ConnectMe_btn = new javax.swing.JButton();
        title_label = new javax.swing.JLabel();
        author_label = new javax.swing.JLabel();
        version_label = new javax.swing.JLabel();
        copyright_label = new javax.swing.JLabel();
        icon_label = new javax.swing.JLabel();
        code_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        OK_btn.setText("确定");
        OK_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OK_btnActionPerformed(evt);
            }
        });
        
        ConnectMe_btn.setText("联系我们");
        ConnectMe_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectMe_btnActionPerformed(evt);
            }
        });

        title_label.setFont(new java.awt.Font("华文行楷", 1, 22)); // NOI18N
        title_label.setText("六子棋小游戏");

        author_label.setFont(new java.awt.Font("华文楷体", 1, 14)); // NOI18N
        author_label.setText("软件工程六子棋项目组");

        version_label.setFont(new java.awt.Font("华文楷体", 1, 14)); // NOI18N
        String v = VersionManager.getLocalVersion();
        if( v == null )
        	version_label.setText("版本：1.0.0 ");
        else
        	version_label.setText("版本：" + v);
        
        copyright_label.setFont(new java.awt.Font("华文楷体", 1, 14)); // NOI18N
        copyright_label.setText("版权所有归 软件工程六子棋项目组 所有 ");

        icon_label.setIcon(new javax.swing.ImageIcon("./res/logo_100.png")); // NOI18N

        code_label.setFont(new java.awt.Font("华文楷体", 1, 14)); // NOI18N
        code_label.setText("软件源代码参见：http://github.com/HouQi/connect6-ng.git");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(copyright_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(version_label, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(author_label, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(icon_label)
                        .addGap(24, 24, 24)
                        .addComponent(title_label)))
                .addGap(126, 126, 126))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(version_label))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(ConnectMe_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(286, Short.MAX_VALUE)
                    .addComponent(OK_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(120, 120, 120)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(title_label))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(icon_label)))
                .addGap(18, 18, 18)
                .addComponent(author_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(version_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(copyright_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(version_label)
                .addGap(28, 28, 28)
                .addComponent(ConnectMe_btn)
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(282, Short.MAX_VALUE)
                    .addComponent(OK_btn)
                    .addGap(27, 27, 27)))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {author_label, copyright_label, version_label});

        setTitle("关于六子棋");
        setIconImage( (new ImageIcon("./res/logo_50.png")).getImage() );
        pack();
    }

    /** OK按钮的触发函数
     * 
     * @param evt 事件
     */
    private void OK_btnActionPerformed(java.awt.event.ActionEvent evt) {
        AboutDialog.this.dispose();
    }
    
    /** @brief 联系我们的触发函数
     * 
     * @param evt 触发事件
     */
    private void ConnectMe_btnActionPerformed(java.awt.event.ActionEvent evt) {
    	try {
			browse("https://github.com/HouQi/connect6-ng-dev/issues");
		} catch (Exception e) {
			Flogger.getLogger().log(Level.SEVERE, e.toString());
		}
        AboutDialog.this.dispose();
    }
    
    /**
     * 
     * @param url URL路径
     * @throws Exception 异常
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

    // 控件的定义
    private javax.swing.JButton OK_btn;
    private javax.swing.JButton ConnectMe_btn;
    private javax.swing.JLabel author_label;
    private javax.swing.JLabel copyright_label;
    private javax.swing.JLabel icon_label;
    private javax.swing.JLabel title_label;
    private javax.swing.JLabel version_label;
    private javax.swing.JLabel code_label;
}
