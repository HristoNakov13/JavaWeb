package exodia.services.implementations;

import exodia.domain.entities.User;
import exodia.domain.models.binding.UserLoginBindingModel;
import exodia.domain.models.binding.UserRegisterBindingModel;
import exodia.domain.models.view.UserLoggedViewModel;
import exodia.repositories.UserRepository;
import exodia.services.UserService;
import exodia.util.ThrowMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(UserRegisterBindingModel user) {
        if (this.isTakenUsername(user.getUsername())) {
            throw new IllegalArgumentException(ThrowMessages.USERNAME_TAKEN);
        }

        if (this.isTakenEmail(user.getEmail())) {
            throw new IllegalArgumentException(ThrowMessages.EMAIL_TAKEN);
        }

        User userEntity = this.modelMapper.map(user, User.class);

        this.userRepository.save(userEntity);
    }

    @Override
    public UserLoggedViewModel login(UserLoginBindingModel user) {
        User userEntity = this.userRepository.findByUsernameAndEmail(user.getUsername(), user.getPassword());

        if (this.hasWrongCredentials(userEntity)) {
            throw new IllegalArgumentException(ThrowMessages.INVALID_CREDENTIALS);
        }

        return this.modelMapper.map(userEntity, UserLoggedViewModel.class);
    }

    private boolean isTakenUsername(String username) {
        return this.userRepository.findByUsername(username) != null;
    }

    private boolean isTakenEmail(String email) {
        return this.userRepository.findByEmail(email) != null;
    }

    private boolean hasWrongCredentials(User user) {
        return user == null;
    }
}
