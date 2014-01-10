/** @file VersionManager.java
 * @brief 版本控制类
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * 
 */
package connect6ng;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class VersionManager {

	public static String getLocalVersion() {
		String v = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"./config/version"));
			v = br.readLine();
			v = v.trim();
			br.close();
		} catch (IOException e) {
			Flogger.getLogger().log(Level.SEVERE, e.toString());
		}
		return v;
	}

	public static String getLatestVersion() {
		String v = null;

		URL url;
		try {
			url = new URL("https://github.com/HouQi/connect6-ng-dev/tree/master/config/version");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			byte[] buf = new byte[1024];
			while( bis.read(buf) != -1 ){
				System.out.println(buf.toString());
				v = buf.toString();
				v = v.trim();
			}			
		} catch (IOException e) {
			Flogger.getLogger().log(Level.SEVERE, e.toString());
		}
		return v;
	}
}
