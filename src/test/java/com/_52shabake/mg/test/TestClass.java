package com._52shabake.mg.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StreamUtils;

import com._52shabake.mg.domain.ActiveCode;
import com._52shabake.mg.domain.GiftPage;
import com._52shabake.mg.domain.Phone;
import com._52shabake.mg.mapper.ActiveCodeMapper;
import com._52shabake.mg.mapper.GiftPageMapper;
import com._52shabake.mg.service.IGiftPageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestClass {
	@Autowired
	private IGiftPageService giftPageService;
	@Autowired
	private ActiveCodeMapper activeCodeMapper;
	@Autowired
	private GiftPageMapper giftPageMapper;
	
	@Test
	public  void testGiftPage() throws Exception {
		try {
			JSONObject obj2 = new JSONObject();
			JSONArray locationArr2 = new JSONArray();
			locationArr2.add("12345");
			locationArr2.add("此验证码用于礼包领取验证");
			obj2.put("type", 12);
			obj2.put("msg_slices", locationArr2);
			JSONObject obj = new JSONObject();
			JSONArray locationArr = new JSONArray();
			locationArr.add("13657634492");
			obj.put("mobiles", locationArr);
			obj.put("app", 12);
			obj.put("appsecret", "9ffde6da43a1d920776eb387f68b9ad6");
			obj.put("parameter", obj2);
			System.out.println(obj);
			// 创建url资源
			URL url = new URL("http://api.52tt.com/notification/sms");
			// 建立http连接
			HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();
			// 设置允许输出
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 设置不用缓存
			conn.setUseCaches(false);
			// 设置传递方式
			conn.setRequestMethod("POST");
			// 设置维持长连接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置文件字符集:
			conn.setRequestProperty("Charset", "UTF-8");
			// 转换为字节数组
			byte[] data = (obj.toString()).getBytes();
			// 设置文件长度
			conn.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			// 设置文件类型:
			conn.setRequestProperty("contentType", "application/json");
			// 开始连接请求
			conn.connect();
			OutputStream out = conn.getOutputStream();
			// 写入请求的字符串
			out.write((obj.toString()).getBytes());
			out.flush();
			out.close();
			System.out.println(conn.getResponseCode());
			String response = StreamUtils.copyToString(
					conn.getInputStream(), Charset.forName("UTF-8"));
			System.out.println("response:"+response);
			if (conn.getResponseCode() == 200) {
				 System.out.println("连接成功:"+conn.getResponseCode());
	             
				
			} else {
				throw new RuntimeException();
			}
		} catch (Exception e) {
			throw new RuntimeException("发送短信失败");
		}
	}

}
