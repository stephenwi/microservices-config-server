package cm.resources.irokotechservice.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="customer_id")
    private Long id;
    @Column(name ="customer_code")
    private String code;
    @Column(name ="customer_name")
    private String name;
    @Column(name ="customer_username")
    private String username; // email
    @Column(name ="customer_password")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "roles")
    private Collection<Role> roles =  new ArrayList<>();
    @Column(name ="enabled")
    private boolean enabled;
    @Column(name ="is_locked")
    private boolean locked;
    @Column(name = "created_on")
    private Date createdOn;
    @Column(name = "last_login")
    private Date last_login;
    //@Column(name ="reset_password")
    //private String resetPasswordToken;


/*    public Customer(Long id, String code, String name, String username, String password, Collection<Role> roles, boolean enabled, boolean locked, Date createdOn, Date last_login) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
        this.locked = locked;
        this.createdOn = createdOn;
        this.last_login = last_login;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    /*public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }*/
}
