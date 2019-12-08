package org.sse.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.sse.authservice.service.AuthService;

/**
 * @author cbc
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthService authService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("browser")
                .secret(encoder.encode("secret"))
                .authorizedGrantTypes("refresh_token", "password")
                .scopes("ui")
                .and()
                .withClient("auth-service")
                .secret(encoder.encode("secret"))
                .authorizedGrantTypes("refresh_token", "client_credentials")
                .scopes("server")
                .and()
                .withClient("data-service")
                .secret(encoder.encode("secret"))
                .authorizedGrantTypes("refresh_token", "client_credentials")
                .scopes("server")
                .and()
                .withClient("metadata-service")
                .secret(encoder.encode("secret"))
                .authorizedGrantTypes("refresh_token", "client_credentials")
                .scopes("server")
                .and()
                .withClient("model-service")
                .secret(encoder.encode("secret"))
                .authorizedGrantTypes("refresh_token", "client_credentials")
                .scopes("server")
                .and()
                .withClient("train-service")
                .secret(encoder.encode("secret"))
                .authorizedGrantTypes("refresh_token", "client_credentials")
                .scopes("server");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                // redis factory doesn't load configuration in application.properties
                // so one way is not set password or write the creating bean method
                // in which manually set the password
                .tokenStore(new RedisTokenStore(redisConnectionFactory))
                .userDetailsService(authService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
