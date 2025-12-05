package br.edu.ifpb.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.authorizeHttpRequests(auth -> auth
               // liberar login, recursos estáticos, página principal e erro
               .requestMatchers("/login", "/logout", "/css/**", "/js/**", "/images/**", "/favicon.ico", "/", "/paginaPrincipal", "/error", "/access-denied").permitAll()
               
               // apenas ADMIN pode criar/editar/salvar/deletar (rotas explícitas)
               .requestMatchers("/usuarios/cadastrar", "/usuarios/salvar", "/usuarios/editar/**", "/usuarios/deletar/**").hasRole("ADMIN")
               .requestMatchers("/perfis/cadastrar",   "/perfis/salvar",   "/perfis/editar/**",   "/perfis/deletar/**").hasRole("ADMIN")
               .requestMatchers("/emprestimos/cadastrar","/emprestimos/salvar","/emprestimos/editar/**","/emprestimos/deletar/**").hasRole("ADMIN")
               .requestMatchers("/roles/cadastrar",    "/roles/salvar",    "/roles/editar/**",    "/roles/deletar/**").hasRole("ADMIN")
               
               // demais requisições exigem autenticação (leitura/listas)
               .anyRequest().authenticated()
           ).formLogin(
                   form -> form
                   .loginPage("/")
                   .defaultSuccessUrl("/paginaPrincipal", true)
                   .permitAll()
           ).logout(logout -> logout
                   .logoutUrl("/logout")
                   .logoutSuccessUrl("/?logout")
                   .permitAll()
           ).exceptionHandling(exception -> exception
                   .accessDeniedPage("/access-denied")
           );

       return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

}

