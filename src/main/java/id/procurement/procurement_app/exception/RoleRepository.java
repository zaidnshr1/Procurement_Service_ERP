package id.procurement.procurement_app.exception;

import id.procurement.procurement_app.entity.ERole;
import id.procurement.procurement_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(ERole roleName);
}
