package com.bilgeadam.service;

import com.bilgeadam.dto.request.UserRegisterRequestDto;
import com.bilgeadam.dto.request.UserUpdateRequestDto;
import com.bilgeadam.dto.response.UserLoginResponseDto;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.repository.IUserRepository;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.utility.ICrudService;
import com.bilgeadam.utility.enums.ECustomEnum;
import com.bilgeadam.utility.enums.EStatus;
import com.bilgeadam.utility.enums.EUserType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements ICrudService<User,Integer> {
    private final IUserRepository userRepository;
    @Override
    public User save(User user) {
        return null;
    }
    @Override
    public Iterable<User> saveAll(Iterable<User> t) {
        return null;
    }
    @Override
    public User update(User user) {
        return null;
    }
    public User updateDto(UserUpdateRequestDto dto){
        Optional<User> optionalUser = userRepository.findById(dto.getId());
        if(optionalUser.isPresent()){
            optionalUser.get().setName(dto.getName());
            optionalUser.get().setSurname(dto.getSurname());
            optionalUser.get().setEmail(dto.getEmail());
            optionalUser.get().setPhone(dto.getPhone());
            return userRepository.save(optionalUser.get());

        }else{
            throw new NotFoundException("Kullanıcı bulunamadı");
        }
    }
    public User updateMapper(UserUpdateRequestDto dto){
        Optional<User> user = userRepository.findById(dto.getId());
        IUserMapper.INSTANCE.updateUserfromDto(dto,user.get());
        return userRepository.save(user.get());
    }
    //Sadece admin rolüne sahip kişiler bu işlemi gerçekleştirebilir.
    @Override
    public User delete(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            //Optional User'ın için erişip durumu inactive yapmalıyız.
            optionalUser.get().setStatus(EStatus.INACTIVE);
            userRepository.save(optionalUser.get());
            return optionalUser.get();
        }else{
            throw new NullPointerException("Böyle bir kullanıcı yok.");
        }
    }
    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()){
            throw new NullPointerException("Liste boş");
        }
        return userList;
    }
    @Override
    public Optional<User> findById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            return optionalUser;
        }else{
            throw new NullPointerException("Böyle bir kullanıcı yok");
        }
    }
    //basic builder register
    public User register(String name,String surname,String email,String password,String repassword) {
        User user = User.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .password(password)
                .repassword(repassword)
                .build();
        if(!password.equals(repassword) || password.isBlank() || repassword.isBlank()) {
            throw new RuntimeException("Şifreler aynı değildir.");
        }else {
            return userRepository.save(user);
        }
    }
    //basic login
    public String login(String email,String password){
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email,password);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Böyle bir kullanıcı bulunmamaktadır");
        }
        // yapsaydık dönüş tipinde Optional kullanmaya gerek yok
        return "Giriş İşlemi Başarılı";
    }
    //dto register
    public UserRegisterRequestDto registerDto(UserRegisterRequestDto dto){
        User user = User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .repassword(dto.getRepassword())
                .build();
        if(!dto.getPassword().equals(dto.getRepassword())
        || dto.getPassword().isBlank() || dto.getRepassword().isBlank()){
            throw new RuntimeException("Şifreler aynı değildir.");
        }else{
            userRepository.save(user);
        }
        return dto;
    }
    //dto login
    public String loginDto(UserLoginResponseDto dto){
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if(optionalUser.isEmpty()){
            throw new NotFoundException("Email veya şifre bilgisi hatalı");
        }
        return dto.getEmail() + " Giriş İşlemi Başarılı";
    }
    //mapper register
    public UserRegisterRequestDto registerMapper(UserRegisterRequestDto dto){
        User user = IUserMapper.INSTANCE.registerMapper(dto);
        if(userRepository.findByEmailEqualsIgnoreCase(dto.getEmail()).isPresent()){
            throw new RuntimeException("Bu email zaten kayıtlı");
        }
        else if(!dto.getPassword().equals(dto.getRepassword())
        || dto.getPassword().isBlank() || dto.getRepassword().isBlank()){
            throw new RuntimeException("Şifreler aynı değildir.");
        }else if(dto.getEmail().equals("superadmin@mail.com")){
                user.setUserType(EUserType.ADMIN);
                user.setStatus(EStatus.ACTIVE);
        }
        userRepository.save(user);
        return dto;
    }

    //mapper login
    public UserLoginResponseDto loginMapper(UserLoginResponseDto dto){
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if(optionalUser.isEmpty()){
            throw new NotFoundException("Email veya şifre bilgisi hatalı");
        }
        return IUserMapper.INSTANCE.loginMapper(optionalUser.get());
    }

    //custom login --> Arda
    public ResponseEntity customLogin(UserLoginResponseDto dto){
        Map<ECustomEnum, Object> hm = new HashMap<>();
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if(optionalUser.isEmpty()){
            hm.put(ECustomEnum.status,false);
            hm.put(ECustomEnum.message,"Email veya şifre hatalıdır.");
            return new ResponseEntity(hm, HttpStatus.UNAUTHORIZED);

        }else{
            hm.put(ECustomEnum.status,true);
            hm.put(ECustomEnum.result,dto.getEmail());
            hm.put(ECustomEnum.message,"Giriş Başarılı");
            return new ResponseEntity(hm, HttpStatus.OK);
        }
    }

    //Order Username
    public List<User> orderByUser(){
        List<User> userList = userRepository.findAllByOrderByName();
        if(userList.isEmpty()){
            throw new NullPointerException("Kullanıcı Listesi Boş");
        }
        return userList;
    }
    public String findByName(String name){
        if(userRepository.findByName(name).isEmpty()){
            throw new NullPointerException(name +" isimli bir isim bulunmamaktadır.");
        }
        return name + " username'li bir kullanıcı bulunmaktadır.";
    }

    public List<User> findByNameContainingIgnoreCase(String name){
        List<User> userList = userRepository.findByNameContainingIgnoreCase(name);
        if(userList.isEmpty()){
            throw new NullPointerException(name +" isimli bir isim bulunmamaktadır.");
        }
        return userList;
    }
    public Boolean existsAllByNameIgnoreCase(String name){
        return userRepository.existsAllByNameIgnoreCase(name);
    }

    public User findAllByEmailIgnoreCase(String email){
        Optional<User> optionalUser = userRepository.findAllByEmailIgnoreCase(email);
        if(optionalUser.isEmpty()){
            throw new NullPointerException(email + " mailli bir kayıt bulunmamaktadır.");
        }
        return optionalUser.get();
    }
    //passwordGreaterThan JPQL
    public List<User> findAllPasswordGreaterThanJpql(Integer value){
        List<User> userList = userRepository.findAllPasswordGreaterThanJpql(value);
        if(userList.isEmpty()){
            throw new NotFoundException("Böyle bir kullanıcı yoktur.");
        }
        return userList;
    }
    public List<User> findAllPasswordGreaterThanNative(Integer value){
        List<User> userList = userRepository.findAllPasswordGreaterThanNative(value);
        if(userList.isEmpty()){
            throw new NotFoundException("Böyle bir kullanıcı yoktur.");
        }
        return userList;
    }
    public List<User> findAllByEmailEndsWith(String value){
        List<User> userList = userRepository.findAllByEmailEndsWithIgnoreCase(value);
        if(userList.isEmpty()){
            throw new NotFoundException("Böyle bir kullanıcı yoktur.");
        }
        return userList;
    }
}
