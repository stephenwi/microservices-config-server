package cm.resources.irokotechservice.utils;

import cm.resources.irokotechservice.common.CustomerRegisterRequest;
import cm.resources.irokotechservice.domaine.Customer;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CodeGenerator {

    public String codeFornewCustomer(CustomerRegisterRequest user) {

        Random random = new Random();
        Integer val = random.nextInt(2020);
        String first = user.getName().substring(0,2);
        String last = user.getPassword().substring(2,6);

        return "S" + first + 2 + val + last + "S" +0;
    }
}
