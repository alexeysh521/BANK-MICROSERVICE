package kafka.system.AuthService.SecretKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface SecretKeyRepository extends JpaRepository<SecretKey, Long> {

    boolean existsByValue(String secretKey);

    @Modifying
    @Transactional
    @Query("update SecretKey s set s.value = :newSecretKey, s.timestamp = CURRENT_TIMESTAMP where s.id = 1")
    void updateSecretKey(@Param("newSecretKey") String newSecretKey);
}
