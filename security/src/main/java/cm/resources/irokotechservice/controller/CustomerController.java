package cm.resources.irokotechservice.controller;

import cm.resources.irokotechservice.common.CustomerRegisterRequest;
import cm.resources.irokotechservice.common.RequestUpdateDetails;
import cm.resources.irokotechservice.common.RequestUpdatePassword;
import cm.resources.irokotechservice.domaine.Customer;
import cm.resources.irokotechservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/ss-resources/customers")
//@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private static Logger logger;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/customer/{id}")
    public Customer getCustomer(@RequestParam("id") Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/new-customer")
    public boolean addNewCustomer(@RequestBody CustomerRegisterRequest request) {
        return customerService.saveCustomer(request);
    }

    @PatchMapping("/update/{id}/informations")
    public boolean customerUpadteSingleDetails(@RequestParam("id") Long id, @RequestBody RequestUpdateDetails request) {
        return customerService.updateDetails(id, request);
    }

    @PatchMapping("/update/{id}/update-password")
    public  boolean updatePassword(@RequestParam("email") String email, @RequestBody RequestUpdatePassword request) {
        return customerService.customerUpdatedPassword(email, request.getOldPassword(), request.getNewPassword());
    }

    @PatchMapping("/request/reset-password/{email}/with-email")
    public boolean resetPassword(@RequestParam("email") String email) {
        return customerService.customerResetPassword(email);
    }

    @PostMapping("/exec/request-password/{token}")
    public void resetPasswordExecution(@RequestParam("token") String token) {
         customerService.confirmResetPassword(token);
    }

    @DeleteMapping("/delete/{id}/customer")
    public boolean deleteCustomer(@RequestParam("id") Long id) {
        return customerService.deleteCustomer(id);
    }

    @PostMapping("/desactive-customer/{id}/customer")
    public boolean desactiveCustomer(@RequestParam("id") Long id) {
        return customerService.desactiveCustomer(id);
    }
}
