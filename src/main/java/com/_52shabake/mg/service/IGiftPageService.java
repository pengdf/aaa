package com._52shabake.mg.service;

import java.util.List;

import com._52shabake.mg.domain.GiftPage;

public interface IGiftPageService {
	void update(GiftPage giftPage);
	
	GiftPage selectByPrimaryKey(Long id);
	
	List<GiftPage> selectByGameId(Long gameId);
}
