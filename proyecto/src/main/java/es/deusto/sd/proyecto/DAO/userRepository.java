package es.deusto.sd.proyecto.DAO;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import es.deusto.sd.proyecto.Entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface userRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    // Añade estos dos métodos:
    boolean existsByUsername(String username);
    @Transactional
    void deleteByUsername(String username);
}