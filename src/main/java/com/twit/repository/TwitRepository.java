package com.twit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.twit.entity.Twit;

public interface TwitRepository extends JpaRepository<Twit, Integer>{

}
