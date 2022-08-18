package cm.resources.irokotechservice.respository;

import cm.resources.irokotechservice.domaine.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
//@EnableJpaRepositories
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c FROM customers c WHERE c.username = ?1", nativeQuery = true)
    Customer findByUsername(String username);

    @Transactional
    @Modifying
   @Query(value = "UPDATE customers c SET c.enabled = TRUE WHERE c.username = ?1", nativeQuery = true)
    void enableCustomers(String username);


    /*@Query("SELECT c FROM tblCustomer c WHERE c.reset_password = ?1")
    public Customer findCustomerByResetPasswordToken(String token);*/


    @Query(value = "SELECT c FROM customers c WHERE c.id = ?1", nativeQuery = true)
    Customer findCustomerById(Long id);

}
