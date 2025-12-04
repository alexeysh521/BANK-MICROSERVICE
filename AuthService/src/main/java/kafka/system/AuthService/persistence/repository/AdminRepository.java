package kafka.system.AuthService.persistence.repository;

import kafka.system.AuthService.persistence.model.AdminCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<AdminCredential, UUID> {
    Optional<AdminCredential> findByUsername(String username);

    Optional<AdminCredential> findByEmail(String email);

    boolean existsByEmail(String email);
}
