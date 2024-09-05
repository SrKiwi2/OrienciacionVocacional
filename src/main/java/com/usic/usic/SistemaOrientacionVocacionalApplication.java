package com.usic.usic;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.usic.usic.model.IService.IPersonaService;
import com.usic.usic.model.IService.IRolService;
import com.usic.usic.model.IService.IUsuarioService;
import com.usic.usic.model.entity.Persona;
import com.usic.usic.model.entity.Rol;
import com.usic.usic.model.entity.Usuario;

@SpringBootApplication
public class SistemaOrientacionVocacionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaOrientacionVocacionalApplication.class, args);
	}


	@Bean
	ApplicationRunner init(IUsuarioService usuarioService, IPersonaService personaService, IRolService rolService){
		return args -> {
			System.out.println("SISTEMA ORIENTACION VOCACIONAL INICIANDO...");

			String[] roles = {"SUPER USUARIO", "ADMINISTRADOR"};
			Rol[] rolObjects = new Rol[roles.length];
			for (int i = 0; i < roles.length; i++) {
                Rol rol = rolService.buscarPorNombre(roles[i]);
                if (rol == null) {
                    rol = new Rol();
                    rol.setNombre(roles[i]);
					rol.setEstado("ACTIVO");
                    rolService.save(rol);
                }
                rolObjects[i] = rol;
            }

			String[] cis = { "111", "222" };
            String[] nombres = { "PRIMER USUARIO", "SEGUNDO USUARIO" };
            String[] usuarios = { "admin1", "admin2" };
			String[] password = { "123", "456" };
            for (int i = 0; i < cis.length; i++) {
                // Verificar si la persona ya existe
                Persona persona = personaService.validarCI(cis[i]);
                if (persona == null) {
                    persona = new Persona();
                    persona.setNombre(nombres[i]);
                    persona.setPaterno("ApellidoP" + (i + 1));
                    persona.setMaterno("ApellidoM" + (i + 1));
                    persona.setCi(cis[i]);
					persona.setEstado("ACTIVO");
                    personaService.save(persona);
                }

                // Verificar si el usuario ya existe
                Usuario usuario = usuarioService.buscarPorUsuario(usuarios[i]);
                if (usuario == null) {
                    usuario = new Usuario();
                    usuario.setUsername(usuarios[i]);
                    usuario.setPassword(password[i]);
                    usuario.setPersona(persona); // Asociar la persona con el usuario
                    usuario.setRol(rolObjects[i % roles.length]); // Asignar el rol correspondiente
					usuario.setEstado("ACTIVO");
                    usuarioService.save(usuario);
                }
            }
		};
	}

}
