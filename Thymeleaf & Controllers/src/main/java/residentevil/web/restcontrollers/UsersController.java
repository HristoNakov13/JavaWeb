package residentevil.web.restcontrollers;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import residentevil.domain.models.binding.Credentials;
import residentevil.domain.models.binding.UserRegisterBindingModel;
import residentevil.domain.models.binding.UsernameValidationBindingModel;
import residentevil.domain.models.service.UserServiceModel;
import residentevil.domain.models.view.JwtResponse;
import residentevil.domain.models.view.UserLoggedViewModel;
import residentevil.services.UserService;
import residentevil.util.JwtUtil;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public UsersController(UserService userService, Gson gson, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;

        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(path = "/validate", method = RequestMethod.POST)
    public ResponseEntity isUniqueUsername(@RequestBody String body) {
        UsernameValidationBindingModel user = this.gson.fromJson(body, UsernameValidationBindingModel.class);
        boolean isTaken = this.userService.isTakenUsername(user.getUsername());

        int statusCode = isTaken
                ? 422
                : 204;

        return ResponseEntity.status(statusCode)
                .build();
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> registerUser(@RequestBody String body) {
        UserRegisterBindingModel user = this.gson.fromJson(body, UserRegisterBindingModel.class);
        int statusCode;

        // Too generic without any validation but whatev. Its for the exercise
        try {
            this.userService.register(this.modelMapper.map(user, UserServiceModel.class));
            statusCode = 201;
        } catch (Exception e) {
            statusCode = 400;
        }

        //lol
        return ResponseEntity.status(statusCode).body("{\"created\": true}");
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody Credentials credentials) throws Exception {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

        UserDetails userDetails = this.userService.loadUserByUsername(credentials.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        UserLoggedViewModel userLoggedViewModel = this.modelMapper.map(userDetails, UserLoggedViewModel.class);

        return ResponseEntity.status(200).body(new JwtResponse(token, userLoggedViewModel));
    }
}
