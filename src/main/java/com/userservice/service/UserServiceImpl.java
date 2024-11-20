package com.userservice.service;

import com.userservice.constants.ApplicationConstants;
import com.userservice.dto.LoginRequestDTO;
import com.userservice.dto.LoginResponseDTO;
import com.userservice.dto.SignUpRequestDTO;
import com.userservice.dto.UserDTO;
import com.userservice.model.Role;
import com.userservice.model.Token;
import com.userservice.model.User;
import com.userservice.repository.TokenRepository;
import com.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    EmailService emailService;
    AuthenticationManager authenticationManager;
    TokenService tokenService;
    TokenRepository tokenRepository;
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
        return Optional.ofNullable(authenticationResponse)
                .filter(Authentication::isAuthenticated)
                .map(auth->new LoginResponseDTO(auth.getName(),generateAndSaveJwtToken(auth,loginRequestDTO.email())))
                .orElseThrow(()->new BadCredentialsException("Invalid Username Or Password"));
    }

    @Override
    public UserDTO validateToken(String token) {
        return tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(token,false,new Date())
                .map(Token::getUser)
                .map(this::from)
                .orElseThrow(()->new RuntimeException("Invalid User"));
    }

    private String generateAndSaveJwtToken(Authentication authentication, String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    String jwtToken = tokenService.generateToken(authentication);
                    Token token = new Token();
                    token.setUser(user);
                    token.setValue(jwtToken);
                    token.setExpiryAt(new Date(System.currentTimeMillis() + ApplicationConstants.EXPIRATION_TIME));
                    return tokenRepository.save(token).getValue();
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found for the provided email: " + email));
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
