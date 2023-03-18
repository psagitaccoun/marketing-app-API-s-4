package blogapp.myblog4.controller;


import blogapp.myblog4.entity.Role;
import blogapp.myblog4.entity.User;
import blogapp.myblog4.payload.JWTAuthResponse;
import blogapp.myblog4.payload.LoginDto;
import blogapp.myblog4.payload.SignUpDto;
import blogapp.myblog4.repository.RoleRepository;
import blogapp.myblog4.repository.UserRepository;
import blogapp.myblog4.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));

    }

    @PostMapping("/signUp")
    public ResponseEntity<String> register(@RequestBody SignUpDto signUpDto){

        if (userRepository.existsByUsername(signUpDto.getUsername())){
        return new ResponseEntity<>("username already exists",HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("email already exists",HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Optional<Role> roleAdmin = roleRepository.findByName("ROLE_USER");
        Role role = roleAdmin.get();
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);



        return new ResponseEntity<>("Registered successfully!!!",HttpStatus.OK);
    }
}
