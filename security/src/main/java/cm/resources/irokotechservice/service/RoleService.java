package cm.resources.irokotechservice.service;

import cm.resources.irokotechservice.domaine.Role;

import java.util.List;

public interface RoleService {
    //List<String> getAllRole();
    boolean createRole(Role role);
    boolean updateRole(Long id, String name);
    boolean updateCustomerRole(Long id, String code, String role);
    void deleteRole(Long id);
    List<String> getAllRoles();
}
