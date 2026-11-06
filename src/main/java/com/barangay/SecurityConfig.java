package com.barangay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

  @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

  @Bean InMemoryUserDetailsManager users(@Value("${ADMIN_USER:admin}") String user,
                                         @Value("${ADMIN_PASSWORD}") String pass,
                                         PasswordEncoder enc){
    UserDetails admin = User.withUsername(user).password(enc.encode(pass)).roles("ADMIN").build();
    return new InMemoryUserDetailsManager(admin);
  }

  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(a -> a
        .requestMatchers("/login", "/verify/**", "/qr/**", "/css/**", "/error").permitAll()
        .anyRequest().authenticated())
      .formLogin(f -> f.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
      .logout(l -> l.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").permitAll());
    return http.build();
  }
}
