package residentevil.config;

import org.springframework.stereotype.Component;
import residentevil.domain.entities.UserRole;
import residentevil.repositories.UserRoleRepository;

import javax.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    private UserRoleRepository userRoleRepository;

    public DatabaseSeeder(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @PostConstruct
    private void seedDatabaseWithRoles() {
        if (this.userRoleRepository.count() > 0) {
            return;
        }

        this.userRoleRepository.save(this.createRole("ADMIN"));
        this.userRoleRepository.save(this.createRole("MODERATOR"));
        this.userRoleRepository.save(this.createRole("USER"));
    }

    private UserRole createRole(String role) {
        UserRole userRole = new UserRole();
        userRole.setName(role);

        return userRole;
    }
}
