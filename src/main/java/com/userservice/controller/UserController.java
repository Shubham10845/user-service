package com.userservice.controller;

import com.userservice.dto.*;
import com.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO)throws RuntimeException{
        UserDTO userDTO = userService.signup(signUpRequestDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        LoginResponseDTO loginResponseDTO = userService.login(loginRequestDTO);
        return new ResponseEntity<>(loginResponseDTO,HttpStatus.OK);
    }
    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDTO> validateToken(@PathVariable("token") String token){
        UserDTO userDTO = userService.validateToken(token);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDTO logoutRequestDTO){
        String logoutResponse = userService.logout(logoutRequestDTO);
        return new ResponseEntity<>(logoutResponse,HttpStatus.OK);
    }
    @GetMapping("/profile")
    public String grtProfile(){
        return "User Profile";
    }

}
