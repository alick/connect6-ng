/**@file HistoryModel.java
 * @brief 历史记录管理类
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * @details
 * 历史记录的管理类
 * 用来管理历史记录，包括历史记录的查询、历史记录的删除等
 */

package connect6ng;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

/**
 * @brief 历史记录管理类
 * @details 历史记录的管理类 用来管理历史记录，包括历史记录的查询、历史记录的删除等
 */
public class HistoryModel {

	/**
	 * @brief 构造函数
	 * 
	 *        初始化数据库
	 */
	public HistoryModel() {

		// 创建数据库
		init();
	}

	/**
	 * 此函数用来执行统计功能
	 * 
	 * @return
	 */
	public HistoryStruct DataStatistics() {
		int all_games = 0, win_games = 0;

		// 连接SQLite的JDBC
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			Flogger.getLogger().log(Level.SEVERE, e.toString());
			return new HistoryStruct(win_games, all_games);
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:./res/connect6.db");
			Statement stat = conn.createStatement();

			// 统计数据
			ResultSet result_query = stat
					.executeQuery("select results, hist_time from HistoryLog;");
			while (result_query.next()) {
				int r = Integer.valueOf(result_query.getString("results"));
				if (r == 1) {
					win_games++;
				}
				all_games++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Flogger.getLogger().log(Level.SEVERE, e.toString());
				}
			}
		}
		return new HistoryStruct(win_games, all_games);
	}

	/**
	 * @brief 向数据库中插入数据
	 * 
	 * @param result
	 *            游戏结果
	 * @param hist_time
	 *            游戏记录的创建事件
	 */
	public void insert(int result, String hist_time) {

		// 连接SQLite的JDBC
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			Flogger.getLogger().log(Level.SEVERE, e.toString());
		}

		Connection conn = null;
		try {
			// 建立一个数据库名connect6.db的连接，如果不存在就在当前目录下创建之
			conn = DriverManager.getConnection("jdbc:sqlite:./res/connect6.db");

			Statement stat = conn.createStatement();

			// 统计数据
			String exe_sql = "insert into HistoryLog values("
					+ String.valueOf(result) + ",\"" + hist_time + "\");";
			stat.execute(exe_sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Flogger.getLogger().log(Level.SEVERE, e.toString());
				}
			}
		}
	}

	/**
	 * @brief 初始化数据库
	 * 
	 *        初始化数据库，如果没有数据库的话则创建一个
	 */
	public void init() {
		// 连接SQLite的JDBC
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			Flogger.getLogger().log(Level.SEVERE, e.toString());
		}
		Connection conn = null;
		try {

			// 建立一个数据库名connect6.db的连接，如果不存在就在当前目录下创建之
			conn = DriverManager.getConnection("jdbc:sqlite:./res/connect6.db");

			Statement stat = conn.createStatement();

			// 判断表是否存在
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rsTables = meta.getTables(null, null, "HistoryLog", null);
			if (!rsTables.next()) {
				// 创建表，-1：负；0：平；1胜
				stat.executeUpdate("create table HistoryLog(results int, hist_time varchar(256));");
			}

		} catch (Exception e) {
			Flogger.getLogger().log(Level.SEVERE, e.toString());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				Flogger.getLogger().log(Level.SEVERE, e.toString());
			} // 结束数据库的连接
		}
	}

	/**
	 * @brief 清空数据库
	 * 
	 *        清空历史记录
	 */
	public void clearDB() {
		// 连接SQLite的JDBC
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			Flogger.getLogger().log(Level.SEVERE, e.toString());
		}

		// 建立一个数据库名connect6.db的连接，如果不存在就在当前目录下创建之
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:./res/connect6.db");
			Statement stat = conn.createStatement();
			// 统计数据
			String exe_sql = "delete from HistoryLog;";
			stat.execute(exe_sql);
		} catch (SQLException e) {
			Flogger.getLogger().log(Level.SEVERE, e.toString());
		} finally {
			try {
				// 结束数据库的连接
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				Flogger.getLogger().log(Level.SEVERE, e.toString());
			}
		}

	}

}
