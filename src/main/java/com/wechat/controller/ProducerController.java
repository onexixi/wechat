package com.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@RestController
@Slf4j
public class ProducerController {

    private final JmsMessagingTemplate firstActivemqTemplate;


    private final JmsMessagingTemplate secondActivemqTemplate;

    private final Queue firstQueue;
    private final Topic firstTopic;

    private final Queue secondQueue;
    private final Topic secondTopic;

    @Autowired
    private  Sender sender;


    public ProducerController(@Qualifier("secondActivemqTemplate") JmsMessagingTemplate secondActivemqTemplate, @Qualifier("firstActivemqTemplate") JmsMessagingTemplate firstActivemqTemplate, Queue firstQueue, Topic firstTopic, Queue secondQueue, Topic secondTopic) {
        this.secondActivemqTemplate = secondActivemqTemplate;
        this.firstActivemqTemplate = firstActivemqTemplate;
        this.firstQueue = firstQueue;
        this.firstTopic = firstTopic;
        this.secondQueue = secondQueue;
        this.secondTopic = secondTopic;
    }

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

    @GetMapping("/topic/sendMessageChannel")
    public String sendMessageChannel(@RequestParam String str) {
        sender.sendMessageChannel(str);
        return"success";
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
