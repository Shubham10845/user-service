package com.userservice.controller;

import com.userservice.dto.LoginRequestDTO;
import com.userservice.dto.LoginResponseDTO;
import com.userservice.dto.SignUpRequestDTO;
import com.userservice.dto.UserDTO;
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
    @GetMapping("/profile")
    public String grtProfile(){
        return "User Profile";
    }

}
