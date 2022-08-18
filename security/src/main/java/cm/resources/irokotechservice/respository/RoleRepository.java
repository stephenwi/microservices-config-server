package cm.resources.irokotechservice.respository;

import cm.resources.irokotechservice.domaine.Customer;
import cm.resources.irokotechservice.domaine.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT r FROM tblRoles r WHERE r.id = ?1", nativeQuery = true)
    Role findRoleById(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tblRoles r SET r.name = ?2 WHERE r.id = ?1", nativeQuery = true)
    boolean updateRole(Long id, String role);

    @Query(value = "SELECT r FROM tblRoles r WHERE r.name = ?1", nativeQuery = true)
    Role findRoleByRoleName(String roleName);
}
