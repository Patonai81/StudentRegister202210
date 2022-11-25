package hu.webuni.studentregister202210.config;


import hu.webuni.studentregister202210.soap.StudentCalendarWS;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.xml.ws.Endpoint;

@EnableAsync
@Configuration
@RequiredArgsConstructor
public class XMLWSConfig {

    private final Bus bus;
    private final StudentCalendarWS studentCalendarWS;

    @Bean
    public Endpoint publishEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, studentCalendarWS);
        endpoint.publish("/calendar");
        return endpoint;
    }
}
