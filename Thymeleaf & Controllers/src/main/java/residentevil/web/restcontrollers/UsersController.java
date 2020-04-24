package residentevil.web.restcontrollers;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import residentevil.domain.models.binding.Credentials;
import residentevil.domain.models.binding.UserRegisterBindingModel;
import residentevil.domain.models.binding.UsernameValidationBindingModel;
import residentevil.domain.models.service.UserServiceModel;
import residentevil.domain.models.view.UserLoggedViewModel;
import residentevil.services.UserService;
import residentevil.util.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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

    //why tf is the cookie not being set in the browser
    //it is present in the response header and everything looks in order

    //well f me sideways the problem was theres a missing line in the login request header
    //namely - "credentials: 'include'"
    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<UserLoggedViewModel> loginUser(@RequestBody Credentials credentials, HttpServletResponse res) throws Exception {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

        UserDetails userDetails = this.userService.loadUserByUsername(credentials.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        UserLoggedViewModel userLoggedViewModel = this.modelMapper.map(userDetails, UserLoggedViewModel.class);

        Cookie cookie = new Cookie("JWT", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.addCookie(cookie);

        return ResponseEntity.status(200).body(userLoggedViewModel);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<UserLoggedViewModel> authUser(HttpServletRequest request) {
        Cookie jwtCookie = request.getCookies() == null
                ? null
                : Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("JWT"))
                .findFirst()
                .orElse(null);

        UserDetails userDetails = jwtCookie == null
                ? null
                : this.userService.loadUserByUsername(jwtUtil.getUsernameFromToken(jwtCookie.getValue()));

        if (userDetails == null || !this.jwtUtil.validateToken(jwtCookie.getValue(), userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(this.modelMapper.map(userDetails, UserLoggedViewModel.class));
    }
}