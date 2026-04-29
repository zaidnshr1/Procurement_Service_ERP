package id.procurement.procurement_app.config;

import id.procurement.procurement_app.entity.ERole;
import id.procurement.procurement_app.entity.Role;
import id.procurement.procurement_app.exception.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByRoleName(ERole.ROLE_STAFF).isEmpty()) {
            roleRepository.save(new Role(null, ERole.ROLE_STAFF));
        }
        if (roleRepository.findByRoleName(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(null, ERole.ROLE_ADMIN));
        }
        if (roleRepository.findByRoleName(ERole.ROLE_FINANCE_MANAGER).isEmpty()) {
            roleRepository.save(new Role(null, ERole.ROLE_FINANCE_MANAGER));
        }
        if (roleRepository.findByRoleName(ERole.ROLE_DIRECTOR).isEmpty()) {
            roleRepository.save(new Role(null, ERole.ROLE_DIRECTOR));
        }
    }
}
