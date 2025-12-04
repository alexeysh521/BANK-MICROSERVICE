package kafka.system.AuthService.persistence.repository;

import kafka.system.AuthService.persistence.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserCredential, UUID> {
    Optional<UserCredential> findByUsername(String username);

    Optional<UserCredential> findByEmail(String email);

    boolean existsByEmail(String email);
}
