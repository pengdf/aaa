package com._52shabake.mg.mapper;

import java.util.List;

import com._52shabake.mg.domain.ActiveCode;

public interface ActiveCodeMapper {
	int insert(ActiveCode activecode);
	
	int updateByPrimaryKey(ActiveCode activecode);

	List<ActiveCode> selectRandByCodeType();
	
	List<ActiveCode> selectByPhoneNum(String phoneNumber);
	
	int selectNumByCodeType(Long codeTypeId);
	
	int selectNumByCodeTypeAll(Long codeTypeId);

}
