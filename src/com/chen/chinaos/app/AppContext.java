package com.chen.chinaos.app;
import java.util.Properties;
import java.util.UUID;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppContext extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// ע��App�쳣����������
		Thread.setDefaultUncaughtExceptionHandler(AppException
				.getAppExceptionHandler());

	}

	/**
	 * ��ȡApp��װ����Ϣ
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}
	
	/**
	 * ��ȡAppΨһ��ʶ
	 * @return
	 */
	public String getAppId() {
		String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
		if(StringUtils.isEmpty(uniqueID)){
			uniqueID = UUID.randomUUID().toString();
			setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
		}
		return uniqueID;
	}
	
	
	public void setProperty(String key,String value){
		AppConfig.getAppConfig(this).set(key, value);
	}
	
	public String getProperty(String key){
		return AppConfig.getAppConfig(this).get(key);
	}
	
	public boolean containsProperty(String key){
		Properties props = getProperties();
		 return props.containsKey(key);
	}
	public Properties getProperties(){
		return AppConfig.getAppConfig(this).get();
	}
	
}
