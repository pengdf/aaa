package com._52shabake.mg.domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ActiveCode {
	private Long id;
	private String activeCode;
	private CodeType codeType;
	private int chartState;
	private String phoneNumber;
	private int version;
}
