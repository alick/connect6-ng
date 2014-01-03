/**
 * @file __FILE__
 * @brief 日志模块
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * @version v 1.0.0
 */
package connect6ng;

import java.util.logging.Logger;

/** @brief 日志模块，用于记录日志
 * 
 */
public class Flogger {

	private static final Logger fLogger = Logger.getLogger(GameController.class
			.getPackage().getName());

	public static Logger getLogger() {
		return fLogger;
	}
}
