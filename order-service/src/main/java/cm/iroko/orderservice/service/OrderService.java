package cm.iroko.orderservice.service;

import cm.iroko.orderservice.dto.InventoryResponse;
import cm.iroko.orderservice.dto.OrderDto;
import cm.iroko.orderservice.dto.OrderItemsDto;
import cm.iroko.orderservice.model.Order;
import cm.iroko.orderservice.model.ProductItems;
import cm.iroko.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    private final Tracer tracer;

    public String placeOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

     List<ProductItems> orderItemsDtoList = orderDto.getOrderItemsList()
                .stream()
                .map(this::mapToDto)
                .toList();
       order.setOrderItems(orderItemsDtoList);

       List<String> skuCodes = order.getOrderItems().stream().map(ProductItems::getSkuCode).toList();

       log.info("Calling inventory service");

          Span inventoryServiceLookup =  tracer.nextSpan().name("InventoryServiceLookup");

        try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())) {
            // Call stock inventory and place order if product is not in stock
            InventoryResponse[] responses  = webClient.build().get()
                    .uri("http://inventory-service/ss/inventory/getSkuCode",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            Boolean allProductsInStock = Arrays.stream(responses).allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                return "Order placed successfully";
            }
            else {
                throw new IllegalArgumentException("Product is not stock, plaese try later");
            }
        } finally {
            inventoryServiceLookup.end();
        }

    }

    private ProductItems mapToDto(OrderItemsDto orderItemsDto) {

        ProductItems orderDto = new ProductItems();

        orderDto.setPrice(orderItemsDto.getPrice());
        orderDto.setQuantity(orderItemsDto.getQuantity());
        orderDto.setSkuCode(orderItemsDto.getSkuCode());

        return orderDto;
    }
}
