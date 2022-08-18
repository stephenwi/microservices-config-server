package cm.resources.irokotechservice.ServiceImpl;

import cm.resources.irokotechservice.domaine.Role;
import cm.resources.irokotechservice.respository.RoleRepository;
import cm.resources.irokotechservice.service.RoleService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private static Logger logger;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean createRole(Role role) {
        logger.info("Saving new role " +role.getRoleName()+" in database tblRoles");
         roleRepository.save(role);
         return true;
    }

    @Override
    public boolean updateRole(Long id, String name) {
        logger.info("Update role =" + id + "with name", name);
        return roleRepository.updateRole(id, name);
    }

    @Override
    public boolean updateCustomerRole(Long id, String code, String role) {
        return false;
    }

    @Override
    public void deleteRole(Long id) {
        logger.info("Deleting role with id= ", id);
         roleRepository.deleteById(id);
    }

    @Override
    public List<String> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<String> allRoles = new ArrayList<>();
        for (Role rl : roles) {
            allRoles.add(rl.getRoleName());
        }
        return allRoles;
    }
}
