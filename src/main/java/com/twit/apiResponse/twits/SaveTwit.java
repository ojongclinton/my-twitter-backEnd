package com.twit.apiResponse.twits;

import com.twit.apiResponse.ResultInterface;
import com.twit.entity.Twit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveTwit implements ResultInterface {
	private Twit savedTwit; 
}
