package cm.resources.irokotechservice.controller;

import cm.resources.irokotechservice.ServiceImpl.CustomerServiceImpl;
import cm.resources.irokotechservice.domaine.Customer;
import cm.resources.irokotechservice.domaine.Role;
import cm.resources.irokotechservice.service.CustomerService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/resources")
//@RequiredArgsConstructor
public class SecurityController {

    private final CustomerService customerService;
    private static Logger logger;
    private final AuthenticationManager authenticationManager;
    private final CustomerServiceImpl customerServiceImpl;


    public SecurityController(CustomerService customerService, AuthenticationManager authenticationManager, CustomerServiceImpl customerServiceImpl) {

        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.customerServiceImpl = customerServiceImpl;
    }

/*    @PostMapping(value = "/register", consumes = "application/json")
    public boolean saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }*/

    @GetMapping("/account-confirmation")
    public boolean accountConfirmation(@RequestParam("token") String token) {
        return customerService.activeAccount(token);
    }

    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(null != authorizationHeader && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Customer user = customerService.getCustomer(username);
                //String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                /*Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });
                Object principal;
                Object object;*/

                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                        .sign(algorithm);

                // response.setHeader("access_token", accessToken);
                // response.setHeader("refresh_token", refreshToken);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


                // filterChain.doFilter(request, response);
            } catch (Exception ex) {
                logger.error("Error logging in {}", ex.getMessage());
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                // response.sendError(FORBIDDEN.value());

                Map<String, String> errors = new HashMap<>();
                errors.put("access_token", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        } else {
            throw new RuntimeException("Missing refresh tokem");
        }
    }


    @PostMapping("/customer/auth")
    public String customAuthentication(@RequestParam("username") String username, @RequestParam("password") String password) {

           final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
           final UserDetails customer = customerServiceImpl.loadUserByUsername(username);
           final String jwtToken;

           return null;
    }
}
