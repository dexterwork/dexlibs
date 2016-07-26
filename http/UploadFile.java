package com.util.upload;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class UploadFile {

	private static final String BOUNDARY = UUID.randomUUID().toString();// 边界标识、随机生成、数据分割线
	private static final String PREFIX = "--"; // 前缀
	private static final String LINE_END = "\r\n"; // 一行的结束标识
	private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型

	private int readTimeOut = 10 * 1000; // 读取超时
	private int connectTimeout = 10 * 1000; // 超时时间
	/***
	 * 请求使用多长时间
	 */
	private static int requestTime = 0;

	private static final String CHARSET = "utf-8"; // 设置编码

	public void toUploadFile(String path, Map<String, String> params,
			Map<String, File> files, String fileKey) {
		String result = null;
		requestTime = 0;

		long startRequestTime = System.currentTimeMillis();
		long responseTime = 0;

		try {

			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(readTimeOut);
			conn.setConnectTimeout(connectTimeout);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			/**
			 * 当文件不为空，把文件包装并且上传
			 */
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

			StringBuffer sb = new StringBuffer();

			/***
			 * 以下是用于上传参数
			 */
			if (params != null) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"")
							.append(entry.getKey()).append("\"")
							.append(LINE_END).append(LINE_END);
					sb.append(entry.getValue()).append(LINE_END);
				}
			}

			// 写入参数信息
			dos.write(sb.toString().getBytes());

			/**
			 * 构造要上传文件的前段参数内容，和普通参数一样，在这些设置后就可以紧跟文件内容了。 这里重点注意：
			 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 filename是文件的名字，包含后缀名的
			 * 比如:abc.png
			 */

			if (files != null) {
				for (Map.Entry<String, File> entry : files.entrySet()) {
					sb = new StringBuffer();
					sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
					sb.append("Content-Disposition:form-data; name=\""
							+ fileKey + "\"; filename=\""
							+ entry.getValue().getName() + "\"" + LINE_END);
					sb.append("Content-Type:image/jpeg" + LINE_END); // 这里配置的Content-type很重要的
																		// ，用于服务器端辨别文件的类型的
					sb.append(LINE_END);

					// 写入文件前段参数信息
					dos.write(sb.toString().getBytes());

					// 写入文件数据
					System.out.println("--文件--:" + entry.getValue());
					InputStream is = new FileInputStream(entry.getValue());
					byte[] bytes = new byte[1024];
					int len = 0;
					int count = 0;
					while ((len = is.read(bytes)) != -1) {
						count += len;
						dos.write(bytes, 0, len);
					}

					is.close();

					// 这个很容易遗忘，刚开始一直不成功就是忘了写这个了
					dos.write(LINE_END.getBytes());

					System.out.println("--count--:" + count);
				}
			}

			// 请求结束符
			byte[] after = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
			// 写结束符，代表该HTTP组包完毕
			dos.write(after);

			// 发送出去
			dos.flush();

			// 关闭流
			dos.close();

			/**
			 * 获取响应码 200=成功 当响应成功，获取响应的流
			 */
			int res = conn.getResponseCode();
			responseTime = System.currentTimeMillis();
			requestTime = (int) ((responseTime - startRequestTime) / 1000);
			System.out.println("--请求时间--:" + requestTime);
			if (res == 200) {
				InputStream input = conn.getInputStream();
				StringBuffer sb1 = new StringBuffer();
				int ss;
				while ((ss = input.read()) != -1) {
					sb1.append((char) ss);
				}
				result = sb1.toString();

				System.out.println("--result--:" + result);
			} else {
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
			return;
		} catch (IOException e) {

			e.printStackTrace();
			return;
		}
	}
}
