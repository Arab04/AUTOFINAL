package com.auto.service.security;

import com.auto.service.entity.ServiceEntityProvider;
import com.auto.service.entity.User;
import com.auto.service.entity.enums.RoleName;
import com.auto.service.payloads.ApiResponse;
import com.auto.service.payloads.ApiResponseModel;
import com.auto.service.payloads.CodeVerification;
import com.auto.service.payloads.ReqSignUp;
import com.auto.service.repository.RoleRepository;
import com.auto.service.repository.ServiceEntityRepository;
import com.auto.service.repository.UserRepository;
import com.auto.service.sms.SmsSender;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    private final Random random;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final SmsSender sender;
    private final ServiceEntityRepository repository;

    public AuthService(@Lazy UserRepository userRepository, Random random, SmsSender sender, PasswordEncoder encoder, RoleRepository roleRepository, ServiceEntityRepository repository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.sender = sender;
        this.random = random;
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isPresent()) {
            return userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }else {
            return repository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
    }


    public UserDetails loadUserById(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User id not found: " + userId));
        }
        else {
            return repository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Service id not found: " + userId));
        }
    }


    public ApiResponse register(ReqSignUp reqSignUp) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(reqSignUp.getPhoneNumber());
        if (optionalUser.isPresent()) {
            return new ApiResponse("phone.number.exist",false);
        } else {
            Integer code = random.nextInt(100000);
            User user = new User();
            user.setPhoneNumber(reqSignUp.getPhoneNumber());
            user.setPassword(encoder.encode(reqSignUp.getPassword()));
            user.setFirstName(reqSignUp.getFirstName());
            user.setRoles(roleRepository.findAllByName(RoleName.USER_NOT_ACTIVE));
            user.setVerificationCode(code.toString());

            //Sending verification code
            sender.smsSender(user.getPhoneNumber(),code.toString());

            userRepository.save(user);
            //userRepository.save(user);
            return new ApiResponse("Send verification code: ",  true);
        }
    }

    public ApiResponseModel verifyCode(CodeVerification verification,User user) {
        if (verification.getCode().equals(user.getVerificationCode())){
            user.setRoles(roleRepository.findAllByName(RoleName.ROLE_CLIENT));
            userRepository.save(user);
            return new ApiResponseModel(true,"User registered successfully",null);
        }
        else {
            return new ApiResponseModel(false,"Wrong code please send right code",null);
        }
    }

}
