package hu.webuni.studentregister202210.ws;

import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

        messages.simpSubscribeDestMatchers("/topic/corseChat/{id}")
                .access("@chatSecurity.canSubscribe(authentication,#id)");
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
