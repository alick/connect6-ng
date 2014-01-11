/**
 * @file Flogger.java
 * @brief 日志模块
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * @brief 日志模块，用来记录错误消息
 * @details
 * 包括资源文件未找到、配置文件错误等类型的错误
 */
package connect6ng;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**@brief 日志模块
 * @details 用来记录包括资源文件未找到、配置文件错误等类型的错误
 */
public class Flogger {

	private static Logger fLogger = null;
	
	private Flogger(){	}

	public static Logger getLogger() {
		if( fLogger == null ){
			fLogger = Logger.getLogger(GameController.class
					.getPackage().getName());
			try {
				FileHandler error_handler = new FileHandler("ErrorLog.log");
				fLogger.addHandler(error_handler);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fLogger;
	}
}
