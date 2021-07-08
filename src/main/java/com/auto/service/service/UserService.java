package com.auto.service.service;

import com.auto.service.entity.FirebaseToken;
import com.auto.service.entity.Role;
import com.auto.service.entity.ServiceEntityProvider;
import com.auto.service.entity.User;
import com.auto.service.entity.enums.RoleName;
import com.auto.service.payloads.ApiResponse;
import com.auto.service.payloads.ReqPassword;
import com.auto.service.payloads.ReqSignUp;
import com.auto.service.payloads.ResUser;
import com.auto.service.repository.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private MessageSource messageSource;

    private FirebaseRepository firebaseRepository;

    private ServiceEntityRepository serviceRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MessageSource messageSource, FirebaseRepository firebaseRepository, ServiceEntityRepository serviceRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.firebaseRepository = firebaseRepository;
        this.serviceRepository = serviceRepository;
    }

    public ApiResponse addUser(ReqSignUp request) {
        if (request.getId()==null) {
            User user = new User(
                    request.getPhoneNumber(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getFirstName(),
                    roleRepository.findAllByNameIn(
                            Collections.singletonList(RoleName.ROLE_ACCOUNTANT)
            ));
            userRepository.save(user);
            return new ApiResponse("Foydalanuvchi muvoffaqiyatli ro'yxatga olindi", true);
        }else {
            Optional<User> optionalUser = userRepository.findById(request.getId());
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                user.setFirstName(request.getFirstName());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setPhoneNumber(request.getPhoneNumber());
                userRepository.save(user);
                return new ApiResponse("Foydalanuvchi malumotlari muvofaqqiyatli o'zgartirildi.", true);
            }else {
                return new ApiResponse("Bunday telefon raqamli foydalanuvchi mavjud", false);
            }
        }
    }


    public ResponseEntity changePassword(ReqPassword request, User user) {
        if (request.getPassword().equals(request.getPrePassword())) {
            if (checkPassword(request.getOldPassword(), user)) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Parol o'zgartirildi", true));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Hozirgi parol xato", false));
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Yangi va tasdiqlovchi parol mos emas", false));
        }
    }


    private Boolean checkPassword(String oldPassword, User user) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }


    public HttpEntity<?> editUser(ReqSignUp reqUser, User user) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(true);
        user.setFirstName(reqUser.getFirstName());

        if (!user.getPhoneNumber().equals(reqUser.getPhoneNumber())) {
            if (!userRepository.findByPhoneNumber(reqUser.getPhoneNumber()).isPresent()) {
                user.setPhoneNumber(reqUser.getPhoneNumber());
            } else {
                response.setSuccess(false);
                response.setMessage("Phone number is already exist");
            }
        }
        if (response.isSuccess()) {
            response.setMessage(messageSource.getMessage("user.edited", null, LocaleContextHolder.getLocale()));
        } else {
            response.setMessage(messageSource.getMessage("error", null, LocaleContextHolder.getLocale()));
        }
        userRepository.save(user);
        return ResponseEntity.ok(response);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public ResUser getResUser(User user){
        ResUser resUser=new ResUser();
        resUser.setId(user.getId());
        resUser.setFirstName(user.getFirstName());
        resUser.setPhoneNumber(user.getPhoneNumber());
        List<String> stringList=new ArrayList<>();
        for (Role role : user.getRoles()) {
            stringList.add(role.getName().name());
        }
        resUser.setRoleNameList(stringList);
        return resUser;
    }

//    public ApiResponse saveFirebase(User user, String firebase) {
//        try {
//
//            System.out.println(firebase);
//            FirebaseToken firebaseToken = firebaseRepository.save(new FirebaseToken(firebase));
//            List<FirebaseToken> firebaseToken1 = user.getFirebaseToken();
//            firebaseToken1.add(firebaseToken);
//            user.setFirebaseToken(firebaseToken1);
//            userRepository.save(user);
//            for (ServiceEntityProvider companies : serviceRepository.findByUser(user)) {
//                companies.setTokenList(firebaseToken1);
//                serviceRepository.save(companies);
//            }
//
//            return new ApiResponse("save",true);
//        }catch (Exception e){
//            return new ApiResponse(e.getMessage(), true);
//
//        }
//
//    }

    public ApiResponse saveServiceToken(ServiceEntityProvider entityProvider, String firebase) {
        FirebaseToken firebaseToken = firebaseRepository.save(new FirebaseToken(firebase));
        List<FirebaseToken> firebaseToken1 = entityProvider.getFirebaseToken();
        firebaseToken1.add(firebaseToken);
        entityProvider.setFirebaseToken(firebaseToken1);
        serviceRepository.save(entityProvider);
        return new ApiResponse("Token Saved",true);
    }

    public ApiResponse saveUserToken(User user, String firebase) {
        FirebaseToken firebaseToken = firebaseRepository.save(new FirebaseToken(firebase));
        List<FirebaseToken> firebaseToken1 = user.getFirebaseToken();
        firebaseToken1.add(firebaseToken);
        user.setFirebaseToken(firebaseToken1);
        userRepository.save(user);
        return new ApiResponse("Token Saved",true);
    }
}
