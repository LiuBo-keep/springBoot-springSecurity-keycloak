package com.example.springbootspringsecuritykeycloak.keycloak.register.parser;

import com.example.springbootspringsecuritykeycloak.keycloak.register.KeycloakUserBo;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author aidan.liu
 */
@Component
public class KeycloakTokenParseHelper implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

    public KeycloakTokenParseHelper() {
    }

    public KeycloakTokenParseHelper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 组装keycloak令牌用户信息
     *
     * @param authentication 认证上下文信息
     * @return keycloak用户信息
     */
    public KeycloakUserBo assembleKeycloakTokenUserInfo(Authentication authentication) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) authentication;
        AccessToken token = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();

        KeycloakUserBo keycloakUserBo = new KeycloakUserBo();

        Map<String, KeycloakTokenParser> keycloakTokenParserMap = applicationContext.getBeansOfType(KeycloakTokenParser.class);

        keycloakTokenParserMap.forEach((k, v) -> {
            v.keycloakTokenParsePopulateUserInfo(token, keycloakUserBo);
        });
        return keycloakUserBo;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

