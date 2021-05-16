package nl.eriksdigital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.eriksdigital.model.Order;
import nl.eriksdigital.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void getOrderByIdTest() throws Exception {
        Order orderRequest = Order.builder().id(1L).build();
        given(orderService.findById(eq(1L))).willReturn(Optional.of(orderRequest));
        Order orderResponse = objectMapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/order/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), Order.class);
        assertThat(orderRequest.getId()).isEqualTo(orderResponse.getId());
        verify(orderService, times(1)).findById(1L);
    }

    @Test
    public void getOrderByIdNotFoundTest() throws Exception {
        given(orderService.findById(eq(1L))).willReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/order/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(orderService, times(1)).findById(1L);
    }

    @Test
    public void saveOrderTest() throws Exception {
        Order orderRequest = Order.builder().id(1L).build();
        doNothing().when(orderService).save(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                .content(objectMapper.writeValueAsString(orderRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        verify(orderService, times(1)).save(refEq(orderRequest));
    }

    @Test
    public void deleteOrderTest() throws Exception {
        doNothing().when(orderService).delete(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(orderService, times(1)).delete(1L);
    }

    @Test
    public void updateOrderTest() throws Exception {
        Order orderRequest = Order.builder().status("REJECTED").build();
        doNothing().when(orderService).update(orderRequest, 1L);
        mockMvc.perform(MockMvcRequestBuilders.put("/order/1")
                .content(objectMapper.writeValueAsString(orderRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(orderService, times(1)).update(refEq(orderRequest), eq(1L));
    }

}
