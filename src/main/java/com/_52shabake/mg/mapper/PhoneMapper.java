package com._52shabake.mg.mapper;

import com._52shabake.mg.domain.Phone;

public interface PhoneMapper {
	int insert(Phone phone);

	Phone selectByPhoneNum(String phoneNumber);

	int updateByPhoneNum(Phone phone);
}
