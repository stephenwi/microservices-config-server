package cm.resources.irokotechservice.respository;

import cm.resources.irokotechservice.domaine.CustomerActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CustomerActivationTokenRepository extends JpaRepository<CustomerActivationToken, Long> {

    @Query(value = "SELECT t FROM tblTokenActivation t WHERE t.token = ?1", nativeQuery = true)
    CustomerActivationToken findByToken(String token);

    @Transactional
    @Modifying
    @Query(value = "UPADTE tblTokenActivation t SET t.confirmationDate = ?2 WHERE t.token = ?1", nativeQuery = true)
    void setConfirmationDate(String token, LocalDateTime conditionDate);

}

