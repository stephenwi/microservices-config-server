package cm.resources.irokotechservice.service;

import cm.resources.irokotechservice.common.CustomerRegisterRequest;
import cm.resources.irokotechservice.common.RequestUpdateDetails;
import cm.resources.irokotechservice.domaine.Customer;
import cm.resources.irokotechservice.domaine.CustomerActivationToken;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    boolean saveCustomer(CustomerRegisterRequest customer);
    void addRoleToCustomer(String username, String roleName);
    Customer getCustomer(String customer);
    Customer getCustomerById(Long id);
    void saveConfirmationToekn(CustomerActivationToken token);
    CustomerActivationToken getToken(String token);
    void setConfirmationDate(String token);
    List<Customer> getCustomers();
    boolean activeAccount(String token);
    void enableCustomer(String username);
    boolean updateDetails(Long id, RequestUpdateDetails request);
    boolean customerUpdatedPassword(String username, String oldPassword, String newPassword);
    boolean customerResetPassword(String email);
    void confirmResetPassword(String token);
    boolean deleteCustomer(Long id);
    boolean desactiveCustomer(Long id);
}

