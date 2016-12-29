package com._52shabake.mg.service;

import java.util.List;
import java.util.Map;

/**
 * 验证码相关服务
 * 
 * @author Administrator
 * 
 */
public interface IVerifyCodeService {

	/**
	 * 发送验证码
	 * 
	 * @param phoneNumber
	 */
	void sendVerifyCode(String phoneNumber,String cookieNumber);

	/**
	 * 验证验证码
	 * 
	 * @param phoneNumber
	 * @param verifyCode
	 * @return
	 */
	boolean validate(String phoneNumber, String verifyCode,String cookieNumber);
	/**
	 * 点击验证手机和验证码
	 */
	List<Map<String,List>> bindPhone(String phoneNumber, String verifyCode,String cookieNumber);
}
