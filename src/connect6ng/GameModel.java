/**
 * @file __FILE__
 * @brief Game的Model
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * @version v 1.0.0
 */
package connect6ng;

import java.io.Serializable;
import java.util.Vector;
import java.util.Observable;

/** @brief 游戏场景的Model
 * 
 * 游戏的数据Model
 */
@SuppressWarnings("serial")
public class GameModel extends Observable implements Serializable  {

	private Vector<MyPoint> Chessmans;

	/** 黑子/白子（先手/后手）
	 * 0 = 黑子
	 * 1 = 白子
	 */
	private int color;

	// 人机/人人
	// 0 = 非人机对战
	// 1 = 人机对战
	private boolean computer;

	// 模式
	// 0 = 练习模式
	// 1 = 实战模式
	private int mode;

	/** @brief 构造函数
	 */
	public GameModel() {
		resetState();
		Chessmans = new Vector<MyPoint>();
	}

	/** @brief 构造函数
	 * 
	 * @param points 棋盘的布局
	 */
	public GameModel(Vector<MyPoint> points) {
		resetState();
		if (points == null)
			Chessmans = new Vector<MyPoint>();
		Chessmans = points;
	}

	/** @brief 新建游戏
	 * 
	 * 不改变先手/后手
	 */
	public void newGame() {
		Chessmans.clear();
		if( getComputer() && getColor() == 1 ){
			Chessmans.add(new MyPoint(9, 9, 0));
		}
		setChanged();
		notifyObservers(this);
	}
	
	/** @brief 新建游戏
	 * 
	 * @param color 先后/后手
	 */
	public void newGame(int color){
		Chessmans.clear();
		setColor(color);
		if( getComputer() && getColor() == 1 ){
			Chessmans.add(new MyPoint(9, 9, 0));
		}
		setChanged();
		notifyObservers(this);
	}

	/** @brief 重置状态
	 */
	public void resetState() {
		computer = true;
		color = 0;
	}

	/** @brief 是否为玩家的turn
	 * 
	 * @return 是否为玩家的turn
	 */
	public boolean playerTurn() {
		if( getComputer() == true )
			return getColor() == getCurrentColor();
		return true;
	}

	/** @brief 添加棋子
	 * 
	 * @param p 待添加的棋子
	 */
	public void AddChessman(MyPoint p) {
		Chessmans.add(p);
		setChanged();
		notifyObservers(this);
	}

	/** @brief 返回棋盘的布局
	 * 
	 * @return 棋盘布局
	 */
	public Vector<MyPoint> getChessmans() {
		return Chessmans;
	}

	/** @brief 返回实战模式/训练模式
	 * 
	 * @return 实战模式/训练模式
	 */
	public int getMode() {
		return mode;
	}

	/** @brief 设置实战模式/训练模式
	 * 
	 * @param mode 实战模式/训练模式
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/** @brief 返回人人对战/人机对战
	 * 
	 * @return 人人对战/人机对战
	 */
	public boolean getComputer() {
		return computer;
	}

	/** @brief 设置人人对战/人机对战
	 * 
	 * @param rival 人人对战/人机对战
	 */
	public void setComputer(boolean rival) {
		this.computer = rival;
	}

	/** @brief 返回颜色（先手/后手）
	 * 
	 * @return 返回颜色（先手/后手）
	 */
	public int getColor() {
		return color;
	}

	/** @brief 设置返回颜色（先手/后手）
	 * 
	 * @param color 返回颜色（先手/后手）
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/** 返回棋盘的子的数目
	 * 
	 * @return 棋盘的子的数目
	 */
	public int getSize() {
		return Chessmans.size();
	}

	/** 设置棋盘的布局
	 * 
	 * @param p 棋盘的布局
	 */
	public void setChessMan(Vector<MyPoint> p) {
		if (p == null) {
			Chessmans.clear();
		} else {
			Chessmans = new Vector<MyPoint>(p);
		}
	}

	/** @brief 设置Model
	 * 
	 * @param model 带设置的Model
	 */
	public void setModel(GameModel model) {
		Chessmans.clear();
		for (MyPoint p : model.Chessmans) {
			Chessmans.add(p);
		}
		setChanged();
		notifyObservers(this);
	}

	/** 返回坐标（x,y）处是否为空
	 * 
	 * @param x x坐标
	 * @param y y坐标
	 * @return 是否为空
	 */
	public boolean isEmpty(int x, int y){
		for( MyPoint p : Chessmans ){
			if( p.getX() == x && p.getY() == y ){
				return false;
			}
		}
		return true;
	}
	
	/** 悔棋
	 * 
	 * @return 悔棋是否成功
	 *  小于0 ： 不成功
	 *  大于等于0 ： 成功
	 */
	public int backStep(){
		if( Chessmans.isEmpty() )
			return -1;
		Chessmans.remove( Chessmans.size() - 1 );
		if( getComputer() ){
			while( !Chessmans.isEmpty() && getLastColor() != getColor() ){
				Chessmans.remove( Chessmans.size() - 1 );
			}
		}
		setChanged();
		notifyObservers(this);
		return 1;
	}
	
	/** 单击(x,y)
	 * 
	 * @param x x坐标
	 * @param y y坐标
	 * @return 点击是否有效
	 *  小于0：无效
	 *  大于等于0 ： 有效
	 */
	public int getClickedAt(int x, int y) {

		if( !isEmpty(x, y) ){
			//TODO
			return -1;
		}		
		
		AddChessman(new MyPoint(x, y, getCurrentColor()));
		
		setChanged();
		notifyObservers(this);
		
		return 0;
	}
	
	/** @brief 返回上一步的颜色
	 * 
	 * @return 上一步的颜色
	 */
	public int getLastColor(){
		return (getSize()%4)/2;
	}
	
	/** 返回当前可以下的颜色
	 * 
	 * @return 当前可以下的颜色
	 */
	public int getCurrentColor(){
		return ((getSize()+1)%4)/2;
	}
}
