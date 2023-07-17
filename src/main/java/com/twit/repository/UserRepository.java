package com.twit.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.twit.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);

	Optional<User> findByOauthId(Long oauthId);
	
}
