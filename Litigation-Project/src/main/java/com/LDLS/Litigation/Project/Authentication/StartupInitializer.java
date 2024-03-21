package com.LDLS.Litigation.Project.Authentication;
import com.LDLS.Litigation.Project.Authentication.Roles.ERole;
import com.LDLS.Litigation.Project.Authentication.Roles.Role;
import com.LDLS.Litigation.Project.Authentication.Roles.RoleRepository;
import com.LDLS.Litigation.Project.Authentication.Users.Users;
import com.LDLS.Litigation.Project.Authentication.Users.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
@Component
@Slf4j
public class StartupInitializer implements ApplicationRunner {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createSuperUser();
    }

    private void createSuperUser() {
        log.info("------->>>>>> CREATING SUPERUSER <<<<<<-------------");

        if (userRepository.existsByUsername("superuser")) {
            log.info("username exists!.Skipping creation");
        }else if (userRepository.existsByEmail("superuser@example.com")) {
            log.info("Superuser with email superuser@example.com already exists. Skipping creation.");
        }else{
//            Role superUserRole = roleRepository.findByName(ERole.ROLE_SUPERUSER.toString())
            Role superUserRole = new Role();
            superUserRole.setName(ERole.ROLE_SUPERUSER.toString());
            roleRepository.save(superUserRole);
            log.info("Role found!");
            Users superUser = new Users();
            superUser.setUsername("superuser");
            superUser.setEmail("superuser@example.com");
            superUser.setPassword(passwordEncoder.encode("LITIGATION"));
            superUser.setRoles(Collections.singleton(superUserRole));
            superUser.setAcctActive(true);
            superUser.setAcctLocked(false);
            superUser.setStatus("ACTIVE");
            log.info("username = superuser");
            log.info( "The superuser  log in password is = LITIGATION ");
            superUser.setVerifiedFlag('Y');
            superUser.setFirstLogin('Y');
            superUser.setVerifiedOn(new Date());
            superUser.setEmail("superuser@example.com");
            superUser.setFirstName("Super");
            superUser.setLastName("User");
            superUser.setPhoneNo("2547123456789");

            userRepository.save(superUser);
        }
    }
}
