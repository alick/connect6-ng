/**
 * @file __FILE__
 * @brief 历史记录管理类
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * @version v 1.0.0
 * 
 */
package connect6ng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

	public Statement stat; // 用来执行SQL指令的句柄

	public Database() {

		// 创建数据库
		init();
	}

	/**
	 * 此函数用来执行统计功能
	 * 
	 * @return
	 */
	public HistResult DataStatistics() {
		try {
			// 连接SQLite的JDBC
			Class.forName("org.sqlite.JDBC");

			// 建立一个数据库名connect6.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:connect6.db");

			Statement stat = conn.createStatement();

			// 统计数据
			ResultSet result_query = stat
					.executeQuery("select results, hist_time from HistoryLog;");
			int all_games = 0, win_games = 0;
			while( result_query.next() ){
				int r = Integer.valueOf(result_query.getString("results"));
				if( r == 1 ){
					win_games++;
				}
				all_games++;
			}

			conn.close(); // 结束数据库的连接
			return new HistResult(win_games, all_games);

		} catch (Exception e) {
			e.printStackTrace();
			return new HistResult(0, 0);
		}

	}
	
	public void insert(int result, String hist_time){
		try {
			// 连接SQLite的JDBC
			Class.forName("org.sqlite.JDBC");

			// 建立一个数据库名connect6.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:connect6.db");

			Statement stat = conn.createStatement();

			// 统计数据
			String exe_sql = "insert into HistoryLog values(" + String.valueOf(result) + ",\"" + hist_time + "\");";
			System.out.println(exe_sql);
			stat.execute(exe_sql);

			conn.close(); // 结束数据库的连接

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化数据库链接，如果没有数据库的话则创建一个
	 */
	public void init() {
		System.out.println("数据库初始化");
		try {
			// 连接SQLite的JDBC
			Class.forName("org.sqlite.JDBC");

			// 建立一个数据库名connect6.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:connect6.db");

			Statement stat = conn.createStatement();

			// 判断表是否存在
			ResultSet rsTables = conn.getMetaData().getTables(null, null,
					"HistoryLog", null);
			if (rsTables.next()) {
				System.out.println("表存在,创建表的事情不要做了");
			} else {
				// 创建表，-1：负；0：平；1胜
				stat.executeUpdate("create table HistoryLog(results int, hist_time varchar(256));");
			}
			conn.close(); // 结束数据库的连接

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearDB(){
		System.out.println("数据库初始化");
		try {
			// 连接SQLite的JDBC
			Class.forName("org.sqlite.JDBC");

			// 建立一个数据库名connect6.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:connect6.db");

			Statement stat = conn.createStatement();

			// 统计数据
			String exe_sql = "delete from HistoryLog;";
			System.out.println(exe_sql);
			stat.execute(exe_sql);

			conn.close(); // 结束数据库的连接

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
