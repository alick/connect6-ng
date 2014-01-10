/** @file VersionManager.java
 * @brief 版本控制类
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * 
 */
package connect6ng;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import javax.net.ssl.HttpsURLConnection;

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

		URL infoUrl = null;
		URLConnection connection = null;
		HttpsURLConnection httpConnection = null;
		InputStream inStream = null;
		// 首先获取URL
		try {
			infoUrl = new URL(
					"https://raw.github.com/HouQi/connect6-ng-dev/master/config/version");
			// version的网址
		} catch (MalformedURLException e) { 
			e.printStackTrace();
		}
		
		// 然后，打开URL连接
		try {
			connection = infoUrl.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 将URL连接转化成 HTTPS 的连接
		httpConnection = (HttpsURLConnection) connection;
		// 从这个 HTTPS 连接中获得流
		try {
			inStream = httpConnection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inStream));
		StringBuilder sb = new StringBuilder();
		String line = null; 
		// 把流转化成字符串
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		v = sb.toString();

		return v;
	}
}
