package hu.webuni.studentregister202210.ws;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Also note that when using Spring Security’s authorization for messages, at present you will need to ensure that the authentication
 * ChannelInterceptor config is ordered ahead of Spring Security’s. This is best done by declaring the custom interceptor in its own sub-class of AbstractWebSocketMessageBrokerConfigurer marked with @Order(Ordered.HIGHEST_PRECEDENCE + 99).
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/stomp");
        registry.addEndpoint("/api/stomp").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(socketInboundInterceptor());
    }
    @Bean
    public SocketInboundInterceptor socketInboundInterceptor() {
        return new SocketInboundInterceptor();
    }
}
