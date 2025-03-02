package ma.abdellah.hospitalapp.security;

import lombok.AllArgsConstructor;
import ma.abdellah.hospitalapp.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
//cette annotation permet de proteger les methodes de l'application en utilisant les annotations @PreAuthorize et @PostAuthorize
public class SecurityConfig {

    private PasswordEncoder passwordEncoder;
    private UserDetailServiceImpl userDetailServiceImpl;
    //@Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    //@Bean
    //cette fonction permet de gerer l'authentification des utilisateurs en utilisant un utilisateur en memoire(inMemoryAuthentication) il existe d'autre methode tels que jdbc, ldap, etc
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        //la fonction va recuperer l'objet passwordEncoder qui est un objet de type PasswordEncoder et qui est injecter dans la classe HospitalAppApplication grace a l'annotation @Bean
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("ADMIN","USER").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/",true).permitAll());
        httpSecurity.rememberMe(Customizer.withDefaults());
        httpSecurity.authorizeHttpRequests(ar -> ar.requestMatchers("/webjars/**","/h2-console/**").permitAll());
        //httpSecurity.authorizeHttpRequests(ar -> ar.requestMatchers("/admin/**").hasRole("ADMIN"));
        //httpSecurity.authorizeHttpRequests(ar -> ar.requestMatchers("/user/**").hasRole("USER"));
        httpSecurity.authorizeHttpRequests(ar -> ar.anyRequest().authenticated());
        httpSecurity.exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler()));
        httpSecurity.userDetailsService(userDetailServiceImpl);
        return httpSecurity.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/notAuthorized");
        return accessDeniedHandler;
    }
}
