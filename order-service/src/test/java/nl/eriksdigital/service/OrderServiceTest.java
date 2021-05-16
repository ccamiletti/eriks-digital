package nl.eriksdigital.service;

import nl.eriksdigital.entity.OrderEntity;
import nl.eriksdigital.exception.OrderNotFoundException;
import nl.eriksdigital.model.Order;
import nl.eriksdigital.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


public class OrderServiceTest {

    private OrderRepository orderRepository = mock(OrderRepository.class);
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private JmsTemplate jmsTemplate;

    private OrderService orderService = new OrderService(modelMapper, orderRepository, jmsTemplate);;

    @Test
    public void getOrderByIdTest() {
        OrderEntity orderEntity = getOrderEntity();
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(orderEntity));
        orderService.findById(1L).ifPresent((o) -> {
            assertThat(o.getId()).isEqualTo(orderEntity.getId());
            assertThat(o.getStatus()).isEqualTo(orderEntity.getStatus());
            assertThat(o.getTotalPrice()).isEqualTo(orderEntity.getTotalPrice());
            assertThat(o.getDate()).isEqualTo(orderEntity.getDate());
        });
    }

    @Test
    public void getOrderByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(orderService.findById(1L)).isEqualTo(Optional.empty());
        Mockito.verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void saveOrderTest() {
        OrderEntity orderEntity = getOrderEntity();
        when(orderRepository.save(getOrderEntity())).thenReturn(orderEntity);
        orderService.save(Order.builder().id(orderEntity.getId())
                .date(orderEntity.getDate())
                .status(orderEntity.getStatus())
                .totalPrice(orderEntity.getTotalPrice())
                .build());
        Mockito.verify(orderRepository, times(1)).save(orderEntity);
    }

    @Test
    public void deleteOrderByIdTest() {
        OrderEntity orderEntity = getOrderEntity();
        doNothing().when(orderRepository).deleteById(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        orderService.delete(orderEntity.getId());
        Mockito.verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteOrderByIdNotFoundTest() {
        try{
            doNothing().when(orderRepository).deleteById(1L);
            orderService.delete(1L);
            fail();
        }catch(OrderNotFoundException orderNotFoundException) {
            Mockito.verify(orderRepository, times(0)).deleteById(1L);
        }
    }

    private OrderEntity getOrderEntity() {
        Instant instant = Instant.now();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setStatus("VALID");
        orderEntity.setDate(instant);
        orderEntity.setTotalPrice(1234.56);
        return orderEntity;
    }
}
