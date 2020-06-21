package com.wechat.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @author yangxixi
 */
@Configuration
public class AcctiveMqConfig {


    @Value("${activemq.first.queue-name}")
    private String firstQueueName;

    @Value("${activemq.first.topic-name}")
    private String firstTopicName;

    @Bean(name = "firstQueue")
    public Queue getFirstQueue() {
        return new ActiveMQQueue (firstQueueName);
    }

    @Bean(name = "firstTopic")
    public Topic getFirstTopic() {
        return new ActiveMQTopic (firstTopicName);
    }



    @Value("${activemq.second.queue-name}")
    private String secondQueueName;

    @Value("${activemq.first.topic-name}")
    private String secondTopicName;

    @Bean(name = "secondQueue")
    public Queue getSecondQueue() {
        return new ActiveMQQueue (secondQueueName);
    }

    @Bean(name = "secondTopic")
    public Topic getSecondTopic() {
        return new ActiveMQTopic (secondTopicName);
    }



    /**
     * Activemq1
     * @param brokerUrl
     * @param username
     * @param password
     * @return
     */
    @Bean(name = "firstConnectionFactory")
    @Primary
    public ActiveMQConnectionFactory firstConnectionFactory(
            @Value("${activemq.first.broker-url}") String brokerUrl,
            @Value("${activemq.first.user}") String username,
            @Value("${activemq.first.close-timeout}") String closeTime,
            @Value("${activemq.first.password}") String password) {
        ActiveMQConnectionFactory firstConnectionFactory = new ActiveMQConnectionFactory();
        firstConnectionFactory.setBrokerURL(brokerUrl);
        firstConnectionFactory.setUserName(username);
        firstConnectionFactory.setPassword(password);
        firstConnectionFactory.setCloseTimeout (Integer.parseInt (closeTime));
        return firstConnectionFactory;
    }
    @Bean(name = "firstActivemqTemplate")
    @Primary
    public JmsMessagingTemplate firstActivemqTemplate(
            @Qualifier("firstConnectionFactory") ActiveMQConnectionFactory connectionFactory) {
        JmsMessagingTemplate template = new JmsMessagingTemplate(connectionFactory);
        return template;
    }
    @Bean(name = "firstFactory")
    public JmsListenerContainerFactory firstFactory(
            @Qualifier("firstConnectionFactory") ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory ();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }



    /**
     * Activemq2
     * @param brokerUrl
     * @param username
     * @param password
     * @return
     */
    @Bean(name = "secondConnectionFactory")
    public ActiveMQConnectionFactory secondConnectionFactory(
            @Value("${activemq.second.broker-url}") String brokerUrl,
            @Value("${activemq.second.user}") String username,
            @Value("${activemq.second.password}") String password,
            @Value("${activemq.second.close-timeout}") String closeTime){
        ActiveMQConnectionFactory secondConnectionFactory = new ActiveMQConnectionFactory();
        secondConnectionFactory.setBrokerURL(brokerUrl);
        secondConnectionFactory.setUserName(username);
        secondConnectionFactory.setPassword(password);
        secondConnectionFactory.setCloseTimeout (Integer.parseInt (closeTime));
        return secondConnectionFactory;
    }
    @Bean(name = "secondActivemqTemplate")
    public JmsMessagingTemplate secondActivemqTemplate(
            @Qualifier("secondConnectionFactory") ActiveMQConnectionFactory connectionFactory) {
        JmsMessagingTemplate template = new JmsMessagingTemplate(connectionFactory);
        return template;
    }
    @Bean(name = "secondFactory")
    public JmsListenerContainerFactory secondFactory(
            @Qualifier("secondConnectionFactory") ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }





    @Bean("firstQueueListener")
    public JmsListenerContainerFactory<?> firstQueueJmsListenerContainerFactory(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory ();
        factory.setConnectionFactory (connectionFactory);
        factory.setPubSubDomain (false);
        return factory;
    }

    //在Topic模式中，对消息的监听需要对containerFactory进行配置
    @Bean("firstTopicListener")
    public JmsListenerContainerFactory<?> firstTopicJmsListenerContainerFactory(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory ();
        factory.setConnectionFactory (connectionFactory);
        factory.setPubSubDomain (true);
        return factory;
    }


    @Bean("secondQueueListener")
    public JmsListenerContainerFactory<?> secondQueueJmsListenerContainerFactory(@Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory ();
        factory.setConnectionFactory (connectionFactory);
        factory.setPubSubDomain (false);
        return factory;
    }

    //在Topic模式中，对消息的监听需要对containerFactory进行配置
    @Bean("secondTopicListener")
    public JmsListenerContainerFactory<?> secondTopicJmsListenerContainerFactory(@Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory ();
        factory.setConnectionFactory (connectionFactory);
        factory.setPubSubDomain (true);
        return factory;
    }

}