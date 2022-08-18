package cm.resources.irokotechservice.controller;

import cm.resources.irokotechservice.domaine.Customer;
import cm.resources.irokotechservice.domaine.Role;
import cm.resources.irokotechservice.service.CustomerService;
import cm.resources.irokotechservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("irokotech/administrator")
//@RequiredArgsConstructor
public class IrokoController {

    private final CustomerService customerService;
    private final RoleService roleService;
    private static Logger logger;

    public IrokoController(CustomerService customerService, RoleService roleService) {
        this.customerService = customerService;
        this.roleService = roleService;
    }


    @PostMapping("/create-role")
    public boolean createRole(@RequestBody Role role) {
        logger.info("Create a new role: " + role.getRoleName());
        return roleService.createRole(role);
    }

    @GetMapping("/allcutomers")
    public List<Customer> getallcutomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/all-roles")
    public List<String> getAllRoles() {
        logger.info("get all presents roles");
        return roleService.getAllRoles();
    }

    @DeleteMapping("/delete/{id}/role")
    public void deleteRole(@RequestParam("id") Long id) {
        logger.info("Delete role with id: " + id);
         roleService.deleteRole(id);
    }

    @PatchMapping("/update/{id}/role")
    public boolean update(@RequestParam("id") Long id, String newRole) {
        logger.info("update role with id: "+ id);
        return roleService.updateRole(id, newRole);
    }
}
