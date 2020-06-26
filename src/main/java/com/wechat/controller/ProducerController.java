package com.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@RestController
@Slf4j
public class ProducerController {

    @Autowired
    @Qualifier("firstActivemqTemplate")
    private JmsMessagingTemplate firstActivemqTemplate;


    @Autowired
    @Qualifier("secondActivemqTemplate")
    private JmsMessagingTemplate secondActivemqTemplate;

    @Autowired
    private Queue firstQueue;
    @Autowired
    private Topic firstTopic;

    @Autowired
    private Queue secondQueue;
    @Autowired
    private Topic secondTopic;

    @PostMapping("/queue/test")
    public String sendQueue(@RequestBody String str) {
        this.sendFirstMessage (this.firstQueue, "first");
        this.sendSecondMessage (this.secondQueue, "ssssecond");
        return "success";
    }

    @PostMapping("/topic/test")
    public String sendTopic(@RequestBody String str) {
        this.sendFirstMessage (this.firstTopic, str);
        this.sendSecondMessage (this.secondTopic, str);
        return "success";
    }

    // 发送消息，destination是发送到的队列，message是待发送的消息
    private void sendFirstMessage(Destination destination, final String message) {
        firstActivemqTemplate.convertAndSend (destination, message);
        log.info ("firstActivemq----",message);
    }


    // 发送消息，destination是发送到的队列，message是待发送的消息
    private void sendSecondMessage(Destination destination, final String message) {
        secondActivemqTemplate.convertAndSend (destination, message);
        log.info ("secondActivemq----",message);

    }
}
