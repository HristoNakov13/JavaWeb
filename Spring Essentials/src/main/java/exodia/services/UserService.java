package exodia.services;

import exodia.domain.models.binding.UserLoginBindingModel;
import exodia.domain.models.binding.UserRegisterBindingModel;
import exodia.domain.models.view.UserLoggedViewModel;

public interface UserService {
    void register(UserRegisterBindingModel user);

    UserLoggedViewModel login(UserLoginBindingModel user);
}
