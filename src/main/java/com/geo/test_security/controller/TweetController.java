package com.geo.test_security.controller;

import com.geo.test_security.controller.dto.TweetDto;
import com.geo.test_security.entities.Tweet;
import com.geo.test_security.repository.TweetRepository;
import com.geo.test_security.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    public TweetController( TweetRepository tweetRepository, UserRepository userRepository){
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/tweets")
    public ResponseEntity<String> createTweet(@RequestBody TweetDto tweetDto, JwtAuthenticationToken token){
        var user = userRepository.findById(Long.valueOf(token.getName()));
        var tweet = new Tweet();


    }



}
