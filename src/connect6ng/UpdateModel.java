package connect6ng;

import java.util.Observable;

public class UpdateModel extends Observable{
	private String latest_version;
	private String local_version;
	
	public UpdateModel(){
		local_version = "正在读取文件...";
		latest_version = "正在读取网络信息...";
	}
	
	public String getLocalVersion(){
		return local_version;
	}
	
	public String getLatestVersion(){
		return latest_version;
	}
	
	public void setLocalVersion(String local_v){
		local_version = local_v;
		setChanged();
		notifyObservers(this);
	}
	
	public void setLatestVersion(String latest_v){
		latest_version = latest_v;
		setChanged();
		notifyObservers(this);
	}
}
