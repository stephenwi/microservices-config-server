package cm.irokotech.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductResponse {

    @Id
    private Long id;

    private String name;

    private String code;

    private String description;

    private BigDecimal price;
}
