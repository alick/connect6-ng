package connect6ng;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameView extends JPanel implements Observer {

	private GameModel game_model;

	int x_start = 20;
	int y_start = 20;
	int chess_size = 30;
	int chess_num = 19;

	public GameView() {
		game_model = new GameModel();
	}

	public GameView(GameModel model) {
		if (model == null)
			game_model = new GameModel();
		game_model = model;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		g.setColor(new Color(240, 120, 20));
		g.fillRect(0, 0, x_start * 2 + (chess_num-1) * chess_size, y_start * 2
				+ (chess_num-1) * chess_size); // 绘制背景
		g.setColor(Color.darkGray); // 绘制网格
		for (int i = 0; i < chess_num; i++) {
			g.drawLine(x_start, y_start + chess_size * i, (chess_num - 1)
					* chess_size + x_start, y_start + chess_size * i);
			g.drawLine(x_start + chess_size * i, y_start, x_start + chess_size
					* i, y_start + chess_size * (chess_num - 1));
		}

		g.setColor(Color.black);
		g.fillOval(10 * chess_size + x_start - 3,
				10 * chess_size + y_start - 3, 7, 7); // 绘制5个标志点
		g.fillOval(3 * chess_size + x_start - 3, 4 * chess_size + y_start - 3,
				7, 7);
		g.fillOval(3 * chess_size + x_start - 3, (chess_num - 4) * chess_size
				+ y_start - 3, 7, 7);
		g.fillOval( (chess_num - 4) * chess_size + x_start - 3, 4 * chess_size
				+ y_start - 3, 7, 7);
		g.fillOval( (chess_num - 4) * chess_size + x_start - 3, (chess_num - 4)
				* chess_size + y_start - 3, 7, 7);

		Vector<MyPoint> data = game_model.getChessmans();
		// 绘制棋子
		for (MyPoint p : data) {
			if (p.getColor() == 0) {
				g.setColor(new Color(20, 20, 20));
			} else {
				g.setColor(Color.white);
			}

			g.fillOval(x_start + chess_size + chess_size * p.getX() - 10,
					y_start + chess_size + chess_size * p.getY() - 10, 21, 21);
		}
	}

	@Override
	public void update(Observable obs, Object obj) {
		game_model = (GameModel) obs;
		repaint();
	}

}
