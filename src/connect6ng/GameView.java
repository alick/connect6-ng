/** @file GameView.java
 * @brief 历史记录显示界面
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 *
 * 历史记录显示界面
 * 用来管理历史记录，包括历史记录的查询、历史记录的删除等
 */
package connect6ng;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**@brief 历史记录显示界面
 *
 * 历史记录显示界面
 * 用来管理历史记录，包括历史记录的查询、历史记录的删除等
 */
@SuppressWarnings("serial")
public class GameView extends JPanel implements Observer {

	/// 棋盘图片
	Image chess_map = new ImageIcon("./res/chess_map.png").getImage();
	/// 黑棋的棋子图片
	Image black_chess = new ImageIcon("./res/black_chess.png").getImage();
	/// 白棋的棋子图片
	Image white_chess = new ImageIcon("./res/white_chess.png").getImage();
	/// 提示框图片
	Image tip_box = new ImageIcon("./res/tip_box.png").getImage();

	/// x 轴的偏移
	int x_start = 22;
	/// y 轴的偏移
	int y_start = 22;
	/// 棋子的大小
	int chess_size = 30;
	/// 棋子的数目
	int chess_num = 19;

	/// Model
	private GameModel game_model;
	
	/**@brief 构造函数
	 * 
	 */
	public GameView() {
		
		game_model = new GameModel();
	}

	/**@brief构造函数
	 * 
	 * @param model Model
	 */
	public GameView(GameModel model) {

		if (model == null)
			game_model = new GameModel();
		game_model = model;
		
	}

	/** @brief 重绘函数
	 * 
	 * 用于绘制棋盘区域
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(chess_map, 0, 0, this);

		Vector<MyPoint> data = game_model.getChessmans();
		// 绘制棋子
		for (MyPoint p : data) {
			if (p.getColor() == 0) {
				g.drawImage(black_chess, p.getX() * chess_size + x_start,
						p.getY() * chess_size + y_start, this);
			} else {
				g.drawImage(white_chess, p.getX() * chess_size + x_start,
						p.getY() * chess_size + y_start, this);
			}
		}
		
		// 练习模式下，绘制提示信息
		if( game_model.getMode() == 0 ){

			// 绘制电脑落子提示
			if( game_model.getComputer() ){
				for( int i = data.size() - 1 ; i >= 0 ; i-- ){
					MyPoint p = data.get(i);
					if( p.getColor() != game_model.getColor() ){
						g.drawImage(tip_box, p.getX() * chess_size + x_start,
								p.getY() * chess_size + y_start, this);
					}else{
						break;
					}
				}
			}
		}else{
			
		}
	}

	/** @brief 用于监听事件的变化
	 * 
	 * 一旦事件发生变化，重绘
	 */
	@Override
	public void update(Observable obs, Object obj) {
		game_model = (GameModel) obs;
		repaint();
	}

}
