package com.bilgeadam.controller;

import com.bilgeadam.dto.request.UserRegisterRequestDto;
import com.bilgeadam.dto.request.UserUpdateRequestDto;
import com.bilgeadam.dto.response.UserLoginResponseDto;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.service.UserService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.bilgeadam.constants.EndPointList.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PutMapping(UPDATE_DTO)
    public ResponseEntity<User> updateDto(UserUpdateRequestDto dto){
        return ResponseEntity.ok(userService.updateDto(dto));
    }
    @PutMapping(UPDATE_MAPPER)
    public ResponseEntity<User> updateMapper(UserUpdateRequestDto dto){
        return ResponseEntity.ok(userService.updateMapper(dto));
    }
    @PostMapping(REGISTER_MAPPER)
    public ResponseEntity<UserRegisterRequestDto> registerMapper(@RequestBody UserRegisterRequestDto dto){
        return ResponseEntity.ok(userService.registerMapper(dto));
    }
    @PostMapping(REGISTER_DTO)
    public ResponseEntity<UserRegisterRequestDto> registerDto(@RequestBody UserRegisterRequestDto dto){
        return ResponseEntity.ok(userService.registerDto(dto));
    }
    @PostMapping(REGISTER)
    public ResponseEntity<User> register(String name, String surname, String email, String password, String repassword){
        return ResponseEntity.ok(userService.register(name,surname,email,password,repassword));
    }
    @PostMapping(LOGIN_MAPPER)
    public ResponseEntity<UserLoginResponseDto> loginMapper(@RequestBody UserLoginResponseDto dto){
        return ResponseEntity.ok(userService.loginMapper(dto));
    }
    @PostMapping(LOGIN_DTO)
    public ResponseEntity<String> loginDto(@RequestBody UserLoginResponseDto dto){
        return ResponseEntity.ok(userService.loginDto(dto));
    }
    @PostMapping(LOGIN)
    public ResponseEntity<String> login(String email,String password){
        return ResponseEntity.ok(userService.login(email,password));
    }
    @PostMapping(CUSTOM_LOGIN)
    public ResponseEntity customLogin(@RequestBody UserLoginResponseDto dto){
        return userService.customLogin(dto);
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Optional<User>> findById(Integer id){
        return ResponseEntity.ok(userService.findById(id));
    }
    //deleteMapping
    @DeleteMapping(DELETE)
    public ResponseEntity<User> delete(Integer id){
        return ResponseEntity.ok(userService.delete(id));
    }
    @GetMapping(ORDER_BY_USER)
    public ResponseEntity<List<User>> orderByUser(){return ResponseEntity.ok(userService.orderByUser());}
    @GetMapping(FIND_BY_NAME)
    public ResponseEntity<String> findByName(String name){return ResponseEntity.ok(userService.findByName(name));}
    @GetMapping(FIND_BY_NAME_CONTAINING)
    public ResponseEntity<List<User>> findByNameContainingIgnoreCase(String name){return ResponseEntity.ok(userService.findByNameContainingIgnoreCase(name));}
    @GetMapping(EXISTS_ALL_BY_NAME)
    public ResponseEntity<Boolean> existsAllByNameIgnoreCase(String name){return ResponseEntity.ok(userService.existsAllByNameIgnoreCase(name));}
    @GetMapping(FIND_ALL_BY_EMAIL)
    public ResponseEntity<User> findAllByEmailIgnoreCase(String email){return ResponseEntity.ok(userService.findAllByEmailIgnoreCase(email));}
    @GetMapping(FIND_PASSWORD_GREATERTHAN_JQSQL)
    public ResponseEntity<List<User>> findAllPasswordGreaterThanJpql(Integer value){return ResponseEntity.ok(userService.findAllPasswordGreaterThanJpql(value));}
    @GetMapping(FIND_PASSWORD_GREATERTHAN_NATIVE)
    public ResponseEntity<List<User>> findAllPasswordGreaterThanNative(Integer value){return ResponseEntity.ok(userService.findAllPasswordGreaterThanNative(value));}
    @GetMapping(FIND_EMAIL_ENDSWITH)
    public ResponseEntity<List<User>> findAllByEmailEndsWith(String value){return ResponseEntity.ok(userService.findAllByEmailEndsWith(value));}
}