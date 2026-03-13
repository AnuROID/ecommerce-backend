package com.example.ecommerce.controller;

import com.example.ecommerce.config.JwtConstant;
import com.example.ecommerce.config.JwtProvider;
import com.example.ecommerce.exception.UserException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.request.LoginRequest;
import com.example.ecommerce.response.AuthResponse;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.CustomUserServiceImplementation;
import com.example.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CartService cartService;
    private  CustomUserServiceImplementation customUserServiceImplementation;

    public AuthController(UserRepository userRepository,UserService userService,
                          JwtProvider jwtProvider,
                          PasswordEncoder passwordEncoder,
                          CartService cartService,
                          CustomUserServiceImplementation customUserServiceImplementation){
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
        this.jwtProvider=jwtProvider;
        this.cartService=cartService;
        this.customUserServiceImplementation=customUserServiceImplementation;
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email= user.getEmail();
        String password= user.getPassword();
        String firstName= user.getFirstName();
        String lastName= user.getLastName();

        User isEmailExist = userRepository.findByEmail(email);
        if (isEmailExist != null) throw new UserException("email is already exist");

        User createUser=new User();
        createUser.setEmail(email);
        createUser.setPassword(passwordEncoder.encode(password));
        createUser.setFirstName(firstName);
        createUser.setLastName(lastName);

        User savedUser=userRepository.save(createUser);
        Cart cart=cartService.creatCart(savedUser);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(savedUser.getEmail(), null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token =jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse(token,"signup successfull");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }
    @PostMapping("/signin")
    public  ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        Authentication authentication=authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse(token,"signin successfull");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(username);

        if (userDetails == null) throw new BadCredentialsException("iinnvalid username");
        if (!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("invalid password");

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
