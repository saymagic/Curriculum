package com.caoyanming.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import android.util.Log;

/**
 * Http请求的工具类
 * 
 * @author saymagic
 *
 */

public class HttpUtils
{

	private static final int TIMEOUT_IN_MILLIONS = 5000;
	private static final int TIME_OUT = 10*1000;
	private static final String CHARSET = "utf-8";
	private static final String TAG = "UPLOADUTIL";
	
	public interface CallBack
	{
		void onRequestComplete(String result);
	}


	public static String uploadFile(File file,String requestUrl){
		String result = null; 
		String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成 
		String PREFIX = "--" ;
		String LINE_END = "\r\n";  
		String CONTENT_TYPE = "multipart/form-data";   //内容类型 
		try{
			URL url = new URL(requestUrl); 
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			conn.setReadTimeout(TIME_OUT); 
			conn.setConnectTimeout(TIME_OUT); 
			conn.setDoInput(true);  //允许输入流 
			conn.setDoOutput(true); //允许输出流 
			conn.setUseCaches(false);  //不允许使用缓存 
			conn.setRequestMethod("POST");  //请求方式 
			conn.setRequestProperty("Charset", CHARSET);  //设置编码 
			conn.setRequestProperty("connection", "keep-alive");    
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);


			if(file!=null) 
			{ 
				/**
				 * 当文件不为空，把文件包装并且上传
				 */ 
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream()); 
				StringBuffer sb = new StringBuffer(); 
				sb.append(PREFIX); 
				sb.append(BOUNDARY); 
				sb.append(LINE_END); 
				/**
				 * 这里重点注意：
				 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名的   比如:abc.png  
				 */ 

				sb.append("Content-Disposition: form-data; name=\"uploadcrash\"; filename=\""+file.getName()+"\""+LINE_END);  
				sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END); 
				sb.append(LINE_END); 
				dos.write(sb.toString().getBytes()); 
				InputStream is = new FileInputStream(file); 
				byte[] bytes = new byte[1024]; 
				int len = 0; 
				while((len=is.read(bytes))!=-1) 
				{ 
					dos.write(bytes, 0, len); 
				} 
				is.close(); 
				dos.write(LINE_END.getBytes()); 
				byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes(); 
				dos.write(end_data); 
				dos.flush(); 
				/**
				 * 获取响应码  200=成功
				 * 当响应成功，获取响应的流  
				 */ 
				int res = conn.getResponseCode();   
				Log.e(TAG, "response code:"+res); 
				//              if(res==200) 
				//              { 
				Log.e(TAG, "request success"); 
				InputStream input =  conn.getInputStream(); 
				StringBuffer sb1= new StringBuffer(); 
				int ss ; 
				while((ss=input.read())!=-1) 
				{ 
					sb1.append((char)ss); 
				} 
				result = sb1.toString(); 
				Log.e(TAG, "result : "+ result); 
				//              } 
				//              else{ 
				//                  Log.e(TAG, "request error"); 
				//              } 
			} 
		}catch (MalformedURLException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		}

		return result;
	}

	/**
	 * 异步的Get请求
	 * 
	 * @param urlStr
	 * @param callBack
	 */
	public static void doGetAsyn(final String urlStr, final CallBack callBack)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					String result = doGet(urlStr);
					if (callBack != null)
					{
						callBack.onRequestComplete(result);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}

			};
		}.start();
	}

	/**
	 * 异步的Post请求
	 * @param urlStr
	 * @param params
	 * @param callBack
	 * @throws Exception
	 */
	public static void doPostAsyn(final String urlStr, final String params,
			final CallBack callBack) throws Exception
			{
		new Thread()
		{
			public void run()
			{
				try
				{
					String result = doPost(urlStr, params);
					if (callBack != null)
					{
						callBack.onRequestComplete(result);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}

			};
		}.start();

			}

	/**
	 * Get请求，获得返回数据
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String urlStr) 
	{
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try
		{
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200)
			{
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1)
				{
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else
			{
				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (is != null)
					is.close();
			} catch (IOException e)
			{
			}
			try
			{
				if (baos != null)
					baos.close();
			} catch (IOException e)
			{
			}
			conn.disconnect();
		}

		return null ;

	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String doPost(String url, String param) 
	{
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try
		{
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

			if (param != null && !param.trim().equals(""))
			{
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				result += line;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return result;
	}
}
