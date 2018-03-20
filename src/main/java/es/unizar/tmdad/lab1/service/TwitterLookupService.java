package es.unizar.tmdad.lab1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TwitterLookupService {
    @Value("${twitter.consumerKey}")
    private String consumerKey;

    @Value("${twitter.consumerSecret}")
    private String consumerSecret;

    @Value("${twitter.accessToken}")
    private String accessToken;

    @Value("${twitter.accessTokenSecret}")
    private String accessTokenSecret;


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void search(String q) {

        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        List<StreamListener> twitterList = new ArrayList<StreamListener>();
        twitterList.add(new SimpleStreamListener(messagingTemplate, q));
        Stream s = twitter.streamingOperations().filter(q, twitterList);

    }

    public SearchResults emptyAnswer() {

        return new SearchResults(Collections.emptyList(), new SearchMetadata(0,0 ));
    }
}

