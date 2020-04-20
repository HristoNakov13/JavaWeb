package residentevil.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import residentevil.domain.entities.User;
import residentevil.domain.models.binding.Credentials;
import residentevil.domain.models.service.UserServiceModel;

public interface UserService extends UserDetailsService {

    void register(UserServiceModel user);

    UserServiceModel getUserByUsername(String username);

    User getUserDetailsByUsername(String username);

    UserDetails login(Credentials user);

    boolean isTakenUsername(String username);
}
