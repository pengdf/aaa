package com._52shabake.mg.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Phone {
	private Long id;
	private int version;
	private String phoneNumber;
	private int activeState;
	private String cookieNumber;
	private String verifyCode;
	private Date sendTime;
}
