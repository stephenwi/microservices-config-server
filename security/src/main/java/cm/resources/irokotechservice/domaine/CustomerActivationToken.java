package cm.resources.irokotechservice.domaine;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="activation_token")
public class CustomerActivationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="token_id")
    private Long id;
    @Column(nullable = false, name="tokens")
    private String token;
    @Column(nullable = false, name="created_on")
    private LocalDateTime createdAt;
    @Column(nullable = false, name="expire_on")
    private LocalDateTime expirationDate;
    @Column(name = "confirmed_on")
    private LocalDateTime confirmationDate;
    @ManyToOne
    @JoinColumn(nullable = false, name = "customer_id")
    private Customer customer;

    public CustomerActivationToken(String token, LocalDateTime createdAt, LocalDateTime expirationDate, LocalDateTime confirmationDate, Customer customer) {
        this.token = token;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        this.confirmationDate = confirmationDate;
        this.customer = customer;
    }

    public CustomerActivationToken(String token, LocalDateTime createdAt, LocalDateTime expirationDate, Customer customer) {
        this.token = token;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
