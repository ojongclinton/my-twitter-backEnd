package com.twit.oauth2;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.twit.configuration.JwtService;
import com.twit.entity.User;
import com.twit.entity.User.Provider;
import com.twit.entity.User.Role;
import com.twit.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Component
public class Oauth2SuccesHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private final JwtService jwtService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);
		
		if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
		//super.onAuthenticationSuccess(request, response, authentication);
	}
	
	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,Authentication auth) {
		// TODO Auto-generated method stub
		//return super.determineTargetUrl(request, response);
		CustomOAuth2User oauth2User = (CustomOAuth2User) auth.getPrincipal();
		var user = User.builder()
						.id(Long.parseLong(oauth2User.getAttribute("id")))
						.oauthId(Long.parseLong(oauth2User.getAttribute("id")))
						.email(oauth2User.getEmail())
						.firstName(getFirstName(oauth2User.getName()))
						.lastName(getRemainingNames(oauth2User.getName()))
						.password(null)
						.role(Role.ROLE_USER)
						.provider(Provider.FACEBOOK)
						.build();
		
		if(userRepository.findByOauthId(user.getOauthId()).isPresent()) {
			String token = jwtService.generateToken(user);
			return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect")
	                .queryParam("token", token)
	                .build().toUriString();
		}
		userRepository.save(user);
		String token = jwtService.generateToken(user);
		return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect")
                .queryParam("token", token)
                .build().toUriString();
//		log.info("THis is the auth data : {} ", auth);
//		return null;
	}
	
	public static String getFirstName(String phrase) {
	    String[] words = phrase.split("\\s+");
	    return words[0];
	}
	public static String getRemainingNames(String phrase) {
	    String[] words = phrase.split("\\s+");
	    if (words.length == 1) {
	        return ""; // no remaining words
	    } else {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 1; i < words.length; i++) {
	            sb.append(words[i]);
	            if (i < words.length - 1) {
	                sb.append(" "); // add space between words
	            }
	        }
	        return sb.toString();
	    }
	}
	
}
