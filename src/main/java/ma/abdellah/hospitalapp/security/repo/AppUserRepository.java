package ma.abdellah.hospitalapp.security.repo;

import ma.abdellah.hospitalapp.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
