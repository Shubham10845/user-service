package com.userservice.controller;

import com.userservice.dto.SignUpRequestDTO;
import com.userservice.dto.UserDTO;
import com.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
