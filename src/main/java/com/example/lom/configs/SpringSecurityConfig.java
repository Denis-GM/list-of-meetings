package com.example.lom.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers("/registration", "/login", "/logout").permitAll()
//                        .requestMatchers("/delete/**").hasRole("ADMIN")
//                        .requestMatchers("/create/**").hasAuthority("CREATOR")
//                        .anyRequest().authenticated())
////                        .anyRequest().permitAll())
//                .formLogin((form) -> form
//                          .loginPage("/login")
//                          .loginProcessingUrl("/login")
//                          .defaultSuccessUrl("/")
//                          .permitAll())
//                .logout()
//                .logoutSuccessUrl("/login.html");
////                .formLogin(Customizer.withDefaults());
//
//        return http.build();
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/registration**",
                        "/js/**",
                        "/css/**",
                        "/img/**").permitAll()
                .anyRequest().authenticated());
        http.formLogin(fL -> fL.loginPage("/login").permitAll());
        http.logout(lOut -> {
            lOut.invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll();
        });
        http.httpBasic(withDefaults());
        return http.build();
    }

}
