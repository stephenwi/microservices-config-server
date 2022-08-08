package cm.iroko.orderservice.dto;

import cm.iroko.orderservice.model.ProductItems;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private List<OrderItemsDto> orderItemsList;
}
