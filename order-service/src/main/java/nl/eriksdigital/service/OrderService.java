package nl.eriksdigital.service;

import nl.eriksdigital.entity.OrderEntity;
import nl.eriksdigital.exception.OrderNotFoundException;
import nl.eriksdigital.model.Order;
import nl.eriksdigital.model.OrderWrapper;
import nl.eriksdigital.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final String ORDER_QUEUE = "order_queue";
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final JmsTemplate jmsTemplate;

    public OrderService(ModelMapper modelMapper, OrderRepository orderRepository, JmsTemplate jmsTemplate) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.jmsTemplate  = jmsTemplate;
    }

    public void save(Order order) {
        orderRepository.save(toEntity(order));
    }

    public void delete(Long id) throws OrderNotFoundException {
        orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.deleteById(id);
    }

    public void update(Order order, Long id) {
        orderRepository.findById(id).map((orderEntity) -> {
            order.setId(id);
            OrderEntity orderRequest = toEntity(order);
            if (!orderRequest.equals(orderEntity)) {
                Order orderOld = toDTO(orderEntity);
                orderEntity.setDate(order.getDate());
                orderEntity.setStatus(order.getStatus());
                orderEntity.setTotalPrice(order.getTotalPrice());
                orderRepository.save(orderEntity);
                sendOrderMessage(order, orderOld);
            }
            return orderEntity;
        }).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void partialUpdate(Order order, Long id) {
        orderRepository.findById(id).map(orderEntity -> {
            Order orderOld = toDTO(orderEntity);
            orderEntity.checkAndSetTotalPrice(order.getTotalPrice());
            orderEntity.checkAndSetDate(order.getDate());
            orderEntity.checkAndSetStatus(order.getStatus());
            Order orderResponse = toDTO(orderRepository.save(orderEntity));
            if (!orderEntity.isEqual()) {
                sendOrderMessage(orderResponse, orderOld);
            }
            return orderResponse;
        }).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId).map(this::toDTO);
    }

    public List<Order> findAll(Integer time) {
        List<Order> orderList = new ArrayList<>();
        orderRepository.findAll().forEach(o -> {
            orderList.add(toDTO(o));
        });
        try {
            Thread.sleep(time);
        } catch(Exception e) {

        }
        return orderList;
    }

    private OrderEntity toEntity(Order order) {
        return modelMapper.map(order, OrderEntity.class);
    }

    private Order toDTO(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, Order.class);
    }

    private void sendOrderMessage(Order orderNew, Order orderOld) {
        OrderWrapper orderWrapper = new OrderWrapper(orderNew, orderOld);
        jmsTemplate.convertAndSend(ORDER_QUEUE, orderWrapper);
    }
}
