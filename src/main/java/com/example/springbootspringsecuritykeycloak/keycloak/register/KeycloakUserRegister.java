package com.example.springbootspringsecuritykeycloak.keycloak.register;

/**
 * @author aidan.liu
 */
public interface KeycloakUserRegister {

    /**
     * 注册keycloak用户
     *
     * @param keycloakUserBo 用户信息
     */
    public void registerUser(KeycloakUserBo keycloakUserBo);
}
