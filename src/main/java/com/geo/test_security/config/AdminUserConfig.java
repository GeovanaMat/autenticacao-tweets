package com.geo.test_security.config;

import com.geo.test_security.entities.Role;
import com.geo.test_security.entities.User;
import com.geo.test_security.repository.RoleRepository;
import com.geo.test_security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        var userAdmin = userRepository.findByUsername("admin");
//        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN);
//        userAdmin.ifPresentOrElse(System.out::println, () -> {
//            var user = new User();
//            user.setUsername("admin");
//            user.setPassword(bCryptPasswordEncoder.encode("123"));
//            user.setRole(Set.of(roleAdmin));
//            userRepository.save(user);
//
//        }  );
//

    }
}
