package com.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
@EnableBinding(RabbitMessageChannel.class)
public class Sender {
    @Resource
    private RabbitMessageChannel rabbitMessageChannel;

    private boolean sendMqMessage(MessageChannel messageChannel, String json) throws Exception {
        Message message = MessageBuilder.withPayload(json) //设置消息的延迟时间，首次发送，不设置延迟时间，直接发送
                .setHeader(AmqpHeaders.CHANNEL, 0).setHeader(AmqpHeaders.DELIVERY_TAG, 0).build();
        log.info("[ 已发送消息]" + message);
        return messageChannel.send(message);
    }

    public void sendMessageChannel(String s) {
        MessageChannel channel = rabbitMessageChannel.personalRegisterExpandOutput();
        try {
            sendMqMessage(channel, s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(RabbitMessageChannel.PERSONAL_INPUT)
    public void receivePersonalRegisterNotifyMessage(@Payload String message, @Header(AmqpHeaders.CHANNEL) Channel channel,
                                                     @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws Exception {
        try {
            log.info("接受消息: {}", message);
        } finally {
            channel.basicAck(tag, false);
        }
    }
}