package es.deusto.sd.proyecto.DAO;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import es.deusto.sd.proyecto.Entity.User;

public interface userRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
