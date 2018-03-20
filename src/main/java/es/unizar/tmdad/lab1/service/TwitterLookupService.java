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
    private String consumerKey = System.getenv("consumerKey");

    private String consumerSecret = System.getenv("consumerSecret");

    private String accessToken = System.getenv("accessToken");

    private String accessTokenSecret = System.getenv("accessTokenSecret");


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private ArrayList<String> queries = new ArrayList<>();
    List<StreamListener> twitterList = new ArrayList<StreamListener>();
    Stream s;


    public void search(String q) {

        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        if (queries.size() >= 10) {
            queries.remove(0);
            twitterList.remove(0);
        }
        twitterList.add(new SimpleStreamListener(messagingTemplate, q));
        s = twitter.streamingOperations().filter(q, twitterList);

    }

    public SearchResults emptyAnswer() {

        return new SearchResults(Collections.emptyList(), new SearchMetadata(0,0 ));
    }
}

