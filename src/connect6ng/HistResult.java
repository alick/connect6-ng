package connect6ng;

public class HistResult {
	private int win;
	private int all;
	
	public HistResult(int win, int all){
		this.win = win;
		this.all = all;
	}
	
	public int getWin(){
		return win;
	}
	
	public int getAll(){
		return all;
	}
}
