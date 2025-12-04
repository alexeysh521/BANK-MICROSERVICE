package kafka.system.AuthService.persistence.repository;

import kafka.system.AuthService.persistence.model.ManagerCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerCredential, UUID> {
    Optional<ManagerCredential> findByUsername(String username);

    Optional<ManagerCredential> findByEmail(String email);

    boolean existsByEmail(String email);
}

