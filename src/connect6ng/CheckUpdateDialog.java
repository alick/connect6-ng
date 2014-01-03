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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** @brief 检查更新的Dialog
 * 
 * @todo 需要完善
 */
public class CheckUpdateDialog {
	JDialog dialog;
	
	/** @brief 构造函数
	 * 
	 * @param f 父部件
	 */
	public CheckUpdateDialog(JFrame f) {
		dialog = new JDialog(f, "帮助文档", true);
		dialog.setSize(300, 300);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		dialog.setLayout(new GridLayout(3, 1));
		
		JLabel img_icon = new JLabel(new ImageIcon("./res/connect_logo.png"));
		img_icon.setSize(50, 50);
		
		JLabel text_label = new JLabel();
		String copyright_text = "<html><body>六子棋游戏<br>	版本 ： V0.0.1<br>	"
				+ "权所有 @2013 Tsinghua EE, ALl right reserved<br>"
				+ "	此软件受GPLv3的保护</body></html> ";
		text_label.setText(copyright_text);
		
		JButton OK_Button = new JButton("OK");
		OK_Button.setSize(60, 30);
		OK_Button.setMaximumSize(new Dimension(60, 30));
		OK_Button.setBounds(100, 100, 60, 30);
		OK_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		
		Container container = dialog.getContentPane();
		container.add(img_icon, "NORTH");
		container.add(text_label, "CENTER");
		container.add(OK_Button, "SOUTH");

	}
}
