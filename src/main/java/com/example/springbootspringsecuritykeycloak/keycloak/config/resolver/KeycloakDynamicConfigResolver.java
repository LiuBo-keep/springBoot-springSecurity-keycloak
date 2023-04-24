package com.example.springbootspringsecuritykeycloak.keycloak.config.resolver;

import com.example.springbootspringsecuritykeycloak.keycloak.config.KeycloakProperties;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author aidan.liu
 */
public class KeycloakDynamicConfigResolver implements KeycloakConfigResolver {

    @Autowired
    private KeycloakProperties keycloakProperties;

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request facade) {
        AdapterConfig adapterConfig = new AdapterConfig();
        adapterConfig.setRealm(keycloakProperties.getRealm());
        adapterConfig.setResource(keycloakProperties.getResource());
        adapterConfig.setAuthServerUrl(keycloakProperties.getAuthServerUrl());
        adapterConfig.setPublicClient(keycloakProperties.getPublicClient());
        return KeycloakDeploymentBuilder.build(adapterConfig);
    }
}
