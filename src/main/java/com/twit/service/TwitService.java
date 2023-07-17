package com.twit.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.twit.apiResponse.ApiResponseEntity;
import com.twit.apiResponse.Code;
import com.twit.apiResponse.twits.AllTwitsRes;
import com.twit.apiResponse.twits.DeletedTwit;
import com.twit.apiResponse.twits.SaveTwit;
import com.twit.apiResponse.twits.SingleTwit;
import com.twit.entity.Twit;
import com.twit.entity.User;
import com.twit.entity.Twit.TwitCategory;
import com.twit.repository.TwitRepository;
import com.twit.repository.UserRepository;
import com.twit.reqs.Email;
import com.twit.reqs.NewTwit;
import com.twit.response.UnauthorizedAction;

@Service
public class TwitService {
	@Autowired
	private TwitRepository twitRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public ApiResponseEntity getAllTwits() {
		List<Twit> themTwits = twitRepository.findAll();
		return ApiResponseEntity.builder()
								.code(Code.SUCCESS)
								.status(HttpStatus.OK)
								.result(AllTwitsRes.builder().allTwits(themTwits).size(themTwits.size()).build())
								.build();
	}
	
	public ApiResponseEntity newTwit(NewTwit theTwit, User user) {
		var twit = Twit.builder()
								.twit(theTwit.getTwit())
								.category(TwitCategory.valueOf(theTwit.getCategory()))
								.user(user)
								.build();
		user.addTwit(twit);
		twitRepository.save(twit);
		
		return ApiResponseEntity.builder()
								.code(Code.SUCCESS)
								.status(HttpStatus.OK)
								.result(SaveTwit.builder().savedTwit(twit).build())
								.build();
	}
	public ApiResponseEntity singleTwit(int twitId) {
		var twit = twitRepository.findById(twitId).orElseThrow();
		
		return ApiResponseEntity.builder()
								.code(Code.SUCCESS)
								.status(HttpStatus.OK)
								.result(SingleTwit.builder().twit(twit).build())
								.build();
	}
	
	public ApiResponseEntity deleteTwit(int twitId,User user) throws UnauthorizedAction{
		var twit = twitRepository.findById(twitId).orElseThrow();
		if(twit.getUser().getEmail() != user.getEmail()) {
			throw new UnauthorizedAction("Not Alloewd");
		}
		user.getTwits().remove(twit);
		twitRepository.delete(twit);
		
		return ApiResponseEntity.builder()
								.code(Code.SUCCESS)
								.status(HttpStatus.OK)
								.result(DeletedTwit.builder().build())
								.build();
	}
	
	public ApiResponseEntity editTwit(NewTwit theTwit, int id) {
		var existingTwit = twitRepository.findById(id).orElseThrow();
		
		existingTwit.setCategory(TwitCategory.valueOf(theTwit.getCategory()));
		existingTwit.setTwit(theTwit.getTwit());
		existingTwit.setEditedAt(new Date());
		twitRepository.save(existingTwit);
		
		return ApiResponseEntity.builder()
								.code(Code.SUCCESS)
								.status(HttpStatus.OK)
								.result(SingleTwit.builder().twit(existingTwit).build())
								.build();
	}
	
	public ApiResponseEntity getTwitsByUser(Email email) {
		User user = userRepository.findByEmail(email.getEmail()).orElseThrow();	
		List<Twit> themTwits = user.getTwits();
		return ApiResponseEntity.builder()
								.code(Code.SUCCESS)
								.status(HttpStatus.OK)
								.result(AllTwitsRes.builder().allTwits(themTwits).size(themTwits.size()).build())
								.build();
	}
	
	
}
