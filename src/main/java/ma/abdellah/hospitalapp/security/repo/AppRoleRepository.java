package ma.abdellah.hospitalapp.security.repo;

import ma.abdellah.hospitalapp.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
    AppRole findByRole(String role);
}
