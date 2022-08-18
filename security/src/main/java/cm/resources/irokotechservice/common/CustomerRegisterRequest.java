package cm.resources.irokotechservice.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterRequest {

    private String name;
    private String username;
    private String password;

}
