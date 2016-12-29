package com._52shabake.mg.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._52shabake.mg.domain.GiftPage;
import com._52shabake.mg.service.IGiftPageService;
import com._52shabake.mg.service.IVerifyCodeService;
import com._52shabake.mg.util.JSONResult;
import com.alibaba.fastjson.JSON;

@Controller
public class GiftPageController {
	@Autowired
	private IGiftPageService giftPageService;
	@Autowired
	private IVerifyCodeService verifyCodeService;
	
	@RequestMapping(value="/jsonDo",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String jsonDo(Long gameId,String callback){
		List<GiftPage> list = giftPageService.selectByGameId(gameId);
		System.out.println(list);
		String liststr = JSON.toJSONString(list);
		StringBuilder sb = new StringBuilder();
		sb.append(callback);
		sb.append("(");
		sb.append(liststr);
		sb.append(")");
		
		return sb.toString();
	}
	@RequestMapping(value="/jsonDo2",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String jsonDo2(String phoneNumber,String cookieNumber,String callback){
		
		JSONResult json = new JSONResult();
		try {
			this.verifyCodeService.sendVerifyCode(phoneNumber, cookieNumber);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		
		String jsonstr = JSON.toJSONString(json);
		
		StringBuilder sb = new StringBuilder();
		sb.append(callback);
		sb.append("(");
		sb.append(jsonstr);
		sb.append(")");
		return sb.toString();
	}
	
	@RequestMapping(value="/jsonDo3",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String jsonDo3(String phoneNumber,
			String verifyCode, String cookieNumber,String callback){
		List<Map<String, List>> listAll = this.verifyCodeService.bindPhone(
				phoneNumber, verifyCode, cookieNumber);
		String liststr = JSON.toJSONString(listAll);
		StringBuilder sb = new StringBuilder();
		sb.append(callback);
		sb.append("(");
		sb.append(liststr);
		sb.append(")");
		return sb.toString();
	}
	
	
}
