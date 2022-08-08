package cm.iroko.orderservice.controller;

import cm.iroko.orderservice.client.InventoryClient;
import cm.iroko.orderservice.dto.OrderDto;
import cm.iroko.orderservice.model.Order;
import cm.iroko.orderservice.repository.OrderRepository;
import cm.iroko.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

@RestController
@RequestMapping("/ss/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    private final StreamBridge streamBridge;
    private final ExecutorService traceableExecutorService;
    private final OrderService orderService;

    @PostMapping("/place-order")
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderDto orderDto) {
    /* circuitBreakerFactory.configureExecutorService(traceableExecutorService);
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");

        Supplier<Boolean> booleanSupplier = () -> orderDto.orderItemsList().stream()
                .allMatch(productsList -> inventoryClient.checkStock(productsList.getSkuCode()));

        Boolean allProductInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase());

        if (allProductInStock) {
            Order order = new Order();
            order.setOrderNumber(orderDto.getProductsList());
            order.setOrderNumber(UUID.randomUUID().toString());

            log.info("Sending Order details to notification Service");
            streamBridge.send("notificationEventSupplier-out-0" ,order.getId());
            orderRepository.save(order);

            return "OK";
        }

        else {
             return "NO";
        }*/
       return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderDto)) ;
       // return "Order placed successfully";
    }


    public Boolean handleErrorCase() {
        return false ;
    }

    public CompletableFuture<String> fallbackMethod(OrderDto orderDto, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Oops! Something wrong , please orderafter some time") ;
    }

}
