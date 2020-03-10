package exodia.services;

import exodia.domain.models.binding.users.UserLoginBindingModel;
import exodia.domain.models.binding.users.UserRegisterBindingModel;
import exodia.domain.models.view.users.UserLoggedViewModel;

public interface UserService {
    void register(UserRegisterBindingModel user);

    UserLoggedViewModel login(UserLoginBindingModel user);
}
