package com.twit.apiResponse.twits;

import com.twit.apiResponse.ResultInterface;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeletedTwit implements ResultInterface{
	
	@Builder.Default
	private String reString= "The Twit has been succesfully deleted";
}
