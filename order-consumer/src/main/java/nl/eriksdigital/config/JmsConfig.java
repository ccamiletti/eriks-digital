package nl.eriksdigital.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("order_queue");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setTrustAllPackages(Boolean.TRUE);
        factory.setBrokerURL(brokerUrl);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return  new JmsTemplate(activeMQConnectionFactory());
    }

}

