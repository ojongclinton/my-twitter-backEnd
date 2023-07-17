package com.twit.apiResponse.twits;

import java.util.List;

import com.twit.apiResponse.ResultInterface;
import com.twit.entity.Twit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllTwitsRes implements ResultInterface{
	private List<Twit> allTwits;
	private int size;
}	
