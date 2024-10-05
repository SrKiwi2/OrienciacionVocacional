package com.usic.usic.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desactiva protección CSRF para pruebas, no recomendado en producción
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/orientacion_vocacional",
                                                "/assets_registro/**", 
                                                "/assets/**", 
                                                "/iniciar-sesion", 
                                                "/vista-administrador",
                                                "/guardar_estudiante_inicio",
                                                "/pre_test",
                                                "/guardar_respuesta",
                                                "/interpretar_respuestas",
                                                "/vista_resultado_pre_test_ia",
                                                "/total-respuestas-si").permitAll() // Permitir acceso sin autenticación
                    .anyRequest().authenticated() // Requiere autenticación para las demás rutas
                )
                .oauth2Login(oauth2 -> oauth2
                    .loginPage("/orientacion_vocacional")
                    .defaultSuccessUrl("/oauth2/success", true)

                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/orientacion_vocacional")
                    .permitAll()
                )
                .build();
    }
}
