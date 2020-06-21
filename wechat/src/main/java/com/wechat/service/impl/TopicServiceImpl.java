package com.wechat.service.impl;

import com.wechat.service.TopicService;
import org.apache.activemq.broker.region.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;

/**
 * @Author: yangxixi
 * @Date: 2020/6/21 12:50
 */
public class TopicServiceImpl implements TopicService {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

}
