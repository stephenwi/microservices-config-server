package cm.irokotech.commerce.service;


import cm.irokotech.commerce.dto.ProductRequest;
import cm.irokotech.commerce.dto.ProductResponse;
import cm.irokotech.commerce.entity.Product;
import cm.irokotech.commerce.repository.ProductRepository;
import cm.irokotech.commerce.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CodeGenerator generator;

    public List<ProductResponse> allProductList() {
        List<Product> products = productRepository.findAll();

       return products.stream().map(this::mapToProductResposne).toList();
    }

    private ProductResponse mapToProductResposne(Product product) {
        return ProductResponse.builder()
                              .id(product.getId())
                .code(product.getCode())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public void createProduct(ProductRequest request) {
       log.info("Creating new product");
        Product product = Product.builder()
                                 .name(request.getName())
                                 .code(generator.productCodeGenerator())
                                 .description(request.getDescription())
                                 .price(request.getPrice()).build();

        productRepository.save(product);
        log.info("Saving product {}", product);
    }
}
