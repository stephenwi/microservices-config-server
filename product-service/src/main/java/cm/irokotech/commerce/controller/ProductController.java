package cm.irokotech.commerce.controller;

import cm.irokotech.commerce.dto.ProductRequest;
import cm.irokotech.commerce.dto.ProductResponse;
import cm.irokotech.commerce.entity.Product;
import cm.irokotech.commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("ss/products")
public class ProductController {


    ProductService productService;

    @GetMapping("/products-list")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProductList() {
       return   productService.allProductList();
    }

    @PostMapping("/new-product")
    @ResponseStatus(HttpStatus.CREATED)
    public void newProduct(@RequestBody ProductRequest request) {
       productService.createProduct(request);
    }


}
