package com.techlab.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
    }
    
    public User register(OAuth2User principal) {
        var attrs = principal.getAttributes();

        String email = attrs.get("email") != null
                ? (String) attrs.get("email")
                : attrs.get("login") + "@github.com";

        String name = attrs.get("name") != null
                ? (String) attrs.get("name")
                : (String) attrs.get("login");

        String avatarUrl = attrs.get("picture") != null
                ? (String) attrs.get("picture")
                : (String) attrs.get("avatar_url");

        return userRepository
                .findByEmail(email)
                .orElseGet(() -> userRepository.save(new User(email, name, avatarUrl)));
    }
    
    // 🔹 Registrar usuário via formulário
    public User registerWithPassword(String email, String name, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }
        
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.withPassword(email, name, encodedPassword);
        return userRepository.save(user);
    }
    
    // 🔹 Buscar usuário por e-mail
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    
    // 🔹 Verificar se e-mail existe
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
