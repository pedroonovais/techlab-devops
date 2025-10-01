package com.techlab.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Data
@Table(name = "techlabuser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String avatarUrl;
    
    private String password;
    
    public User() {
    }

    // Construtor para Google
    public User(OAuth2User principal) {
        var attrs = principal.getAttributes();

        this.email = (String) attrs.get("email");
        this.name = (String) attrs.getOrDefault("name", (String) attrs.get("login"));
        Object avatar = attrs.get("picture") != null
                ? attrs.get("picture")
                : attrs.get("avatar_url");
        this.avatarUrl = avatar != null ? avatar.toString() : null;
    }

    // 🔹 Construtor genérico usado pelo UserService
    public User(String email, String name, String avatarUrl) {
        this.email = email;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }
    
    //  Método estático para criar usuário com senha (login via formulário)
    public static User withPassword(String email, String name, String password) {
        User user = new User();
        user.email = email;
        user.name = name;
        user.password = password;
        return user;
    }
}