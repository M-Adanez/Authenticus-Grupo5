package es.deusto.sd.proyecto.DAO;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import es.deusto.sd.proyecto.Entity.User;

@Repository 
public interface userRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    @Transactional
    void deleteByUsername(String username);
}