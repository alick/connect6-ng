package connect6ng;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameView extends JPanel implements Observer {

	Image chess_map;
	Image black_chess;
	Image white_chess;

	private GameModel game_model;

	int x_start = 22;
	int y_start = 22;
	int chess_size = 30;
	int chess_num = 19;

	public GameView() {

		chess_map = new ImageIcon("./res/chess_map.png").getImage();
		black_chess = new ImageIcon("./res/black_chess.png").getImage();
		white_chess = new ImageIcon("./res/white_chess.png").getImage();
		
		game_model = new GameModel();
	}

	public GameView(GameModel model) {

		chess_map = new ImageIcon("res/chess_map.png").getImage();
		black_chess = new ImageIcon("res/black_chess.png").getImage();
		white_chess = new ImageIcon("res/white_chess.png").getImage();

		if (model == null)
			game_model = new GameModel();
		game_model = model;
		
	}

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
	}

	@Override
	public void update(Observable obs, Object obj) {
		game_model = (GameModel) obs;
		repaint();
	}

}
