package com.chen.chinaos.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class AppConfig {
	private final static String APP_CONFIG = "config";
	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
	private Context mContext;
	private static AppConfig appConfig;
	private static ProgressDialog dialog;

	public static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator
					+ APP_CONFIG);

			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);

			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	public void set(String key, String value) {
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}

	public String get(String key) {
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}
	
	
	
	/**
	 * Toast提示
	 * @param context
	 * @param msg
	 */
	public static void showShortToast(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	public static void showLongToast(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public static void showDialog(Context context){
		if((dialog==null)||(!dialog.isShowing())){
			dialog=ProgressDialog.show(context, "正在读取", "请稍后……", true, true);
		}
	}
	public static void cancleDialog(){
		if((dialog!=null)||(dialog.isShowing())){
			dialog.dismiss();
		}
	}
}
