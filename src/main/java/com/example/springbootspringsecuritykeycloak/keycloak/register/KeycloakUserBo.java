package com.example.springbootspringsecuritykeycloak.keycloak.register;

import java.util.List;

/**
 * @author aidan.liu
 */
public class KeycloakUserBo {
    /**
     * 用户名称
     */
    private String username;

    /**
     * 名字
     */
    private String firstName;

    /**
     * 姓
     */
    private String lastName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 安全上下文角色组
     * 主要用于初始化SpringSecurity上下文权限
     */
    private List<String> grantedAuthorityList;

    /**
     * 用户酒店
     */
    private String resort;

    /**
     * 用户具体来源(保留)
     */
    private String ldapSource;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getGrantedAuthorityList() {
        return grantedAuthorityList;
    }

    public void setGrantedAuthorityList(List<String> grantedAuthorityList) {
        this.grantedAuthorityList = grantedAuthorityList;
    }

    public String getResort() {
        return resort;
    }

    public void setResort(String resort) {
        this.resort = resort;
    }

    public String getLdapSource() {
        return ldapSource;
    }

    public void setLdapSource(String ldapSource) {
        this.ldapSource = ldapSource;
    }
}
