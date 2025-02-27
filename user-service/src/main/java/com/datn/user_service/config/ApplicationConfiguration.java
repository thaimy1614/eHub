package com.datn.user_service.config;

import com.datn.user_service.model.Account;
import com.datn.user_service.model.Permission;
import com.datn.user_service.model.Role;
import com.datn.user_service.repository.AccountRepository;
import com.datn.user_service.repository.PermissionRepository;
import com.datn.user_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final PermissionRepository permissionRepository;

    @Bean
    public ApplicationRunner applicationRunner(PasswordEncoder passwordEncoder) {
        return args -> {
            // Default student role
            if (!roleRepository.existsById("STUDENT")) {
                Set<Permission> studentPerms = Set.of(
                        permissionRepository.save(
                                Permission.builder().name("STUDENT_PERMS").description("All student permissions").build()
                        )
                );
                roleRepository.save(
                        Role.builder().
                                name("STUDENT")
                                .description("Student_role")
                                .permissions(studentPerms)
                                .build()
                );
            }

            // Default teacher role
            if (!roleRepository.existsById("TEACHER")) {
                Set<Permission> instructorPerms = Set.of(
                        permissionRepository.save(
                                Permission.builder().name("TEACHER_PERMS").description("All teacher permissions").build()
                        )
                );
                roleRepository.save(
                        Role.builder().
                                name("TEACHER")
                                .description("Teacher_role")
                                .permissions(instructorPerms)
                                .build()
                );
            }

            // Default admin account
            if (accountRepository.findByEmail("group01se1709@gmail.com").isEmpty()) {
                Set<Permission> adminPerms = Set.of(
                        permissionRepository.save(
                                Permission.builder().name("BLOCK_USER").description("Block account").build()
                        ),
                        permissionRepository.save(
                                Permission.builder().name("DELETE_USER").description("Delete account").build()
                        ),
                        permissionRepository.save(
                                Permission.builder().name("ACCEPT_COURSE").description("Accept course").build()
                        ),
                        permissionRepository.save(
                                Permission.builder().name("ACCEPT_INSTRUCTOR").description("Accept instructor").build()
                        ),
                        permissionRepository.save(
                                Permission.builder().name("ADMIN_PERMS").description("All admin perm").build()
                        )
                );

                // default admin roles
                Set<Role> roles = Set.of(
                        Role.builder()
                                .name("ADMIN")
                                .description("Admin_role")
                                .permissions(adminPerms)
                                .build()
                );

                //admin account
                Account account = Account
                        .builder()
                        .email("group01se1709@gmail.com")
                        .username("admin")
                        .password(passwordEncoder.encode("123456"))
                        .roles(roles)
                        .status(Account.Status.ACTIVATED)
                        .build();
                accountRepository.save(account);
            }
        };
    }
}
