/**
 /** @file __FILE__
 * @brief "关于六子棋"菜单的弹出界面
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * 
 * 用来显示About界面，介绍软件的大体情况
 */

package connect6ng;

import javax.swing.ImageIcon;

/** @brief "关于六子棋"菜单的弹出界面
 * 
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * 
 * 用来显示About界面，介绍软件的大体情况
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        OK_btn = new javax.swing.JButton();
        title_label = new javax.swing.JLabel();
        author_label = new javax.swing.JLabel();
        version_label = new javax.swing.JLabel();
        copyright_label = new javax.swing.JLabel();
        icon_label = new javax.swing.JLabel();
        code_label = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        OK_btn.setText("确定");
        OK_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OK_btnActionPerformed(evt);
            }
        });

        title_label.setFont(new java.awt.Font("华文行楷", 1, 22)); // NOI18N
        title_label.setText("六子棋小游戏");

        author_label.setFont(new java.awt.Font("华文楷体", 1, 14)); // NOI18N
        author_label.setText("软件工程六子棋项目组");

        version_label.setFont(new java.awt.Font("华文楷体", 1, 14)); // NOI18N
        version_label.setText("版本：1.0.0 ");

        copyright_label.setFont(new java.awt.Font("华文楷体", 1, 14)); // NOI18N
        copyright_label.setText("版权所有归 软件工程六子棋项目组 所有 ");

        icon_label.setIcon(new javax.swing.ImageIcon("./res/logo_100.png")); // NOI18N

        code_label.setFont(new java.awt.Font("华文楷体", 1, 14)); // NOI18N
        code_label.setText("软件源代码参见：http://github.com/HouQi/connect6-ng.git");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGap(85, 85, 85)
                        .addComponent(copyright_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(OK_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(version_label, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(code_label)
                .addContainerGap(58, Short.MAX_VALUE))
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
                .addComponent(code_label)
                .addGap(11, 11, 11)
                .addComponent(OK_btn)
                .addContainerGap(22, Short.MAX_VALUE))
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

    // 控件的定义
    private javax.swing.JButton OK_btn;
    private javax.swing.JLabel author_label;
    private javax.swing.JLabel copyright_label;
    private javax.swing.JLabel icon_label;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel title_label;
    private javax.swing.JLabel version_label;
    private javax.swing.JLabel code_label;
}
