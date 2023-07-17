package com.twit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twit.apiResponse.ApiResponseEntity;
import com.twit.entity.User;
import com.twit.repository.UserRepository;
import com.twit.reqs.Email;
import com.twit.reqs.NewTwit;
import com.twit.response.UnauthorizedAction;
import com.twit.service.TwitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "twits")
public class TwitController {
	@Autowired
	private TwitService twitService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public ApiResponseEntity RecentTwits() {
		return twitService.getAllTwits();
	}
	
	@PostMapping
	public ApiResponseEntity newTwit(@RequestBody @Valid NewTwit twit,@AuthenticationPrincipal String email) {
		User user = userRepository.findByEmail(email).orElseThrow();
		return twitService.newTwit(twit,user);
	}
	@GetMapping(path = "/{id}")
	public ApiResponseEntity singleTweet(@PathVariable int id) {
		return twitService.singleTwit(id);	
	}
	
	@DeleteMapping(path = "/{id}")
	public ApiResponseEntity deleteTwit(@PathVariable int id,@AuthenticationPrincipal String email) throws UnauthorizedAction {
		User user = userRepository.findByEmail(email).orElseThrow();
		return twitService.deleteTwit(id,user);	
	}
	@PatchMapping(path = "/{id}")
	public ApiResponseEntity editTwit(@PathVariable int id,@RequestBody @Valid NewTwit twit) {
		return twitService.editTwit(twit,id);
	}
	@GetMapping(path = "/user")
	public ApiResponseEntity userTwits(@RequestBody @Valid Email email) {
		return twitService.getTwitsByUser(email);	
	}
}
