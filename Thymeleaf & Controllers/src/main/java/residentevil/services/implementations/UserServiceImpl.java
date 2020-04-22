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
import residentevil.domain.models.binding.Credentials;
import residentevil.domain.models.service.UserServiceModel;
import residentevil.repositories.UserRepository;
import residentevil.repositories.UserRoleRepository;
import residentevil.services.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findUserByUsername(username).orElse(null);
    }

    @Override
    public void register(UserServiceModel user) {
        User userEntity = this.modelMapper.map(user, User.class);
        userEntity.setRoles(this.getAssignedRoles());
        userEntity.setPassword(this.encoder.encode(user.getPassword()));
        userEntity.setEnabled(true);

        this.userRepository.save(userEntity);
    }

    @Override
    public UserServiceModel getUserByUsername(String username) {
        User user = this.userRepository
                .findUserByUsername(username)
                .orElse(null);

        return user == null
                ? null
                : this.modelMapper.map(user, UserServiceModel.class);
    }

    private boolean passwordsMatch(String userPassword, String loginPassword) {
        return this.encoder.matches(loginPassword, userPassword);
    }

    @Override
    public boolean isTakenUsername(String username) {
        User user = this.userRepository
                .findUserByUsername(username)
                .orElse(null);

        return user != null;
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
