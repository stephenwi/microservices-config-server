package cm.resources.irokotechservice.domaine;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="reset_password")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="reset_password_id")
    private Long id;
    @Column(nullable = false, name="tokens")
    private String token;
    @Column(nullable = false, name="created_on")
    private LocalDateTime create_on;
    @Column(nullable = false, name="expire_on")
    private LocalDateTime expire_on;
    @OneToOne
    @JoinColumn(name ="customer_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;


    public ResetPassword(String token, LocalDateTime create_on, LocalDateTime expire_on, Customer customer) {
        this.token = token;
        this.create_on = create_on;
        this.expire_on = expire_on;
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

    public LocalDateTime getCreate_on() {
        return create_on;
    }

    public void setCreate_on(LocalDateTime create_on) {
        this.create_on = create_on;
    }

    public LocalDateTime getExpire_on() {
        return expire_on;
    }

    public void setExpire_on(LocalDateTime expire_on) {
        this.expire_on = expire_on;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
