package cm.resources.irokotechservice.ServiceImpl;

import cm.resources.irokotechservice.common.CustomerRegisterRequest;
import cm.resources.irokotechservice.common.RequestUpdateDetails;
import cm.resources.irokotechservice.domaine.Customer;
import cm.resources.irokotechservice.domaine.ResetPassword;
import cm.resources.irokotechservice.domaine.Role;
import cm.resources.irokotechservice.domaine.CustomerActivationToken;
import cm.resources.irokotechservice.respository.CustomerActivationTokenRepository;
import cm.resources.irokotechservice.respository.CustomerRepository;
import cm.resources.irokotechservice.respository.ResetPassordRepository;
import cm.resources.irokotechservice.respository.RoleRepository;
import cm.resources.irokotechservice.service.CustomerService;
import cm.resources.irokotechservice.service.EmailService;
import cm.resources.irokotechservice.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
//import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.*;

@Service
//@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerActivationTokenRepository tokenRepository;
    private final CodeGenerator codeGenerator;
    private final EmailService emailService;
    private static Logger logger;
    private EntityManager em;
    private final ResetPassordRepository resetPassordRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder, CustomerActivationTokenRepository tokenRepository,
                               CodeGenerator codeGenerator, EmailService emailService,
                               ResetPassordRepository resetPassordRepository) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.codeGenerator = codeGenerator;
        this.emailService = emailService;
        this.resetPassordRepository = resetPassordRepository;
    }


    @Override
    public boolean saveCustomer(CustomerRegisterRequest customer) {
        List<Role> role = new ArrayList<>();
        Customer user = customerRepository.findByUsername(customer.getUsername());
        if (user != null){
            logger.info("Customer with name: "+ customer.getUsername() + "already exists");
            return false;
        }

        else {
            // saving new customer
            logger.info("Saving new customer with name: "+ customer.getUsername());
            //TypedQuery<PurchaseOrder> q = em.createQuery("SELECT o FROM PurchaseOrder o JOIN Item i ON o.id = i.order.id WHERE i.id = :itemId", PurchaseOrder.class);
            Role r = roleRepository.findRoleByRoleName("SS_USER");
            role.add(r);
            Customer newUser = new Customer();
            newUser.setPassword(passwordEncoder.encode(customer.getPassword()));
            newUser.setCode(codeGenerator.codeFornewCustomer(customer));
            newUser.setRoles(role);
            newUser.setEnabled(false);
            newUser.setLocked(false);
            customerRepository.save(newUser);

            // generate a token confirmation
            logger.info("Generate activation token for customer  "+ customer.getUsername());
            String tk = UUID.randomUUID().toString();
            CustomerActivationToken currentToken = new CustomerActivationToken(
                    tk,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(30),
                    newUser
            );
            tokenRepository.save(currentToken);

            //Send email for account activation
            logger.info("Send email for account activation with token for customer  "+ customer.getUsername());
            String url = "http://irokotech/confirm-my-account?token="+tk;
            emailService.sendConfirmationTokentoNewCustomer(customer.getName(), customer.getUsername(), url);
            return true;
            }


    }

    @Override
    public void addRoleToCustomer(String username, String roleName) {

        logger.info("Attribute role " + roleName+ " to customer with name: "+ username);
         Customer customer = customerRepository.findByUsername(username);
         Role role = roleRepository.findRoleByRoleName(roleName);
         customer.getRoles().add(role);
    }

    // get single user
    @Override
    public Customer getCustomer(String customer) {
        logger.info("Fetching user {}", customer);
        return customerRepository.findByUsername(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        logger.info("Find customer with id= ", id);
        return customerRepository.findCustomerById(id);
    }

    // save token activation for new customer
    @Override
    public void saveConfirmationToekn(CustomerActivationToken token) {
        logger.info("Saving token: ", token);
        tokenRepository.save(token);
    }

    //  get customer token
    @Override
    public CustomerActivationToken getToken(String token) {
                return tokenRepository.findByToken(token);
    }

    // Update the date where Custumer confirm his token activation
    @Override
    public void setConfirmationDate(String token) {
        logger.info("Update confirmation date");
        tokenRepository.setConfirmationDate(token, LocalDateTime.now());
    }

    @Override
    public List<Customer> getCustomers() {
        logger.info("Fetching all customers");
        return customerRepository.findAll();
    }

    @Override
    public boolean activeAccount(String token) {
        CustomerActivationToken confirmationToken = tokenRepository.findByToken(token);
        if (confirmationToken == null) {
            logger.info("Token " + token + " not found....");
            return false;
        }
        else {
             if( null!= confirmationToken.getConfirmationDate() || confirmationToken.getExpirationDate().isBefore(LocalDateTime.now())) {
                 logger.info("Token " + token + " expired...");
                 return false;
             }
             logger.info("Activation account with token: ", token);
             updateConfirmationdate(token);
             enableCustomer(confirmationToken.getCustomer().getUsername());
            return true;
        }

    }

    @Override
    public void enableCustomer(String username) {
        logger.info("Enable customer with username= ", username);
        customerRepository.enableCustomers(username);
    }

    @Override
    public boolean updateDetails(Long id, RequestUpdateDetails request) {
         Customer user = customerRepository.findCustomerById(id);
         if (user == null) {
             logger.info("Customer not found");
             return false;
         }
         logger.info("Updating customer " + user.getUsername() + "dates");
         user.setName(request.getName());
         user.setUsername(request.getUsername());
         user.setPassword(passwordEncoder.encode(request.getPassword()));
         customerRepository.save(user);
        return true;
    }

    @Override
    public boolean customerUpdatedPassword(String username, String oldPassword, String newPassword) {
        Customer user = customerRepository.findByUsername(username);
        if (user == null) {
            logger.info("Customer with user: " + username + "not found");
            return false;
        } else if(!user.getPassword().equals(passwordEncoder.encode(oldPassword))){
            logger.info("Invalid old password not");
            return false;
        } else {
            user.setPassword(passwordEncoder.encode(newPassword));
            customerRepository.save(user);
            logger.info("Password for customer " +  username + "successufully updated");
            return true;
        }
    }

    @Override
    public boolean customerResetPassword(String email) {
        Customer user = customerRepository.findByUsername(email);
        if (user == null) {
            logger.info("Customer " +email+ " not found");
            return false;
        } else {
            logger.info("Generate user token for reset password");
            String tk = UUID.randomUUID().toString();
            //user.setResetPasswordToken(tk);
            //customerRepository.save(user);

            ResetPassword resetPassword = new ResetPassword(
                    tk,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(30),
                    user
            );
            resetPassordRepository.save(resetPassword);
            String url = "http://developer-irokotech.africa/customer_reset_password?token="+tk;
            emailService.sendEmailForForgotedPassword(email, url);
            return true;
        }
    }

    @Override
    public void confirmResetPassword(String token) {


    }

    @Override
    public boolean deleteCustomer(Long id) {
        return false;
    }

    @Override
    public boolean desactiveCustomer(Long id) {
        return false;
    }


    public void updateConfirmationdate(String tk) {
        tokenRepository.setConfirmationDate(tk, LocalDateTime.now());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(s);
        if (customer == null || (customer != null && !customer.isEnabled())) {
            logger.info("Customer with name: " + s + " not found");
            throw new UsernameNotFoundException("This user not exist");
        } else {
            logger.info("Found customer with name: "+ s);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        customer.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(), authorities);
    }
}
