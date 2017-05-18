package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;

/**
 * Created by YS on 2017-05-17.
 */

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfiguration{
}
