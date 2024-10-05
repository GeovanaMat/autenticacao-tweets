package com.geo.test_security.repository;

import com.geo.test_security.entities.Tweet;
import com.geo.test_security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet,Long> {

}
