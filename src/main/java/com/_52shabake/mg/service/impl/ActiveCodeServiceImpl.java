package com._52shabake.mg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._52shabake.mg.domain.ActiveCode;
import com._52shabake.mg.mapper.ActiveCodeMapper;
import com._52shabake.mg.service.IActiveCodeService;
@Service
public class ActiveCodeServiceImpl implements IActiveCodeService{
	@Autowired
	private ActiveCodeMapper activeCodeMapper;
	@Override
	public void update(ActiveCode activeCode) {
		int ret = this.activeCodeMapper.updateByPrimaryKey(activeCode);
		if (ret == 0) {
			throw new RuntimeException("系统繁忙,请稍后重试" );
		}
		
	}

}
