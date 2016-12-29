package com._52shabake.mg.mapper;

import java.util.List;

import com._52shabake.mg.domain.GiftPage;

public interface GiftPageMapper {
	
	int updateByPrimaryKey(GiftPage giftPage); 

	GiftPage selectByPrimaryKey(Long id);
    
    List<GiftPage> selectByGameId(Long gameId);

}
