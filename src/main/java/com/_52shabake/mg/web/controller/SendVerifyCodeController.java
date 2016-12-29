package com._52shabake.mg.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._52shabake.mg.domain.GiftPage;
import com._52shabake.mg.service.IGiftPageService;
import com._52shabake.mg.service.IVerifyCodeService;
import com._52shabake.mg.util.JSONResult;

/**
 * 发送验证码控制器
 * 
 * @author Administrator
 * 
 */
@Controller
public class SendVerifyCodeController {

	@Autowired
	private IGiftPageService giftPageService;
	@Autowired
	private IVerifyCodeService verifyCodeService;
	


	@RequestMapping(value="/giftpage",produces="application/json;charset=UTF-8")
	@ResponseBody
	public List getPage(Long gameId,String callback) {
		//gameId=1L;
		List<GiftPage> list = giftPageService.selectByGameId(gameId);
		System.out.println(list);
		
		return list;
	}

	/**
	 * 发送验证码
	 * 
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping("sendVerifyCode")
	@ResponseBody
	public JSONResult sendVerifyCode(String phoneNumber, String cookieNumber) {
		JSONResult json = new JSONResult();
		try {
			this.verifyCodeService.sendVerifyCode(phoneNumber, cookieNumber);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 点击领取礼包 手机号,验证码核对,获取激活码
	 */
	@RequestMapping("check")
	@ResponseBody
	public List<Map<String, List>> bindPhone(String phoneNumber,
			String verifyCode, String cookieNumber) {

		List<Map<String, List>> listAll = this.verifyCodeService.bindPhone(
				phoneNumber, verifyCode, cookieNumber);
		return listAll;
	}
}
