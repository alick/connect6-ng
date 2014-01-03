/** @file __FILE__
 * @brief AboutSixChessDialog : "关于六子棋"菜单的弹出界面
 * @author 侯奇
 * @author 卢嘉勋
 * @date 2013-12-16
 * @version v0.1.0
 * 
 * 此页面为只读页面，显示关于程序的基本信息。比如\n 
 * -# 游戏开发人员\n
 * -# 游戏的版本号\n
 * -# 游戏的版权信息等\n

 */
package connect6ng;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * @brief AboutSixChessDialog : "关于六子棋"菜单的弹出界面
 * 
 * 此页面为只读页面，显示了游戏的基本信息，比如\n
 * -# 游戏开发人员\n
 * -# 游戏的版本号\n
 * -# 游戏的版权信息等\n
 */
public class AboutSixChessDialog {
	JDialog dialog;
	
	/**
	 * @brief 构造函数
	 * 
	 * @param f
	 *            父构件 JFrame
	 */
	public AboutSixChessDialog(JFrame f) {
		dialog = new JDialog(f, "帮助文档", true);
		dialog.setSize(300, 300);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		dialog.setLayout(new GridLayout(3, 1));

		JLabel img_icon = new JLabel(new ImageIcon("./res/image/connect_logo.png"));
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
