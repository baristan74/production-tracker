package com.dev.productionTracker.service;


import com.dev.productionTracker.dto.AuthRequest;
import com.dev.productionTracker.dto.CreateUserRequest;
import com.dev.productionTracker.model.User;
import com.dev.productionTracker.repository.UserRepository;
import com.dev.productionTracker.utils.results.*;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService, @Lazy AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Result createUser(CreateUserRequest createUserRequest) {
        try{
            if(validateRegister(createUserRequest)){
                if(userRepository.findByUsername(createUserRequest.username()).isPresent()){
                    return new ErrorResult("Username already exists!");
                }
                User newUser = User.builder()
                        .firstName(createUserRequest.firstName())
                        .lastName(createUserRequest.lastName())
                        .username(createUserRequest.username())
                        .password(passwordEncoder.encode(createUserRequest.password()))
                        .authorities(createUserRequest.authorities())
                        .accountNonExpired(true)
                        .isEnabled(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .build();
                userRepository.save(newUser);
                return new SuccessResult("User created successfully");
            }else{
                return new ErrorResult("Please fill in all required fields!");
            }
        }catch (Exception e){
            return new ErrorResult("Error creating user: " + e.getMessage());
        }
    }

    public DataResult<String> login(AuthRequest authRequest) {
        try{
            if(validateLogin(authRequest)){
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
                if(authentication.isAuthenticated()){
                    String token = jwtService.generateToken(authRequest.username());
                    return new SuccessDataResult<>(token,"Login successful");
                }else{
                    return new ErrorDataResult<>("invalid username {} or password {}" + authRequest.username() + authRequest.password());
                }

            }else{
                return new ErrorDataResult<>("username or password cannot be null");
            }
        }catch (Exception e){
            return new ErrorDataResult<>(null,"Login failed: " + e.getMessage());
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    private boolean validateLogin(AuthRequest authRequest) {
        if (authRequest == null) {
            return false;
        }

        if (authRequest.username() == null || authRequest.username().trim().isEmpty()) {
            return false;
        }

        if(authRequest.password() == null || authRequest.password().trim().isEmpty()){
            return false;
        }

        return true;
    }

    private boolean validateRegister(CreateUserRequest createUserRequest) {
        if (createUserRequest == null) {
            return false;
        }

        if (createUserRequest.firstName() == null || createUserRequest.firstName().trim().isEmpty()) {
            return false;
        }
        if(createUserRequest.lastName() == null || createUserRequest.lastName().trim().isEmpty()){
            return false;
        }
        if (createUserRequest.username() == null || createUserRequest.username().trim().isEmpty()) {
            return false;
        }
        if (createUserRequest.password() == null || createUserRequest.password().trim().isEmpty()) {
            return false;
        }
        if (createUserRequest.authorities() == null || createUserRequest.authorities().getValue().isEmpty()) {
            return false;
        }

        return true;
    }

}
