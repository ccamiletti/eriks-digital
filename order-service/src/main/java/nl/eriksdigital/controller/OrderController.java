package nl.eriksdigital.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import nl.eriksdigital.model.Order;
import nl.eriksdigital.service.OrderService;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@EnableHystrix
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(
        tags = {"order"},
        summary = "Retrieves all Orders",
        description = "Retrieves all orders. It can be empty",
        responses = {
                @ApiResponse(responseCode = "200", description = "successful operation",
                        content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class)))}
                )
        }
    )
    @HystrixCommand(fallbackMethod = "fallBackGettingOrders", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public ResponseEntity<List<Order>> findAll(@RequestParam(name = "timeOut", defaultValue = "0") long timeOut) throws InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll(timeOut));
    }

    private ResponseEntity<List<Order>> fallBackGettingOrders(long timeOut) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new ArrayList<Order>());
    }

    @GetMapping("/{id}")
    @Operation(
        tags = {"order"},
        summary = "Retrieve an Order",
        description = "Retrieve an order for the specified order id. It can be empty",
        responses = {
                @ApiResponse(responseCode = "200", description = "get order by id",
                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))}
                )
        }
    )
    public ResponseEntity<Order> findById(@PathVariable("id") Long orderId) {
        return orderService.findById(orderId).map(order -> ResponseEntity.status(HttpStatus.OK).body(order))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.OK).build());
    }

    @PostMapping
    @Operation(
            tags = {"order"},
            summary = "Create an Order",
            description = "Create a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public void saveOrder(@RequestBody Order order) {
        orderService.save(order);
    }

    @PatchMapping("/{id}")
    @Operation(
            tags = {"order"},
            summary = "Update an order partially",
            description = "Update an order partially based on request body",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void partialUpdate(@RequestBody Order order, @PathVariable("id") Long id) {
        orderService.partialUpdate(order, id);
    }

    @PutMapping("/{id}")
    @Operation(
            tags = {"order"},
            summary = "Update an order",
            description = "update an order by id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order updated"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateOrder(@RequestBody Order order, @PathVariable("id") Long id) {
        orderService.update(order, id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            tags = {"order"},
            summary = "Delete order",
            description = "Delete an order by id",
            operationId = "delete and Order",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order deleted"),
                    @ApiResponse(responseCode = "404", description = "No order deleted")
            }
    )
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        orderService.delete(id);
    }

}
