package com.twit.reqs;


import com.twit.entity.Twit.TwitCategory;
import com.twit.validators.StringEnumeration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewTwit {
	
	@NotBlank(message = "twit cannot be empty")
	private String twit;
	
	
	@StringEnumeration(enumClass = TwitCategory.class)
	@NotNull(message = "category must not be null")
	private String category;
}
