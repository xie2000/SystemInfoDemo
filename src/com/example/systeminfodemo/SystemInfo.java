package com.example.systeminfodemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 系统信息<br/>
 * csdnBlog:http://blog.csdn.net/xiechengfa/article/details/46420117<br/>
 * 
 * @Description:
 * @author xiechengfa2000@163.com
 * @date 2015-6-9 下午6:08:47
 */
public class SystemInfo {

	/**
	 * 获取IMEI号
	 * 
	 * @Description:
	 * @param @param activity
	 * @param @return
	 * @return String
	 */
	public static String getIMEI(Activity activity) {
		TelephonyManager manager = (TelephonyManager) activity
				.getSystemService(Context.TELEPHONY_SERVICE);
		return manager.getDeviceId();
	}

	/**
	 * 获取IMSI
	 * 
	 * @Description:
	 * @param @param activity
	 * @param @return
	 * @return String
	 */
	public static String getIMSI(Activity activity) {
		TelephonyManager manager = (TelephonyManager) activity
				.getSystemService(Context.TELEPHONY_SERVICE);
		return manager.getSubscriberId();
	}

	/**
	 * 手机型号
	 * 
	 * @Description:
	 * @param @return
	 * @return String
	 */
	public static String getPhoneModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 系统版本号(数值)
	 * 
	 * @Description:
	 * @param @return
	 * @return int
	 */
	public static int getPhoneSdkVersionInt() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * 系统版本号(字符串)
	 * 
	 * @Description:
	 * @param @return
	 * @return String
	 */
	public static String getPhoneSDKVersionChar() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * @Description:APP版本号(字符串)
	 * @param @param activity
	 * @param @return
	 * @return String
	 */
	public static String getAppVersionChars(Activity activity) {
		PackageManager packageManager = activity.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(
					activity.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return packageInfo.versionName;
	}

	/**
	 * APP版本号(整型)
	 * 
	 * @Description:
	 * @param @param activity
	 * @param @return
	 * @return int
	 */
	public static int getAppVersionInt(Activity activity) {
		PackageManager packageManager = activity.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(
					activity.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packageInfo.versionCode;
	}

	/**
	 * 获取外网IP
	 * 
	 * @Description:
	 * @param @return
	 * @return String
	 */
	public static String GetNetIp() {
		URL infoUrl = null;
		InputStream inStream = null;
		String ipLine = "";
		HttpURLConnection httpConnection = null;
		try {
			infoUrl = new URL("http://ip168.com/");
			URLConnection connection = infoUrl.openConnection();
			httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inStream, "utf-8"));
				StringBuilder strber = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null)
					strber.append(line + "\n");

				Pattern pattern = Pattern
						.compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
				Matcher matcher = pattern.matcher(strber.toString());
				if (matcher.find()) {
					ipLine = matcher.group();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (httpConnection != null) {
					httpConnection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ipLine;
	}

	/**
	 * 获取局网IP
	 * 
	 * @Description:
	 * @param @return
	 * @return String
	 */
	public static String getLocalIpAddress() {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface ni = en.nextElement();
				Enumeration<InetAddress> enIp = ni.getInetAddresses();
				while (enIp.hasMoreElements()) {
					InetAddress inet = enIp.nextElement();
					if (!inet.isLoopbackAddress()
							&& (inet instanceof Inet4Address)) {
						return inet.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "0";
	}
}
