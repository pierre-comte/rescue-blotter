package lu.sgbt.maks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // authorize requests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/webjars/**").permitAll() // Allow access to login and static resources
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                // form login configuration
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/", true) // Redirect to home page after successful login
                        .permitAll()
                )
                // Logout configuration
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))  // Support GET method
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
        // CSRF is enabled by default in Spring Security
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // todo: for demo only
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(@Value("${config.user.login}") String username, @Value("${config.user.password}") String password) {
        // in memory user for demo
        UserDetails user = User.withUsername(username)
                .password(password)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
