package com.techlab.helper;

import com.techlab.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

/**
 * Classe base para controllers que centraliza funcionalidades comuns
 * como adição de informações do usuário autenticado ao modelo.
 */
public abstract class BaseController {
    
    @Autowired
    private UserService userService;

    /**
     * Adiciona informações do principal (usuário autenticado) ao modelo.
     * Suporta tanto OAuth2 quanto login via formulário.
     * 
     * @param model Model do Spring MVC
     * @param authentication Informações de autenticação
     */
    protected void addPrincipal(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        
        Object principal = authentication.getPrincipal();
        model.addAttribute("user", principal);
        
        // OAuth2 Login (Google/GitHub)
        if (principal instanceof OAuth2User oAuth2User) {
            Object picture = oAuth2User.getAttributes().get("picture");
            Object avatar = picture != null ? picture : oAuth2User.getAttributes().get("avatar_url");
            
            if (avatar != null) {
                model.addAttribute("avatar", avatar.toString());
            }
            
            String name = (String) oAuth2User.getAttributes().getOrDefault("name", 
                    oAuth2User.getAttributes().get("login"));
            model.addAttribute("username", name);
        }
        // Form Login
        else if (principal instanceof UserDetails userDetails) {
            var user = userService.findByEmail(userDetails.getUsername());
            if (user != null) {
                model.addAttribute("username", user.getName());
                if (user.getAvatarUrl() != null) {
                    model.addAttribute("avatar", user.getAvatarUrl());
                }
            }
        }
    }
}