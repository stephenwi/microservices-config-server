package cm.irokotech.commerce.repository;


import cm.irokotech.commerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
