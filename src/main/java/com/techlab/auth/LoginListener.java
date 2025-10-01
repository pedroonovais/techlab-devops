package com.techlab.auth;

import com.techlab.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private final UserService userService;

    public LoginListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        
        // Login OAuth2 (Google/GitHub)
        if (principal instanceof OAuth2User oAuth2User) {
            log.info("Login OAuth2 realizado com usuário: {}", oAuth2User.getAttributes().get("email"));
            userService.register(oAuth2User);
        } 
        // Login via formulário
        else if (principal instanceof UserDetails userDetails) {
            log.info("Login via formulário realizado com usuário: {}", userDetails.getUsername());
        }
    }
}
