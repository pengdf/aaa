package com._52shabake.mg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._52shabake.mg.domain.Phone;
import com._52shabake.mg.mapper.PhoneMapper;
import com._52shabake.mg.service.IPhoneService;
@Service
public class PhoneServiceImpl implements IPhoneService{
	@Autowired
	private PhoneMapper phoneMapper;
	@Override
	public void update(Phone phone) {
		int ret = phoneMapper.updateByPhoneNum(phone);
		if (ret == 0) {
			throw new RuntimeException("系统繁忙,请稍后重试" );
		}
		
	}
	
}
