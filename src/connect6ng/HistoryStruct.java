/** @file __FILE__ 
 * @brief  返回对弈结果
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2013-12-30
 *
 * 历史记录的数据结构，包括
 * 	- 和电脑对弈的总局数
 * 	- 和电脑对弈的胜利的局数
 */
package connect6ng;

/**
 * @brief 对弈结果管理类，用来管理对弈结果
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 *	
 * 历史记录的数据结构，包括
 * 	- 和电脑对弈的总局数
 * 	- 和电脑对弈的胜利的局数
 */
public class HistoryStruct {
	/// 胜利的局数
	private int win;
	/// 失败的局数
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
