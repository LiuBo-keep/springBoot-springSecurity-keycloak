package com.example.springbootspringsecuritykeycloak.keycloak.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author aidan.liu
 */
@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    /**
     * 是否开启keycloak(默认关闭)
     */
    private Boolean enable = false;

    /**
     * 提供给前端的keycloak访问路径
     */
    private String authenticationPath;

    /**
     * keycloak中超级管理员组
     */
    private String adminGroup;

    /**
     * keycloak租户
     */
    private String realm;

    /**
     * keycloak租户终端
     */
    private String resource;

    /**
     * keycloak服务url
     */
    private String authServerUrl;

    /**
     * keycloak终端类型
     */
    private Boolean publicClient = true;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getAuthenticationPath() {
        return authenticationPath;
    }

    public void setAuthenticationPath(String authenticationPath) {
        this.authenticationPath = authenticationPath;
    }

    public String getAdminGroup() {
        return adminGroup;
    }

    public void setAdminGroup(String adminGroup) {
        this.adminGroup = adminGroup;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAuthServerUrl() {
        return authServerUrl;
    }

    public void setAuthServerUrl(String authServerUrl) {
        this.authServerUrl = authServerUrl;
    }

    public Boolean getPublicClient() {
        return publicClient;
    }

    public void setPublicClient(Boolean publicClient) {
        this.publicClient = publicClient;
    }
}
