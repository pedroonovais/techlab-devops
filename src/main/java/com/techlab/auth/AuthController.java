package com.techlab.auth;

import com.techlab.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(){
        return "logout";
    }
    
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }
    
    @PostMapping("/register")
    public String processRegister(@Valid RegisterForm form, 
                                BindingResult result, 
                                Model model,
                                RedirectAttributes redirectAttributes) {
        
        log.info("Tentativa de registro para: {}", form.getEmail());
        
        // Validar se senhas coincidem
        if (!form.isPasswordMatch()) {
            log.warn("Senhas não coincidem para: {}", form.getEmail());
            result.rejectValue("confirmPassword", "error.confirmPassword", "Senhas não coincidem");
        }
        
        // Verificar se e-mail já existe
        if (userService.emailExists(form.getEmail())) {
            log.warn("Tentativa de registro com e-mail já existente: {}", form.getEmail());
            result.rejectValue("email", "error.email", "E-mail já cadastrado");
        }
        
        if (result.hasErrors()) {
            log.warn("Erros de validação no registro: {}", result.getAllErrors());
            model.addAttribute("registerForm", form);
            return "register";
        }
        
        try {
            userService.registerWithPassword(form.getEmail(), form.getName(), form.getPassword());
            log.info("Usuário registrado com sucesso: {}", form.getEmail());
            redirectAttributes.addFlashAttribute("message", "Registro realizado com sucesso! Faça seu login.");
            return "redirect:/login";
        } catch (Exception e) {
            log.error("Erro ao registrar usuário: {}", form.getEmail(), e);
            result.reject("error.register", "Erro ao realizar cadastro: " + e.getMessage());
            model.addAttribute("registerForm", form);
            return "register";
        }
    }

}