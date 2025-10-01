package com.techlab.debug;

import com.techlab.user.User;
import com.techlab.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
@RequiredArgsConstructor
@Slf4j
public class DebugController {
    
    private final UserService userService;
    
    @GetMapping("/test-user-creation")
    public String testUserCreation(@RequestParam String email, 
                                  @RequestParam String name, 
                                  @RequestParam String password) {
        try {
            log.info("Testando criação de usuário: {}", email);
            
            // Verificar se usuário já existe
            boolean exists = userService.emailExists(email);
            log.info("E-mail {} já existe: {}", email, exists);
            
            if (exists) {
                return "E-mail já existe: " + email;
            }
            
            // Tentar criar usuário
            User user = userService.registerWithPassword(email, name, password);
            log.info("Usuário criado com sucesso: {}", user);
            
            return "Usuário criado com sucesso: " + user.getEmail() + " | ID: " + user.getId();
            
        } catch (Exception e) {
            log.error("Erro ao criar usuário", e);
            return "Erro: " + e.getMessage() + " | Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "N/A");
        }
    }
    
    @GetMapping("/check-user")
    public String checkUser(@RequestParam String email) {
        try {
            User user = userService.findByEmail(email);
            if (user != null) {
                return "Usuário encontrado: " + user.getEmail() + " | Nome: " + user.getName() + " | ID: " + user.getId();
            } else {
                return "Usuário não encontrado: " + email;
            }
        } catch (Exception e) {
            log.error("Erro ao buscar usuário", e);
            return "Erro: " + e.getMessage();
        }
    }
}