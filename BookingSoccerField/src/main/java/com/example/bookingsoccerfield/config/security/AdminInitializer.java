package com.example.bookingsoccerfield.config;

import com.example.bookingsoccerfield.models.entity.User;
import com.example.bookingsoccerfield.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createAdminAccount() {
        return args -> {
            String adminEmail = "admin@gmail.com";

            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("admin123")); // đổi mật khẩu mạnh hơn nếu cần
                admin.setRole(User.Role.ADMIN);
                admin.setName("Super Admin");
                admin.setPhone("0123456789");
                admin.setEnabled(true); // ❗❗ Quan trọng để không bị chặn
                admin.setCreatedAt(LocalDateTime.now());
                userRepository.save(admin);
                System.out.println("✅ Admin account created: " + adminEmail);
            }
        };
    }
}
