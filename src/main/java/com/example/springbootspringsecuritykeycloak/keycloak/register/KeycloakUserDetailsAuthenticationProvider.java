package com.example.springbootspringsecuritykeycloak.keycloak.register;

import com.example.springbootspringsecuritykeycloak.keycloak.register.parser.KeycloakTokenParseHelper;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;

/**
 * @author aidan.liu
 */
public class KeycloakUserDetailsAuthenticationProvider extends KeycloakAuthenticationProvider {

    @Autowired
    private KeycloakTokenParseHelper keycloakTokenParseHelper;
    private KeycloakUserRegister keycloakUserRegister;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KeycloakUserBo keycloakUserBo = keycloakTokenParseHelper.assembleKeycloakTokenUserInfo(authentication);
        keycloakUserRegister.registerUser(keycloakUserBo);
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) authentication;
        Set<String> roles = keycloakAuthenticationToken.getAccount().getRoles();
        roles.addAll(keycloakUserBo.getGrantedAuthorityList());
        return super.authenticate(authentication);
    }


    public KeycloakUserRegister getKeycloakUserRegister() {
        return keycloakUserRegister;
    }

    public void setKeycloakUserRegister(KeycloakUserRegister keycloakUserRegister) {
        this.keycloakUserRegister = keycloakUserRegister;
    }
}
