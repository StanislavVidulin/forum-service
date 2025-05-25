package ait.cohort55.security;

import ait.cohort55.accounting.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

// ЭТО АВТОРИЗАЦИЯ

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        // permitAll() - разрешено всем, authenticated() - разрешено только залогинившимся
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/account/register", "/forum/posts/**")
                        .permitAll() // ** - сколько угодно слэшей, * - только следующий уровень
                .requestMatchers("/account/user/{login}/role/{role}")
                        .hasRole(Role.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.PATCH, "/account/user/{login}")
                        .access(new WebExpressionAuthorizationManager("#login == authentication.name"))
                .requestMatchers("/forum/post/{author}")
                        .access(new WebExpressionAuthorizationManager("#author == authentication.name"))

                .anyRequest().authenticated());

        return http.build();
    }
}
