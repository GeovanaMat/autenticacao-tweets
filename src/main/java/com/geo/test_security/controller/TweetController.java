package com.geo.test_security.controller;

import com.geo.test_security.controller.dto.TweetDto;
import com.geo.test_security.entities.Tweet;
import com.geo.test_security.repository.TweetRepository;
import com.geo.test_security.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    public TweetController( TweetRepository tweetRepository, UserRepository userRepository){
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/tweets")
    @Transactional
    public ResponseEntity<String> createTweet(@RequestBody TweetDto tweetDto, JwtAuthenticationToken token){
        var user = userRepository.findById(Long.valueOf(token.getName()));
        var tweet = new Tweet();
        tweet.setContent(tweetDto.content());
        user.ifPresentOrElse(tweet::setUser, () -> {
            throw new EntityNotFoundException("User not found");
        });
        tweetRepository.save(tweet);

        return new ResponseEntity<>("Tweet Criado com sucesso", HttpStatus.OK);

    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<String> deleteTweet(@PathVariable("id") Long id,
                                              JwtAuthenticationToken token){
        var user = userRepository.findById(Long.valueOf(token.getName()));
        var tweet = tweetRepository.findById(id).orElseThrow(EntityNotFoundException::new);


        if(tweet.getUser().getId() != user.get().getId()){
           return new ResponseEntity<>("Usuário sem autorização", HttpStatus.FORBIDDEN);
        }
        tweetRepository.delete(tweet);
        return new ResponseEntity<>("Tweet deletado com sucesso", HttpStatus.OK);
    }



}
