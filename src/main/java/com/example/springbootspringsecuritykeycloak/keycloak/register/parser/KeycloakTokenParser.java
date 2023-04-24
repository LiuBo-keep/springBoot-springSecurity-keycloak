package com.example.springbootspringsecuritykeycloak.keycloak.register.parser;

import com.example.springbootspringsecuritykeycloak.keycloak.register.KeycloakUserBo;
import org.keycloak.representations.AccessToken;

/**
 * @author aidan.liu
 */
public interface KeycloakTokenParser {

    /**
     * 解析keycloak颁发token并填充keycloakUserBo
     *
     * @param token          keycloak颁发token
     * @param keycloakUserBo keycloak用户信息
     */
    public abstract void keycloakTokenParsePopulateUserInfo(AccessToken token, KeycloakUserBo keycloakUserBo);
}
