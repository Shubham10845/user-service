package com.userservice.service;

import com.userservice.dto.LoginRequestDTO;
import com.userservice.dto.LoginResponseDTO;
import com.userservice.dto.SignUpRequestDTO;
import com.userservice.dto.UserDTO;
import com.userservice.model.Role;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    EmailService emailService;
    AuthenticationManager authenticationManager;
    TokenService tokenService;
    @Override
    public UserDTO signup(SignUpRequestDTO signUpRequestDTO) {
        if(!emailService.isValidEmail(signUpRequestDTO.getEmail())){
            throw new RuntimeException("Email is not valid");
        }
        User user = from(signUpRequestDTO);
        User savedUser = userRepository.save(user);
        return from(savedUser);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequestDTO.email(), loginRequestDTO.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if(authenticationResponse != null && authenticationResponse.isAuthenticated()){
            return new LoginResponseDTO(authenticationResponse.getName(),tokenService.generateToken(authenticationResponse));
        }else {
            throw new BadCredentialsException("Invalid Username or Password ");
        }
    }

    public User from (SignUpRequestDTO signUpRequestDTO){
        User user = new User();
        Role role = new Role();
        user.setEmail(signUpRequestDTO.getEmail());
        user.setName(signUpRequestDTO.getName());
        user.setHashedPassword(bCryptPasswordEncoder.encode(signUpRequestDTO.getPassword()));
        role.setValue(signUpRequestDTO.getRole());
        user.setRole(role);
        user.setEmailVerified(true);
        return user;
    }
    public UserDTO from(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
