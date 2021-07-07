package com.auto.service.component;


import com.auto.service.entity.Role;
import com.auto.service.entity.User;
import com.auto.service.entity.enums.RoleName;
import com.auto.service.repository.RoleRepository;
import com.auto.service.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Arrays;

@Component
@NoArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.enabled}")
    private boolean initialMode;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(1, RoleName.ROLE_CLIENT));
                roleRepository.save(new Role(2, RoleName.ROLE_ACCOUNTANT));
                roleRepository.save(new Role(3, RoleName.ROLE_ADMIN));
                roleRepository.save(new Role(4, RoleName.ROLE_DIRECTOR));
                roleRepository.save(new Role(5, RoleName.ROLE_EMPLOYER));
                roleRepository.save(new Role(6,RoleName.USER_NOT_ACTIVE));
            }
            if (initialMode) {
                userRepository.save(new User(
                        "+998931852542",
                        passwordEncoder.encode("password"),
                        "Bakhtiyor",
                        roleRepository.findAllByNameIn(
                                Arrays.asList(RoleName.ROLE_ADMIN,
                                        RoleName.ROLE_DIRECTOR, RoleName.ROLE_ACCOUNTANT
                                        , RoleName.ROLE_EMPLOYER, RoleName.ROLE_CLIENT)

                        )));
            }

    }
}
