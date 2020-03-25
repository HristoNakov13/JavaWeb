package residentevil.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import residentevil.domain.entities.BaseEntity;
import residentevil.domain.entities.User;
import residentevil.domain.entities.UserRole;
import residentevil.domain.models.service.UserServiceModel;
import residentevil.repositories.UserRepository;
import residentevil.repositories.UserRoleRepository;
import residentevil.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return this.userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    @Override
    public void register(UserServiceModel user) {
        User userEntity =  this.modelMapper.map(user, User.class);
        userEntity.setRoles(this.getAssignedRoles());
        userEntity.setPassword(this.encoder.encode(user.getPassword()));

        this.userRepository.save(userEntity);
    }

    private Set<UserRole> getAssignedRoles() {
        Set<UserRole> roles = new HashSet<>();

        if (this.isFirstToRegister(this.userRepository)) {
            roles.add(this.userRoleRepository.findByName("ADMIN"));
            roles.add(this.userRoleRepository.findByName("MODERATOR"));
            roles.add(this.userRoleRepository.findByName("USER"));
        } else {
            roles.add(this.userRoleRepository.findByName("USER"));
        }

        return roles;
    }

    private boolean isFirstToRegister(JpaRepository<? extends BaseEntity, String> repository) {
        return repository.count() == 0;
    }
}
