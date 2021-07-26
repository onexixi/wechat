package com.wechat.controller;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface RabbitMessageChannel {
    String PERSONAL_OUTPUT = "personalRegisterExpand_output";
    String PERSONAL_INPUT = "personalRegisterInput";

    @Output(RabbitMessageChannel.PERSONAL_OUTPUT)
    MessageChannel personalRegisterExpandOutput();

    @Input(RabbitMessageChannel.PERSONAL_INPUT)
    SubscribableChannel rechargeinput();
}

