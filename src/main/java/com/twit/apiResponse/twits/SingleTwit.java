package com.twit.apiResponse.twits;

import com.twit.apiResponse.ResultInterface;
import com.twit.entity.Twit;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SingleTwit implements ResultInterface{
	private Twit twit;
}
