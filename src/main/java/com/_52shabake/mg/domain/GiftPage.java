package com._52shabake.mg.domain;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
@Getter@Setter
public class GiftPage {
	
	private Long id;
	private int version;
	private String giftName;
	private BigDecimal giftTotal;
	private String giftDetails;
	private GiftType giftType;
	private Game gameType;
	private BigDecimal giftNum;
	private CodeType codeType;
}
