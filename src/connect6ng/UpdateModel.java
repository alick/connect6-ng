/**@file UpdateModel.java
 * @brief 软件更新的版本号的Model
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * 
 * 用来记录版本号，包括最新版本号和当前版本号
 */
package connect6ng;

import java.util.Observable;

/** @brief 软件更新的版本号的Model
* 用来记录版本号，包括最新版本号和当前版本号
*/
public class UpdateModel extends Observable{
	/// 最新版本号
	private String latest_version;
	/// 当前版本号
	private String local_version;
	
	/** @brief 构造函数
	 * 
	 * 初始化构造函数为未读取完成的状态
	 */
	public UpdateModel(){
		local_version = "正在读取文件...";
		latest_version = "正在读取网络信息...";
	}
	
	/** @brief 返回本地版本号
	 * 
	 * @return 本地版本号
	 */
	public String getLocalVersion(){
		return local_version;
	}
	
	/**@brief 返回最新版本号
	 * 
	 * @return 最新版本号
	 */
	public String getLatestVersion(){
		return latest_version;
	}
	
	/**@brief 设置本地版本号为local_v
	 * 
	 * @param local_v 本地版本号
	 */
	public void setLocalVersion(String local_v){
		local_version = local_v;
		setChanged();
		notifyObservers(this);
	}
	
	/** @brief 设置最新版本号为latest_v
	 * 
	 * @param latest_v 最新版本号
	 */
	public void setLatestVersion(String latest_v){
		latest_version = latest_v;
		setChanged();
		notifyObservers(this);
	}
}
