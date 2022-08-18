package cm.resources.irokotechservice.respository;

import cm.resources.irokotechservice.domaine.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPassordRepository extends JpaRepository<ResetPassword, Long> {

}
