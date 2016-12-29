package com._52shabake.mg.service.impl;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com._52shabake.mg.domain.ActiveCode;
import com._52shabake.mg.domain.GiftPage;
import com._52shabake.mg.domain.Phone;
import com._52shabake.mg.mapper.ActiveCodeMapper;
import com._52shabake.mg.mapper.GiftPageMapper;
import com._52shabake.mg.mapper.PhoneMapper;
import com._52shabake.mg.service.IActiveCodeService;
import com._52shabake.mg.service.IGiftPageService;
import com._52shabake.mg.service.IPhoneService;
import com._52shabake.mg.service.IVerifyCodeService;
import com._52shabake.mg.util.DateUtil;
import com._52shabake.mg.util.JSONResult;
import com._52shabake.mg.util.VerifyCodeUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {

	@Autowired
	private PhoneMapper phoneMapper;
	@Autowired
	private ActiveCodeMapper activeCodeMapper;
	@Autowired
	private GiftPageMapper giftPageMapper;
	@Autowired
	private IGiftPageService giftPageService;
	@Autowired
	private IPhoneService phoneService;
	@Autowired
	private IActiveCodeService activeCodeService;

	public void sendVerifyCode(String phoneNumber, String cookieNumber) {
		// 查询是否有该手机号存在,如果存在,查询出上次发送手机验证码时间
		Phone content = phoneMapper.selectByPhoneNum(phoneNumber);
		if (content == null // 没有发过
				|| (content != null && DateUtil.getBetweenSecond(
						content.getSendTime(),// 如果发送过，要判断当前时间和上次发送时间的间隔时间
						new Date()) >= 4)) {
			// 生成一个验证码
			String code = VerifyCodeUtil.getCode();
			System.out.println(code);
			System.out.println(cookieNumber);

			// 发送短信，短信发送成功了
			try {
				JSONObject obj2 = new JSONObject();
				JSONArray locationArr2 = new JSONArray();
				locationArr2.add(code);
				locationArr2.add("此验证码用于礼包领取验证");
				obj2.put("type", 12);
				obj2.put("msg_slices", locationArr2);
				JSONObject obj = new JSONObject();
				JSONArray locationArr = new JSONArray();
				locationArr.add(phoneNumber);
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
					System.out.println("连接成功:" + conn.getResponseCode());
					// 查询表中是否存在该手机对象
					// 如果存在,更新验证码
					if (content != null) {
						content.setPhoneNumber(phoneNumber);
						content.setCookieNumber(cookieNumber);
						content.setSendTime(new Date());
						content.setVerifyCode(code);
						System.out.println("1");
						try {
							phoneService.update(content);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						System.out.println("2");
					} else {
						// 如果不存在,将对象保存进表里面,设置为未激活状态
						Phone phone = new Phone();
						phone.setPhoneNumber(phoneNumber);
						phone.setCookieNumber(cookieNumber);
						phone.setSendTime(new Date());
						phone.setVerifyCode(code);
						phone.setActiveState(0);
						phoneMapper.insert(phone);
						System.out.println("3");
					}

				} else {
					throw new RuntimeException();
				}

			} catch (Exception e) {
				throw new RuntimeException("发送短信失败");
			}
		} else {
			throw new RuntimeException("发送过于频繁!");
		}
	}

	public List<Map<String, List>> bindPhone(String phoneNumber,
			String verifyCode, String cookieNumber) {
		// 1,做验证码的校验
		if (this.validate(phoneNumber, verifyCode, cookieNumber)) {
			List list = new ArrayList<>();
			Map map = new HashMap<>();
			JSONResult json = new JSONResult();
			Long gameId = 1L;
			// 查询该手机对象状态码
			Phone phone = phoneMapper.selectByPhoneNum(phoneNumber);
			// 如果是激活状态,根据手机号码从激活码对象查询出该手机不同礼包的激活码并显示出来
			if (phone.getPhoneNumber() != null && phone.getActiveState() == 1) {
				
				List<GiftPage> fistPages = giftPageService
						.selectByGameId(gameId);
				map.put("fistPages", fistPages);
				
				List<ActiveCode> activeCodeList = activeCodeMapper
						.selectByPhoneNum(phoneNumber);
				map.put("activeCodeList", activeCodeList);
				
				List<GiftPage> pageList = giftPageMapper.selectByGameId(gameId);
				for (GiftPage giftPage : pageList) {
					int giftNums = activeCodeMapper
							.selectNumByCodeType(giftPage.getCodeType().getId());
					int giftTotals = activeCodeMapper
							.selectNumByCodeTypeAll(giftPage.getCodeType()
									.getId());
					BigDecimal giftNum = new BigDecimal(giftNums);
					BigDecimal giftTotal = new BigDecimal(giftTotals);
					giftPage.setGiftTotal(giftTotal);
					giftPage.setGiftNum(giftNum);
					giftPageService.update(giftPage);
				}
				map.put("pageList", pageList);
				json.setSuccess(true);
				json.setMsg(null);
				map.put("json", json);
				list.add(map);
				return list;
			} else {
				
				List<GiftPage> fistPages = giftPageService
						.selectByGameId(gameId);
				map.put("fistPages", fistPages);
				System.out.println("5");
				// 如果是未激活状态,从激活码表中随机不重复查询出不同礼包的验证码对象,
				List<ActiveCode> activeCodeList = activeCodeMapper
						.selectRandByCodeType();
				// 将手机号码更新到查询出来的激活码对象中,将激活码对象状态变为已查询状态(状态码为1)
				System.out.println("activeCodeList:" + activeCodeList);
				for (ActiveCode activeCode : activeCodeList) {
					System.out.println("activeCode:"
							+ activeCode.getActiveCode());
					activeCode.setPhoneNumber(phoneNumber);
					activeCode.setChartState(1);
					activeCodeService.update(activeCode);
				}
				// 将查询出来的激活码对象集合放到Map中
				map.put("activeCodeList", activeCodeList);
				// 将手机对象状态改为激活状态
				phone.setSendTime(new Date());
				phone.setActiveState(1);
				phoneService.update(phone);
				System.out.println("6");
				// 获取礼包对象,将礼包数量减去1

				List<GiftPage> pageList = giftPageMapper.selectByGameId(gameId);

				for (GiftPage giftPage : pageList) {
					int giftNums = activeCodeMapper
							.selectNumByCodeType(giftPage.getCodeType().getId());
					int giftTotals = activeCodeMapper
							.selectNumByCodeTypeAll(giftPage.getCodeType()
									.getId());
					BigDecimal giftNum = new BigDecimal(giftNums);
					BigDecimal giftTotal = new BigDecimal(giftTotals);
					giftPage.setGiftTotal(giftTotal);
					giftPage.setGiftNum(giftNum);
					System.out.println("7");
					giftPageService.update(giftPage);
					
					System.out.println("8");
				}
				System.out.println("9");

				System.out.println("11");
				// 查询出所有礼包信息,查询出激活码对象信息 放到map中
				map.put("pageList", pageList);
				List nums = new ArrayList<>();
				for (GiftPage giftPage : fistPages) {
					int giftNum = giftPage.getGiftNum().intValue();
					if (giftNum != 0) {
						System.out.println(giftNum);
						nums.add(giftNum);
					}
				}
				
				System.out.println(nums);
				if (nums.isEmpty()) {
					System.out.println("10");
					json.setSuccess(false);
					json.setMsg("礼包领取完毕,下次请赶早");
					map.put("json", json);
				} else {
					json.setSuccess(true);
					json.setMsg(null);
					map.put("json", json);

				}
				list.add(map);
				return list;
			}
		} else {
			throw new RuntimeException("验证码校验失败");
		}
	}

	public boolean validate(String phoneNumber, String verifyCode,
			String cookieNumber) {
		Phone content = phoneMapper.selectByPhoneNum(phoneNumber);
		return content != null// 发了验证码
				&& content.getPhoneNumber().equals(phoneNumber)// 两次电话相同
				&& content.getVerifyCode().equalsIgnoreCase(verifyCode)// 两次验证码相同
				&& content.getCookieNumber().equals(cookieNumber)// 两次cookie值相同
		 /*&& DateUtil.getBetweenSecond(content.getSendTime(), new Date()) <= 90*/ ;// 短信在有效期之内
	}
}
