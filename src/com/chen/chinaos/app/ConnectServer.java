package com.chen.chinaos.app;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class ConnectServer {

	private static String appCookie;
	private static String appUserAgent;
	public static final String UTF_8 = "UTF-8";
	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;

	public static void cleanCookie() {
		appCookie = "";
	}

	private static String getCookie(AppContext appContext) {
		if (appCookie == null || appCookie == "") {
			appCookie = appContext.getProperty("cookie");
		}
		return appCookie;
	}

	private static String getUserAgent(AppContext appContext) {
		if (appUserAgent == null || appUserAgent == "") {
			StringBuilder ua = new StringBuilder("OSChina.NET");
			ua.append('/' + appContext.getPackageInfo().versionName + '_'
					+ appContext.getPackageInfo().versionCode);// App�汾
			ua.append("/Android");// �ֻ�ϵͳƽ̨
			ua.append("/" + android.os.Build.VERSION.RELEASE);// �ֻ�ϵͳ�汾
			ua.append("/" + android.os.Build.MODEL); // �ֻ��ͺ�
			ua.append("/" + appContext.getAppId());// �ͻ���Ψһ��ʶ
			appUserAgent = ua.toString();
		}
		return appUserAgent;
	}

	// public String ConnectByPost(String url, String parameter) {
	// HttpClient client = (HttpClient) new DefaultHttpClient();
	// // ��������ʱʱ��
	// client.getParams().setParameter(
	// CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
	// client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
	// HttpPost post = new HttpPost(url);
	// StringEntity entity = null;
	// try {
	// entity = new StringEntity(parameter);
	// entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
	// "application/json"));
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	// post.setEntity(entity);
	// HttpResponse response;
	// try {
	// response = client.execute(post);
	// if (response.getStatusLine().getStatusCode() == 200) {
	// return EntityUtils.toString(response.getEntity(), "utf-8");
	// }
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	public String ConnectByGet(AppContext appContext, String url) {
		String cookie = getCookie(appContext);
		String userAgent = getUserAgent(appContext);

		HttpClient client = null;
		HttpMethod method = null;
		String response = "";
		int time = 0;
		try {
			client = getHttpClient();
			method = getHttpGet(url, cookie, userAgent);
			int statuCodes=client.executeMethod(method);
			if(statuCodes==HttpStatus.SC_OK){
				response=method.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			// �ͷ�����
			method.releaseConnection();
			client = null;
		}
		response = response.replaceAll("\\p{Cntrl}", "");

		// HttpClient client = new DefaultHttpClient();
		// // ��������ʱʱ��
		// client.getParams().setParameter(
		// CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		// 10000);
		// HttpGet get=new HttpGet(url);
		// try {
		// HttpResponse response=client.execute(get);
		// System.out.println(response.getStatusLine().getStatusCode());
		// if (response.getStatusLine().getStatusCode() == 200) {
		// System.out.println(EntityUtils.toString(response.getEntity()));
		// return EntityUtils.toString(response.getEntity(), "utf-8");
		// }
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return response;
	}

	/**
	 * ��������ͷ�ļ�
	 * @param url
	 * @param cookie
	 * @param userAgent
	 * @return
	 */
	private HttpMethod getHttpGet(String url, String cookie, String userAgent) {
		GetMethod httpGet = new GetMethod(url);
		// ���� ����ʱʱ��
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.setRequestHeader("Host", Constants.HOST);
		httpGet.setRequestHeader("Connection", "Keep-Alive");
		httpGet.setRequestHeader("Cookie", cookie);
		httpGet.setRequestHeader("User-Agent", userAgent);
		return httpGet;
	}

	/**
	 * ����HttpClient����
	 * @return
	 */
	private static HttpClient getHttpClient() {
		HttpClient httpClient = new HttpClient();
		// ���� HttpClient ���� Cookie,���������һ���Ĳ���
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		// ���� Ĭ�ϵĳ�ʱ���Դ������
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// ���� ���ӳ�ʱʱ��
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(TIMEOUT_CONNECTION);
		// ���� �����ݳ�ʱʱ��
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(TIMEOUT_SOCKET);
		// ���� �ַ���
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}

}
