package nl.eriksdigital.listener;

import nl.eriksdigital.model.OrderWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class Consumer {

    Logger logger = LoggerFactory.getLogger(Consumer.class);

    @JmsListener(destination = "order_queue")
    public void consume(OrderWrapper message) {
        logger.info(String.format("The order object with id %d was updated", message.getOrderNew().getId()));
        logger.info(String.format("Old version order: %s", message.getOrderOld().toString()));
        logger.info(String.format("New version order: %s", message.getOrderNew().toString()));
    }
}
