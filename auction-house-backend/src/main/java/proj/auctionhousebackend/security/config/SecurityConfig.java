package proj.auctionhousebackend.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import proj.auctionhousebackend.exception.AccessDeniedHandlerBean;
import proj.auctionhousebackend.repository.CredentialRepository;
import proj.auctionhousebackend.security.filter.AuthorizationFilter;
import proj.auctionhousebackend.security.filter.LoginFilter;
import proj.auctionhousebackend.security.service.UserDetailsServiceBean;
import proj.auctionhousebackend.security.util.SecurityConstants;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            LoginFilter loginFilter,
            AuthorizationFilter authorizationFilter,
            AccessDeniedHandler accessDeniedHandler
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> handler.accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(SecurityConstants.AUTH_PATHS_TO_SKIP).permitAll()
                        .requestMatchers(SecurityConstants.SWAGGER_PATHS_TO_SKIP).permitAll()
                        .anyRequest().authenticated())
                .addFilter(loginFilter)
                .addFilterAfter(authorizationFilter, LoginFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(SecurityConstants.PASSWORD_STRENGTH);
    }

    @Bean
    public UserDetailsService userDetailsService(CredentialRepository userRepository) {
        return new UserDetailsServiceBean(userRepository);
    }

    @Bean
    public AuthorizationFilter authorizationManager(ObjectMapper objectMapper) {
        return new AuthorizationFilter(objectMapper);
    }

    @Bean
    public LoginFilter loginFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        return new LoginFilter(objectMapper, authenticationManager);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return new AccessDeniedHandlerBean(objectMapper);
    }
}
