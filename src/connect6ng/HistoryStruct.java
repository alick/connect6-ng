package connect6ng;
/** @file __FILE__ 
 * @brief  返回对弈结果
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2013-12-30
 * @version 1.0.0
 * 
 */

/**
 * @brief 对弈结果管理类，用来管理对弈结果
 * @author lujx
 *	
 */
public class HistoryStruct {
	private int win;
	private int all;
	
	/**
	 * @brief 构造函数，结果判定
	 * @param win
	 * @param all
	 */
	public HistoryStruct(int win, int all){
		this.win = win;
		this.all = all;
	}
	
	/**
	 * @brief 返回获胜结果
	 * @return
	 */
	public int getWin(){
		return win;
	}
	/**
	 * @brief 返回所有结果
	 * @return
	 */
	public int getAll(){
		return all;
	}
}
