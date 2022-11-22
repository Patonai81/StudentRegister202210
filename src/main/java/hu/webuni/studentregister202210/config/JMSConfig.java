package hu.webuni.studentregister202210.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@Slf4j
@Configuration
public class JMSConfig {

    @Bean
    public MessageConverter configConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        messageConverter.setTargetType(MessageType.TEXT);
        messageConverter.setTypeIdPropertyName("_type");
        return messageConverter;
    }

    @Bean
    public ConnectionFactory paymentConnector(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:9999");
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory semesterConnector(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:9998");
        return connectionFactory;
    }

    @Bean(name = "semesterTemplate")
    public JmsTemplate jmsTemplate(ObjectMapper objectMapper){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(semesterConnector());
        template.setMessageConverter(configConverter(objectMapper));
        return template;
    }


    @Bean
    public JmsListenerContainerFactory<?> paymentFactory(ConnectionFactory paymentConnector, DefaultJmsListenerContainerFactoryConfigurer defaultJmsListenerContainerFactoryConfigurer) {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setSubscriptionDurable(Boolean.TRUE);
        defaultJmsListenerContainerFactory.setPubSubDomain(Boolean.TRUE);
        defaultJmsListenerContainerFactory.setClientId("payment-service");
        defaultJmsListenerContainerFactoryConfigurer.configure(defaultJmsListenerContainerFactory,paymentConnector);
        return defaultJmsListenerContainerFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> semesterFactory(ConnectionFactory semesterConnector, DefaultJmsListenerContainerFactoryConfigurer defaultJmsListenerContainerFactoryConfigurer) {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setSubscriptionDurable(Boolean.TRUE);
        defaultJmsListenerContainerFactory.setPubSubDomain(Boolean.TRUE);
        defaultJmsListenerContainerFactory.setClientId("semester-service");
        defaultJmsListenerContainerFactoryConfigurer.configure(defaultJmsListenerContainerFactory,semesterConnector);
        return defaultJmsListenerContainerFactory;
    }




}
