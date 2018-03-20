package es.unizar.tmdad.lab1.service;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.util.MimeTypeUtils;

import java.util.HashMap;
import java.util.Map;

public class SimpleStreamListener implements StreamListener {

    private SimpMessageSendingOperations ops;
    private String name;

    public SimpleStreamListener(SimpMessageSendingOperations ops, String name) {
        this.ops = ops;
        this.name = name;
    }

    @Override
    public void onTweet(Tweet tweet) {
        Map<String, Object> map = new HashMap<>();
        map.put(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        ops.convertAndSend("/queue/search/" + name, tweet, map);

    }

    @Override
    public void onDelete(StreamDeleteEvent deleteEvent) {

    }

    @Override
    public void onLimit(int numberOfLimitedTweets) {

    }

    @Override
    public void onWarning(StreamWarningEvent warningEvent) {

    }
}
