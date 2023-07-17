package com.twit.configuration;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.twit.oauth2.CustomOAuth2UserService;
import com.twit.oauth2.Oauth2SuccesHandler;
import com.twit.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;
	
	@Autowired
	private CustomOAuth2UserService oauth2UserService;
	
//	@Autowired
//	private  AuthenticationService authenticationService;
	
	@Autowired
	private Oauth2SuccesHandler oauth2SuccesHandler;
	
	@Autowired
	CustomauthenticationEntryPoint customauthenticationEntryPoint;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf
                        .disable()
        )
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/v1/auth/***")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2Login(auth2 ->
                        auth2.userInfoEndpoint(usr -> usr
                                        .userService(oauth2UserService)
                        )
                                //.authorizationEndpoint(end->end.baseUri("/oauth2/authorize"))
                                //.redirectionEndpoint(red->red.baseUri("/home"))
                                .successHandler(oauth2SuccesHandler)
                                .failureHandler(new AuthenticationFailureHandler() {

                                    @Override
                                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                        log.info("Something went wrong : {} ", exception.getMessage());
                                    }
                                })

                )
                .cors(withDefaults())
                .sessionManagement(sess -> sess
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(excp->excp.authenticationEntryPoint(customauthenticationEntryPoint));
		
		return http.build();
	}
	
	
}
