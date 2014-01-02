package connect6ng;

import java.util.Vector;
import java.util.Observable;

public class GameModel extends Observable {

	private Vector<MyPoint> Chessmans;

	private int State;
	// 0 = 初始、游戏暂停状态
	// 1 = 用户还须下1子
	// 2 = 用户还须下2子

	private boolean pause;
	// 游戏是否处于暂停状态

	private int color;
	// 0 = 黑子
	// 1 = 白子
	// -1 = 空白

	private boolean computer;
	// 0 = 非人机对战
	// 1 = 人机对战

	private int mode;

	// 0 = 练习模式
	// 1 = 实战模式

	public GameModel() {
		resetState();
		Chessmans = new Vector<MyPoint>();
	}

	public GameModel(Vector<MyPoint> points) {
		resetState();
		if (points == null)
			Chessmans = new Vector<MyPoint>();
		Chessmans = points;
	}

	public void newGame() {
		Chessmans.clear();
		setChanged();
		notifyObservers(this);
	}

	public void resetState() {
		pause = true;
		computer = true;
		State = 0;
		color = 0;
	}

	public boolean playerTurn() {
		if( getComputer() == true )
			return getColor() == getCurrentColor();
		return true;
	}

	public void AddChessman(MyPoint p) {
		Chessmans.add(p);
		setChanged();
		notifyObservers(this);
	}

	public Vector<MyPoint> getChessmans() {
		return Chessmans;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public boolean getComputer() {
		return computer;
	}

	public void setComputer(boolean rival) {
		this.computer = rival;
	}

	public boolean getPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		this.State = state;
	}

	public int getSize() {
		return Chessmans.size();
	}

	public void setChessMan(Vector<MyPoint> p) {
		if (p == null) {
			Chessmans.clear();
		} else {
			Chessmans = new Vector<MyPoint>(p);
		}
	}

	public void setModel(GameModel model) {
		Chessmans.clear();
		for (MyPoint p : model.Chessmans) {
			Chessmans.add(p);
		}
		setChanged();
		notifyObservers(this);
	}

	public boolean isEmpty(int x, int y){
		for( MyPoint p : Chessmans ){
			if( p.getX() == x && p.getY() == y ){
				return false;
			}
		}
		return true;
	}
	
	public int getClickedAt(int x, int y) {

		if( !isEmpty(x, y) ){
			//TODO
			return -1;
		}		
		
		int color = getCurrentColor();
		AddChessman(new MyPoint(x, y, color));
		State--;
		
		setChanged();
		notifyObservers(this);
		
		return 0;
	}
	
	public int getCurrentColor(){
		return ((getSize()+1)%4)/2;
	}
	
	public void display(){
		System.out.println("Current state : ");
		System.out.println("Chesses : " + getSize());
		System.out.println("State   : " + getState());
		System.out.println("Color   : " + getColor());
		System.out.println("Color cur:" + getColor());
		System.out.println("Computer :" +          getComputer());
	}
}
